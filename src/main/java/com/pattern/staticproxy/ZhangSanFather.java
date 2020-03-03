package com.pattern.staticproxy;

/**
 * @program: proxy-pattern
 * @description: 张三的爸爸
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:28 下午
 * @Version: 1.0
 */
public class ZhangSanFather implements IPerson {

	private ZhangSan zhangSan;

	public ZhangSanFather(ZhangSan zhangSan) {
		this.zhangSan = zhangSan;
	}

	@Override
	public void findLove(String name, Integer age) {

	}

	@Override
	public void findLove() {
		before();
		zhangSan.findLove();
		after();
	}

	private void after() {
		System.out.println("相亲成功，开始交往...");
	}

	private void before() {
		System.out.println("张三的爸爸开始给张三物色相亲对象...");
	}


}
