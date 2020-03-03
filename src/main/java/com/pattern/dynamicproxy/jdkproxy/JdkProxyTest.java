package com.pattern.dynamicproxy.jdkproxy;

import com.pattern.staticproxy.IPerson;
import com.pattern.staticproxy.ZhangSan;

/**
 * @program: proxy-pattern
 * @description: jdk 动态代理测试类
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:39 下午
 * @Version: 1.0
 */
public class JdkProxyTest {

	public static void main(String[] args) {
		JdkMeiPo jdkMeiPo = new JdkMeiPo();
		IPerson instance = jdkMeiPo.getInstance(new ZhangSan());
		instance.findLove();
	}
}
