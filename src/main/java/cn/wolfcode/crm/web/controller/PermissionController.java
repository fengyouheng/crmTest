package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.PermissionQueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.web.result.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @RequestMapping("/index")
    public String index(){
        return "permission";
    }

    @RequestMapping("/list")
    @ResponseBody
    public Pagination list(PermissionQueryObject qo){
        return permissionService.list(qo);
    }

    @RequestMapping("/load")
    @ResponseBody
    public AjaxResult load(){
        try{
            permissionService.load();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult(e.getMessage());
        }
        return new AjaxResult(true, "º”‘ÿ≥…π¶");
    }

}
