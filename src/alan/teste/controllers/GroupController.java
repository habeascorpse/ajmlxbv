/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.config.AuthenticatedUser;
import alan.teste.config.Secured;
import alan.teste.entities.MocGroup;
import alan.teste.entities.MocUser;
import alan.teste.services.GroupService;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
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
@Path("/group")
@RequestScoped
public class GroupController implements Serializable {

    @Inject
    private GroupService groupModel;

    @Inject
    @AuthenticatedUser
    private MocUser authenticatedUser;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Secured
    //@ requires max >= 0;
    //@ ensures \result.length <= max;
    public MocGroup[] getAllGroups(@QueryParam("maxResult") int max) {

        MocGroup groups[] = groupModel.getAllFromUser(authenticatedUser, max).toArray(new MocGroup[]{});
        return groups;

    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        return Response.ok(groupModel.getByID(id)).build();

    }

}
