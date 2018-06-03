package com.hxy.robot.dao.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 用作配置机器人服务群体类型
 * Created by HUANGXIYAO on 2017/11/23.
 */
public class TRobotServiceDao implements Serializable{
    
	private static final long serialVersionUID = 1L;
	/**
     * 服务类型码
     */
    private int serviceType;
    /**
     * 服务类型描述
     */
    private String serviceDesc;
    /**
     * 备用字段1
     */
    private String reverse1;
    /**
     * 备用字段2
     */
    private String reverse2;
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
	
	public int getServiceType() {
		return serviceType;
	}
	public void setServiceType(int serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceDesc() {
		return serviceDesc;
	}
	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}
	public String getReverse1() {
		return reverse1;
	}
	public void setReverse1(String reverse1) {
		this.reverse1 = reverse1;
	}
	public String getReverse2() {
		return reverse2;
	}
	public void setReverse2(String reverse2) {
		this.reverse2 = reverse2;
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
