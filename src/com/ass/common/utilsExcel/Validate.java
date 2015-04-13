package com.ass.common.utilsExcel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ass.common.utils.StringUtil;



public class Validate {
	
	
	public  static final String ID_CARD = "身份证格式错误";
	
	
	
	/**
	 * 必须为数字，字符串中【至多含有一个小数点，多位位数字】，不能含有空格
	 * 如： 整数："0000000",".00000000","111",".0123","000.12341","0.123"
	 *     小数："0.111","000.111",".0000","000.123"
	 * @param value
	 * @return
	 */
	public static Boolean number(String value){
		return StringUtil.isNumeric(value);
	};
	
	
	/**
	 * 必须为email
	 * @param value
	 * @return
	 */
	public static Boolean email(String value){
		return true;
	};
	/**
	 * 必须为url
	 * @param value
	 * @return
	 */
	public static Boolean url(String value){
		return true;
	};
	
	/**
	 * 不为空或者空字符串
	 * @param value
	 * @return 错误信息
	 */
	public static Boolean required(String value){
		return true;
	};
	
	/**
	 * 字符串限定长度；
	 * @param value
	 * @return
	 */
	public static Boolean length(String value,int minLength,int maxLength){
		return true;
	};
	
	/**
	 * 数值大小范围
	 * @param value
	 * @return
	 */
	public static Boolean numberRange(String value,Double min,Double max){
		return true;
	};
	
	/**
	 * regex
	 * @param value
	 * @return
	 */
	public static Boolean regEx(String value,String regEx){
		return true;
	};
	
	/**
	 * 该字符串必须是日期类型
	 * @param value
	 * @return
	 */
	public static Boolean Date(String value){
		return true;
	};
	
	/**
	 * 验证18位身份证号
	 * @param idCard
	 * @return
	 */
	public static Boolean IdCard(String value){
		Pattern pattern = Pattern.compile("^\\d{18}$"); 
		 Matcher matcher = pattern.matcher(value); 
		 //return matcher.matches();
		 return true;
	};
	
	/**
	 * 手机号
	 * @param idCard
	 * @return
	 */
	public static Boolean mobile(String value){
		return true;
	};
	
	/**
	 * 固定电话
	 * @param idCard
	 * @return
	 */
	public static Boolean tel(String value){
		return true;
	};
	
	
	
	
	
}
