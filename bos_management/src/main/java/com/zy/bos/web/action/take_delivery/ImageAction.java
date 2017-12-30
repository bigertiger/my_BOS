package com.zy.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月13日 下午9:24:17 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class ImageAction extends BaseAction<Object> {

	// 接收上传来的图片
	private File imgFile;
	// 上传图片的文件名
	private String imgFileFileName;
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	
	@Action(value = "image_upload", results = {@Result(type = "json")})
	public String upload() throws IOException {
		// System.out.println("上传的文件是:" + imgFile);
		// System.out.println("上传的文件名是:" + imgFileFileName);
		UUID uuid = UUID.randomUUID();
		String extName = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
		String fileName = uuid + extName;
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload/");
		String urlPath = ServletActionContext.getRequest().getContextPath() + "/upload/";
		// 第一种处理方式
		// FileUtils.copyFile(imgFile, new File(realPath + File.separator + fileName));
		FileUtils.copyFile(imgFile, new File(realPath, fileName));
		
		
		Map<String, Object> result = new HashMap<>();
		result.put("error", 0);
		result.put("url", urlPath + fileName);
		ActionContext.getContext().getValueStack().push(result);
		
		return SUCCESS;
	}
	
	
}
