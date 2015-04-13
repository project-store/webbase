package com.ass.application.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TVacationMapper;
import com.ass.common.generated.dao.TWorkflowTaskMapper;
import com.ass.common.generated.model.TVacation;
import com.ass.common.generated.model.TWorkflowTask;
import com.ass.common.utils.DateUtil;
import com.ass.workflow.assenum.EnumWorkflowStatus;
import com.ass.workflow.constant.WorkFlowConstant;


@Service
public class VacationServiceImpl extends BaseServiceImpl implements VacationService {

    @Resource
    private TVacationMapper tVacationMapper;
    
    @Resource
    private TWorkflowTaskMapper tWorkflowTaskMapper;
    
    
    /**
     * 保存休假申请(未提交)
     * //1保存vacation 同时保存了workflowTask
     * @param vacation
     * @date wangt 2014年12月12日 上午9:38:54
     * return tworkflowTaskId
     */
    @Override
    public Long saveVacationApply(TVacation vacation) {

        tVacationMapper.insertSelective(vacation);
        
        TWorkflowTask tworkflowTask = new TWorkflowTask();
        tworkflowTask.setApplyUserId(vacation.getOperatorId());
        tworkflowTask.setBusinessId(vacation.getId());
        tworkflowTask.setBusinessType(WorkFlowConstant.Vacation_Approval);
        tworkflowTask.setMakeTime(DateUtil.getCurrentTimestamp());
        tworkflowTask.setStatus(EnumWorkflowStatus.NOTSUBMIT.getCode());
        tworkflowTask.setTitle(this.getUser().getName()+"("+this.getUser().getLoginName()+")的休假申请。");
        //tworkflowTask.settOrganizationId(); //提交申请的时候再保存
        tWorkflowTaskMapper.insertSelective(tworkflowTask);
        return tworkflowTask.getId();
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
