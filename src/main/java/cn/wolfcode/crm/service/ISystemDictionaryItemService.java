package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryItemQueryObject;

import java.util.List;

public interface ISystemDictionaryItemService {
    void delete(Long id);
    void save(SystemDictionaryItem systemDictionaryItem);
    SystemDictionaryItem get(Long id);
    List<SystemDictionaryItem> query();
    void update(SystemDictionaryItem systemDictionaryItem);
    Pagination list(SystemDictionaryItemQueryObject qo);
	List queryBySystemDictionaryId(Long id);
}
