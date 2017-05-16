/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config.Mapper;

import alan.teste.entities.Message;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author habea
 */
public class UnauthorizedMapper implements ExceptionMapper<NotAuthorizedException> {

	
	@Override
	public Response toResponse(NotAuthorizedException ex) {
		Message message = new Message(401,"location",ex.getMessage());
		
		return Response.status(Response.Status.UNAUTHORIZED).entity(message).build();
	}
}
