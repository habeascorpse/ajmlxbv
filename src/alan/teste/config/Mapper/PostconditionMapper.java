package alan.teste.config.Mapper;

import alan.teste.config.AjmlPostConditionParser;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import alan.teste.entities.Message;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.jmlspecs.ajmlrac.runtime.JMLPostconditionError;

public class PostconditionMapper implements ExceptionMapper<JMLPostconditionError> {
    
    
    @Context private HttpServletRequest request;

    @Override
    public Response toResponse(JMLPostconditionError ex) {

        try {
            
            Message msg = AjmlPostConditionParser.parser(ex.getMessage());
            msg.setUri(request.getRequestURL().toString() + "?" + request.getQueryString());
            msg.setMessage("Internal error on Post-Condition: "+ msg.getMessage());
            
            return Response.status(msg.getErrorCode()).entity(msg).build();
            
        } catch (Exception ex1) {
            
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
