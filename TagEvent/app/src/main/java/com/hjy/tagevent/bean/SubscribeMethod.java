package com.hjy.tagevent.bean;

import java.lang.reflect.Method;

/**
 * 被注解的实体bean
 */
public class SubscribeMethod {

    private  String lable;

    private Method method;

    private Class[] paramterclass;


    public SubscribeMethod(String lable, Method method, Class[] paramterclass) {
        this.lable = lable;
        this.method = method;
        this.paramterclass = paramterclass;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class[] getParamterclass() {
        return paramterclass;
    }

    public void setParamterclass(Class[] paramterclass) {
        this.paramterclass = paramterclass;
    }
}
