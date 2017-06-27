/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config;

import alan.teste.entities.Message;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;

/**
 *
 * @author alan
 */
public class AjmlEntryParserMessage {

    private static String extractParameter(String error) {

        String m = error;
        // Extraindo o nome do parâmetro
        int i = m.indexOf("when") + 8;
        m = m.substring(i);
        i = m.indexOf("'");
        m = m.substring(0, i);

        String parameter = m;

        return parameter;

    }
    
    private static String extractCondition(String error) {

        String m = error;
        // Extraindo o nome do parâmetro
        int i = m.indexOf("when") + 4;
        m = m.substring(i);

        String condition = m;

        return condition;

    }

    private static String extractMethod(String error) {

        // Extraindo o nome do método
        String m = error;
        int i = m.indexOf("by method") + 10;
        m = m.substring(i);
        i = m.indexOf(" ");
        String method = m.substring(0, i);

        return method;
    }

    private static String extractClass(String method) {

        //Extraindo nome da Classe
        int i = method.lastIndexOf(".");
        String classe = method.substring(0, i);

        return classe;
    }

    private static List<String> extractAnnotations(String classe, String method, String parameter) throws ClassNotFoundException {

        Class cl = Class.forName(classe);

        List<String> annotations = new ArrayList();

        for (Method me : cl.getMethods()) {
            if (me.getName().equals(method)) {
                for (Parameter pm : me.getParameters()) {
                    if (pm.getName().equals(parameter)) {
                        for (Annotation a : pm.getDeclaredAnnotations()) {

                            annotations.add(a.annotationType().getName());
                        }
                    }
                }
            }
        }

        return annotations;
    }

    public static Message parser(String error) throws ClassNotFoundException {

        String parameter = extractParameter(error);
        String condition = extractCondition(error);
        String method = extractMethod(error);
        String classe = extractClass(method);

        int i = method.lastIndexOf(".");
        method = method.substring(i + 1);
        
        List<String> annotations = extractAnnotations(classe, method, parameter);
        String tipo = "";
        if (annotations.stream().anyMatch(s -> s.endsWith("Filtro")))
            tipo = "filter";
        
        if (annotations.stream().anyMatch(s -> s.endsWith("Resource")))
            tipo = "resource";
        
        int errorCode = tipo.equals("filter") ? Response.Status.PRECONDITION_FAILED.getStatusCode() : Response.Status.NOT_FOUND.getStatusCode();
        
        Message msg = new Message(errorCode, "", "Pre-Condition failed: "+ error, parameter, tipo);

        return msg;

    }
}
