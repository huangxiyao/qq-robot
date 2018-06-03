package com.hxy.robot.dao.task;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;

import com.alibaba.druid.filter.config.ConfigTools;
import com.hxy.util.ConfigRepository;

/**
 * 在bean初始化前加载项目中所有的配置
 * @author HUANGXIYAO
 *
 */
@Service
public class ConfigLoadTask implements BeanPostProcessor {
	
	public static final Logger logger = LoggerFactory.getLogger(ConfigLoadTask.class);

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	@PostConstruct
	private void loadConfig(){
		//1.加载数据库配置
		logger.info("首次开始加载配置库");
		readConfig();
		logger.info("首次加载完成，开始进入定时加载配置库任务");
		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				logger.info("开始从数据库同步配置信息到系统内存");
				try {
					readConfig();
				} catch (Exception e) {
					logger.error("数据库同步配置信息到系统内存发生异常，异常原因是：", e);
				}
				logger.info("数据库同步配置信息到系统内存任务执行结束，等待下次的执行。");
			}
		}, 10 * 60 * 1000l, TimeUnit.MINUTES.toMillis(5));
	}
	
	/**
	 * 从数据库读取配置
	 */
	private void readConfig(){
		//2.读取数据库的值
		Properties prop = new Properties();
		InputStream in=this.getClass().getResourceAsStream("/application.properties");
		try {
			prop.load(in);
			String userName = prop.getProperty("spring.datasource.username");
			String passwd = prop.getProperty("spring.datasource.password");
			String driver = prop.getProperty("spring.datasource.driverClassName");
			String url = prop.getProperty("spring.datasource.url");
			//解密用户名，密码
			//passwd = ConfigTools.decrypt(passwd);
			logger.info("driver:{},url:{},用户名：{}，密码：{}",driver,url,userName,passwd);
			Class.forName(driver);
			try(Connection conn = DriverManager.getConnection(url, userName, passwd);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("select cfg_key cfgKey,cfg_value cfgValue,cfg_desc cfgDesc,IS_EFFECTIVE isEffective from  T_ROBOT_CONFIG");){
				while(rs.next()) {
					String key = rs.getString("cfgKey");
					String value = rs.getString("cfgValue");
					String isEffective = rs.getString("isEffective");
					if(Boolean.TRUE.toString().equals(isEffective)){
						logger.info("从数据库加载配置key:{},value:{}",key,value);
						ConfigRepository.put(key, Optional.ofNullable(value).orElse(""));
					}else{
						logger.info("数据库中不存在key{}，从缓存中删除key:{},value:{}",key,key,value);
						ConfigRepository.delete(key);
					}

				}
			}catch(Exception e1){
				logger.error("读取配置出错",e1);
			}
		}catch (Exception e1) {
			logger.error("出现异常，请查看错误信息",e1);
		}finally{
			try {
			    if(in != null){
			    	in.close();
			    }
			} catch (IOException e) {
				logger.error("加载配置出错",e);
			}
		}
	}
}
