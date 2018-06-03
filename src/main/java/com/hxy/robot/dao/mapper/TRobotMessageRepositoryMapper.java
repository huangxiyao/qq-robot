package com.hxy.robot.dao.mapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.hxy.robot.dao.model.TRobotMessageRepositoryDao;

/**
 * 信息应答模型句柄
 * Created by HUANGXIYAO on 2017/11/23.
 */
public interface TRobotMessageRepositoryMapper {
    
    List<TRobotMessageRepositoryDao> select();
    
    List<TRobotMessageRepositoryDao> selectByServiceType(@Param("serviceType") int type);
    @MapKey("msgQuestion")
    Map<String,String> getSelectMapByServiceType(@Param("serviceType") int type);
    int update(TRobotMessageRepositoryDao message);
    int insert(TRobotMessageRepositoryDao message) throws SQLIntegrityConstraintViolationException;
    int delete(TRobotMessageRepositoryDao message);
}
