package com.hxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author HUANGXIYAO
 */

@SpringBootApplication
@MapperScan("com.hxy.robot.dao.mapper")  
public class SpringBootRobotApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootRobotApplication.class,args);
	}
}
