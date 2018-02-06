package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemDictionaryItem;
import cn.wolfcode.crm.mapper.SystemDictionaryItemMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemDictionaryItemQueryObject;
import cn.wolfcode.crm.service.ISystemDictionaryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemDictionaryItemServiceImpl implements ISystemDictionaryItemService {

    @Autowired
    private SystemDictionaryItemMapper systemDictionaryItemMapper;

    @Override
    public void delete(Long id) {
    	systemDictionaryItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void save(SystemDictionaryItem systemDictionaryItem) {
    	systemDictionaryItemMapper.insert(systemDictionaryItem);
    }

    @Override
    public SystemDictionaryItem get(Long id) {
        return systemDictionaryItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SystemDictionaryItem> query() {
        return systemDictionaryItemMapper.selectAll();
    }

    @Override
    public void update(SystemDictionaryItem systemDictionaryItem) {
    	systemDictionaryItemMapper.updateByPrimaryKey(systemDictionaryItem);
    }
    
    public Pagination list(SystemDictionaryItemQueryObject qo) {
        long total = systemDictionaryItemMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, systemDictionaryItemMapper.query(qo));
    }

	@Override
	public List queryBySystemDictionaryId(Long id) {
		return systemDictionaryItemMapper.queryBySystemDictionaryId(id);
	}

}
