package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.query.DepartmentQueryObject;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Department record);

    Department selectByPrimaryKey(Long id);

    List<Department> selectAll();

    int updateByPrimaryKey(Department record);
    
    long count(DepartmentQueryObject qo);

   	List<Department> query(DepartmentQueryObject qo);

	String selectManagerByEmployeeId(Long id);
}