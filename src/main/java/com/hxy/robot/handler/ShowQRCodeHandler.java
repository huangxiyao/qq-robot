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
package com.hxy.robot.handler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hxy.robot.service.QQService;
import com.hxy.util.ConfigRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;

/**
 * Shows QR code servlet.
 *
 * @author <a href="http://88250.b3log.org">Liang Ding</a>
 * @version 1.0.0.1, Apr 2, 2018
 * @since 2.1.0
 */
@RestController
public class ShowQRCodeHandler{
	
	Logger logger = LoggerFactory.getLogger(ShowQRCodeHandler.class);

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;
    
    @Autowired
	private QQService qqService;

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowQRCodeHandler.class);
    @RequestMapping(value = "/login")
    public void getLoginPicture(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//初始化智能机器人
        resp.addHeader("Cache-Control", "no-store");
        resp.setContentType("text/html; charset=UTF-8");
        LOGGER.info("首次登陆初始化qq客户端");
        final StringBuilder htmlBuilder = new StringBuilder();
        try (final PrintWriter writer = resp.getWriter()) {
        	//如果已经登陆了，这个也买会返回登陆成功的提示
        	if(Boolean.valueOf(ConfigRepository.get("qrCodeLoginFlag"))){
        		htmlBuilder.append("<html><body><div style=\"text-align:center;border:white solid 1px;padding-top:10%;\"><div>你已经登陆成功啦，请不要重复登陆</div></div></body></html>");
            }else{
            	//初始化qq
            	//初始化qq客户端
            	ConfigRepository.put("initQQFlag","true");
        		new Thread(() -> {
                    try {
                        Thread.sleep(3000);
                    } catch (final Exception e) {
                    	logger.error("发生线程中断异常",e);
                    }
                    qqService.initQQClient();
                }).start();
        		//等待二维码生成
        		while(!Boolean.valueOf(ConfigRepository.get("finishInitQQFlag"))){
        			Thread.sleep(500l);
        			logger.info("等待登陆二维码生成");
        		}
        		String url  = ConfigRepository.get("url");
        		//开始写回二维码图片
                final String filePath = new File("qrcode.png").getCanonicalPath();
                final byte[] data = IOUtils.toByteArray(new FileInputStream(filePath));
                htmlBuilder.append("<!DOCTYPE html><html><head>"
                		+ "<script src=\"http://code.jquery.com/jquery-1.7.2.min.js\"></script>"
                        + "<script>	var ref = \"\";"
                        + "function query(){"
                        + "$.get(\"http://"+url+"/loginAgain\","
                        + 			"function(data,status){"
                        + 				"if(data.indexOf(\"qqIniting\") < 0){"
                        + 						"clearInterval(ref);"
                        +						"$(\"#loginDiv\").html(data);"
                        + 					"}else{}});"
                        + 		"}"
                        + "function load(){"
                        + 		"ref = setInterval(\"query()\",1000); "
                        + 		"}"
                        + "</script>"
                        + "</head>"
                        + "<body onload=\"load()\">"
                        + "<div id =\"loginDiv\" style=\"text-align:center;border:white solid 1px;padding-top:10%;\">"
                        + "<div><img src=\"data:image/png;base64,").append(Base64.getEncoder().encodeToString(data)).append("\"/></div>"
                        + "<div>请扫描二维码登陆</div>"
                        + "</div></body></html>");
                    }
            writer.write(htmlBuilder.toString());
            writer.flush();
        } catch (final Exception e) {
        	LOGGER.error("在线显示二维码图片异常", e);
        }
    }
    
    
    @RequestMapping(value = "/loginAgain")
    public void getLoginPictureAgain(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//初始化智能机器人
        resp.addHeader("Cache-Control", "no-store");
        resp.setContentType("text/html; charset=UTF-8");
        LOGGER.info("查询是否初始化成功");
        final StringBuilder htmlBuilder = new StringBuilder();
        try (final PrintWriter writer = resp.getWriter()) {
        	//如果已经登陆了，这个也买会返回登陆成功的提示
        	if(Boolean.valueOf(ConfigRepository.get("qrCodeLoginFlag"))){
        		htmlBuilder.append("你已经登陆成功啦!");
                ConfigRepository.put("initQQFlag","false");
            }else if(Boolean.valueOf(ConfigRepository.get("initQQFlag"))){
            	//客服端反复调用后台请求参数
            	htmlBuilder.append("qqIniting");
            	LOGGER.info("qq客户端正在初始化二维码");
            }else{
            	htmlBuilder.append("异常!");
            	LOGGER.info("qq初始化二维码异常");
            }
            writer.write(htmlBuilder.toString());
            writer.flush();
        } catch (final Exception e) {
        	LOGGER.error("在线显示二维码图片异常", e);
        }
    }
}
