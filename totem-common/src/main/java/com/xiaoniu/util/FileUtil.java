package com.xiaoniu.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public abstract class FileUtil {

	/**
	 * 上传文件,是多文件上传,文件验证就在前端验证.比如限制文件上传格式
	 * 需要修改上传文件大小的话,就修改配置文件spring-mvc.xml
	 * @param goalFolder 目标文件夹（上传的目的地）
	 * @param request
	 * @return 返回上传后的新文件路径
	 */
	public static Map<String,String> upLoadFile(String goalFolder,HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		CommonsMultipartResolver mutilpartResolver = 
				new CommonsMultipartResolver();
		Map<String,String> retMap = new HashMap<String,String>();

		// request如果是Multipart类型、
		if (mutilpartResolver.isMultipart(request)) {
			// 转成 MultipartHttpServletRequest
			MultipartResolver resolver = new CommonsMultipartResolver(
					context);
			MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
			
			// 目标地址
			String projectAddress = context.getRealPath("/"+goalFolder);
			//String projectAddress = "E:/local/tomcateFile"+"/"+goalFolder;
			Iterator<String> it = multiRequest.getFileNames();
			while (it.hasNext()) {
				String textName = it.next();
				// 获取MultipartFile类型文件
				List<MultipartFile> fileList = multiRequest.getFiles(textName);
				for(MultipartFile fileDetail:fileList){
					if (fileDetail != null && fileDetail.getSize() >= 1) {
						String path = getFileAddress(fileDetail, projectAddress);

						path = path.replaceAll("\\\\", "/");
						try {
							// 将上传文件写入到指定文件出、核心！
//							fileDetail.transferTo(localFile);
							// 非常重要、有了这个想做什么处理都可以 ,可以自己处理
							InputStream in =  fileDetail.getInputStream();
							FileOutputStream out = new FileOutputStream(path);
							int b = 0;
							while((b = in.read()) != -1){  
								out.write(b);  
							}  
							in.close();  
							out.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						path = path.substring(path.indexOf(goalFolder));
						if(retMap.get(textName) == null){
							retMap.put(textName, path);
						}else{
							retMap.put(textName, retMap.get(textName)+","+path);
						}
					}
				}
			}
		}
		return retMap;
	}

	/**
	 * 创建或者获取文件目录,并且修改文件名称
	 *
	 * @param fileDetail
	 * @return
	 */
	private static String getFileAddress(MultipartFile fileDetail,String projectAddress) {
		String[] fileNames = fileDetail.getOriginalFilename().split("\\.");
		String fileNamedir = projectAddress + "/";
		File localFileDir = new File(fileNamedir);
		if (!localFileDir.exists()) {
			localFileDir.mkdirs();
		}
		// 随机前缀
		String fileName = /*Kit.get32UUID() +*/ "." + fileNames[fileNames.length - 1];
		String path = fileNamedir + fileName;
		return path;
	}

}
