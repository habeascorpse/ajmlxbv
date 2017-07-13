package alan.teste.config.Mapper;

import alan.teste.config.AjmlEntryParserMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jmlspecs.ajmlrac.runtime.JMLEntryPreconditionError;

import alan.teste.entities.Message;
import com.auth0.jwt.internal.org.apache.commons.lang3.exception.ExceptionUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

@Provider
public class PreconditionMapper implements ExceptionMapper<JMLEntryPreconditionError> {
    
    @Context private HttpServletRequest request;

    @Override
    public Response toResponse(JMLEntryPreconditionError ex) {

        
        try {
            Message msg = AjmlEntryParserMessage.parser(ex.getMessage());
            msg.setUri(request.getRequestURL().toString()+"?"+request.getQueryString());
            return Response.status(msg.getErrorCode()).entity(msg).build();
        } catch (ClassNotFoundException ex1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } 
    }

}
