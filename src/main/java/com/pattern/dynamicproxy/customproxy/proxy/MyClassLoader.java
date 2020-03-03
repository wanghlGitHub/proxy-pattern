package com.pattern.dynamicproxy.customproxy.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @program: proxy-pattern
 * @description: 自定义类加载器
 * @Author: heliang.wang
 * @Date: 2020/3/3 5:58 下午
 * @Version: 1.0
 */
public class MyClassLoader extends ClassLoader {

	private File classPathFile;
	public MyClassLoader(){
		String classPath = MyClassLoader.class.getResource("").getPath();
		this.classPathFile = new File(classPath);
	}
	/**
	 * 通过此方法找到指定的class 文件
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String className = MyClassLoader.class.getPackage().getName() + "." + name;
		if(classPathFile  != null){
			File classFile = new File(classPathFile,name.replaceAll("\\.","/") + ".class");
			if(classFile.exists()){
				FileInputStream in = null;
				ByteArrayOutputStream out = null;
				try{
					in = new FileInputStream(classFile);
					out = new ByteArrayOutputStream();
					byte [] buff = new byte[1024];
					int len;
					while ((len = in.read(buff)) != -1){
						out.write(buff,0,len);
					}
					return defineClass(className,out.toByteArray(),0,out.size());
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
