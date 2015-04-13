package com.ass.application.assenum;

/**
 * 
 * @author wangt
 */
public enum EnumVacation {

	/**01事假，02病假，03产假，04年假，05婚假，06工伤，07产检假，08其他
	 * 01系统02微信
	 * 01未提交，02处理中，03通过，04退回，05作废
	 */
	//vacationType
	type_Shijian("01", "事假"),
	type_Bingjia("02", "病假"),
	type_Chanjia("03", "产假"),
	type_Nianjia("04", "年假"),
	type_Hunjia("05", "婚假"),
	type_Gongshang("06", "工伤"),
	type_Chanjianjia("07", "产检假"),
	type_Other("08", "其他"),
	
	
	source_system("01", "系统"),
	source_wechat("02", "微信");
	
	
	
	private final String code;
	private final String name;
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	private EnumVacation(String code, String name) {
		this.code = code;
		this.name = name;
	}
	
	
	
}
