package com.hxy.robot.dao.mapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.hxy.robot.dao.model.TRobotConfigDao;
import com.hxy.robot.dao.model.TRobotServiceDao;

/**
 * 系统配置
 * Created by HUANGXIYAO on 2017/11/23.
 */
public interface TRobotServiceMapper {
    /**
     * 查询所有配置
     * @return
     */
    List<TRobotServiceDao> select();
    int update(TRobotConfigDao config);
    int insert(TRobotConfigDao config) throws SQLIntegrityConstraintViolationException;
    int delete(TRobotConfigDao config);
}
