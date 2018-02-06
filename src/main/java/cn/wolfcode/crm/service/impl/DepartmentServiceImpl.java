package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.mapper.DepartmentMapper;

import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.DepartmentQueryObject;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void delete(Long id) {
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(Department department) {
        departmentMapper.insert(department);
    }

    @Override
    public Department get(Long id) {
        return departmentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Department> query() {
        return departmentMapper.selectAll();
    }

    @Override
    public void update(Department department) {
        departmentMapper.updateByPrimaryKey(department);
    }
    
    public Pagination list(DepartmentQueryObject qo) {
        long total = departmentMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, departmentMapper.query(qo));
    }


	@Override
	public String selectManagerByEmployeeId(Long id) {
		return departmentMapper.selectManagerByEmployeeId(id);
	}
}
