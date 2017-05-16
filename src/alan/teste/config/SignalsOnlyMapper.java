package alan.teste.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.jmlspecs.ajmlrac.runtime.JMLExceptionalPostconditionError;

import alan.teste.entities.Message;

public class SignalsOnlyMapper implements ExceptionMapper<JMLExceptionalPostconditionError> {

	@Override
	public Response toResponse(JMLExceptionalPostconditionError ex) {
		Message message = new Message(500, "location", ex.getMessage());

		return Response.status(500).entity(message).build();
	}

}
