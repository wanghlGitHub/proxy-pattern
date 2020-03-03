package com.pattern.staticproxy;

/**
 * @program: proxy-pattern
 * @description:
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:27 下午
 * @Version: 1.0
 */
public class ZhangSan implements IPerson {

	@Override
	public void findLove(String name,Integer age) {
		System.out.println(name + " 找对象的要求是：肤白貌美大长腿，年龄为：" + age);
	}

	@Override
	public void findLove() {

	}
}
