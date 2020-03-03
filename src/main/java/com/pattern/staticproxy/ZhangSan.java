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
	public void findLove() {
		System.out.println("儿子要求：肤白貌美大长腿");
	}
}
