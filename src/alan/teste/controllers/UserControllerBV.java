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
	
	

}
