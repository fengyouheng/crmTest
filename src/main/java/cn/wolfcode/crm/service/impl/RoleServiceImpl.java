package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.RoleMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.RoleQueryObject;
import cn.wolfcode.crm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Role role) {
        roleMapper.insert(role);
     // 获取所有该角色的权限
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRelation(role.getId(), permission.getId());
        }
    }

    @Override
    public Role get(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Role> query() {
        return roleMapper.selectAll();
    }

    @Override
    public void update(Role role) {
    	// 去除关联关系，删除中间表的数据
        roleMapper.deleteRelation(role.getId());
        roleMapper.updateByPrimaryKey(role);
     // 获取所有该角色的权限
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRelation(role.getId(), permission.getId());
        }
    }

    @Override
    public Pagination list(RoleQueryObject qo) {
        long total = roleMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, roleMapper.query(qo));
    }

    @Override
    public Pagination queryPermissionsByRole(Long roleId) {
        List<Permission> permissions = roleMapper.queryPermissionsByRole(roleId);
        return new Pagination(Long.MAX_VALUE, permissions);
    }

    @Override
    public void addMenu(List<Long> systemMenuIds, Long roleId) {
    	//把中间表的关系删除
        roleMapper.deleteMenuRelation(roleId);
      //循环ids集合,往中间表插入关系
        for (Long systemMenuId : systemMenuIds) {
            roleMapper.insertMeneRelation(systemMenuId, roleId);
        }
    }

    @Override
    public List<String> queryByEmployee(Employee employee) {
        return roleMapper.queryByEmployee(employee);
    }
}
