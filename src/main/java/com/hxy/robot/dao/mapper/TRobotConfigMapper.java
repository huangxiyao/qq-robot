package com.hxy.robot.dao.mapper;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hxy.robot.dao.model.TRobotConfigDao;

/**
 * 系统配置
 * Created by HUANGXIYAO on 2017/11/23.
 */
public interface TRobotConfigMapper {
    /**
     * 查询所有配置
     * @return
     */
    List<TRobotConfigDao> select();
    int findAllRobotConfigCount(@Param("cfgKey") String cfgKey, @Param("isEffective") String isEffective);
    int update(TRobotConfigDao config);
    int insert(TRobotConfigDao config) throws SQLIntegrityConstraintViolationException;
    int delete(TRobotConfigDao config);
}
