package cn.wolfcode.crm.security.shiro.filter;

import cn.wolfcode.crm.domain.Employee;
import cn.wolfcode.crm.domain.SystemMenu;
import cn.wolfcode.crm.service.ISystemMenuService;
import cn.wolfcode.crm.util.MenuUtil;
import lombok.Setter;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AajxFormAuthenticationFilter extends FormAuthenticationFilter {
    @Setter
    private ISystemMenuService systemMenuService;

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        Employee currentUser = (Employee) subject.getPrincipal();
        //___________________________________
        //登陆成功
        List<SystemMenu> menuList = systemMenuService.indexMenu();
        System.out.println(menuList);
        //若不是管理员,则需要过滤掉当前用户没有的菜单
        if (!currentUser.getAdmin()) {
            List<Long> userMenuIds = systemMenuService.queryMenuIds(currentUser.getId());
            MenuUtil.filterMenu(menuList, userMenuIds);
        }
        Session session = subject.getSession();
        session.setAttribute("indexMenu", menuList);
        //___________________________________
        System.out.println("登陆成功");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("{\"success\":true,\"msg\":\"登入成功\"}");
        out.flush();
        out.close();
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"密码错误\"}");
            } else if ("UnknownAccountException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"账号不存在\"}");
            } else if ("LockedAccountException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"账号被锁定\"}");
            } else if ("AuthenticationException".equals(message)) {
                out.println("{\"success\":false,\"msg\":\"认证失败\"}");
            } else {
                out.println("{\"success\":false,\"msg\":\"未知错误\"}");
            }
            out.flush();
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return true;
    }
}