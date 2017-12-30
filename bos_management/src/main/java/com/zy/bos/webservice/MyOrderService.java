package com.zy.bos.webservice;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.zy.bos.domain.take_delivery.Order;

/** 
* @author  philchang 
* @date 2017年12月18日 下午7:23:58 
* @version 1.0.0
*   
*/
@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public interface MyOrderService {
	@POST
	@Path("/saveOrder")
	void saveOrder(Order order);

}
