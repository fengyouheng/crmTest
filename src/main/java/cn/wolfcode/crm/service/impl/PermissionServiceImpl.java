package cn.wolfcode.crm.service.impl;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.Permission;
import cn.wolfcode.crm.mapper.PermissionMapper;
import cn.wolfcode.crm.pagination.Pagination;
import cn.wolfcode.crm.query.PermissionQueryObject;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.util.PermissionName;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;

@Service
public class PermissionServiceImpl implements IPermissionService, ApplicationContextAware {

    @Autowired
    private PermissionMapper permissionMapper;

    private ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void save(Permission permission) {
        permissionMapper.insert(permission);
    }

    @Override
    public Permission get(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Permission> query() {
        return permissionMapper.selectAll();
    }


    @Override
    public Pagination list(PermissionQueryObject qo) {
        long total = permissionMapper.count(qo);
        if(total == 0){
            return new Pagination(total, Collections.EMPTY_LIST);
        }
        return new Pagination(total, permissionMapper.query(qo));
    }

    @Override
    public void load() {
        List<Permission> permissions = query();
        Set<String> permissonResources = new HashSet<>();
        for (Permission permission : permissions) {
            permissonResources.add(permission.getResource());
        }

        RequestMappingHandlerMapping handlerMapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        Collection<HandlerMethod> handlerMethods = handlerMapping.getHandlerMethods().values();
        for (HandlerMethod handlerMethod : handlerMethods) {
            PermissionName permissionNameAnno = handlerMethod.getMethodAnnotation(PermissionName.class);
            RequiresPermissions requiresPermissionsAnno = handlerMethod.getMethodAnnotation(RequiresPermissions.class);
            if(permissionNameAnno != null && requiresPermissionsAnno != null){
                String name = permissionNameAnno.value();
                String resource = StringUtils.join(requiresPermissionsAnno.value(), ",");
                if(!permissonResources.contains(resource)){
                    Permission permission = new Permission();
                    permission.setName(name);
                    permission.setResource(resource);
                    save(permission);
                }
            }
        }
    }

    @Override
    public List<String> queryByEmployee(Employee employee) {
        return permissionMapper.queryByEmployee(employee);
    }

}
