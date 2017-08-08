/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config.Mapper;

import alan.teste.entities.Message;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import org.aspectjml.ajmlrac.runtime.JMLExitExceptionalPostconditionError;

/**
 *
 * @author habea
 */
public class SingnalOnly implements ExceptionMapper<JMLExitExceptionalPostconditionError> {
    
    @Override
    
	public Response toResponse(JMLExitExceptionalPostconditionError ex) {
		Message message = new Message(500,"location",ex.getMessage());
		
		return Response.status(400).entity(message).build();
	}
    
}
