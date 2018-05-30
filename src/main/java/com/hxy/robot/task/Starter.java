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
import com.hxy.util.CommandRepository;
import com.hxy.util.ConfigRepository;

@Service
public class Starter {
	Logger logger = LoggerFactory.getLogger(Starter.class);
	
	@Autowired
	private QQService qqService;
	
	/**
	 * 初始化智能qq
	 */
	@PostConstruct
	public void init(){
		CommandRepository.put("电影票订单出票量","该服务还没有提供啊，请稍后！");
		CommandRepository.put("电影票差异数据","该服务还没有提供啊，请稍后！");
	}
}
