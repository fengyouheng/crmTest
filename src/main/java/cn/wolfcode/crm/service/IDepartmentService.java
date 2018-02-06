package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.DepartmentQueryObject;

import java.util.List;

public interface IDepartmentService {
    void delete(Long id);
    void save(Department department);
    Department get(Long id);
    List<Department> query();
    void update(Department department);
    Pagination list(DepartmentQueryObject qo);
	String selectManagerByEmployeeId(Long id);
}
