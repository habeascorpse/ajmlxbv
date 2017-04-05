/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.controllers;

import alan.teste.entities.MocUser;
import alan.teste.services.UserService;
import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.jmlspecs.lang.annotation.Pure;
import org.jmlspecs.lang.annotation.SpecPublic;

/**
 *
 * @author habea
 */
@Path("/authentication")
public class AuthenticationEndpoint {
    
    @EJB
    private UserService userService;
    
    
    protected /*@ spec_public @*/ String token;

    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    //@ requires user.getLogin().length() >= 4;
    //@ requires user.getPassword().length() >= 4;
    //@ ensures validateToken(token) ;
    public Response authenticateUser(MocUser user) throws Exception {
            System.out.println("Tamanho do login:"+user.getLogin().length());
            try {
            // Authenticate the user using the credentials provided
                System.out.println("login:"+user.getLogin()+" senha:"+user.getPassword());
                authenticate(user.getLogin(), user.getPassword());
            }
            catch (Exception ex) {
                throw new NotAuthorizedException(Response.status(Response.Status.UNAUTHORIZED).build());
            }

            // Issue a token for the user
            token = issueToken(user.getLogin());

            // Return the token on the response
            return Response.ok().header("Authorization", token).build();

        
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
    
    @SpecPublic
    @Pure
    public boolean validateToken(String token) {
        
        try {
            // Check if it was issued by the server and if it's not expired
            // Throw an Exception if the token is invalid
            final String secret = "secret";

            final JWTVerifier verifier = new JWTVerifier(secret);
            final Map<String, Object> claims = verifier.verify(token);
        
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
