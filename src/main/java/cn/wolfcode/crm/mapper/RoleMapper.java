package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.RoleQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    Role selectByPrimaryKey(Long id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    long count(RoleQueryObject qo);

    List<Role> query(RoleQueryObject qo);

    void insertRelation(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);

    void deleteRelation(Long roleId);

    List<Permission> queryPermissionsByRole(Long roleId);

    void insertMeneRelation(@Param("systemMenuId")Long systemMenuId, @Param("roleId")Long roleId);

    void deleteMenuRelation(Long roleId);

    List<String> queryByEmployee(Employee employee);
}
