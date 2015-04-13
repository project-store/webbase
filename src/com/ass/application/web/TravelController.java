package com.ass.application.web;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.generated.model.TTravel;
import com.ass.common.generated.model.TVacation;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
import com.ass.application.service.TravelService;
import com.ass.application.service.VacationService;
@Controller
@RequestMapping("/travel.do")
public class TravelController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private TravelService travelService;
	
	@RequestMapping(params="action=saveTravel")
	public void saveVacation(HttpServletRequest request,HttpServletResponse response)  {
	    String beginTime = request.getParameter("beginTime");
	    String endTime = request.getParameter("endTime");
	    String appHours = request.getParameter("appHours");
	    String reason = request.getParameter("reason");
	    String source = request.getParameter("source");
	    Date bd = DateUtil.parseDate(beginTime, "yyyy-MM-dd HH:mm");
		Date ed = DateUtil.parseDate(endTime, "yyyy-MM-dd HH:mm");

		int beginTimeStamp = DateUtil.getTimestampByDate(bd);
		int endTimeStamp = DateUtil.getTimestampByDate(ed);
	    
	    Map<String, Object> map = this.getMap();
	    map.put("ok", "ok");
	    if(beginTimeStamp >= endTimeStamp){
	        map.put("ok", "休假开始时间应小于结束时间。");
	        this.printStr(response, JsonUtil.writeValue(map));
	        return ;
	    }
	    TTravel travel = new TTravel();
	    if(StringUtil.isNotEmpty(appHours)){
	    	travel.setAppHours(new Double(appHours));
	    }
	    travel.setReason(reason);
	    travel.setSource(source);
	    travel.setStartDate(beginTimeStamp);
	    travel.setEndDate(endTimeStamp);
	    
	    travel.setOperatorId(this.getUser().getId());
	    travel.setMakeTime(DateUtil.getCurrentTimestamp());
	    //保存外出申请，未提交
	    //保存travel 同时保存了workflowTask
	    Long workflowtaskId = travelService.saveTravelApply(travel);
	    map.put("workflowtaskid", workflowtaskId);
	    
	    this.printStr(response, JsonUtil.writeValue(map));
	}
	
	
	
	
	
	
	

}
