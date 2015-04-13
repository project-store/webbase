package com.ass.common.utils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * 
 * @author wangt
 * @time 2014年7月10日 下午2:51:39 
 */
public class MathUtil {


	
		
		
	/**
	 * 保留小数点后二位
	 * @param number
	 * @return 处理后的数值
	 */
	public static double saveTwoDecimal(double number) {
		return roundHalfUp(number, 2);
	}

	/**
	 * 精确的加法运算（支持多个数相加）
	 * 
	 * @param number 数字组成的数组
	 * @return 加法运算后的结果
	 */
	public static double add(double[] number) {
		BigDecimal sum = new BigDecimal(0d);
		for (double num : number) {
			sum = sum.add(new BigDecimal(Double.toString(num)));
		}
		return sum.doubleValue();
	}

	/**
	 * 精确的加法运算（两个数相加）
	 * @param v1 加数
	 * @param v2 被加数
	 * @return 加法运算后的结果
	 */
	public static double add(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * 精确的减法运算
	 * @param v1 被减数
	 * @param v2 减数
	 * @return 减法运算后的结果
	 */
	public static double subtract(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算
	 * @param v1 乘数
	 * @param v2 被乘数
	 * @return 乘法运算后的结果
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供精确的除法运算
	 * @param v1 除数
	 * @param v2 被除数
	 * @return 结果保留10为小数，四舍五入。
	 */
	public static double division(double v1, double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, 10, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的四舍五入处理.
	 * @param v 需要四舍五入的数字
	 * @param scale 小数点后保留几位
	 * @return 处理后的结果
	 */
	public static double roundHalfUp(double v, int scale) {
		BigDecimal b = new BigDecimal(Double.toString(v));
		return b.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}



	/**
	 * 数字转换成金额大写
	 * @param value
	 * @return
	 * @author wangt 2014年8月7日 下午2:40:52 
	 */
	public static String changeToBig(double value){
		if(value > 0){
			return MathUtil.changeToBigWhenBiggerThenZero(value);
		}else if(value < 0){
			double zhengValue = value * -1 ;//取其正直
			String result2 = MathUtil.changeToBigWhenBiggerThenZero(zhengValue);
			return "负"+result2;
		}else{
			return "零";
		}
		
                                                             //返回正确表示
      }

	/**
	 * 大于0的值
	 * @param value
	 * @return
	 * @author wangt 2014年8月22日 下午4:00:23 
	 */
	public static String changeToBigWhenBiggerThenZero(double value){
		char[] hunit={'拾','佰','仟'};                                    //段内位置表示
        char[] vunit={'万','亿'};                                         //段名表示
        char[] digit={'零','壹','贰','叁','肆','伍','陆','柒','捌','玖'}; //数字表示
      //  long midVal = (long)(value*100);                                  ////存在精度问题,如0.9->0.8999...
        BigDecimal midVal = new BigDecimal(Math.round(value*100));    //转化成整形,替换上句
        String valStr=String.valueOf(midVal);                             //转化成字符串
        String head=valStr.substring(0,valStr.length()-2);                //取整数部分
        String rail=valStr.substring(valStr.length()-2);                  //取小数部分

        String prefix="";                                                 //整数部分转化的结果
        String suffix="";                                                 //小数部分转化的结果
        //处理小数点后面的数
        if(rail.equals("00")){                                           //如果小数部分为0
          suffix="整";
        } else{
          suffix=digit[rail.charAt(0)-'0']+"角"+digit[rail.charAt(1)-'0']+"分"; //否则把角分转化出来
        }
        //处理小数点前面的数
        char[] chDig=head.toCharArray();                   //把整数部分转化成字符数组
        boolean preZero=false;                             //标志当前位的上一位是否为有效0位（如万位的0对千位无效）
        byte zeroSerNum = 0;                               //连续出现0的次数
        for(int i=0;i<chDig.length;i++){                   //循环处理每个数字
          int idx=(chDig.length-i-1)%4;                    //取段内位置
          int vidx=(chDig.length-i-1)/4;                   //取段位置
          if(chDig[i]=='0'){                               //如果当前字符是0
            preZero=true;
            zeroSerNum++;                                  //连续0次数递增
            if(idx==0 && vidx >0 &&zeroSerNum < 4){
              prefix += vunit[vidx-1];
              preZero=false;                                //不管上一位是否为0，置为无效0位
            }
          }else{
          zeroSerNum = 0;                                 //连续0次数清零
          if(preZero) {                                   //上一位为有效0位
            prefix+=digit[0];                                //只有在这地方用到'零'
            preZero=false;
          }
          prefix+=digit[chDig[i]-'0'];                    //转化该数字表示
          if(idx > 0) prefix += hunit[idx-1];                  
          if(idx==0 && vidx>0){
            prefix+=vunit[vidx-1];                      //段结束位置应该加上段名如万,亿
          }
        }
        }

        if(prefix.length() > 0) prefix += '圆';                               //如果整数部分存在,则有圆的字样
        return prefix+suffix;       
	}




	public static void main(String[] args) {
		double a = -2.55;
		System.out.println(a);
		double b = a * -1;
		System.out.println(b);
	}








}
