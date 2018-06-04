package com.hxy.robot.api.model.req;

import java.io.Serializable;
import java.util.Map;

public class RecieveQQMsg implements Serializable{
	private static final long serialVersionUID = 1L;
	//时间戳
	private String timestamp;
	//验签
	private String sign;
	/**
	 * robotKey
	 */
	private String key;
	//信息类型
	private String type;
	//信息内容
	private String message;
	//备用字段集
	private Map<String,String> reserveMap;
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getReserveMap() {
		return reserveMap;
	}
	public void setReserveMap(Map<String, String> reserveMap) {
		this.reserveMap = reserveMap;
	}
	

}
