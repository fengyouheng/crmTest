package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.EmployeeQueryObject;

import java.util.List;

public interface IEmployeeService {
    void delete(Long id);
    void save(Employee employee);
    Employee get(Long id);
    List<Employee> query();
    void update(Employee employee);
    Pagination list(EmployeeQueryObject qo);
    void leave(Long id);
    List<Role> queryRoleByEmployee(Long employeeId);
    Employee queryByUsername(String username);
	List<Employee> queryEmployee();
}
