package com.ass.workflow.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TWorkflowTaskDetailMapper;
import com.ass.common.generated.dao.TWorkflowTaskMapper;
import com.ass.common.generated.model.TWorkflowTask;
import com.ass.common.generated.model.TWorkflowTaskDetail;
import com.ass.common.generated.model.TWorkflowTaskDetailExample;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.workflow.assenum.EnumWorkflowOperatorType;
import com.ass.workflow.assenum.EnumWorkflowStatus;

/**
 * @author Administrator
 *
 */
@Service
public class WorkflowDwrServiceImpl extends BaseServiceImpl {

	
	@Resource
	private CommonService commonService;
	
	@Resource
	private TWorkflowTaskMapper tWorkflowTaskMapper;
	
	@Resource
	private TWorkflowTaskDetailMapper tWorkflowTaskDetailMapper;
	
	/**作废工作流记录
	 * @param workflowId
	 * @return
	 * Map<String,Object>
	 * @author wangt 2015年1月28日 上午10:47:42 
	 */
	public Map<String, Object> invalidWorkflowTask(String workflowId){
		Map<String, Object> m = this.getMap();
		TWorkflowTask task = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowId));
		task.setStatus(EnumWorkflowStatus.CANCEL.getCode());
		task.setCancelDate(DateUtil.getCurrentTimestamp());
		task.setOperateTime(DateUtil.getCurrentTimestamp());
		tWorkflowTaskMapper.updateByPrimaryKeySelective(task);
		
		//String sql = "update t_workflow_task set status = '"+EnumWorkflowStatus.CANCEL.getCode() +"' where id = "+workflowId;
		//commonService.updateBySql(sql);
		return m;
	}
	
	
	/**审批确认通过
	 * @param workflowId
	 * @return
	 * Map<String,Object>
	 * @author wangt 2015年1月29日 下午9:25:00 
	 */
	public Map<String, Object> agree(String workflowId, String comment){
		Map<String, Object> m = this.getMap();
		//退回到发起人
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowId));

		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		tWorkflowTask.setStatus(EnumWorkflowStatus.AGREE.getCode());
		tWorkflowTask.setOperateTime(DateUtil.getCurrentTimestamp());
		tWorkflowTask.setParticipantId(null);
		
		//tWorkflowTaskDetail set workflowtaskid operatorid operatortype[退回] comment maketime
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.ENSURE.getCode());
		
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		return m;
	}
	
	
	
	/**审批退回
	 * @param workflowId
	 * @return
	 * Map<String,Object>
	 * @author wangt 2015年1月29日 下午9:25:18 
	 */
	public Map<String, Object> back(String workflowId, String comment){
		Map<String, Object> m = this.getMap();
		
		//退回到发起人
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowId));

		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		tWorkflowTask.setStatus(EnumWorkflowStatus.BACK.getCode());
		tWorkflowTask.setOperateTime(DateUtil.getCurrentTimestamp());
		tWorkflowTask.setParticipantId(null);
		
		//tWorkflowTaskDetail set workflowtaskid operatorid operatortype[退回] comment maketime
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.REBACK.getCode());
		
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		
		return m;
	}
	
	
	
	
	
}
