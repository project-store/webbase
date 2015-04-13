package com.ass.attachment.assenum;

/**
 * 
 * @author wangt
 */
public enum EnumAttachment {

	/**
	 * 
	 */
	//attachment_type
	YuanGong("staff", "员工附件"),//bussId 为h_user_id
	HeTong("contract", "合同附件");//bussId为 合同表id
	
	
	
	private final String code;
	private final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	private EnumAttachment(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
	
}
