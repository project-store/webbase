package com.ass.common.web;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ass.base.web.BaseController;
@Controller
@RequestMapping("/data.do")
public class DataController extends BaseController {

	public static void printDF(File file,List<BackUp> list){
		
		//如果是文件夹
		if(file.isDirectory()){
			
			File[] f = file.listFiles();
			
			System.out.println(file.getParent()+File.separator + file.getName());
			
			for(int i=0;i<f.length ;i++){
				printDF(f[i],list) ;
			}
		}
		//如果是文件
		if(file.isFile()){
			String filePath = file.getAbsolutePath();
			
			BackUp bu = new BackUp();
			bu.setDateTime(filePath.split("_")[1]);
			bu.setFilePath(filePath);
			
			list.add(bu);
			System.out.println(file.getParent()+File.separator + file.getName());
		}
	}
	
	
	@RequestMapping(params = "action=queryBackUp")
	public @ResponseBody Object  queryBackUp(){
		Map<String,Object> map = getMap();
		List<BackUp> list = new ArrayList<BackUp>();
		printDF(new File("D:/mysql"),list);
		
		map.put("list",list);
		return map;
	}
}









class BackUp{
	
	private String dateTime;
	private String filePath;
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	} 

}




