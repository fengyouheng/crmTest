package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Long id);

    List<Employee> selectAll();

    int updateByPrimaryKey(Employee record);

    long count(EmployeeQueryObject qo);

    List<Employee> query(EmployeeQueryObject qo);
    void insertRelation(@Param("employeeId") Long employeeId, @Param("roleId") Long roleId);
    List<Role> queryRoleByEmployee(Long employeeId);
    void deleteRelation(Long employeeId);

    Employee queryByUsername(String username);

	List<Employee> queryEmployee();
}
