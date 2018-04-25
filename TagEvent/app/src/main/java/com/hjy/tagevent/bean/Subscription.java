package com.hjy.tagevent.bean;

/**
 * 执行注解bean
 */
public class Subscription {

    SubscribeMethod subscribeMethod;

    Object subscribe;

    public Subscription(SubscribeMethod subscribeMethod, Object subscribe) {
        this.subscribeMethod = subscribeMethod;
        this.subscribe = subscribe;
    }

    public SubscribeMethod getSubscribeMethod() {
        return subscribeMethod;
    }

    public void setSubscribeMethod(SubscribeMethod subscribeMethod) {
        this.subscribeMethod = subscribeMethod;
    }

    public Object getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Object subscribe) {
        this.subscribe = subscribe;
    }
}
