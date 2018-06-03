package com.hxy.robot.service.baseservice.custom;

import java.util.Map;

/**
 * 本类提供了信息处理服务的接口，针对信息处理可以自定义处理方法
 * 通过实现该接口可以使用自己自定义的处理信息的方法
 * 待实现
 * @author HUANGXIYAO
 *
 */
public interface InfomationProcessService {
	/**
	 * 此方法将讨论组、qq群、单个qq好友发送过来的信息处理后，返回给调用方
	 * @param id 讨论组id，群id，
	 * @param msgContent 远程的消息内容
	 * @param userName 发出消息的用户
	 * @return 处理后的消息
	 */
	String processMessage(long id, String msgContent, String userName);
}
