package alan.teste.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import alan.teste.util.Obturador;

@Path("user")
public class UserControllerBV {
	
	
	@Path("get")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {
		String users = "{user: 'alan', password: '123456'}";
		
		Obturador obt = new Obturador();
		
		obt.isOk(57);
		
		return Response.ok(users).build();
		
	}

}
