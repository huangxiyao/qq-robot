package com.hxy.robot.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统配置
 * Created by HUANGXIYAO on 2017/11/23.
 */
public class TRobotConfigDao implements Serializable{
   
	private static final long serialVersionUID = 1L;
	/**
     * 配置key
     */
    private String cfgKey;
    /**
     * 配置key对应的value
     */
    private String cfgValue;
    /**
     * key藐视
     */
    private String cfgDesc;
    /**
     * 是否有效
     */
    private String isEffective;
    /**
	 * 创建时间
	 */
	private Date createdAt;

	/**
	 * 创建人
	 */
	private String createdBy;

	/**
	 * 更新时间
	 */
	private Date updatedAt;

	/**
	 * 更新人
	 */
	private String updatedBy;
	public String getCfgKey() {
		return cfgKey;
	}
	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}
	public String getCfgValue() {
		return cfgValue;
	}
	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}
	public String getCfgDesc() {
		return cfgDesc;
	}
	public void setCfgDesc(String cfgDesc) {
		this.cfgDesc = cfgDesc;
	}
	public String getIsEffective() {
		return isEffective;
	}
	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
    
    
}
