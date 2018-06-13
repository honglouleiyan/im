package com.jihuiduo.test;

import java.net.URL;

public class ClassPathTest {
	
	public static void main(String[] args) {
		ClassPathTest test = new ClassPathTest();
		ClassLoader loader = test.getParentClassLoader();
		System.out.println("ClassLoader : " + loader);
		URL url = loader.getResource("");
		System.out.println("Resource URL : " + url);
		String classpath = url.getPath();
		System.out.println("当前classpath的绝对路径 : " + classpath);
		
	}
	
	
	/**
	 * 获取ClassLoader
	 */
	public ClassLoader getParentClassLoader() {
	    ClassLoader parent = Thread.currentThread().getContextClassLoader();
	    if (parent == null) {
	        parent = this.getClass().getClassLoader();
	        if (parent == null) {
	            parent = ClassLoader.getSystemClassLoader();
	        }
	    }
	    return parent;
	}

}
