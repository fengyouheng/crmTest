package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.domain.Role;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.RoleQueryObject;
import cn.wolfcode.crm.service.IRoleService;
import cn.wolfcode.crm.util.PermissionName;
import cn.wolfcode.crm.web.result.AjaxResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @RequestMapping("/index")
    @PermissionName("员工首页")
    @RequiresPermissions("role:index")
    public String index(){
        return "role";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Pagination list(RoleQueryObject qo){
        return roleService.list(qo);
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResult save(Role role){
        try {
            roleService.save(role);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "保存成功");
    }

    @RequestMapping("/update")
    @ResponseBody
    public AjaxResult update(Role role){
        try {
            roleService.update(role);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "保存成功");
    }
    
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResult delete(Long id){
        try {
            roleService.delete(id);
        } catch (Exception e) {
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "删除成功");
    }

    @RequestMapping("/queryPermissionsByRole")
    @ResponseBody
    public Pagination queryPermissionsByRole(Long roleId){
        return roleService.queryPermissionsByRole(roleId);
    }

    @RequestMapping("/query")
    @ResponseBody
    public List<Role> query(RoleQueryObject qo){
        return roleService.query();
    }

    @RequestMapping("/addMenu")
    @ResponseBody
    public AjaxResult addMenu(@RequestParam(value="ids[]",required=false) ArrayList<Long> ids, Long roleId){
        try{
            if(ids==null){
                ids = new ArrayList<Long>();
            }
            roleService.addMenu(ids,roleId);
            return new AjaxResult(true,"添加菜单成功");
        }catch(Exception e){
            e.printStackTrace();
           return new AjaxResult("添加菜单失败,请联系管理员！");
        }
    }
}
