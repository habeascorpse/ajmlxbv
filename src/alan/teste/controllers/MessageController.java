/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.config.AuthenticatedUser;
import alan.teste.config.Secured;
import alan.teste.entities.MocGroup;
import alan.teste.entities.MocMessage;
import alan.teste.entities.MocUser;
import alan.teste.entities.UserGroup;
import alan.teste.filters.Filtro;
import alan.teste.filters.HttpBody;
import alan.teste.filters.Resource;
import alan.teste.filters.UrlDoc;
import alan.teste.services.GroupService;
import alan.teste.services.MessageService;
import alan.teste.services.UserGroupService;
import java.util.List;
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
import javax.ws.rs.core.NoContentException;
import javax.ws.rs.core.Response;
import org.jmlspecs.lang.annotation.SpecPublic;

/**
 *
 * @author alan
 */
@Path("/message")
@RequestScoped
public class MessageController {

    @Inject
    private MessageService messageService;

    @Inject
    private GroupService groupService;

    @Inject
    @AuthenticatedUser
    private MocUser authenticatedUser;

    @Inject
    private UserGroupService userGroupService;
    
    @SpecPublic
    private List<MocMessage> lista;

    @GET
    @Secured
    @UrlDoc("https://example.com/doc/item/3")
    @Produces(MediaType.APPLICATION_JSON)
    //@ requires group.length() <= 50;
    //@ requires group.length() > 0;
    //@ requires maxResult > 0;
    //@ requires maxResult <= 100;
    //@ ensures \result.size() <= maxResult;
    public List<MocMessage> getMessageByGroup(@Filtro @QueryParam("group") String group, @Filtro @QueryParam("maxResult")  int maxResult) throws NoContentException {

        
        MocGroup groupObj = groupService.getGroupByName(authenticatedUser, group);
        
        if (groupObj != null) {

            UserGroup userGroup = userGroupService.getByUserAndGroup(authenticatedUser, groupObj);
            lista = messageService.getMessageByGroup(userGroup, 5);
            
            return lista;
        } else {
            return null;
        }

    }

    @POST
    @Secured
    @Consumes(MediaType.APPLICATION_JSON)
    //@ requires message.getText().length() > 4;
    //@ requires message.getText().length() < 1000;
    //@ requires group != null;
    //@ requires group.length() > 0 && group.length() < 255;
    public Response sendMessage(@HttpBody MocMessage message,@Filtro @QueryParam("group") String group) {
        MocGroup groupObj = groupService.getGroupByName(authenticatedUser, group);
        if (groupObj != null) {
            message.setUserGroup(userGroupService.getByUserAndGroup(authenticatedUser, groupObj));
            messageService.sendMessage(message);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

    }
    
    @GET
    @Secured
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    //@ requires id > 0;
    public MocMessage getMessage(@Resource  @PathParam("id") long id) throws NoContentException {

            MocMessage message = messageService.getByID( id);
            
            return message;
        
    }

}
