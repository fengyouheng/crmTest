package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.SystemLog;
import cn.wolfcode.crm.query.SystemLogQueryObject;

import java.util.List;

public interface SystemLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemLog record);

    SystemLog selectByPrimaryKey(Long id);

    List<SystemLog> selectAll();

    int updateByPrimaryKey(SystemLog record);

	long count(SystemLogQueryObject qo);

	List query(SystemLogQueryObject qo);
}