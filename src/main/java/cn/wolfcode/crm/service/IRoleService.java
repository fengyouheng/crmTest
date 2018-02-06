package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.RoleQueryObject;

import java.util.ArrayList;
import java.util.List;

public interface IRoleService {
    void delete(Long id);
    void save(Role role);
    Role get(Long id);
    List<Role> query();
    void update(Role role);
    Pagination list(RoleQueryObject qo);
    Pagination queryPermissionsByRole(Long roleId);
    void addMenu(List<Long> systemMenuIds, Long roleId);
    List<String> queryByEmployee(Employee employee);
}
