package alan.teste.config.Mapper;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jmlspecs.ajmlrac.runtime.JMLEntryPreconditionError;

import alan.teste.entities.Message;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;

@Provider
public class PreconditionMapper implements ExceptionMapper<JMLEntryPreconditionError> {

    @Override
    public Response toResponse(JMLEntryPreconditionError ex) {

        String m = ex.getMessage();
        int i = m.indexOf("when") + 8;
        m = m.substring(i);
        i = m.indexOf("'");
        m = m.substring(0, i);

        String parameter = m;

        m = ex.getMessage();
        i = m.indexOf("by method") + 10;
        m = m.substring(i);
        i = m.indexOf(" ");
        String method = m.substring(0, i);

        String classe;

        i = method.lastIndexOf(".");
        classe = method.substring(0, i);

        method = method.substring(i + 1);

        StringBuilder filter = new StringBuilder();

        try {
            Class cl = Class.forName(classe);

            for (Method me : cl.getMethods()) {
                System.out.println("Método: "+me.getName()+ ", método comparacao: "+method);
                if (me.getName().equals(method)) {
                    for (Parameter pm : me.getParameters()) {
                        System.out.println("Parametro: "+pm.getName()+", parametro comparacao: "+parameter);
                        if (pm.getName().equals(parameter)) {
                            for (Annotation a : pm.getDeclaredAnnotations()) {
                                filter.append(a.annotationType().getName() + ";");
                            }
                        }
                    }
                }
            }

        } catch (ClassNotFoundException ex1) {
            Logger.getLogger(PreconditionMapper.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (SecurityException ex1) {
            Logger.getLogger(PreconditionMapper.class.getName()).log(Level.SEVERE, null, ex1);
        }

        Message message = new Message("400", "location", "class:" + classe + ", method:" + method + ", parameter:" + parameter + ", filter:" + filter.toString(), parameter);

        return Response.status(400).entity(message).build();
    }

}
