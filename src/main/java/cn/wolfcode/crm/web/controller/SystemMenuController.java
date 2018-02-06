package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemMenu;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemMenuQueryObject;
import cn.wolfcode.crm.service.ISystemMenuService;
import cn.wolfcode.crm.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/systemMenu")
public class SystemMenuController {

	@Autowired
	private ISystemMenuService systemMenuService;

	@RequestMapping("/index")
	public String index(){
		return "systemMenu";
	}
	@RequestMapping("/list")
	@ResponseBody
	public Pagination list(SystemMenuQueryObject qo){
		return systemMenuService.queryPage(qo);
	}
	@RequestMapping("/save")
	@ResponseBody
	public AjaxResult save(SystemMenu systemMenu){
		try{
			systemMenuService.insert(systemMenu);
			return new AjaxResult(true,"保存成功");
		}catch(Exception e){
			e.printStackTrace();
			return new AjaxResult("保存失败,请联系管理员！");
		}
	}
	@RequestMapping("/update")
	@ResponseBody
	public AjaxResult update(SystemMenu systemMenu){
		try{
			systemMenuService.updateByPrimaryKey(systemMenu);
			return new AjaxResult(true,"更新成功");
		}catch(Exception e){
			e.printStackTrace();
			return new AjaxResult("更新失败,请联系管理员！");
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public AjaxResult delete(Long systemMenuId){
		try{
			systemMenuService.deleteByPrimaryKey(systemMenuId);
			return new AjaxResult(true,"删除成功");
		}catch(Exception e){
			return new AjaxResult("删除失败,请联系管理员！");
		}
	}
	@RequestMapping("/queryTree")
	@ResponseBody
	public List<SystemMenu> queryTree(){
		List<SystemMenu> result = systemMenuService.queryTree();
		return result;
	}
	@RequestMapping("/queryForRole")
	@ResponseBody
	public List queryForRole(){
		List<SystemMenu> result = systemMenuService.queryForRole();
		return result;
	}
	@RequestMapping("/queryMenuIdsListForRole")
	@ResponseBody
	public List<Long> queryMenuIdsListForRole(Long roleId){
		List<Long> result = systemMenuService.queryMenuIdsListForRole(roleId);
		return result;
	}
	@RequestMapping("/indexMenu")
	@ResponseBody
	public List<SystemMenu> indexMenu(){
		List<SystemMenu> result = systemMenuService.indexMenu();
		return result;
	}
}
