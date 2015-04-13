package com.ass.customer.web;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.generated.model.TCustomer;
import com.ass.common.utils.JsonUtil;
import com.ass.customer.service.CustomerService;
/**
 * 
 * 客户Controller
 *
 */
@Controller
@RequestMapping("/customer.do")
public class CustomerController extends BaseController{
	
	@Resource
	public CustomerService customerService;
	
	/**
	 * 
	 * @param 保存或修改客户表t_constumer
	 * @param response
	 */
	@RequestMapping(params="action=saveOrupdate")
	public void saveOrupdateTCustomer(TCustomer tCustomer,HttpServletResponse response){
		logger.info("开始保存或修改客户表");
		/**
		 * TODO 取当前用户的ID作为t_customer表中的operate_id
		 */
		// 保存客户信息
		customerService.saveTcustomer(tCustomer);
		Map<String, Object> m = this.getMap();
		m.put("msg", "添加客户"+tCustomer.getName()+"成功!");
		super.printStr(response, JsonUtil.writeValue(m));
		logger.info("保存客户表完成");
	}

}
