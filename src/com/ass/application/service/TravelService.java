package com.ass.application.service;

import com.ass.common.generated.model.TTravel;
import com.ass.common.generated.model.TVacation;


public interface TravelService {
    
    
    /**保存外出申请
     * //1保存travel 同时保存了workflowTask并set bussinessId，bussinessType，make_time)
     * @param vacation
     * @return
     * Long
     * @author wangt 2015年2月6日 上午10:15:55 
     */
    public Long saveTravelApply(TTravel	travel);
    
    
    
    
    
    
}
