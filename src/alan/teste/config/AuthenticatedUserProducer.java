/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config;

import alan.teste.entities.MocUser;
import alan.teste.services.UserService;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;

/**
 *
 * @author habea
 */
@RequestScoped
public class AuthenticatedUserProducer {

    @Produces
    @RequestScoped
    @AuthenticatedUser
    private MocUser authenticatedUser;
    
    @EJB
    private UserService userService;

    public void handleAuthenticationEvent(@Observes @AuthenticatedUser String username) {
        this.authenticatedUser = findUser(username);
    }

    private MocUser findUser(String username) {
        // Hit the the database or a service to find a user by its username and return it
        // Return the User instance
        
        return userService.getByLogin(username);
    }
}
