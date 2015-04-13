package com.ass.workflow.service;

import java.util.Map;

public interface ApprovalNodeService {

	/** 
	 * 开始流程，  String approvalType
	 * @param approvalType
	 * @return 流程节点step  下一步参与人id  person
	 * @author wangt 2014年12月3日 上午10:14:47 
	 */
	public Map<String, String> firstStep(String approvalType);
	
	/**
	 * 下一步  String approvalType, String curStep
	 * @param approvalType
	 * @param curStep
	 * @return 流程节点step 结束为"end" |   下一步参与人id  or "end" person
	 * @author wangt 2014年12月3日 上午10:15:09 
	 */
	public Map<String, String> nextStep(String approvalType, String curStep);
	
	/**
	 * 上一步  String approvalType, String curStep  ||未使用！！！！！，退回默认退回给发起人。
	 * @param approvalType
	 * @param curStep
	 * @return 流程节点step 发起人为"start"  |  上一步参与人id or "start"(代表发起人)person
	 * @author wangt 2014年12月3日 上午10:15:24 
	 */
	public Map<String, String> preStep(String approvalType, String curStep);
}
