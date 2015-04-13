package com.ass.log.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ass.base.service.BaseServiceImpl;
import com.ass.common.generated.dao.TLogDetailMapper;
import com.ass.common.generated.dao.TLogMapper;
import com.ass.common.generated.model.TLog;
import com.ass.common.generated.model.TLogDetail;
import com.ass.common.utils.DateUtil;
import com.ass.shiro.dto.CurUser;



@Service
public class LogServiceImpl extends BaseServiceImpl implements LogService{

	@Resource
	private TLogMapper tLogMapper;
	@Resource
	private TLogDetailMapper tLogDetailMapper;
	
	/*
	 * 
	 * @see com.ass.log.service.LogService#saveLog(com.ass.common.generated.model.TLog, java.util.List)
	 * @author wangt 2014年11月26日 下午2:56:16 
	 */
	@Override
	public void saveLog(TLog tLog,List<TLogDetail> list) {
		
		CurUser user  = this.getUser();
		
		tLog.settUserId(user.getId());
		tLog.setCreateTime(DateUtil.getCurrentTimestamp());
		
		tLogMapper.insertSelective(tLog);
		
		Long tLogId = tLog.getId();
		if(list!=null){
			for(TLogDetail logDetail:list){
				logDetail.settLogId(tLogId);
				tLogDetailMapper.insertSelective(logDetail);
			}
		}
	}
	
	
	
	
	
}
