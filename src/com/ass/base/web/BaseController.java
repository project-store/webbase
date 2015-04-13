package com.ass.base.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ass.base.model.BaseClass;
import com.ass.base.prop.MyPropertyConfigurer;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.StringUtil;
import com.ass.shiro.dto.CurUser;

public class BaseController extends BaseClass{
	
	
	

	/**
	 * 往前台打印json格式字符串
	 * @param response
	 * @param str
	 * @author wangt 2014年7月24日 下午4:53:12 
	 */
	protected void printStr(HttpServletResponse response, String str) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json;charset=utf-8");//适用于$.ajax(datatype:json) 的形式，common.jajax用的很多
		//如果表单提交了文件。如果text/json则返回字符串变成下载。解决方法1 用 text/html
		//解决方法 2: controller的action加返回值    public @ResponseBody Object upload()          return m;
		// /ass/src/com/ass/attachment/web/AttachmentController.java 的upload方法为例
		//		response.setContentType("text/html;charset=utf-8");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("pragma", "no-cache");
		response.setDateHeader("expires", 0l);
		try {
			response.getWriter().write(str);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("BaseController 打印字符串报错。");
		}

	}
	
	
	
	/**
	 * 获得输出流
	 * @param response
	 * @param filename
	 * @return
	 * @throws IOException
	 * @author wangt 2014年7月24日 下午4:53:32 
	 */
	protected ServletOutputStream getOut(HttpServletResponse response, String filename) throws IOException {
		response.reset();
        response.setContentType("application/x-msdownload");
        response.setCharacterEncoding("UTF-8");
        String filename2;
		filename2 = new String(filename.getBytes("GBK"), "ISO8859_1");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + filename2 + "\"");
		return response.getOutputStream();
        
	}
	
	
	/**
	 * 获得request当中的所有参数
	 * @param request
	 * @return
	 * @author wangt 2014年7月24日 下午4:54:57 
	 */
	public Map<String, String> getRequestParams(HttpServletRequest request ){
		Map<String, String> m = new HashMap<String, String>();
		
		// 获得表单提交请求参数名枚举
		Enumeration pNames = request.getParameterNames();
		while (pNames.hasMoreElements()) {
			// 取得表单参数名
			String name = (String) pNames.nextElement();
			// 取得表单参数值
			String value = StringUtil.getString(request.getParameter(name)).trim();
			//用于乱码 的转换
			value = StringUtil.decode(value);
			
			logger.info("key:  " + name + "   value:  " + value);
			if (StringUtil.isNotEmpty(value)) {
				m.put(name, value);
			}
		}
		return m;
		
	}
	
	
	
	
}


