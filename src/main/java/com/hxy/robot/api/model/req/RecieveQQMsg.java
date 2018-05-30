package com.hxy.robot.api.model.req;

import java.io.Serializable;

public class RecieveQQMsg implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String key;
	private String msg;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
