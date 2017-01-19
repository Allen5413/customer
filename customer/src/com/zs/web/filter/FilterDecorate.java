package com.zs.web.filter;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;  

import javax.servlet.Filter;  
import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletRequest;  
import javax.servlet.ServletResponse;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Created by Allen on 2017/1/12.
 */
public class FilterDecorate implements Filter{

    //不需要拦截的路径
    private static final String[] IGNORE_URI = {"/uploadTempFile"};
          
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        //对POST方法有用
        request.setCharacterEncoding("utf-8");

        boolean flag = false;
        String url = req.getRequestURL().toString();
        for (String s : IGNORE_URI) {
            if (url.contains(s)) {
                flag = true;
                break;
            }
        }

        if(!flag) {
            //用装饰类方法重写getParameter（）
            //创建装饰类对象
            MyRequest myRequest = new MyRequest((HttpServletRequest) request);
            chain.doFilter(myRequest, response);
        }else {
            chain.doFilter(request, response);
        }
    }
    public void destroy() {

    }
              
          
}
          
//1、继承HttpServletRequest的实现类  
class MyRequest extends HttpServletRequestWrapper{  
    //2、声明一个变量
    HttpServletRequest request;

    public MyRequest(HttpServletRequest request) {
        super(request);
        //3、接收被装饰类对象
        this.request = request;

    }
              
    //4、重写getParameter方法
    @Override
    public String getParameter(String name) {
        try {
            //得到原来的参数
            String value = request.getParameter(name);
            //增强
            if("GET".equals(request.getMethod()) && !StringUtils.isEmpty(value)){
                value  = new String(value.getBytes("ISO-8859-1"),"utf-8");
            }
            //返回增强后的内容
            return value;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
              
    //重写getParameterValues方法
    @Override
    public String[] getParameterValues(String name) {
        try {
            String[] values = request.getParameterValues(name);
            //对GET方法进行转码
            if(values !=null && "GET".equals(request.getMethod())){
            for(int i = 0 ;i< values.length; i++){
                //对每一个元素进行转码
                values[i] = new String(values[i].getBytes("ISO-8859-1"),"utf-8");
            }
        }
        //返回增强值
        return values;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
