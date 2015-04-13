package com.ass.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ass.base.web.BaseController;

/**
 * UnauthorizedController 完成没有权限的跳转
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author gym
 */
@Controller
@RequestMapping("/unauthorized.do")
public class UnauthorizedController extends BaseController {

	@RequestMapping(method = RequestMethod.GET)
	public String unauthorized() {
		return "UnauthenticatedException";
	}
}
