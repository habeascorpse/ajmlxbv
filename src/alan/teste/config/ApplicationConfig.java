package alan.teste.config;

import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/app")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {

        resources.add(alan.teste.config.Mapper.PreconditionMapper.class);
        resources.add(alan.teste.config.Mapper.PostconditionMapper.class);
        resources.add(alan.teste.config.Mapper.UnauthorizedMapper.class);
        resources.add(alan.teste.controllers.UserController.class);
        resources.add(alan.teste.controllers.ContactController.class);
        resources.add(alan.teste.controllers.GroupController.class);
        resources.add(alan.teste.controllers.MessageController.class);
        resources.add(alan.teste.controllers.AuthenticationEndpoint.class);
        resources.add(alan.teste.config.auth.AuthenticationFilter.class);
    }

}
