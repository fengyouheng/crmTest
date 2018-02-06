package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.PermissionQueryObject;

import java.util.List;

public interface IPermissionService {
    void save(Permission permission);
    Permission get(Long id);
    List<Permission> query();
    Pagination list(PermissionQueryObject qo);
    void load();
    List<String> queryByEmployee(Employee employee);
}
