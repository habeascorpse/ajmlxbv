/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.config.Secured;
import alan.teste.entities.Country;
import alan.teste.entities.MocUser;
import alan.teste.services.CountryService;
import alan.teste.services.UserService;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author alan
 */
@Path("/users")
@RequestScoped
public class UserController implements Serializable {

    @Inject
    private UserService userModel;
    @Inject
    private CountryService countryModel;

    @Path("/get/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {

        return Response.ok(userModel.getAll().toArray(new MocUser[]{})).build();

    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    public MocUser getById(@PathParam("id") String id) {

        Long cod = Long.parseLong(id);

        return userModel.getByID(cod);

    }

    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(MocUser user) {
        System.out.println(user.getCountry());
        userModel.createUser(user);
        
        return Response.status(Response.Status.CREATED).build();
        
    }

    @Path("/countries")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Country> getCountries() {

        return countryModel.getAll();
    }

    @Path("/confirm")
    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response confirm(@PathParam("hash") String hash) {

        if (!userModel.confirmUser(hash)) {

            String message = "Congratulation, now you has ready to join in our team!"
                    + "<br> <a href=\"http://cloudmessenger.com.br/moc\">Login</a> ";

            return Response.ok(message).build();
        } else {
            return Response.status(Response.Status.CONFLICT).build();
        }

    }

    @Path("authenticate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response authenticate(MocUser user) {

        return Response.ok().build();

    }

}
