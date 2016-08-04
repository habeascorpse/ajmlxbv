package alan.teste.config.Mapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jmlspecs.ajmlrac.runtime.JMLEntryPreconditionError;

import alan.teste.entities.Message;

@Provider
public class PreconditionMapper implements ExceptionMapper<JMLEntryPreconditionError> {

	
	@Override
	public Response toResponse(JMLEntryPreconditionError ex) {
		Message message = new Message("400","location",ex.getMessage());
		
		return Response.status(400).entity(message).build();
	}
	

}
