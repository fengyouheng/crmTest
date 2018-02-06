package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.DepartmentQueryObject;
import cn.wolfcode.crm.service.IDepartmentService;
import cn.wolfcode.crm.web.result.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @RequestMapping("/index")
	public String index(){
		return "department";
	}
    
    @RequestMapping("/query")
    @ResponseBody
    public List<Department> query(){
        return departmentService.query();
    }
    
    @RequestMapping("/selectManagerByEmployeeId")
    @ResponseBody
    public String selectManagerByEmployeeId(Long id){
    	return departmentService.selectManagerByEmployeeId(id);
    }
    
    @RequestMapping("/list")  //翻页查询	
	@ResponseBody
	public Pagination list(DepartmentQueryObject qo){
		Pagination pagination = departmentService.list(qo);
		List<Department> list = departmentService.query();
    	for (Department department : list) {
			System.out.println(department.toString());
		}
		return pagination;
	}
    
    @RequestMapping("/save") 
	@ResponseBody
	public AjaxResult save(Department department){
		 try {
			 departmentService.save(department);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}
	
	@RequestMapping("/update")  
	@ResponseBody
	public AjaxResult update(Department department){
		 try {
			 departmentService.update(department);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"修改成功");
	}
	
	@RequestMapping("/delete")  
	@ResponseBody
	public AjaxResult leave(Long id){
		 try {
			 departmentService.delete(id);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"离职成功");
	}
}
