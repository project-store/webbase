package com.ass.workflow.assenum;

public enum EnumWorkflowStatus {

//	00未提交01审批中02退回03已通过04作废
	
	NOTSUBMIT("00","未提交"),
	APPROVALING("01","审批中"),
	BACK("02","退回"),
	AGREE("03","已通过"),
	CANCEL("04","作废");
	
	
	public final String code;
	public final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	private EnumWorkflowStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
}
