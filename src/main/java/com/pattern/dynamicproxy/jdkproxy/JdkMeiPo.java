package com.pattern.dynamicproxy.jdkproxy;

import com.pattern.staticproxy.IPerson;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @program: proxy-pattern
 * @description: JDK 动态代理
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:35 下午
 * @Version: 1.0
 */
public class JdkMeiPo implements InvocationHandler {

	private IPerson iPerson;

	public IPerson getInstance(IPerson iPerson) {
		this.iPerson = iPerson;
		Class<? extends IPerson> clazz = iPerson.getClass();
		return (IPerson) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();
		Object result = method.invoke(this.iPerson, args);
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
