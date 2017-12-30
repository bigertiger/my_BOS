package com.zy.bos.domain.webservice;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.zy.bos.domain.take_delivery.Promotion;

/** 
* @author  philchang 
* @date 2017年12月14日 下午6:37:16 
* @version 1.0.0
 * @param <T>
*   
*/
@XmlRootElement(name = "pageBean")
@XmlSeeAlso({Promotion.class})
public class PageBean<T> {
	
	private Long totalCount;
	private List<T> pageData;
	public Long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getPageData() {
		return pageData;
	}
	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	

}
