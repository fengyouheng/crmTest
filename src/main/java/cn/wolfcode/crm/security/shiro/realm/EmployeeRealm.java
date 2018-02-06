package cn.wolfcode.crm.security.shiro.realm;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.service.IEmployeeService;
import cn.wolfcode.crm.service.IPermissionService;
import cn.wolfcode.crm.service.IRoleService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class EmployeeRealm extends AuthorizingRealm  {

    private IEmployeeService employeeService;
    private IRoleService roleService;
    private IPermissionService permissionService;

    public void setEmployeeService(IEmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    public void setPermissionService(IPermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        Employee employee = employeeService.queryByUsername(usernamePasswordToken.getUsername());
        if(employee == null){
            return null;
        }
        return new SimpleAuthenticationInfo(employee, employee.getPassword(), this.getName());
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Employee employee = (Employee) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if(employee.getAdmin()){
            info.addRole("ADMIN");
            info.addStringPermission("*:*");
            return info;
        }

        List<String> roles = roleService.queryByEmployee(employee);
        List<String> permissions = permissionService.queryByEmployee(employee);
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }
}
