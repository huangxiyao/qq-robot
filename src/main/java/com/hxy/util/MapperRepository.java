package com.hxy.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 本类用于映射qq的服务群的操作类型
 * @author HUANGXIYAO
 *
 */
public class MapperRepository {
	private MapperRepository(){
		throw new IllegalAccessError("Utility class");
	}
	
	private static ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<String, Integer>();
	public static void put(String key, Integer value){
		map.put(key, value);
	}
	public static Integer get(String key){
		return  map.get(key);
	}
	
	public static Integer delete(String key){
		return map.remove(key);
	}
	
	public static ConcurrentHashMap<String, Integer> getDataMap(){
		return map;
	}
}
