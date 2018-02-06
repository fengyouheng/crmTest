package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.EmployeeQueryObject;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.util.PermissionName;
import cn.wolfcode.crm.web.result.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @RequestMapping("/index")
    @PermissionName("员工首页")
    @RequiresPermissions("employee:index")
    public String index(){
        return "employee";
    }

    @RequestMapping("/list")
    @PermissionName("员工列表")
    @RequiresPermissions("employee:list")
    @ResponseBody
    public Pagination list(EmployeeQueryObject qo){
        return employeeService.list(qo);
    }

    @RequestMapping("/save")
    @PermissionName("员工新增")
    @RequiresPermissions("employee:save")
    @ResponseBody
    public AjaxResult save(Employee employee){
        try {
            employeeService.save(employee);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "保存成功");
    }

    @RequestMapping("/update")
    @PermissionName("员工编辑")
    @RequiresPermissions("employee:update")
    @ResponseBody
    public AjaxResult update(Employee employee){
        try {
            employeeService.update(employee);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "保存成功");
    }

    @RequestMapping("/queryEmployee")
    @ResponseBody
    public List<Employee> queryEmployee(){
        return employeeService.queryEmployee();
    }
    
    @RequestMapping("/leave")
    @PermissionName("员工离职")
    @RequiresPermissions("employee:leave")
    @ResponseBody
    public AjaxResult leave(Long id){
        try {
            employeeService.leave(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "离职成功");
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(Long id){
        try {
            employeeService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "删除成功");
    }

    @RequestMapping("/queryRoleByEmployee")
    @ResponseBody
    public List<Role> queryRoleByEmployee(Long employeeId){
        return employeeService.queryRoleByEmployee(employeeId);
    }
}
