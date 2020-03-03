package com.pattern.dynamicproxy.customproxy.client;

import com.pattern.dynamicproxy.customproxy.proxy.MyClassLoader;
import com.pattern.dynamicproxy.customproxy.proxy.MyInvocationHandler;
import com.pattern.dynamicproxy.customproxy.proxy.MyProxy;
import com.pattern.staticproxy.IPerson;

import java.lang.reflect.Method;

/**
 * @program: proxy-pattern
 * @description: 使用自己写的动态代理类
 * @Author: heliang.wang
 * @Date: 2020/3/3 6:14 下午
 * @Version: 1.0
 */
public class CustomMeiPo implements MyInvocationHandler {


	private IPerson target;

	public IPerson getInstance(IPerson target) {
		this.target = target;
		Class<? extends IPerson> clazz = target.getClass();
		return (IPerson) MyProxy.newProxyInstance(new MyClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object result = method.invoke(this.target, args);
		after();
		return result;
	}

	private void after() {
		System.out.println("双方同意，开始交往");
	}

	private void before() {
		System.out.println("我是媒婆，已经收集到你的需求，开始物色");
	}
}
