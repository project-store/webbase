package com.ass.workflow.service;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TOrganizationMapper;
import com.ass.common.generated.dao.TUsualApproverMapper;
import com.ass.common.generated.dao.TWorkflowTaskDetailMapper;
import com.ass.common.generated.dao.TWorkflowTaskMapper;
import com.ass.common.generated.model.TOrganization;
import com.ass.common.generated.model.TUsualApprover;
import com.ass.common.generated.model.TWorkflowTask;
import com.ass.common.generated.model.TWorkflowTaskDetail;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.StringUtil;
import com.ass.workflow.assenum.EnumWorkflowOperatorType;
import com.ass.workflow.assenum.EnumWorkflowStatus;

@Service
public class WorkFlowServiceImpl extends BaseServiceImpl implements WorkFlowService {

	@Resource
	private ApprovalNodeService approvalNodeService;
	
	@Resource
	private TWorkflowTaskMapper tWorkflowTaskMapper;
	
	@Resource
	private TOrganizationMapper tOrganizationMapper;
	
	@Resource
	private TWorkflowTaskDetailMapper tWorkflowTaskDetailMapper;
	
	@Resource
	private CommonService commonService;
	
	@Resource
	private TUsualApproverMapper tUsualApproverMapper;
	
	///** 申请或者 提交到下一参与人 流程
	@Override
	public void applyOrNextFlow(String workflowtaskId, String comment,	String nextParticipant) {
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowtaskId));
		
		
		
		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		if(tWorkflowTask.getStatus().equals(EnumWorkflowStatus.NOTSUBMIT.getCode())){
			////申请流程 需要设置 操作类型为提交
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.SUBMIT.getCode());
		}else{
			//提交到下一审批人
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.PASS.getCode());
		}
		
		
		if(tWorkflowTask.getStatus().equals(EnumWorkflowStatus.NOTSUBMIT.getCode())){
			//申请流程 需要设置申请日期,    提交到下一步,不需要设置申请日期
			tWorkflowTask.setAppDate(DateUtil.getCurrentTimestamp());
		}
		//退回状态再次提交也应该设置成审批中  setStatus 应该在 getStatus做判断之后使用
		tWorkflowTask.setStatus(EnumWorkflowStatus.APPROVALING.getCode());
		tWorkflowTask.setParticipantId(new Long(nextParticipant));
		tWorkflowTask.setOperateTime(DateUtil.getCurrentTimestamp());
		
		
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		
		//自动把审批人添加到 常用审批人列表
		this.addUsualParticipant(nextParticipant);
			
	}
	
	//添加常用审批人列表
	private void addUsualParticipant(String userId){
		Long curId = this.getUser().getId();
		String sql = "select id from t_usual_approver where uid = "+curId +" and approver_id = "+userId;
		List<Object> lst = commonService.selectBySql(sql);
		if(lst.size() == 0){
			//增加
			TUsualApprover tua = new TUsualApprover();
			tua.setUid(curId);
			tua.setApproverId(new Long(userId));
			tUsualApproverMapper.insertSelective(tua);
		}else{
			//已经存在.
		}
		
	}
	
	
	
	
	//************以下为.properties定义流程的方式做法.********20150126*******************************
	
	//提交流程需要做的 如下：
    //1保存vacation 同时保存了workflowTask并set bussinessId，bussinessType，make_time
    //2 提交申请时候弹出窗口 
            //a：理由
            //（如果流程第一步不为leader， 不用显示下拉框）
            //b：发出申请部门的下拉框 
                //查询person_incharge_id 为当前登录人的id的 部门的数量 为 count1
                //count1==0， workflowTask的t_organization_id为当前登录人的t_organization_id
                //count1==1，判断 person_incharge_id为当前登录人id的部门的id  是否等于  当前登录人所在的 t_organization_id
                            // 相等：默认为t_organization_id （登录人为其自身所在部门的负责人）
                            // 不相等：下拉框给出两个部门的候选 （登录人为其自身所在部门之外的其他部门的负责人）
                //count1>1 下拉框候选为： person_incharge_id 为当前登录人的id的 部门 加上 当前登录人所在的部门，（去重）
    //保存Tworkflowtask， 有t_organization_id 字段。
	
	
	/**
	 * 申请流程，传 workflowtaskId 和 原因
	 * @param workflowtaskId
	 * @param comment
	 * @author wangt 2014年12月8日 下午3:38:09 
	 */
	public void applyFlow(String workflowtaskId, String comment) {
		
		//1mapper根据workflowtaskId 查询workflowtask 对象 workflowTask。
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowtaskId));
		if(tWorkflowTask.getCurStep() != null){
			// 不可以申请流程。 不为null：1流程已经结束，curstep为end，2未结束为具体step步骤
			// 可以申请流程。 为 null：  3未提交为null  4退回为null
			return;
		}
		Map<String, String> map = approvalNodeService.firstStep(tWorkflowTask.getBusinessType());
		
		tWorkflowTask.setCurStep(map.get("step"));
		//2 workflowTask.set(tOrganizationId, appDate)，修改status为审批中
		tWorkflowTask.setStatus(EnumWorkflowStatus.APPROVALING.getCode());
		tWorkflowTask.setAppDate(DateUtil.getCurrentTimestamp());
		
		//3获得下一步参与人，setParticipantId
		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		
		if(!this.getProp("leader").equals(map.get("person"))){
			/**指定了下一步参与人*/
			//4设置workflowtask的下一步参与人participantid
			tWorkflowTask.setParticipantId(new Long(map.get("person")));
			
			//5TtorkflowTaskDetail，set（workflowtaskid，operator_id，operator_type[提交申请]，comment，maketime）
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.SUBMIT.getCode());
			
		}else{
			/** A.下一步参与人为部员的所在部门负责人，或者 B.为负责人的上级部门的负责人。*/
			
			// a判断 tOrganizatioinId的person_incharge_id 是否为当前登录人
			TOrganization tOrganization = tOrganizationMapper.selectByPrimaryKey(this.getUser().gettOrganizatioinId());
			if(tOrganization.getInchargeUserId() != this.getUser().getId()){
				//A.否：定义下一步参与部门为 tOrganizationId的id   ----participant_organization_id
				tWorkflowTaskDetail.setParticipantOrganizationId(tOrganization.getId());
				tWorkflowTask.setParticipantId(tOrganization.getInchargeUserId());
				tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.SUBMIT.getCode());
				
			}else{
				//B.是：//改变workflowtask的curStep， 需要再往下流转一步。
						//B1  end 流程结束
						//B2  流程制定具体人
						//B3  step还是“leader”定义下一步参与部门为  tOrganizationId的pid ----participant_organization_id
							// 并且 修改tworkflowtask的 curstep
				
				Map<String, String> map2 = approvalNodeService.nextStep(tWorkflowTask.getBusinessType(), tWorkflowTask.getCurStep());
				
				if("end".equals(map2.get("step"))){//B1  end 流程结束
					//成功结束  流程2级： 某人提交给自己，直接成功。
					//tworkflowtask set status, cur_step, 清除participant_id
					tWorkflowTask.setStatus(EnumWorkflowStatus.AGREE.getCode());
					tWorkflowTask.setCurStep("end");
					//tWorkflowTask.setParticipantId(null);
					//set workflowtaskid operatorid operatortype[确认] comment maketime
					tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.ENSURE.getCode());
				}else if(!this.getProp("leader").equals(map2.get("person"))){//B2  流程制定具体人
					tWorkflowTask.setCurStep(map2.get("step"));
					tWorkflowTask.setParticipantId(new Long(map2.get("person")));
					tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.SUBMIT.getCode());
				}else{
					//B3  step还是“leader”定义下一步参与部门为  tOrganizationId的pid ----participant_organization_id
					// 并且 修改tworkflowtask的 curstep.
					TOrganization tOrganizationNext = tOrganizationMapper.selectByPrimaryKey(tOrganization.getPid());
					//tworkflowtask participantId curStep
					tWorkflowTask.setCurStep(map2.get("step"));////////////////
					tWorkflowTask.setParticipantId(tOrganizationNext.getInchargeUserId());
					//tworkflowtaskdetail operatortype, participantOrganizationId
					tWorkflowTaskDetail.setParticipantOrganizationId(tOrganizationNext.getId());////////////////////
					tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.SUBMIT.getCode());
				}
			}
		}
		
		//6 update workflowTask
		//7 insert workflowtaskDetail
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		
		
	}
	
	
	/**
	 * 下一步，传workflowtaskId  和 通过原因
	 * @param workflowtaskId
	 * @param comment
	 * @Return 流程完结return “end” 否则 return null
	 * @author wangt 2014年12月8日 下午3:39:09 
	 */
	public String nextStep(String workflowtaskId, String comment) {
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowtaskId));
		if(tWorkflowTask.getParticipantId() != this.getUser().getId()){
			//流程不在当前登录人这。
			//没必要：能看到的都是自己的流程。session过期后，请求被shiro拦截。
			return "no_01";
		}
		Map<String, String> map = approvalNodeService.nextStep(tWorkflowTask.getBusinessType(),tWorkflowTask.getCurStep());
		
		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		//set tworkflowtaskdetail  workflowtaskid operatorid  comment maketime
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		if("end".equals(map.get("step"))){
			//成功结束
			//tworkflowtask set status, cur_step, 清除participant_id
			tWorkflowTask.setStatus(EnumWorkflowStatus.AGREE.getCode());
			tWorkflowTask.setCurStep("end");
			//tWorkflowTask.setParticipantId(null);
			//set  operatortype[确认] 
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.ENSURE.getCode());
			
		}else if(!this.getProp("leader").equals(map.get("person"))){
			/**指定了下一步参与人*/
			//1设定 下一步 步骤，和参与人
			//tworkflowtask set （cur_step[map.get("step")]  participant_id[map.get("person")]）
			tWorkflowTask.setCurStep(map.get("step"));
			tWorkflowTask.setParticipantId(new Long(map.get("person")));
			//2 set tworkflowtaskdetail  operatortype[通过] 
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.PASS.getCode());
			
		}else{
			/**下一步参与人为 当前参与人的 上级部门的负责人。*/
			
			//1设定 下一步 步骤[curStep]，和参与人[participantId]
			//a: 下一步步骤tworkflowtask set （cur_step[map.get("step")] ）
			tWorkflowTask.setCurStep(map.get("step"));
			//b： 下一步参与人获取方法：
					//select  t_workflow_task_detail where t_workflow_task_id = tWorkflowTask.getId order by make_time desc
			String sql = "select id, participant_organization_id poid from t_workflow_task_detail "
						+ "where t_workflow_task_id = "+tWorkflowTask.getId()+" order by id desc";
			List<Map<String, Object>> lst = commonService.selectBySql(sql);
			String curBumenID = StringUtil.getString(lst.get(0).get("poid"));
					//取第一条[时间倒序，离当前最近一次]的 participant_organization_id 当前参与人部门
					//然后取其  上级部门!  为 shangjiOrganization
			TOrganization curOrganization = tOrganizationMapper.selectByPrimaryKey(new Long(curBumenID));
			TOrganization shangjiOrganization = tOrganizationMapper.selectByPrimaryKey(curOrganization.getPid());
			
					//设置 下一步参与人为 shangjiOrganization的负责人
			tWorkflowTask.setParticipantId(shangjiOrganization.getInchargeUserId());
			
			//2 set tworkflowtaskdetail   operatortype[通过] participantOrganizationId[为 shangjibumenID]
			tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.PASS.getCode());
			tWorkflowTaskDetail.setParticipantOrganizationId(shangjiOrganization.getId());
			
		}
		//update tworkflowtask
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		//insert workflowtaskdetail
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		
		if("end".equals(tWorkflowTask.getCurStep())){
			return "end";
		}
		return null;
	}

	
	
	
	/**
	 * 退回到发起者。传 workflowtaskId 和 退回原因
	 * @param workflowtaskId
	 * @param comment
	 * @author wangt 2014年12月8日 下午3:39:35 
	 */
	public void back(String workflowtaskId, String comment) {
		//退回到发起人
		TWorkflowTask tWorkflowTask = tWorkflowTaskMapper.selectByPrimaryKey(new Long(workflowtaskId));
		if(tWorkflowTask.getCurStep() == null || "end".equals(tWorkflowTask.getCurStep())){
			// 可以退回：a未结束为具体step步骤
			// 不可退回：  b未提交为null  c退回为null  d流程已经结束，curstep为end，
			return;
		}
		TWorkflowTaskDetail tWorkflowTaskDetail = new TWorkflowTaskDetail();
		
		//tWorkflowTask set cur_step 为空， participant_id为空 status为退回
		tWorkflowTask.setCurStep(null);//
		tWorkflowTask.setParticipantId(null);//
		tWorkflowTask.setStatus(EnumWorkflowStatus.BACK.getCode());
		//tWorkflowTaskDetail set workflowtaskid operatorid operatortype[退回] comment maketime
		tWorkflowTaskDetail.settWorkflowTaskId(tWorkflowTask.getId());
		tWorkflowTaskDetail.setOperatorId(this.getUser().getId());
		tWorkflowTaskDetail.setComment(comment);
		tWorkflowTaskDetail.setMakeTime(DateUtil.getCurrentTimestamp());
		tWorkflowTaskDetail.setOperatorType(EnumWorkflowOperatorType.REBACK.getCode());
		
		//update tworkflowtask
		tWorkflowTaskMapper.updateByPrimaryKey(tWorkflowTask);
		//insert workflowtaskdetail
		tWorkflowTaskDetailMapper.insertSelective(tWorkflowTaskDetail);
		
	}


	

	
}
