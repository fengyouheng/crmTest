package cn.wolfcode.crm.service;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryQueryObject;

import java.util.List;

public interface ISystemDictionaryService {
    void delete(Long id);
    void save(SystemDictionary systemDictionary);
    SystemDictionary get(Long id);
    List<SystemDictionary> query();
    void update(SystemDictionary systemDictionary);
    Pagination list(SystemDictionaryQueryObject qo);
}
