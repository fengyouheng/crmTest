package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemDictionary;
import cn.wolfcode.crm.mapper.SystemDictionaryMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemDictionaryServiceImpl implements ISystemDictionaryService {

    @Autowired
    private SystemDictionaryMapper systemDictionaryMapper;

    @Override
    public void delete(Long id) {
    	systemDictionaryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(SystemDictionary SystemDictionary) {
    	systemDictionaryMapper.insert(SystemDictionary);
    }

    @Override
    public SystemDictionary get(Long id) {
        return systemDictionaryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionary> query() {
        return systemDictionaryMapper.selectAll();
    }

    @Override
    public void update(SystemDictionary SystemDictionary) {
    	systemDictionaryMapper.updateByPrimaryKey(SystemDictionary);
    }

    @Override
    public Pagination list(SystemDictionaryQueryObject qo) {
        long total = systemDictionaryMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, systemDictionaryMapper.query(qo));
    }
}
