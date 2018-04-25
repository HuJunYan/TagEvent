package com.hjy.tagevent;

import com.hjy.tagevent.bean.SubscribeMethod;
import com.hjy.tagevent.bean.Subscription;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TagEventBus {

    private static volatile TagEventBus instance;
    //注解方法 缓存容器
    private Map<Class, List<SubscribeMethod>> METHOD_CACHE = new HashMap<>();

    //根据tag 执行容器
    private Map<String, List<Subscription>> SUBSCRIBES = new HashMap<>();

    //注销类里面的标签
    private Map<Class, List<String>> REGISTER = new HashMap<>();

    private TagEventBus() {

    }

    public static TagEventBus getDefault() {

        if (null == instance) {
            synchronized (TagEventBus.class) {
                instance = new TagEventBus();
            }
        }
        return instance;
    }


    /**
     * 注册事件
     *
     * @param object
     */
    public void register(Object object) {
        //得到被注解的类
        Class<?> subscribeclass = object.getClass();
        //找到类中被注解的函数
        List<SubscribeMethod> subscribes = findSubscribe(subscribeclass);
        //得到类里面的所有标签集合
        List<String> labels = REGISTER.get(subscribeclass);
        if (null == labels) {
            labels = new ArrayList<>();
            REGISTER.put(subscribeclass, labels);
        }

        for (SubscribeMethod subscribeMethod : subscribes) {

            //得到注解方法中的标签
            String lable = subscribeMethod.getLable();

            if (!labels.contains(lable)) {
                labels.add(lable);
            }
            //根据标签得到被注解的实体集合
            List<Subscription> subscriptions = SUBSCRIBES.get(lable);

            if (subscriptions == null) {
                subscriptions = new ArrayList<>();
                SUBSCRIBES.put(lable, subscriptions);
            }
            subscriptions.add(new Subscription(subscribeMethod, object));
        }
    }


    /**
     * 发送订阅事件
     *
     * @param label  标签
     * @param params 传递值
     */
    public void post(String label, Object... params) {

        List<Subscription> subscriptions = SUBSCRIBES.get(label);
        if (subscriptions == null) {
            return;
        }

        for (Subscription subscription : subscriptions) {
            SubscribeMethod subscribeMethod = subscription.getSubscribeMethod();
            //得到参数集合
            Class[] paramterclass = subscribeMethod.getParamterclass();
            Object[] realParams = new Object[paramterclass.length];

            if (null != params) {

                for (int i = 0; i < paramterclass.length; i++) {
                    //赋值传参
//                    if (i<params.length && )
                }

            }
            try {
                subscribeMethod.getMethod().invoke(subscription.getSubscribe(), realParams);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }
    }


    public List<SubscribeMethod> findSubscribe(Class<?> subscribeclass) {

        //先从容器中获取
        List<SubscribeMethod> subscribeMethods = METHOD_CACHE.get(subscribeclass);

        if (null == subscribeMethods) {
            subscribeMethods = new ArrayList<>();

            //得到类里面的所有的方法
            Method[] methods = subscribeclass.getDeclaredMethods();

            //遍历方法
            for (Method method : methods) {
                Subscribe subscribe = method.getAnnotation(Subscribe.class);

                if (null != subscribe) {
                    String[] values = subscribe.value();
                    //得到参数类型
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    for (String value : values) {
                        method.setAccessible(true);
                        //添加到集合里面
                        subscribeMethods.add(new SubscribeMethod(value, method, parameterTypes));
                    }
                }
                METHOD_CACHE.put(subscribeclass, subscribeMethods);
            }
        }
        return subscribeMethods;
    }

    public void unregister(Object object) {

        //得到对象中 所有的注册标签
        List<String> labels = REGISTER.get(object);

        if (null != labels) {
            for (String label : labels) {
                //根据执行标签查询对应所有的函数
                List<Subscription> subscriptions = SUBSCRIBES.get(label);
                if (null != subscriptions) {
                    Iterator<Subscription> iterator = subscriptions.iterator();
                    while (iterator.hasNext()) {
                        Subscription subscription = iterator.next();
                        //如果对象一样就删除
                        if (subscription.getSubscribe() == object) {
                            iterator.remove();
                        }
                    }
                }

            }
        }
    }
}


