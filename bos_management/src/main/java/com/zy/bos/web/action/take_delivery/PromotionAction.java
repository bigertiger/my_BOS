package com.zy.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.zy.bos.domain.take_delivery.Promotion;
import com.zy.bos.service.take_delivery.PromotionService;
import com.zy.bos.utils.BaseAction;

/** 
* @author  philchang 
* @date 2017年12月14日 下午12:48:27 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion> {
	
	@Autowired
	private PromotionService promotionService;
	
	private File titleImgFile;
	private String titleImgFileFileName;
	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}
	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}
	
	
	@Action(value = "promotion_save", 
			results = {@Result(type = "redirect", 
			location = "./pages/take_delivery/promotion.html")})
	public String save() throws IOException {
		String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
		String randomName = UUID.randomUUID() + ext;
		String savePath = ServletActionContext.getServletContext().getRealPath("/upload");
		String imgURl = ServletActionContext.getRequest().getContextPath() + "/upload/";
		FileUtils.copyFile(titleImgFile, new File(savePath, randomName));
		model.setTitleImg(imgURl + randomName);
		promotionService.save(model);
		return SUCCESS;
	}
	
	
	@Action(value = "promotion_page", results = {@Result(type = "json")})
	public String page() {
		Page<Promotion> pageData = promotionService.page(page - 1, rows, model);
		pushPageDataToValueStack(pageData);
		return SUCCESS;
	}

}
