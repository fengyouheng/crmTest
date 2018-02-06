package cn.wolfcode.crm.web.controller;

import com.alibaba.druid.support.json.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HandlerMethod method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // e.printStackTrace();
        ResponseBody responseBodyAnnotation = method.getMethodAnnotation(ResponseBody.class);
        if(responseBodyAnnotation == null){
            request.setAttribute("msg", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(request, response);
            return;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("total", 0);
        result.put("rows", Collections.EMPTY_LIST);
        result.put("success", false);
        result.put("msg", e.getMessage());
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSONUtils.toJSONString(result));
    }
}
