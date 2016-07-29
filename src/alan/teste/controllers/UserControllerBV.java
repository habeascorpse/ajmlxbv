package alan.teste.controllers;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import alan.teste.entities.Message;
import alan.teste.services.MessageService;
import javax.ejb.EJB;
import javax.ejb.Stateless;



@Path("user")
@Stateless
public class UserControllerBV {
	
	private /*@ spec_public @*/ int i;
        
        @EJB
        private MessageService messageService;
	
	public UserControllerBV() {
		i = 7;
	}
	
	
	
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@ requires o < 30 && o > 0;
	//@ ensures o < i;
	public Response getAll(@PathParam("id") int o) {
            
            int p = messageService.calcular(o);
		
		Message message = new Message("201","criado","created: "+ p);
		//String users = "{user: 'alan', password: '123456'}";
		return Response.ok(message).build();
		
	}
	

}
