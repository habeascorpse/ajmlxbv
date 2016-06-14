package alan.teste.config;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import org.jmlspecs.ajmlrac.runtime.JMLExitNormalPostconditionError;

import alan.teste.entities.Message;

public class PostconditionMapper implements ExceptionMapper<JMLExitNormalPostconditionError> {

	@Override
	public Response toResponse(JMLExitNormalPostconditionError ex) {
		Message message = new Message("502", "location", ex.getMessage());

		return Response.status(502).entity(message).build();
	}

}
