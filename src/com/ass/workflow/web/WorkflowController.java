package com.ass.workflow.web;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ass.base.web.BaseController;
import com.ass.common.generated.dao.TUserMapper;
import com.ass.common.generated.dao.TWorkflowTaskDetailMapper;
import com.ass.common.generated.model.TUser;
import com.ass.common.generated.model.TVacation;
import com.ass.common.generated.model.TWorkflowTaskDetail;
import com.ass.common.generated.model.TWorkflowTaskDetailExample;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
import com.ass.workflow.service.WorkFlowService;

/**
 */
@Controller
@RequestMapping("/workflow.do")
public class WorkflowController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private WorkFlowService workflowService;
	
	@Resource
	private TUserMapper tUserMapper;
	
	@Resource
	private TWorkflowTaskDetailMapper tWorkflowTaskDetailMapper;
	
	
	/** 获取当前登录人的常用审批人列表
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月26日 下午12:21:02 
	 */
	@RequestMapping(params="action=getUsualApprover")
	public void getUsualApprover(HttpServletRequest request, HttpServletResponse response) {
		
		Long curId = this.getUser().getId();
		
		String sql = "select tua.approver_id uid , concat(tu.name,'(',tu.login_name,')') uname "
				+ " from t_usual_approver tua"
				+ " left join t_user tu on tu.id = tua.approver_id "
				+ "  where tua.uid = "+curId;
		List<Map<String, Object>> lst =  commonService.selectBySql(sql);
		this.printStr(response, JsonUtil.writeValue(lst));
		
	}
	
	/**删除当前登录人的常用审批人
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月26日 下午12:34:17 
	 */
	@RequestMapping(params="action=deleteUsualApprover")
	public void deleteUsualApprover(HttpServletRequest request, HttpServletResponse response) {
		String approverId = request.getParameter("approverId");
		Long curId = this.getUser().getId();
		String sql = "delete from t_usual_approver where uid = "+curId +" and approver_id = "+approverId;
		commonService.deleteBySql(sql);
	}
	
	
	
	
	
	/**申请流程 or 流程下一步
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月26日 下午9:34:58 
	 */
	@RequestMapping(params="action=applyOrNext")
	public void applyOrNext(HttpServletRequest request, HttpServletResponse response) {
		
		Map<String, Object> m = this.getMap();
		
		String comment = request.getParameter("comment");
		String nextParticipant = request.getParameter("nextParticipant");
		String workflowTaskId = request.getParameter("workflowTaskId");
		
		//判断当前登录人的isdelete为0
		TUser u = tUserMapper.selectByPrimaryKey(new Long(nextParticipant));
		if(1 == u.getIsdelete()){
			m.put("ok", "下一步参与人不存在,请重新选择.");
			this.printStr(response, JsonUtil.writeValue(m));
			return ;
		}else if(u.getId() == this.getUser().getId()){
			m.put("ok", "不能提交给自己,请重新选择.");
			this.printStr(response, JsonUtil.writeValue(m));
			return ;
		}
		m.put("ok", "ok");
		workflowService.applyOrNextFlow(workflowTaskId, comment, nextParticipant);
		this.printStr(response, JsonUtil.writeValue(m));
		return ;
		
	}
	
	
	/**根据workflowtaskid 获得 workflowtaskdetail的详情,放到页面上展示
	 * @param request
	 * @param response
	 * void
	 * @author wangt 2015年1月29日 下午10:53:16 
	 */
	@RequestMapping(params="action=taskDetailComments")
	public void getWorkflowTaskDetailComments(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> m = this.getMap();
		String workflowtaskid = request.getParameter("workflowtaskid");
		Map<String, String > param = new HashMap<String, String>();
		param.put("workflowtaskid", workflowtaskid);
		List<Map<String ,Object>> lst = commonService.selectList("workFlow_SpecSql.detailComments", param);
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<lst.size(); i++){
			sb.append("<p  class='text-success'>").append(lst.get(i).get("uname")).append(": ")
			.append(lst.get(i).get("operate")).append("。 ").append(lst.get(i).get("time2")).append("</p>")
			.append("<p class='text-info'>意见: ").append(lst.get(i).get("comment2")).append("</p>");
		}
		m.put("comments", sb.toString());
		this.printStr(response, JsonUtil.writeValue(m));
		
	}
	
	
	
	

}
