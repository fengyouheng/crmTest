package cn.wolfcode.crm.web.controller;

import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemLogQueryObject;
import cn.wolfcode.crm.service.ISystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/systemLog")
public class SystemLogController {

    @Autowired
    private ISystemLogService systemLogService;

    @RequestMapping("/index")
    public String index(){
        return "systemLog";
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public Pagination list(SystemLogQueryObject qo){
        return systemLogService.list(qo);
    }   
}
