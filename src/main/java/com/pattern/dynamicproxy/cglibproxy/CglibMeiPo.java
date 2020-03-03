package com.pattern.dynamicproxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @program: proxy-pattern
 * @description: cglib 动态代理
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:43 下午
 * @Version: 1.0
 */
public class CglibMeiPo implements MethodInterceptor {

	public Object getInstance(Class<?> clazz) {
		//相当于Proxy，代理的工具类
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(clazz);
		enhancer.setCallback(this);
		return enhancer.create();
	}

	@Override
	public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
		before();
		Object obj = methodProxy.invokeSuper(o, objects);
		after();
		return obj;
	}

	private void before() {
		System.out.println("我是媒婆，我要给你找对象，现在已经确认你的需求");
		System.out.println("开始物色");
	}

	private void after() {
		System.out.println("OK的话，准备办事");
	}
}
