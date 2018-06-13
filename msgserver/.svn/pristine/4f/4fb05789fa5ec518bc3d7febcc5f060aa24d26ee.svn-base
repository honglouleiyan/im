package com.jihuiduo.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RandomAlgorithm {
	
	/**
	 * 主方法
	 * @param args
	 */
	public static void main(String[] args) {
		// 邀请好友随机红包算法
		// testInvitingRandom();
		
		// 分享随机红包算法
		testSharingRandom();
	}
	
	/**
	 * 获取邀请好友随机红包金额
	 * 
	 * @return
	 */
	public static float getInvitingRandom() {
		float[] array = new float[10000];
		array = setArrayRandom(array, 0.1f, 0, 1980);
		array = setArrayRandom(array, 0.2f, 990, 1980);
		array = setArrayRandom(array, 0.3f, 1980, 1980);
		array = setArrayRandom(array, 0.4f, 2970, 1980);
		array = setArrayRandom(array, 0.5f, 3960, 1980);
		array = setArrayRandom(array, 0.6f, 4950, 8);
		array = setArrayRandom(array, 0.7f, 4954, 8);
		array = setArrayRandom(array, 0.8f, 4958, 8);
		array = setArrayRandom(array, 0.9f, 4962, 8);
		array = setArrayRandom(array, 1.0f, 4966, 8);
		array = setArrayRandom(array, 1.1f, 4970, 6);
		array = setArrayRandom(array, 1.2f, 4973, 6);
		array = setArrayRandom(array, 1.3f, 4976, 6);
		array = setArrayRandom(array, 1.4f, 4979, 6);
		array = setArrayRandom(array, 1.5f, 4982, 6);
		array = setArrayRandom(array, 1.6f, 4985, 4);
		array = setArrayRandom(array, 1.7f, 4987, 4);
		array = setArrayRandom(array, 1.8f, 4989, 4);
		array = setArrayRandom(array, 1.9f, 4991, 4);
		array = setArrayRandom(array, 2.0f, 4993, 4);
		array = setArrayRandom(array, 20.0f, 4995, 10);
		int i = (int) (Math.random()*10000);
		return array[i];
	}
	
	/**
	 * 获取分享随机红包金额
	 * 
	 * @return
	 */
	public static float getSharingRandom() {
		float[] array = new float[2000];
		array = setArrayRandom(array, 0.1f, 0, 1980);
		array = setArrayRandom(array, 0.2f, 990, 6);
		array = setArrayRandom(array, 0.3f, 993, 6);
		array = setArrayRandom(array, 0.4f, 996, 6);
		array = setArrayRandom(array, 0.5f, 999, 2);
		int i = (int) (Math.random()*2000);
		return array[i];
	}
	
	/**
	 * 设置随机数组
	 * 
	 * @param array
	 * @param k
	 * @param begin
	 * @param length
	 * @return
	 */
	private static float[] setArrayRandom(float[] array, float k, int begin, int length) {
		int size = array.length;
		int l = length / 2;
		for (int i = begin; i < begin + l; i++){
			array[i] = k;
			array[size - i - 1] = k;
		}
		return array;
	}
	
	/**
	 * 测试方法
	 */
	public static void testInvitingRandom() {
		// 结果集
		Map<Float, Integer> map = new HashMap<Float, Integer>();
		for (int i = 0; i < 1000000; i++) {
			float r = getInvitingRandom();
			if (map.containsKey(r)) {
				int temp = map.get(r);
				map.put(r, temp + 1);
			} else {
				map.put(r, 1);
			}
		}
		// 打印结果
		Set<Float> set = map.keySet();
		for(Float i : set) {
			System.out.println("key : " + i + " , value : " + map.get(i));
		}
	}
	
	/**
	 * 测试方法
	 */
	public static void testSharingRandom() {
		// 结果集
		Map<Float, Integer> map = new HashMap<Float, Integer>();
		for (int i = 0; i < 1000000; i++) {
			float r = getSharingRandom();
			if (map.containsKey(r)) {
				int temp = map.get(r);
				map.put(r, temp + 1);
			} else {
				map.put(r, 1);
			}
		}
		// 打印结果
		Set<Float> set = map.keySet();
		for(Float i : set) {
			System.out.println("key : " + i + " , value : " + map.get(i));
		}
	}

}
