package com.ass.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ass.common.service.CommonService;


public class FileUtil {

	
	
	
	/**
	 * path 以 / 结尾  
	 * @param 
	 * @param path
	 * @param saveFileName
	 * @return
	 * @throws IOException
	 * @author wangt 2014年11月27日 下午5:22:55 
	 */
	public static boolean saveFile(MultipartFile infile, String path, String saveFileName) throws IOException{
		if (!path.endsWith(File.separator)) {
            path = path + File.separator;
        }
		
		File dir = new File(path);
        if(!dir.exists()){
        	dir.mkdirs();
        }
        if(infile.getOriginalFilename().indexOf(".") == -1){
        	return false;
        }
        OutputStream f2 = new FileOutputStream(path+saveFileName+"."+infile.getOriginalFilename().split("\\.")[1]);
        FileCopyUtils.copy(infile.getInputStream(), f2);
        return true;
	}
	
	
	
	
	
	
	
	/**
     * 从本地下载文件。将文件放入response，返回给客户端浏览器
     * 
     * @param response
     *            页面响应
     * @param filePath
     *            文件所在的绝对路径（包括文件名）本地硬盘上
     * @param fileName
     *            文件名称
     * @throws IOException
     *             IO异常
     */
    public static void download(HttpServletResponse response, String filePath, String fileName) throws IOException {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            inStream = new FileInputStream(filePath);
            // 设置输出的格式
            response.reset();
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("UTF-8");
            String filename = new String(fileName.getBytes("utf-8"), "ISO8859-1");
            response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
            outStream = response.getOutputStream();
            // 循环取出流中的数据
            byte[] b = new byte[4096];
            int len;
            while ((len = inStream.read(b)) != -1){
            	outStream.write(b, 0, len);
            }
            inStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("处理下载文件时发生IO异常");
        }
        finally {
            if (inStream != null) {
                inStream.close();
            }
            if(outStream != null){
            	outStream.close();
            }
        }
    }
    
    /**
     * 获取文件的大小，单个文件不可能到T，所以暂不考虑这种情况。
     * @param fileSize
     * @return
     */
    public static String FormetFileSize(Long fileSize) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        
        String fileSizeString = "";
       
        Long B = 1L;            //1024^0
        Long K = 1024L;         //1024^1
        Long M = 1048576L;      //1024^2
        Long G = 1073741824L;   //1024^3
        Long T = G*1024;
        
        if (fileSize < K) {  
            fileSizeString = df.format((double) fileSize / B) + "B";
        } else if (fileSize < M) {
            fileSizeString = df.format((double) fileSize / K) + "K";
        } else if (fileSize < G) {//1024^3
            fileSizeString = df.format((double) fileSize / M) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / G) + "G";
        }
        return fileSizeString;
    }
    
    
    /**
     *  根据路径删除指定的目录或文件，无论存在与否
     *@param sPath  要删除的目录或文件
     *@return 删除成功返回 true，否则返回 false。
     */
    public static boolean deleteFolder(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 判断目录或文件是否存在
        if (!file.exists()) {  // 不存在返回 false
            return flag;
        } else {
            // 判断是否为文件
            if (file.isFile()) {  // 为文件时调用删除文件方法
                return FileUtil.deleteFile(sPath);
            } else {  // 为目录时调用删除目录方法
                return FileUtil.deleteDirectory(sPath);
            }
        }
    }
    
    /**
     * 删除单个文件
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    
    /**
     * 删除目录（文件夹）以及目录下的文件
     * @param   sPath 被删除目录的文件路径
     * @return  目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = FileUtil.deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = FileUtil.deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }
    
    
    
    public static void main(String[] args) {
		
		 Long G = 1073741824L;   //1024^3
	     Long T = G*1024;
	     
	     System.out.println(T);
	     System.out.println(FileUtil.FormetFileSize(G));
	     System.out.println(FileUtil.FormetFileSize(T));
	     System.out.println(".sdfsfs".indexOf(".") );
	     System.out.println("sdf.sfs".indexOf(".") );
	     System.out.println("sdfsfs.".indexOf(".") );
	     System.out.println("sdfsfs".indexOf(".") );
	     File f = new File("D:\\\\\\tt\\\\erro");
	     f.mkdirs();
	     String a = "aa";
	     a += "b";
	     System.out.println(a);
		
	}
}
