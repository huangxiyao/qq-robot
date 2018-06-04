package com.hxy.robot.integeration.electronic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hxy.util.ConfigRepository;
import com.hxy.util.http.HttpClientUtils;

/**
 * 查询电商接口信息
 * @author HUANGXIYAO
 *
 */
@Service
public class ThirdProxy {
	Logger logger = LoggerFactory.getLogger(ThirdProxy.class);
	
	/**
	 * 查询出票结果
	 * @param queryType
	 * @return
	 */
	public String ticketDateCount(Map<String ,String> paraMap){
		String result = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("traceLogId", "");
		map.put("timestamp", "");
		map.put("fromChannelId", "");
		map.put("sign", "");
		int queryType = Integer.valueOf(paraMap.get("queryType"));
		map.put("model", queryType);
		try {
			String paramStr = JSON.toJSONString(map);
			String resStr = HttpClientUtils.doPost(ConfigRepository.get("eclectonic_url")+"/ticketDateCount", paramStr, "UTF-8", 60, 60);
			JSONObject resJsonObj = JSON.parseObject(resStr);
			logger.info("出票量，请求返回结果:{}",resStr);
			if("000000".equals(resJsonObj.getString("errorCode"))){
				result = (String) resJsonObj.get("result");
			}else{
				logger.info("出票结果查询失败");
			}
		} catch (Exception e) {
			logger.error("机器人请求订单查询发生异常");
		}
		return result;
	}
	
	/**
	 * 查询差异数据
	 * @param queryType
	 * @return
	 */
	public String differentDataCount(Map<String ,String> paraMap){
		String result = "";
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("traceLogId", "");
		map.put("timestamp", "");
		map.put("fromChannelId", "");
		map.put("sign", "");
		try {
			String paramStr = JSON.toJSONString(map);
			String resStr = HttpClientUtils.doPost(ConfigRepository.get("eclectonic_url")+"/differentDataCount", paramStr, "UTF-8", 60, 60);
			JSONObject resJsonObj = JSON.parseObject(resStr);
			logger.info("差异数据，请求返回结果:{}",resStr);
			if("000000".equals(resJsonObj.getString("errorCode"))){
				result = (String) resJsonObj.get("result");
			}else{
				logger.info("差异数据结果查询失败");
			}
		} catch (Exception e) {
			logger.error("机器人请求差异数据结果发生异常");
		}
		return result;
	}
	
	//反射各种执行方法
	public String query(String methodName,Map<String ,String> map){
		String result="";
		try {			
			Method serviceMethod = this.getClass().getMethod(methodName,Map.class);
			result = String.valueOf(serviceMethod.invoke(this,map));
		}catch(Exception e){
			logger.error("反射方法-"+methodName+"-执行错误",e);
		}
		return result;
	}
}
