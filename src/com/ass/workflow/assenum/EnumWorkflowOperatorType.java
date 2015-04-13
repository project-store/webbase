package com.ass.workflow.assenum;

public enum EnumWorkflowOperatorType {

//	01提交申请 02通过 03退回 04确认
	
	SUBMIT("01","提交申请"),
	PASS("02","通过"),
	REBACK("03","退回"),
	ENSURE("04","确认");
	
	
	public final String code;
	public final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	private EnumWorkflowOperatorType(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
}
