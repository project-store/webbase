package com.ass.customer.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TCustomerMapper;
import com.ass.common.generated.model.TCustomer;

@Service
public class CustomerServiceImpl extends BaseServiceImpl implements CustomerService{

	@Resource
	public TCustomerMapper tCustomerMapper;
	/**
	 * 
	 * 保存客户信息
	 */
	public void saveTcustomer(TCustomer tCustomer){
		logger.info("开始保存客户信息");
		tCustomerMapper.insertSelective(tCustomer);
		logger.info("客户信息保存完成，ID：" + tCustomer.getId());
	}
}
