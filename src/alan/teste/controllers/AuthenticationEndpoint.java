/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.entities.MocUser;
import alan.teste.services.UserService;
import com.auth0.jwt.JWTSigner;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 *
 * @author habea
 */
@Path("/authentication")
public class AuthenticationEndpoint {
    
    @EJB
    private UserService userService;
    
    
    protected /*@ spec_public @*/ String token;

    
    //@ requires username.length() >= 4 && username.length() <= 20;
    //@ requires password.length() >= 4 && password.length() <= 20;
    //@ ensures token != null && token.length() > 0;
    @POST
    @Produces("application/json")
    @Consumes("application/x-www-form-urlencoded")
    public Response authenticateUser(@FormParam("username") String username,
            @FormParam("password") String password) throws Exception {

            try {
            // Authenticate the user using the credentials provided
                authenticate(username, password);
            }
            catch (Exception ex) {
                throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            // Issue a token for the user
            token = issueToken(username);

            // Return the token on the response
            return Response.ok(token).build();

        
    }

    private void authenticate(String username, String password) throws Exception {
        // Authenticate against a database, LDAP, file or whatever
        // Throw an Exception if the credentials are invalid
        MocUser user = userService.Authenticate(username, password);
        if (user == null) {
            throw new RuntimeException("Authentication failed");
        }
    }

    private String issueToken(String username) {
        // Issue a token (can be a random String persisted to a database or a JWT token)
        // The issued token must be associated to a user
        // Return the issued token

        //final String username = "https://moc.com";
        final String secret = "secret";

        final long iat = System.currentTimeMillis() / 1000l; // issued at claim 
        final long exp = iat + 1440000L; // expires claim. In this case the token expires in 60 seconds

        final JWTSigner signer = new JWTSigner(secret);
        final HashMap<String, Object> claims = new HashMap<String, Object>();
        claims.put("username", username);
        claims.put("exp", exp);
        claims.put("iat", iat);

        return signer.sign(claims);
    }
}
