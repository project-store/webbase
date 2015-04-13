package com.ass.test.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ass.base.model.PageGrid;
import com.ass.base.model.PageQueryModel;
import com.ass.base.web.BaseController;
import com.ass.common.generated.dao.TTestMapper;
import com.ass.common.generated.model.TTest;
import com.ass.common.generated.model.TTestExample;
import com.ass.common.service.CommonService;
import com.ass.common.utils.JsonUtil;
import com.ass.common.utils.StringUtil;
import com.ass.workflow.service.WorkFlowService;
@Controller
@RequestMapping("/t.do")
public class TestWorkflowController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private WorkFlowService workFlowService;

	@RequestMapping(params = "action=1")
	public void test1(HttpServletRequest r){
		workFlowService.applyFlow("6", "申请流程leader-leader-end");
	}

	@RequestMapping(params = "action=2")
	public void test2(HttpServletRequest r){
		workFlowService.nextStep("6", "通过u了，下一步！");
	}
	
	
	
	
	
	
	
	
	
	
	
}
