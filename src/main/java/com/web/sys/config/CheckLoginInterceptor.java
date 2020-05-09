package com.web.sys.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.sys.utils.E;
import com.web.sys.utils.Redis;
import com.web.sys.utils.T;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.web.sys.bean.SysUser;

import java.util.Map;

/**
 *
 */
@Component
public class CheckLoginInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(CheckLoginInterceptor.class);
    ThreadLocal<Object> threadLocal = new ThreadLocal<>();
    /**
     * 请求前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, Authorization");
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/login");
            return false;
        }
        threadLocal.set(System.currentTimeMillis());
        return true;
    }

    /**
     * 请求后
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        SysUser user = (SysUser) request.getSession().getAttribute("user");
        if(user != null && modelAndView != null){
            modelAndView.addObject("user",user);
            modelAndView.addObject("isLogin",true);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

//        log.info(msg);

    }

    private void writeLog(HttpServletRequest request, HttpServletResponse response){

    }

}