package alan.teste.config.Mapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import alan.teste.entities.Message;
import org.jmlspecs.ajmlrac.runtime.JMLPostconditionError;

public class PostconditionMapper implements ExceptionMapper<JMLPostconditionError> {

	@Override
	public Response toResponse(JMLPostconditionError ex) {
		Message message = new Message("500", "location", ex.getMessage());

		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
	}

}
