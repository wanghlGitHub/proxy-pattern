package com.pattern.dynamicproxy.cglibproxy;

import com.pattern.staticproxy.ZhangSan;

/**
 * @program: proxy-pattern
 * @description: cglib 动态代理测试类
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:49 下午
 * @Version: 1.0
 */
public class CglibProxyTest {

	public static void main(String[] args) {

		/**
		 * 在 spring aop 中，如果一个类有接口，默认使用的是 jdk 动态代理，没有接口使用 cglib 动态代理
		 */
		CglibMeiPo cglibMeiPo = new CglibMeiPo();
		ZhangSan instance = (ZhangSan)cglibMeiPo.getInstance(ZhangSan.class);
		instance.findLove();
	}
}
