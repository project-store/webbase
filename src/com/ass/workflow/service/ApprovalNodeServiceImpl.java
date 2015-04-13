package com.ass.workflow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.ass.base.model.BaseClass;
import com.ass.base.service.BaseServiceImpl;
import com.ass.common.utils.StringUtil;
import com.ass.workflow.constant.WorkFlowConstant;


@Service
public class ApprovalNodeServiceImpl extends BaseServiceImpl implements ApprovalNodeService {
	
	
	
	//************以下为.properties定义流程的方式做法.**150126*************************************
	
	
	private Map<String, String> getReturnMap(String step, String person){
		Map<String, String> m = new HashMap<String, String>();
		m.put("step", step);
		m.put("person", person);
		return m;
	}
	
	/**
	 * 开始流程，
	 * @param approvalType
	 * @return 流程节点step  下一步参与人id  person
	 * @author wangt 2014年12月3日 上午10:14:47 
	 */
	@Override
	public Map<String, String> firstStep(String approvalType) {
		List<String> lst = this.reloadLst(approvalType);
		return this.getReturnMap(lst.get(0), this.getProp(lst.get(0)));
	}

	/**
	 * 下一步
	 * @param approvalType
	 * @param curStep
	 * @return 流程节点step 结束为"end" |   下一步参与人id  or "end" person
	 * @author wangt 2014年12月3日 上午10:15:09 
	 */
	@Override
	public Map<String, String> nextStep(String approvalType, String curStep) {
		List<String> lst = this.reloadLst(approvalType);
		int now = 0;
		for(int i=0; i<lst.size(); i++){
			if(lst.get(i).equals(curStep)){
				now = i;
				break;
			}
		}
		//now 不会等于 lst.size()-1（最后一个），
		//因为其为倒数第二个的时候，返回倒数第一的值（“end”），这次流程结束了 ,传来的curStep不可能为最后一步。
		if(now+1 == lst.size()-1 || now == lst.size()-1){
			return this.getReturnMap("end", "end");
		}
		return this.getReturnMap(lst.get(now+1), this.getProp(lst.get(now+1)));
	}

	/**
	 * 上一步  ||未使用！！！！！，退回默认退回给发起人。
	 * @param approvalType
	 * @param curStep
	 * @return 流程节点step 发起人为"start"  |  上一步参与人id or "start"(代表发起人)person
	 * @author wangt 2014年12月3日 上午10:15:24 
	 */
	@Override
	public Map<String, String> preStep(String approvalType, String curStep) {
		List<String> lst = this.reloadLst(approvalType);
		int now = 0;
		for(int i=0; i<lst.size(); i++){
			if(lst.get(i).equals(curStep)){
				now = i;//now肯定会被i赋值。 因为curStep 和 approvalType不会传错！
				break;
			}
		}
		if(now == 0){
			return this.getReturnMap("start", "start");
		}else{
			return this.getReturnMap(lst.get(now-1), this.getProp(lst.get(now-1)));
		}
	}
	
	List<String> vacationL = new ArrayList<String>();
	List<String> test1 = new ArrayList<String>();
	List<String> test2 = new ArrayList<String>();
	List<String> test3 = new ArrayList<String>();
	
	/**
	 * 加载指定流程数据节点
	 * @param approvalType
	 * @return
	 * @author wangt 2014年12月3日 上午11:24:05 
	 */
	private List<String> reloadLst(String approvalType){
		if(WorkFlowConstant.Vacation_Approval.equals(approvalType)){
			if(vacationL.size() == 0){
				vacationL = this.loadApprovalList(approvalType);
			}
			return vacationL;
		}
		if("test1".equals(approvalType)){
			if(test1.size() == 0){
				test1 = this.loadApprovalList(approvalType);
			}
			return test1;
		}
		if("test2".equals(approvalType)){
			if(test2.size() == 0){
				test2 = this.loadApprovalList(approvalType);
			}
			return test2;
		}
		if("test3".equals(approvalType)){
			if(test3.size() == 0){
				test3 = this.loadApprovalList(approvalType);
			}
			return test3;
		}
		
		//永远走不到return null、( 除非乱传参数)参数用CommonConstant里面的静态变量
		return null;
	}
	
	private List<String> loadApprovalList(String approvalType){
		String[] s = StringUtil.split(this.getProp(approvalType),",");
		return Arrays.asList(s);
	}
}
