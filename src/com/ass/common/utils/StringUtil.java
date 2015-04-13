package com.ass.common.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil extends org.apache.commons.lang.StringUtils {

	/**
	 * 存放国标一级汉字不同读音的起始区位码
	 */
	static final int[] secPosValueList = { 1601, 1637, 1833, 2078, 2274, 2302,
			2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027,
			4086, 4390, 4558, 4684, 4925, 5249, 5600 };

	/**
	 * 存放国标一级汉字不同读音的起始区位码对应读音
	 */
	static final char[] firstLetter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'w', 'x',
			'y', 'z' };

	/**
	 * 去除字符串中的空格、回车、换行符、制表符
	 * 
	 * @param str
	 *            源字符串
	 * @return 处理后的字符串
	 */
	public static String replaceBlank(String str) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(str);
		String after = m.replaceAll("");
		return after;
	}

	/**
	 * 得到c在s中的出现的索引列表
	 * 
	 * @param s
	 *            原字符串
	 * @param c
	 *            子字符串
	 * @return c在s中出现的索引列表
	 */
	@SuppressWarnings("unchecked")
	public static List getIndexList(String s, String c) {
		int x = s.indexOf(c);
		int replaceLenght = 0;
		List list = new ArrayList();
		while (x != -1) {
			list.add(x + "");
			s = s.replaceFirst(c, "");
			replaceLenght = replaceLenght + c.length();
			x = s.indexOf(c);
			if (x != -1) {
				x = s.indexOf(c) + replaceLenght;
			}
		}
		return list;
	}

	/**
	 * 判断是否为数字（包括小数和整数）
	 * 
	 * @param str
	 *            要判断的字符串
	 * @return true/flase（是/否）
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("-?[0-9]*\\.?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取一个字符串的拼音码
	 * 
	 * @param oriStr
	 *            要操作的字符串
	 * @return 拼音码
	 */
	public static String getFirstLetter(String oriStr) {
		String str = oriStr.toLowerCase();
		StringBuffer buffer = new StringBuffer();
		char ch;
		char[] temp;
		for (int i = 0; i < str.length(); i++) {
			// 依次处理str中每个字符
			ch = str.charAt(i);
			temp = new char[] { ch };
			byte[] uniCode = new String(temp).getBytes();
			if (uniCode[0] < 128 && uniCode[0] > 0) {
				// 非汉字
				buffer.append(temp);
			} else {
				buffer.append(convert(uniCode));
			}
		}
		return buffer.toString();
	}

	/**
	 * 获取一个汉字的拼音首字母
	 * 
	 * @param bytes
	 *            要操作的字符串
	 * @return 拼音首字母
	 */
	public static char convert(byte[] bytes) {
		char result = '-';
		int secPosValue = 0;
		int i;
		for (i = 0; i < bytes.length; i++) {
			bytes[i] -= 160;
		}
		secPosValue = bytes[0] * 100 + bytes[1];
		for (i = 0; i < 23; i++) {
			if (secPosValue >= secPosValueList[i]
					&& secPosValue < secPosValueList[i + 1]) {
				result = firstLetter[i];
				break;
			}
		}
		return result;
	}

	/**
	 * 比较两个字符串的大小,按拼音顺序
	 * 
	 * @param str1
	 *            要操作的字符串
	 * @param str2
	 *            要操作的字符串
	 * @return -1:表示str1<str2 ; 1:表示str1>str2 ;0:表示str1=str2
	 */
	public static int compareString(String str1, String str2) {
		int m = str1.length();
		int n = str2.length();
		for (int i = 0; i < m; i++) {
			if (i < n) {
				if (str1.charAt(i) > str2.charAt(i)) {
					return 1;
				} else if (str1.charAt(i) == str2.charAt(i)) {
					if (m == n && i + 1 == m) {
						return 0;
					} else {
						continue;
					}
				} else {
					return -1;
				}
			} else {
				return 1;
			}
		}
		return -1;
	}

	/**
	 * 替换字符串
	 * 
	 * @param resource
	 *            要操作的字符串
	 * @param target
	 *            要替换的目标子串
	 * @param result
	 *            用来替换目标子串的字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAllStr(String resource, String target,
			String result) {
		resource = resource.replaceAll(target, result);
		return resource;
	}

	/**
	 * 将object 转为 string value并去空格 若object为null返回空字串
	 * 
	 * @param value
	 * @return 转换后的字符串
	 */
	public static String getString(Object value) {
		if (value == null) {
			return "";
		}
		return String.valueOf(value).trim();
	}

	/**
	 * 将字符串转为整形值
	 * 
	 * @param value
	 * @return 转换后的int数字
	 */
	public static int parseStringToInt(String value) {
		try {
			if (value == null || value.trim().equals("")) {
				return 0;
			}
			return Integer.parseInt(value);
		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * 根据分割符','，将输入字符串转换为String数组
	 * 
	 * @param value
	 * @return
	 */

	public static String arrayToCSV(String[] value) {
		return arrayToDelimited(value, ",", true, true);
	}

	/**
	 * 根据分割符，将输入字符串转换为String数组,以','分割
	 * 
	 * @param value
	 * @return
	 */

	public static String arrayToDelimited(Object[] value) {
		return arrayToDelimited(value, ",");
	}

	/**
	 * 根据分割符，将输入字符串转换为String数组
	 * 
	 * @param value
	 * @param delimiter
	 * @return
	 */

	public static String arrayToDelimited(Object[] value, String delimiter) {
		return arrayToDelimited(value, delimiter, false, false, false);
	}

	/**
	 * 根据分割符，将输入字符串转换为String数组
	 * 
	 * @param value
	 * @param delimiter
	 * @return
	 */

	public static String arrayToDelimited(String[] value, String delimiter) {
		return arrayToDelimited(value, delimiter, true, true);
	}

	/**
	 * 根据分割符，将输入字符串转换为String数组
	 * 
	 * @param value
	 * @param delimiter
	 * @param prepend
	 * @param append
	 * @return
	 */
	public static String arrayToDelimited(String[] value, String delimiter,
			boolean prepend, boolean append) {
		return arrayToDelimited(value, delimiter, prepend, append, false);
	}

	/**
	 * 根据分割符，将输入字符串转换为String数组
	 * 
	 * @param value
	 * @param delimiter
	 * @param prepend
	 * @param append
	 * @param eliminateDuplicates
	 * @return
	 */
	public static String arrayToDelimited(Object[] value, String delimiter,
			boolean prepend, boolean append, boolean eliminateDuplicates) {
		if (delimiter == null)
			delimiter = ",";
		String retVal = null;
		if (value != null) {
			StringBuffer buff = new StringBuffer();
			int length = value.length;
			if (length > 0) {
				if (prepend)
					buff.append(delimiter);
				boolean isDuplicateValue = false;
				buff.append(delimiter); // Always make sure the buff starts with
				// a delimiter for duplicate checking
				for (int i = 0; i < length; i++) {
					isDuplicateValue = (eliminateDuplicates ? (buff
							.indexOf(delimiter + value[i] + delimiter) != -1)
							: false);
					if (!isDuplicateValue) {
						buff.append(value[i]);
						if (i < length - 1)
							buff.append(delimiter);
					}
				}
				buff.deleteCharAt(0); // remove the delimiter added for checking
				// duplicates
				// If the last value is a duplicate value, remove the delimiter
				// added to the end of the string
				if (isDuplicateValue) {
					buff.deleteCharAt(buff.length() - 1);
				}
				if (append)
					buff.append(delimiter);
			}
			retVal = buff.toString();
		}
		return retVal;
	}

	/**
	 * 根据数组返回String
	 * 
	 * @param array
	 *            数组
	 * @return
	 */
	public static String transformStringByArray(String array[]) {
		StringBuffer buff = new StringBuffer();
		if (array != null && array.length > 0) {
			for (int i = 0; i < array.length; i++) {
				if (i == 0) {
					buff.append(array[i]);
				} else {
					buff.append(",");
					buff.append(array[i]);
				}
			}
		}
		return buff.toString();
	}

	/**
	 * 根据对象返回String
	 * 
	 * @param obj
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static String transformStringObject(Object obj) throws Exception {
		StringBuffer buff = new StringBuffer();
		Class cls = obj.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getMethodName = "get" + firstLetter + fieldName.substring(1);
			Method getMethod = cls.getMethod(getMethodName, new Class[] {});
			Object value = getMethod.invoke(obj, new Object[] {});
			buff.append(fieldName);
			buff.append(":");
			if (value instanceof Object[]) {
				buff.append(StringUtil.transformStringByArray((String[]) value));
			} else {
				buff.append(value);
			}
			buff.append("\n");
		}
		return buff.toString();
	}
	
	/**
	 * ajax传来中文都会 encodeURI ，这里进行解码操作
	 * @param str
	 * @return
	 * @author wangt
	 * @time 2014年6月18日 下午4:10:14 
	 */
	public static String decode(String str){
		if(StringUtil.isBlank(str)){
			return "";
		}
		String s = new String();
		try {
			s = URLDecoder.decode(str,"utf-8");
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		return s;
	}
}
