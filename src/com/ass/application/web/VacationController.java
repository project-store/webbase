package com.ass.application.web;


import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.generated.model.TVacation;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
import com.ass.application.service.VacationService;

@Controller
@RequestMapping("/vacation.do")
public class VacationController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private VacationService vacationService;
	
	
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
	 * 新建休假申请
	 * @param request
	 * @param response
	 * @date wangt 2014年12月12日 上午9:32:20
	 */
	@RequestMapping(params="action=saveVacation")
	public void saveVacation(HttpServletRequest request,HttpServletResponse response)  {
	    String vacationType = request.getParameter("vacationType");
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
	    TVacation vacation = new TVacation();
	    vacation.setVacationType(vacationType);
	    if(StringUtil.isNotEmpty(appHours)){
	    	vacation.setAppHours(new Double(appHours));
	    	
	    }
	    vacation.setReason(reason);
	    vacation.setSource(source);
	    vacation.setStartDate(beginTimeStamp);
	    vacation.setEndDate(endTimeStamp);
	    
	    vacation.setOperatorId(this.getUser().getId());
	    vacation.setMakeTime(DateUtil.getCurrentTimestamp());
	    //保存休假申请，未提交
	    //保存vacation 同时保存了workflowTask
	    Long workflowtaskId = vacationService.saveVacationApply(vacation);
	    map.put("workflowtaskid", workflowtaskId);
	    
	    this.printStr(response, JsonUtil.writeValue(map));
	}
	
	
	
	
	
	
	

}
