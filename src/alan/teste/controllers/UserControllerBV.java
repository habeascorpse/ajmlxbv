package alan.teste.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.jmlspecs.ajmlrac.runtime.JMLEntryDefaultPreconditionError;

import alan.teste.entities.Message;



@Path("user")
@RequestScoped
public class UserControllerBV {
	
	private /*@ spec_public @*/ int i;
	
	public UserControllerBV() {
		i = 7;
	}
	
	
	
	@Path("get/{o}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@ requires o < 30 && o > 0;
	//@ ensures o < i;
	public Response getAll(@PathParam("o") int o) {
		
		Message message = new Message("201","criado","created");
		//String users = "{user: 'alan', password: '123456'}";
		return Response.ok(message).build();
		
	}
	

}
