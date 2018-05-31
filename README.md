1.添加小翼服务群的权限，在下面这个类
com.hxy.robot.task.Starter  可以在这里面配置相应的使用权限
    //初始化服务的qq群
		//QQGroupRepository.put("智能机器人", 0);
		QQGroupRepository.put("电影票", 1);
		QQGroupRepository.put("党费", 2);
		//定义服务种类
		//CommandRepository.put("0","该服务还没有提供啊，请稍后！");
		CommandRepository.put("1","{\"电影票差异数据\":\"该服务还没有提供啊，请稍后！\",\"电影票订单出票量\":\"该服务还没有提供啊，请稍后！\"}");
		CommandRepository.put("2","{\"党费账单\":\"该服务还没有提供啊！\",\"党费短信发送量\":\"该服务还没有提供啊，请稍等！\"}");
2.登陆
本地部署后：访问 localhost:8080/login
