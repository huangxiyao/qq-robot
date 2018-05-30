package com.hxy.util;

import java.util.concurrent.ConcurrentHashMap;

public class ConfigRepository {
	private ConfigRepository(){
		throw new IllegalAccessError("Utility class");
	}
	
	private static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<String, String>();
	public static void put(String key, String value){
		map.put(key, value);
	}
	public static String get(String key){
		return  map.get(key);
	}
	
	public static String delete(String key){
		return map.remove(key);
	}
}
