package alan.teste.config.Mapper;

import alan.teste.config.AjmlEntryParserMessage;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jmlspecs.ajmlrac.runtime.JMLEntryPreconditionError;

import alan.teste.entities.Message;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class PreconditionMapper implements ExceptionMapper<JMLEntryPreconditionError> {

    @Override
    public Response toResponse(JMLEntryPreconditionError ex) {

        
        try {
            return Response.status(400).entity(AjmlEntryParserMessage.parser(ex.getMessage())).build();
        } catch (ClassNotFoundException ex1) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
