package cn.wolfcode.crm.mapper;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.query.SystemDictionaryItemQueryObject;

import java.util.List;

public interface SystemDictionaryItemMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SystemDictionaryItem record);

    SystemDictionaryItem selectByPrimaryKey(Long id);

    List<SystemDictionaryItem> selectAll();

    int updateByPrimaryKey(SystemDictionaryItem record);
    
    long count(SystemDictionaryItemQueryObject qo);

    List<SystemDictionaryItem> query(SystemDictionaryItemQueryObject qo);

	List queryBySystemDictionaryId(Long id);
}