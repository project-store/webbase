package com.ass.workflow.service;

import java.util.Map;

public interface WorkFlowService {

	
	
	
	/** 申请或者审批通过流程
	 * @param workflowtaskId
	 * @param comment
	 * @param nextParticipant
	 * void
	 * @author wangt 2015年1月26日 下午8:17:24 
	 */
	public void applyOrNextFlow(String workflowtaskId, String comment, String nextParticipant);
	
	
	
	//************以下为.properties定义流程的方式做法.********150126*******************************
	/**
	 * 申请流程，传 workflowtaskId 和 原因
	 * @param workflowtaskId
	 * @param comment
	 * @author wangt 2014年12月8日 下午3:38:09 
	 */
	public void applyFlow(String workflowtaskId, String comment);
	
	/**
	 * 下一步，传workflowtaskId  和 通过原因
	 * @param workflowtaskId
	 * @param comment
	 * @Return 流程完结return “end” 否则 return null
	 * @author wangt 2014年12月8日 下午3:39:09 
	 */
	public String nextStep(String workflowtaskId, String comment);
	
	
	/**
	 * 退回到发起者。传 workflowtaskId 和 退回原因
	 * @param workflowtaskId
	 * @param comment
	 * @author wangt 2014年12月8日 下午3:39:35 
	 */
	public void back(String workflowtaskId, String comment);
	
	
}
