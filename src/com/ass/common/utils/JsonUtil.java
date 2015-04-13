package com.ass.common.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;



/**
 * JSON转换工具类
 * 
 * @date 2010-11-23
 * @since JDK 1.5
 * @version 1.0
 * 
 */
public class JsonUtil {
	
	/**
     * 
     */
	public static ObjectMapper mapper;

	static {
		JsonUtil.mapper = new ObjectMapper();
	}

	/**
	 * 将object转为json字符串-------1
	 * 
	 * @param pObj
	 *            参数
	 * @throws IOException
	 *             异常
	 * @throws JsonMappingException
	 *             异常
	 * @throws JsonGenerationException
	 *             异常
	 * @return String
	 */
	public static String writeValue(Object pObj) {
		String str = "";
		try {
			str = JsonUtil.mapper.writeValueAsString(pObj);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将string转为java object -------1
	 * 
	 * @param <T>
	 *            参数
	 * @param pContent
	 *            参数
	 * @param pObj
	 *            参数
	 * @throws JsonParseException
	 *             异常
	 * @throws JsonMappingException
	 *             异常
	 * @throws IOException
	 *             异常
	 * @return Object
	 */
	@SuppressWarnings("unchecked")
	public static <T> Object readValue(String pContent, Object pObj)  {
		Object o = new Object();
		try {
			o = JsonUtil.mapper.readValue(pContent, (Class<T>) pObj);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return o;
	}
	/*用法示例
	 * lst =  (List<Map<String, Object>>) JsonUtil.readValue(jsonStr2, ArrayList.class);
	 */
	
	
	//test
	public static void main(String[] args) {

	}
}
