package com.web.sys.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.sys.bean.SysUser;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Aspect
@Component
public class WebLogAspect {
    private static Logger log = LoggerFactory.getLogger(WebLogAspect.class);
    private ThreadLocal<Object> threadLocal = new ThreadLocal<>();
    private final ObjectMapper mapper;

    @Autowired
    public WebLogAspect(ObjectMapper mapper) {
        this.mapper = mapper;
    }


    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void webLog() {
    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        threadLocal.set(System.currentTimeMillis());
    }

//    @Around("webLog()")
//    public Object around(ProceedingJoinPoint pjp){
//        String classAndMethodName = null;
//        Method currentMethod = null;
//
//        try {
//            currentMethod = this.getCurrentMethod(pjp);
//            classAndMethodName = this.getCurrentCompleteMethodName(pjp);
//        } catch (Throwable e) {
//            log.error("初始化日志记录信息时出错", e);
////            return pjp.proceed();
//        }
//
//        // 处理入参
//        this.processBefore(pjp, classAndMethodName);
//
//        Object result = null;
//        try {
//            // 调用目标方法
//            result = pjp.proceed();
//        } catch (Throwable e) {
//            // 目标方法异常了
//            log.info("end执行方法：{}发生异常，异常简述：{}", classAndMethodName, e.getMessage());
////            throw e;
//        }
//
//        // 处理返回值
//        processReturnValue(result, currentMethod, classAndMethodName);
//
//        return result;
//    }

    @AfterReturning(returning = "respbody", pointcut = "webLog()")
    public void doAfterReturning(Object respbody) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        long timeStart = (long)threadLocal.get();
        long time = (System.currentTimeMillis() - timeStart);
        String ip = request.getRemoteAddr();
        String path = request.getContextPath();
        String username = "None";
//        SysUser user = (SysUser)Redis.get(request.getHeader(E.SessionName));
        SysUser user = (SysUser)(request.getSession().getAttribute("user"));
        if(user != null){
            username = user.getUsername();
        }

        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
        String requestUri = request.getRequestURI();//得到请求的资源
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
//        String remoteSysUser = request.getRemoteSysUser();
        String method = request.getMethod();//得到请求URL地址时使用的方法
        String pathInfo = request.getPathInfo();
        String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
        String localName = request.getLocalName();//获取WEB服务器的主机名
        String ips =  request.getHeader("X-Forwarded-For");
        Map<String,String[]> parameterMap = request.getParameterMap();
//        String paramString = JSONObject.valueToString(parameterMap);
//        int statusCode = response.getStatus();
//        String msg = String.format(
//                " %s@%s %s %s %s  %sms        parameters:%s  response:%s"
//                ,username,remoteAddr,method,statusCode,requestUri,time,paramString,respbody
//        );
//        log.info(msg);
        if(user!=null){

            // 访问日志入库
        }
    }



    /**
     * 得到当前方法的对象引用
     * @param pjp
     * @return
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    private Method getCurrentMethod(ProceedingJoinPoint pjp) throws NoSuchMethodException, SecurityException {
        MethodSignature mig = (MethodSignature) pjp.getSignature();
        return pjp.getTarget().getClass().getMethod(mig.getName(), mig.getParameterTypes());
    }
    /**
     * 得到完整的方法名
     * @param pjp
     * @return
     */
    private String getCurrentCompleteMethodName(ProceedingJoinPoint pjp) {
        return pjp.getTarget().getClass() + "的" + pjp.getSignature().getName() + "方法";
    }

    /**
     * 处理入参打印
     * @param pjp
     * @param classAndMethodName
     */
    private void processBefore(ProceedingJoinPoint pjp, String classAndMethodName) {
        try {
            if(null==pjp.getArgs() || pjp.getArgs().length==0) {
                log.info("begin执行方法：{},方法无入参", classAndMethodName);
            } else {
                if(pjp.getArgs().length == 1) {
                    if (pjp.getArgs()[0] instanceof Serializable) {
                        if(isFile(pjp.getArgs()[0])) {
                            log.info("begin执行方法：{},入参为文件类型，文件名为：{}", classAndMethodName, getFileName(pjp.getArgs()[0]));
                        } else {
                            log.info("begin执行方法：{},入参为：{}", classAndMethodName, pjp.getArgs()[0]);
                        }
                    }
                } else {
                    log.info("begin执行方法：{},有多个入参", classAndMethodName);
                    List<Object> list = Arrays.asList(pjp.getArgs());

                    final AtomicInteger index = new AtomicInteger(1);
                    list.stream().filter(x -> x instanceof Serializable).forEach(x -> {
                        if(isFile(x)) {
                            log.info("入参{}:{}", index.get(), getFileName(x));
                        } else {
                            log.info("入参{}:{}", index.get(), x);
                        }
                        index.incrementAndGet();
                    });

                }
            }
        } catch (Throwable e) {
            log.error("记录入参日志的时候出错:", e);
        }
    }
    /**
     * 处理返回值
     * @param result
     * @param currentMethod
     * @param classAndMethodName
     */
    private void processReturnValue(Object result, Method currentMethod, String classAndMethodName) {
        if(null == result) {
            return;
        }
        try {
//            if(!currentMethod.isAnnotationPresent(NotPrintResponseLog.class) && result instanceof Serializable) {
            log.info("end执行方法：{}，返回结果:{}", classAndMethodName, result);
//            }
        } catch (Throwable e) {
            log.error("记录返回日志的时候出错:", e);

        }

    }

    /**
     * 获取文件上传的文件名
     * @param file
     * @return
     */
    private String getFileName(Object file) {
        return null==file ? "空文件" : ((MultipartFile)file).getName();
    }

    /**
     * 判断是否是文件类型
     * @param obj
     * @return
     */
    private boolean isFile(Object obj) {
        return obj instanceof MultipartFile;
    }

}