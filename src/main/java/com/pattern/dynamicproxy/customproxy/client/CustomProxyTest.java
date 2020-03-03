package com.pattern.dynamicproxy.customproxy.client;

import com.pattern.staticproxy.IPerson;
import com.pattern.staticproxy.ZhangSan;

/**
 * @program: proxy-pattern
 * @description: 自定义动态代理类的测试类
 * @Author: heliang.wang
 * @Date: 2020/3/3 6:15 下午
 * @Version: 1.0
 */
public class CustomProxyTest {

	public static void main(String[] args) {
		CustomMeiPo customMeiPo = new CustomMeiPo();
		IPerson instance = customMeiPo.getInstance(new ZhangSan());
		instance.findLove("周瑜",18);
	}
}
