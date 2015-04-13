package com.ass.application.service;

import com.ass.common.generated.model.TVacation;


public interface VacationService {
    
    
    /**
     * 保存休假申请(未提交
     * //1保存vacation 同时保存了workflowTask并set bussinessId，bussinessType，make_time)
     * @param vacation
     * @date wangt 2014年12月12日 上午9:38:54
     * return tworkflowTaskId
     */
    public Long saveVacationApply(TVacation vacation);
    
    
    
    
    
    
}
