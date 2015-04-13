package com.ass.attachment.service;

import com.ass.common.generated.model.TAttachment;

public interface AttachmentService {
	/**
	 * 删除附件
	 * @param ids
	 * @author wangt 2014年12月1日 下午5:24:07 
	 */
	public void deleteAttachments(String ids);
	
	
	public void saveAttachment(TAttachment a);
	
	
	public TAttachment getAttachment(String id);
	
}
