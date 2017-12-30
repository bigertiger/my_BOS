package com.zy.bos.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.zy.bos.domain.take_delivery.Promotion;
import com.zy.bos.domain.webservice.PageBean;

/** 
* @author  philchang 
* @date 2017年12月14日 下午6:33:52 
* @version 1.0.0
*   
*/
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface MyPromotionService {
	
	@GET
	@Path("/page")
	PageBean<Promotion> page(@QueryParam("page")Integer page, @QueryParam("rows")Integer rows);

}
