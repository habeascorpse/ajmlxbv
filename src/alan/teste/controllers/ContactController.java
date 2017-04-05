/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.config.AuthenticatedUser;
import alan.teste.config.Secured;
import alan.teste.entities.MocUser;
import alan.teste.services.UserService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alan
 */
@Path("contact")
@RequestScoped

public class ContactController {

    @Inject
    private UserService userService;

    @Inject
    @AuthenticatedUser
    private MocUser authenticatedUser;

    
    @POST
    @Secured
    //@ requires contact.length() > 4;
    public Response addContact(@QueryParam("contact") String contact) {
        
        MocUser usuario = userService.getByID(authenticatedUser.getId());

        MocUser userContact = userService.getByLogin(contact);
        System.out.println("usuário: "+authenticatedUser);
        System.out.println("contato: "+userContact);
        userService.addContact(usuario, userContact);

        return Response.ok().build();

    }

    @Path("/confirm")
    @POST
    @Secured
    @Consumes(MediaType.TEXT_PLAIN)
    //@ requires contact.length() > 4;
    public Response confirmContact(String contact) {

        MocUser userContact = userService.getByLogin(contact);
        userService.confirmContact(authenticatedUser, userContact);

        return Response.ok().build();
    }

    
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    //@ requires search.length() >= 1;
    //@ requires search.length() < 20;
    public Response findUsers(@QueryParam("search") String search) {

        return Response.ok(userService.search(search, authenticatedUser).toArray(new MocUser[]{})).build();

    }

}
