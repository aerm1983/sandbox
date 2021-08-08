package com.sandbox.controller;

import java.io.Serializable;

public class MyResponsePojo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String actionCode;
	private String errorCode;
	private Integer deviceIndex;
	
	public String getActionCode() {
		return actionCode;
	}
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getDeviceIndex() {
		return deviceIndex;
	}
	public void setDeviceIndex(Integer deviceIndex) {
		this.deviceIndex = deviceIndex;
	}

}
