package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.SystemMenu;
import cn.wolfcode.crm.mapper.SystemMenuMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.SystemMenuQueryObject;
import cn.wolfcode.crm.service.ISystemMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class SystemMenuServiceImpl implements ISystemMenuService {

	@Autowired
	private SystemMenuMapper systemMenuMapper;

	public int deleteByPrimaryKey(Long id) {
		return systemMenuMapper.deleteByPrimaryKey(id);
	}

	public int insert(SystemMenu record) {
		return systemMenuMapper.insert(record);
	}

	public SystemMenu selectByPrimaryKey(Long id) {
		return systemMenuMapper.selectByPrimaryKey(id);
	}

	public List<SystemMenu> selectAll() {
		return systemMenuMapper.selectAll();
	}

	public int updateByPrimaryKey(SystemMenu record) {
		return systemMenuMapper.updateByPrimaryKey(record);
	}

	@Override
	public Pagination queryPage(SystemMenuQueryObject qo) {
		long count = systemMenuMapper.queryPageCount(qo);
		if(count<=0){
			return new Pagination(0, Collections.EMPTY_LIST);
		}
		return new Pagination(count,systemMenuMapper.queryPageDataResult(qo));
	}

	@Override
	public List<SystemMenu> queryTree() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<SystemMenu> queryForRole() {
		return systemMenuMapper.queryTree();
	}

	@Override
	public List<Long> queryMenuIdsListForRole(Long roleId) {
		return systemMenuMapper.systemMenuMapper(roleId);
	}

	@Override
	public List<SystemMenu> indexMenu() {
		return systemMenuMapper.queryTree();
	}
    @Override
    public List<Long> queryMenuIds(Long id) {
        return systemMenuMapper.queryMenuIds(id);
    }
}
