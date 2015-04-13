package com.ass.shiro.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ass.base.web.BaseController;
import com.ass.common.utils.JsonUtil;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author calvin
 */
@Controller
@RequestMapping("/login.do")
public class LoginController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		if(SecurityUtils.getSubject().isAuthenticated()){
//			System.out.println("logincontroller1111");
			return "redirect:/";
		}else{
//			System.out.println("logincontroller2222");
			return "login";
		}
	}

	//帐号密码输入有误
	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,Model model) {
		
		if(SecurityUtils.getSubject().isAuthenticated()){
//			System.out.println("logincontroller3333");
			return "redirect:/";
		}
		
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,userName);
//		System.out.println("logincontroller4444");
		return "login";
	}


}
