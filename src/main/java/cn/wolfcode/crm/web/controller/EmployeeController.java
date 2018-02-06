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
    @PermissionName("Ա����ҳ")
    @RequiresPermissions("employee:index")
    public String index(){
        return "employee";
    }

    @RequestMapping("/list")
    @PermissionName("Ա���б�")
    @RequiresPermissions("employee:list")
    @ResponseBody
    public Pagination list(EmployeeQueryObject qo){
        return employeeService.list(qo);
    }

    @RequestMapping("/save")
    @PermissionName("Ա������")
    @RequiresPermissions("employee:save")
    @ResponseBody
    public AjaxResult save(Employee employee){
        try {
            employeeService.save(employee);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "����ɹ�");
    }

    @RequestMapping("/update")
    @PermissionName("Ա���༭")
    @RequiresPermissions("employee:update")
    @ResponseBody
    public AjaxResult update(Employee employee){
        try {
            employeeService.update(employee);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "����ɹ�");
    }

    @RequestMapping("/queryEmployee")
    @ResponseBody
    public List<Employee> queryEmployee(){
        return employeeService.queryEmployee();
    }
    
    @RequestMapping("/leave")
    @PermissionName("Ա����ְ")
    @RequiresPermissions("employee:leave")
    @ResponseBody
    public AjaxResult leave(Long id){
        try {
            employeeService.leave(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "��ְ�ɹ�");
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
        return new AjaxResult(true, "ɾ���ɹ�");
    }

    @RequestMapping("/queryRoleByEmployee")
    @ResponseBody
    public List<Role> queryRoleByEmployee(Long employeeId){
        return employeeService.queryRoleByEmployee(employeeId);
    }
}
