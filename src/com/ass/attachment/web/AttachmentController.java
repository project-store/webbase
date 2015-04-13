package com.ass.attachment.web;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ass.attachment.service.AttachmentService;
import com.ass.base.web.BaseController;
import com.ass.common.generated.model.TAttachment;
import com.ass.common.service.CommonService;
import com.ass.common.utils.DateUtil;
import com.ass.common.utils.FileUtil;
import com.ass.common.utils.JsonUtil;

/**
 * 
 */
@Controller
@RequestMapping("/attachment")
public class AttachmentController extends BaseController {

	@Resource
	private CommonService commonService;
	
	@Resource
	private AttachmentService attachmentService;
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @author wangt 2014年12月4日 下午3:34:58 
	 */
	@RequestMapping(params = "action=delete")
	public void deleteAttachment(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		attachmentService.deleteAttachments(id);
		this.printStr(response, JsonUtil.writeValue(this.getMap()));
	
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @author wangt 2014年12月4日 下午3:34:55 
	 */
	@RequestMapping(params = "action=upload")
	public @ResponseBody Object upload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// 转型为MultipartHttpRequest  
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;  
        // 根据前台的name名称得到上传的文件  
        MultipartFile file = multipartRequest.getFile("uploadFile"); 
        String filename = request.getParameter("filename");
        String remark = request.getParameter("remark");
        String bussId = request.getParameter("bussId");
        String bussType = request.getParameter("bussType");
        Map<String,Object> m = this.getMap();
        m.put("flag", "ok");
        if(file.getSize() > 0){
        	//1 上传  2保存
        	//文件命名方式：  uploadPath + bussType + yyyMMdd
        	if(file.getOriginalFilename().indexOf(".") == -1){
        		m.put("msg", "上传文件名称没有后缀。");
            	m.put("flag", "no");
//            	this.printStr(response, JsonUtil.writeValue(m));//会变成下载response.setContentType("text/json;charset=utf-8");
            	return m;
            }
        	Date now = new Date();
        	String originalName = file.getOriginalFilename().split("\\.")[0];
        	String originalSuffix = file.getOriginalFilename().split("\\.")[1];
        	String path = this.getProp("uploadPath");
        	path = path+bussType+File.separator+DateUtil.getDateStr(now, "yyyyMM")+File.separator;
        	String saveFileName = originalName+"_"+DateUtil.getTimestampByDate(now);
        	//1上传
        	if(FileUtil.saveFile(file, path, saveFileName)){
        		//2保存
        		TAttachment att = new TAttachment();
        		att.setFilePath(path+saveFileName+"."+originalSuffix);//完整路径
        		att.setFileName(filename);
        		att.setBussType(bussType);
        		att.setBussId(new Long(bussId));
        		att.setRemark(remark);
        		att.setFileSuffix(originalSuffix);
        		att.setCreateTime(DateUtil.getTimestampByDate(now));
        		att.setOperatorId(this.getUser().getId());
        		attachmentService.saveAttachment(att);
//        		this.printStr(response, JsonUtil.writeValue(m));//会变成下载response.setContentType("text/json;charset=utf-8");
        		return m;
        	}else{
        		m.put("msg", "上传过程出错。");
            	m.put("flag", "no");
//            	this.printStr(response, JsonUtil.writeValue(m));//会变成下载response.setContentType("text/json;charset=utf-8");
            	return m;
        	}
        }else{
        	m.put("msg", "文件不能为空。");
        	m.put("flag", "no");
//        	this.printStr(response, JsonUtil.writeValue(m));//会变成下载response.setContentType("text/json;charset=utf-8");
        	return m;
        }
        
	}
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author wangt 2014年12月4日 下午3:34:42 
	 */
	@RequestMapping(params = "action=download")
	public void download(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String id = request.getParameter("id");
		TAttachment att = attachmentService.getAttachment(id);
		FileUtil.download(response, att.getFilePath(), att.getFileName()+"."+att.getFileSuffix());
	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
