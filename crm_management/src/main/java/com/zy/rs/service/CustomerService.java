package com.zy.rs.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.itcast.crm.domain.Customer;

/** 
* @author  philchang 
* @date 2017年12月7日 下午3:00:10 
* @version 1.0.0
*   
*/
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface CustomerService {
	
	
	@GET
	@Path("/findCustomerByAddress")
	Customer findCustomerByAddress(@QueryParam("address")String address);
	
	@GET
	@Path("/findCustomerNull")
	List<Customer> findCustomerNull();
	
	@GET
	@Path("/findCustomerByFid/{fixedAreaId}")
	List<Customer> findCustomerByFid(@PathParam("fixedAreaId") String fid);
	
	
	@PUT
	@Path("/customerToFixedArea")
	void customerToFixedArea(@QueryParam("cidStr") String cidStr, @QueryParam("fid") String fid);
	
	@POST
	@Path("/saveCustomer")
	void saveCustomer(Customer customer);
	
	@GET
	@Path("/findByTelephone/{telephone}")
	Customer findByTelephone(@PathParam("telephone")String telephone);
	
	@GET
	@Path("/updateType/{telephone}")
	void updateType(@PathParam("telephone")String telephone);
	
	
	@GET
	@Path("/login")
	Customer login(@QueryParam("telephone")String telephone, @QueryParam("password")String password);

}
