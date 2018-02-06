package cn.wolfcode.crm.web.controller;

import java.util.List;

import cn.wolfcode.crm.domain.Department;
import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryService;
import cn.wolfcode.crm.util.PermissionName;
import cn.wolfcode.crm.web.result.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemDictionary")
public class SystemDictionaryController {

    @Autowired
    private ISystemDictionaryService systemDictionaryService;

    @RequestMapping("/index")
    @PermissionName("×ÖµäÊ×Ò³")
    @RequiresPermissions("systemDictionary:index")
    public String index(){
        return "systemDictionary";
    }

    @RequestMapping("/list")
    @PermissionName("×ÖµäÁÐ±í")
    @RequiresPermissions("systemDictionary:list")
    @ResponseBody
    public Pagination list(SystemDictionaryQueryObject qo){
        return systemDictionaryService.list(qo);
    }

    @RequestMapping("/save")
    @PermissionName("×ÖµäÐÂÔö")
    @RequiresPermissions("systemDictionary:save")
    @ResponseBody
    public AjaxResult save(SystemDictionary systemDictionary){
        try {
            systemDictionaryService.save(systemDictionary);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "±£´æ³É¹¦");
    }

    @RequestMapping("/update")
    @PermissionName("×Öµä±à¼­")
    @RequiresPermissions("systemDictionary:update")
    @ResponseBody
    public AjaxResult update(SystemDictionary systemDictionary){
        try {
            systemDictionaryService.update(systemDictionary);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "±à¼­³É¹¦");
    }
    
    @RequestMapping("/delete")
    @PermissionName("×ÖµäÉ¾³ý")
    @RequiresPermissions("systemDictionary:update")
    @ResponseBody
    public AjaxResult delete(Long id){
        try {
            systemDictionaryService.delete(id);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "É¾³ý³É¹¦");
    }
    @RequestMapping("/query")
    @ResponseBody
    public List<SystemDictionary> query(){
    	List<SystemDictionary> querys = systemDictionaryService.query();
    	for (SystemDictionary a : querys) {
    		System.out.println(a);
		}
        return systemDictionaryService.query();
    }
}
