package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.mapper.EmployeeMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import lombok.Setter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public void delete(Long id) {
        employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Employee employee) {
        employee.setInputTime(new Date());
        employee.setPassword(Employee.DEFAULT_PASSWORD);
        employee.setState(Employee.IN_JOB);
        employeeMapper.insert(employee);
        for (Role role : employee.getRoles()) { // 处理关联关系
            employeeMapper.insertRelation(employee.getId(), role.getId());
        }

    }

    @Override
    public Employee get(Long id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> query() {
        return employeeMapper.selectAll();
    }

    @Override
    public void update(Employee employee) {
    	 // 去除关系
        employeeMapper.deleteRelation(employee.getId());
        employeeMapper.updateByPrimaryKey(employee);
        // 新建关系
        for (Role role : employee.getRoles()) {
            employeeMapper.insertRelation(employee.getId(), role.getId());
        }
    }

    @Override
    public Pagination list(EmployeeQueryObject qo) {
    	
    	Subject subject = SecurityUtils.getSubject();
    	Employee currentEmployee = ((Employee)subject.getPrincipal());
    	
    	if (!currentEmployee.getAdmin()) {
    		qo.setEmployeeId(currentEmployee.getId());
		}
    	
    	long total = employeeMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, employeeMapper.query(qo));
    }

    @Override
    public void leave(Long id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        if(employee == null){
            throw new RuntimeException("没该员工");
        }
        if(Employee.LEAVE == employee.getState()){
            throw new RuntimeException("该员工离职");
        }
        employee.setState(Employee.LEAVE);
        update(employee);
    }

    @Override
    public List<Role> queryRoleByEmployee(Long employeeId) {
        return employeeMapper.queryRoleByEmployee(employeeId);
    }

    @Override
    public Employee queryByUsername(String username) {
        return employeeMapper.queryByUsername(username);
    }

	@Override
	public List<Employee> queryEmployee() {
		// TODO Auto-generated method stub
		return employeeMapper.queryEmployee();
	}
}
