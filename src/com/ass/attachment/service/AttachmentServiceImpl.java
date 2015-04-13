package com.ass.attachment.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ass.common.generated.dao.TAttachmentMapper;
import com.ass.common.generated.model.TAttachment;
import com.ass.common.service.CommonService;
import com.ass.common.utils.FileUtil;
import com.ass.common.utils.StringUtil;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Resource
	private TAttachmentMapper tAttachmentMapper;
	
	@Resource
	private CommonService commonService;
	
	@Override
	public void deleteAttachments(String id) {
		
		if(StringUtil.isNotBlank(id)){
			
			//删除硬盘文件
			TAttachment att = tAttachmentMapper.selectByPrimaryKey(new Long(id));
			FileUtil.deleteFile(att.getFilePath());
			
			//删除附件内容
			String sql = "delete from t_attachment where id = "+id;
			commonService.updateBySql(sql);
			
		}
	}

	@Override
	public void saveAttachment(TAttachment a) {
		tAttachmentMapper.insertSelective(a);
	}

	@Override
	public TAttachment getAttachment(String id) {
		 return tAttachmentMapper.selectByPrimaryKey(new Long(id));
	}

}
