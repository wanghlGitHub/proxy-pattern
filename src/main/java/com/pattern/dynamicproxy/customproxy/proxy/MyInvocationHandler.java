package com.pattern.dynamicproxy.customproxy.proxy;

import java.lang.reflect.Method;

/**
 * @program: proxy-pattern
 * @description: InvocationHandler 接口，JDK 动态代理必须实现的一个接口
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:59 下午
 */
public interface MyInvocationHandler {

	/**
	 * 调用此方法进行执行代理类的具体方法
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 */
	Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
