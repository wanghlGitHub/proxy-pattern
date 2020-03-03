package com.pattern.staticproxy;

/**
 * @program: proxy-pattern
 * @description: 静态代理测试类
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:31 下午
 * @Version: 1.0
 */
public class StatciProxyTest {

	public static void main(String[] args) {
		/**
		 * 像这种静态代理，在开发过程中经常用到，service层调用 repository 层这种就是静态代理
		 */
		ZhangSanFather zhangSanFather = new ZhangSanFather(new ZhangSan());
		zhangSanFather.findLove();
	}
}
