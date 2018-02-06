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
     // ��ȡ���иý�ɫ��Ȩ��
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
    	// ȥ��������ϵ��ɾ���м�������
        roleMapper.deleteRelation(role.getId());
        roleMapper.updateByPrimaryKey(role);
     // ��ȡ���иý�ɫ��Ȩ��
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
    	//���м��Ĺ�ϵɾ��
        roleMapper.deleteMenuRelation(roleId);
      //ѭ��ids����,���м������ϵ
        for (Long systemMenuId : systemMenuIds) {
            roleMapper.insertMeneRelation(systemMenuId, roleId);
        }
    }

    @Override
    public List<String> queryByEmployee(Employee employee) {
        return roleMapper.queryByEmployee(employee);
    }
}
