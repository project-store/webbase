package com.ass.common.utils;

/**
 * SQL字符串工具类
 */
public class SQLUtil {
	
	
	/**
	 * 拼接insert的values
	 * @param args
	 * @return String ('param1','param2','param3','param4')
	 */
	public static String values(Object...args){
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("(");
		for(int i=0;i<args.length-1;i++){
			sb.append("'"+args[i]+"',");
		}
		sb.append("'"+args[args.length-1]+"')");
		
		
		return sb.toString();
	}
	
	
	
	public static void main(String[] args) {
		
		System.out.println(SQLUtil.values(1,2,3,"1212","2013-10-12"));
	}


}
