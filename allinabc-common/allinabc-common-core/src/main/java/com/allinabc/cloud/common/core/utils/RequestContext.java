package com.allinabc.cloud.common.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Slf4j
public final class RequestContext {
    @SuppressWarnings("unchecked")
    /**
     * 获取session属性值
     */
    public static <T> T get(String name) {
        HttpSession session = getSession();
        return (session != null) ? (T) session.getAttribute(name) : null;
    }

    /**
     * 存储session属性值
     *
     * @param name
     * @param value
     * @param <T>
     */
    public static <T> void set(String name, T value) {
        HttpSession session = getSession();
        if (session != null) session.setAttribute(name, value);
    }

    /**
     * 获取请求上下文
     *
     * @return
     */
    public static HttpServletRequest getServletRequest() {
        ServletRequestAttributes requestAttributes = requestAttributes();
        return null != requestAttributes ? requestAttributes.getRequest() : null;
    }


    private static ServletRequestAttributes requestAttributes() {
        return (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    }

    public static HttpSession getSession() {
        HttpServletRequest request = getServletRequest();
        return null != request ? request.getSession() : null;
    }

    public static String getHeader(String headerName){
        HttpServletRequest request = getServletRequest();
        Enumeration<String> enumeration= request.getHeaderNames();
//        while (enumeration.hasMoreElements()){
//            log.info("headerName {}",enumeration.nextElement());
//        }
        return null != request ? request.getHeader(headerName) : null;
    }


    private static ThreadLocal<String> threadLocal=new ThreadLocal<>();


    public static void setBody(String body){
        threadLocal.set(body);
    }


    public static String getBody(){
        return threadLocal.get();
    }

    public static void removeBody(){
        threadLocal.remove();
    }

}
