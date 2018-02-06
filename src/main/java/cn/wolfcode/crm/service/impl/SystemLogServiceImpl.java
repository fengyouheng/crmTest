package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.mapper.SystemLogMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemLogQueryObject;
import cn.wolfcode.crm.service.ISystemLogService;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class SystemLogServiceImpl implements ISystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    @Override
    public void save(SystemLog SystemLog) {
    	systemLogMapper.insert(SystemLog);
    }

    @Override
    public SystemLog get(Long id) {
        return systemLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemLog> query() {
        return systemLogMapper.selectAll();
    }
    
    public Pagination list(SystemLogQueryObject qo) {
    	
    	long total = systemLogMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, systemLogMapper.query(qo));
    }

	public void writeLog(JoinPoint joinPoint) throws Exception {
		System.out.println(joinPoint.getSignature().getName());
		System.out.println(Arrays.toString(joinPoint.getArgs()));
		
		SystemLog log = new SystemLog();
		log.setEmployeeId(((Employee)SecurityUtils.getSubject().getPrincipal()).getId());
		log.setOperateTime(new Date());
		log.setFunction(joinPoint.getSignature().getName());
		log.setIp(((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr());
		String params = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(joinPoint.getArgs());
		log.setParams(params);
		this.save(log);
	}
}
