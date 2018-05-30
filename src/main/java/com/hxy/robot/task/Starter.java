/*
 * Copyright (c) 2012-2018, b3log.org & hacpai.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hxy.robot.task;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hxy.robot.service.QQService;

@Service
public class Starter {
	Logger logger = LoggerFactory.getLogger(Starter.class);
	
	@Autowired
	private QQService qqService;
	
	/**
	 * 初始化智能qq
	 */
	//@PostConstruct
	public void init(){
		logger.info("开始初始化qq");
		//初始化qq客户端
		new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (final Exception e) {
            	logger.error("发生线程中断异常",e);
            }
            qqService.initQQClient();
        }).start();
	}
}
