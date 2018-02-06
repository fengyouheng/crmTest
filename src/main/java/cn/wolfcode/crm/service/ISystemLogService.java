package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.mapper.SystemLogMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemLogQueryObject;

import java.util.List;

import org.aspectj.lang.JoinPoint;

public interface ISystemLogService {
    void save(SystemLog systemLogM);
    SystemLog get(Long id);
    List<SystemLog> query();
    Pagination list(SystemLogQueryObject qo);
    
    void writeLog(JoinPoint joinPoint) throws Exception;
}
