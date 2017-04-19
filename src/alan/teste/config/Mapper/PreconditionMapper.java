package alan.teste.config.Mapper;

import alan.teste.config.AjmlParserMessage;
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

        
        return Response.status(400).entity(AjmlParserMessage.parser(ex.getMessage())).build();
    }

}
