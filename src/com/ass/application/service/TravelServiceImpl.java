package com.ass.application.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TTravelMapper;
import com.ass.common.generated.dao.TWorkflowTaskMapper;
import com.ass.common.generated.model.TTravel;
import com.ass.common.generated.model.TWorkflowTask;
import com.ass.common.utils.DateUtil;
import com.ass.workflow.assenum.EnumWorkflowStatus;
import com.ass.workflow.constant.WorkFlowConstant;

@Service
public class TravelServiceImpl extends BaseServiceImpl implements TravelService {

	@Resource
	private TTravelMapper tTravelMapper;
	
	@Resource
    private TWorkflowTaskMapper tWorkflowTaskMapper;
	@Override
	public Long saveTravelApply(TTravel travel) {

		tTravelMapper.insertSelective(travel);
        
        TWorkflowTask tworkflowTask = new TWorkflowTask();
        tworkflowTask.setApplyUserId(travel.getOperatorId());
        tworkflowTask.setBusinessId(travel.getId());
        tworkflowTask.setBusinessType(WorkFlowConstant.Travel_Approval);
        tworkflowTask.setMakeTime(DateUtil.getCurrentTimestamp());
        tworkflowTask.setStatus(EnumWorkflowStatus.NOTSUBMIT.getCode());
        tworkflowTask.setTitle(this.getUser().getName()+"("+this.getUser().getLoginName()+")的外出申请。");
        //tworkflowTask.settOrganizationId(); //提交申请的时候再保存
        tWorkflowTaskMapper.insertSelective(tworkflowTask);
        return tworkflowTask.getId();
	}

}
