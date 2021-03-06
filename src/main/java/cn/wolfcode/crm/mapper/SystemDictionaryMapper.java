package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.query.SystemDictionaryQueryObject;

import java.util.List;

public interface SystemDictionaryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionary record);

    SystemDictionary selectByPrimaryKey(Long id);

    List<SystemDictionary> selectAll();

    int updateByPrimaryKey(SystemDictionary record);
    
    long count(SystemDictionaryQueryObject qo);

    List<Employee> query(SystemDictionaryQueryObject qo);
}