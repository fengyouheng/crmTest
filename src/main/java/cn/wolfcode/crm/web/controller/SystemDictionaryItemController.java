package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryItemQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import cn.wolfcode.crm.web.result.AjaxResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/systemDictionaryItem")
public class SystemDictionaryItemController {

    @Autowired
    private ISystemDictionaryItemService systemDictionaryItemService;

    @RequestMapping("/index")
	public String index(){
		return "systemDictionaryItem";
	}
    
    @RequestMapping("/query")
    @ResponseBody
    public List<SystemDictionaryItem> query(){
        return systemDictionaryItemService.query();
    }
    
    @RequestMapping("/list")  //翻页查询	
	@ResponseBody
	public Pagination list(SystemDictionaryItemQueryObject qo){
		Pagination pagination = systemDictionaryItemService.list(qo);
		return pagination;
	}
    
    @RequestMapping("/save") 
	@ResponseBody
	public AjaxResult save(SystemDictionaryItem systemDictionaryItem){
		 try {
			 systemDictionaryItemService.save(systemDictionaryItem);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"保存成功");
	}
	
	@RequestMapping("/update")  
	@ResponseBody
	public AjaxResult update(SystemDictionaryItem systemDictionaryItem){
		 try {
			 systemDictionaryItemService.update(systemDictionaryItem);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"修改成功");
	}
	
	@RequestMapping("/delete")  
	@ResponseBody
	public AjaxResult leave(Long id){
		 try {
			 systemDictionaryItemService.delete(id);
		} catch (Exception e) {
			return new AjaxResult(e.getMessage());
		}
		return new AjaxResult(true,"离职成功");
	}
	
	@RequestMapping("/queryBySystemDictionaryId")
	@ResponseBody
	public List queryBySystemDictionaryId(Long id){
		return systemDictionaryItemService.queryBySystemDictionaryId(id);
	}
}
