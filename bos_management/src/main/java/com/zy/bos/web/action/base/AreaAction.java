package com.zy.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.hibernate.engine.spi.CascadingActions.BaseCascadingAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zy.bos.domain.base.Area;
import com.zy.bos.domain.base.Courier;
import com.zy.bos.service.base.AreaService;
import com.zy.bos.utils.BaseAction;
import com.zy.bos.utils.PageBean;
import com.zy.bos.utils.PinYin4jUtils;

/** 
* @author  philchang 
* @date 2017年12月3日 下午5:57:58 
* @version 1.0.0
*   
*/
@SuppressWarnings("all")
@ParentPackage("json-default")
@Namespace("/")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area> {

	
	
	@Autowired
	private AreaService areaService;
	
	// 接收上传来的文件
	private File file;


	public void setFile(File file) {
		this.file = file;
	}
	
	// 导入区域信息的action
	@Action(value = "area_import")
	public String areaImport() throws Exception {
		List<Area> areas = new ArrayList<Area>();
		HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = book.getSheetAt(0);
		for (Row row : sheet) {
			if(row.getRowNum() == 0) {
				continue;
			}
			if(row.getCell(0) == null || 
					StringUtils.isBlank(row.getCell(0).getStringCellValue())) {
				continue;
			}
			
			Area area = new Area();
			area.setId(row.getCell(0).getStringCellValue());
			area.setProvince(row.getCell(1).getStringCellValue());
			area.setCity(row.getCell(2).getStringCellValue());
			area.setDistrict(row.getCell(3).getStringCellValue());
			area.setPostcode(row.getCell(4).getStringCellValue());
			
			// 获得area的简码
			String province = area.getProvince();
			String city = area.getCity();
			String district = area.getDistrict();
			province = province.substring(0, province.length()-1);
			city = city.substring(0, city.length()-1);
			district = district.substring(0, district.length()-1);
			String[] head = PinYin4jUtils.getHeadByString(province + city + district);
			StringBuffer sb = new StringBuffer();
			for (String string : head) {
				sb.append(string);
			}
			
			// 设置area的简码
			area.setShortcode(sb.toString());
			
			// 设置area的城市编码
			area.setCitycode(PinYin4jUtils.hanziToPinyin(city, ""));
			
			
			areas.add(area);
		}
		areaService.save(areas);
		
		return NONE;
	}
	
	
	// 分页查询区域信息的action
	@Action(value = "area_page", results = {@Result(type = "json")})
	public String page() {
		Page<Area> pageData =  areaService.findPage(model, page-1, rows);
		pushPageDataToValueStack(pageData);
		/*PageBean<Area> page = new PageBean<>();
		page.setTotal(pageData.getTotalElements());
		page.setRows(pageData.getContent());
		ActionContext.getContext().getValueStack().push(page);*/
		return SUCCESS;
	}

	
	

}
