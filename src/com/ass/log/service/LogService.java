package com.ass.log.service;

import java.util.List;

import com.ass.common.generated.model.TLog;
import com.ass.common.generated.model.TLogDetail;

public interface LogService {

	/**
	 * 保存日志
	 */
	public abstract void saveLog(TLog tLog, List<TLogDetail> list);

}