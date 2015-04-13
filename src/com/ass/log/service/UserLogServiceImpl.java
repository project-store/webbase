package com.ass.log.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.common.generated.dao.TUserMapper;
import com.ass.common.generated.model.TLog;
import com.ass.common.generated.model.TLogDetail;
import com.ass.common.generated.model.TUser;
import com.ass.common.service.CommonService;
import com.ass.common.utils.StringUtil;

@Service
public class UserLogServiceImpl implements UserLogService {
	
	@Resource
	private LogService logService;
	@Resource
	private CommonService commonService;
	@Resource
	private TUserMapper tUserMapper;
	
	//codetype---properties.codetype
	
	private String propertiesCode = "user";//员工管理
	
	
	/*
	 * 
	 * @see com.ass.log.service.UserLogService#addUser(com.ass.common.generated.model.TUser)
	 * @author wangt 2014年11月26日 下午3:07:59 
	 */
	@Override
	public void addUser(TUser user){
		TLog tLog = new TLog();
		tLog.setLogType(this.propertiesCode);
		tLog.setComment("新建用户"+user.getName()+"("+user.getLoginName()+")。");
		logService.saveLog(tLog, null);
	}
	
	
	



	/*
	 * 保存之前调用
	 * @see com.ass.log.service.UserLogService#editUser(com.ass.common.generated.model.TUser)
	 * @author wangt 2014年11月26日 下午3:33:52 
	 */
	@Override
	public void editUser(TUser user) {
		TLog htLog = new TLog();
		htLog.setLogType(this.propertiesCode);
		htLog.setComment("编辑用户"+user.getName()+"("+user.getLoginName()+")。");
		
		List<TLogDetail> list = new ArrayList<TLogDetail>();
		TUser oldUser = tUserMapper.selectByPrimaryKey(user.getId());
		//name
		if(oldUser.getName() != user.getName()){
			TLogDetail tLogDetail = new TLogDetail();
			tLogDetail.setTableName("TUser");
			tLogDetail.setRecordId(user.getId());
			tLogDetail.setFieldName("name");
			tLogDetail.setFieldOldValue(oldUser.getName());
			tLogDetail.setFieldNewValue(user.getName());
			list.add(tLogDetail);
		}
		//loginName
		if(oldUser.getLoginName() != user.getLoginName()){
			TLogDetail tLogDetail = new TLogDetail();
			tLogDetail.setTableName("TUser");
			tLogDetail.setRecordId(user.getId());
			tLogDetail.setFieldName("loginName");
			tLogDetail.setFieldOldValue(oldUser.getLoginName());
			tLogDetail.setFieldNewValue(user.getLoginName());
			list.add(tLogDetail);
		}
		//mobile
		if(oldUser.getMobile() != user.getMobile()){
			TLogDetail tLogDetail = new TLogDetail();
			tLogDetail.setTableName("TUser");
			tLogDetail.setRecordId(user.getId());
			tLogDetail.setFieldName("mobile");
			tLogDetail.setFieldOldValue(oldUser.getMobile());
			tLogDetail.setFieldNewValue(user.getMobile());
			list.add(tLogDetail);
		}
		//email
		if(oldUser.getEmail() != user.getEmail()){
			TLogDetail tLogDetail = new TLogDetail();
			tLogDetail.setTableName("TUser");
			tLogDetail.setRecordId(user.getId());
			tLogDetail.setFieldName("email");
			tLogDetail.setFieldOldValue(oldUser.getEmail());
			tLogDetail.setFieldNewValue(user.getEmail());
			list.add(tLogDetail);
		}
		
		logService.saveLog(htLog, list);
		
	}
	
	
	
}
