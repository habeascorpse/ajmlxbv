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
import alan.teste.filters.DocNumber;
import alan.teste.filters.ValidationMessage;

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

    private static List<String> extractAnnotations(Method method, String parameter) throws ClassNotFoundException {

        List<String> annotations = new ArrayList();

        for (Parameter pm : method.getParameters()) {
            if (pm.getName().equals(parameter)) {
                for (Annotation a : pm.getDeclaredAnnotations()) {

                    annotations.add(a.annotationType().getName());
                }
            }
        }

        return annotations;
    }

    private static String extractUrlDoc( Method method) throws ClassNotFoundException {

        DocNumber url = method.getAnnotation(DocNumber.class);
        return url != null ? url.value() : null;

    }

    private static String getValidationMessage( Method method, String parameter) {

        
        for (Parameter pm : method.getParameters()) {
            if (pm.getName().equals(parameter)) {
                ValidationMessage vm = pm.getAnnotation(ValidationMessage.class);
                return vm != null ? vm.value() : null;
            }
        }
        
        return null;

    }

    public static Message parser(String error) throws ClassNotFoundException {
        

        String parameter = extractParameter(error);
        String condition = extractCondition(error);
        String method = extractMethod(error);
        String classe = extractClass(method);

        int i = method.lastIndexOf(".");
        method = method.substring(i + 1);

        Class clazz = Class.forName(classe);
        Method _method = null;

        for (Method me : clazz.getMethods()) {
            if (me.getName().equals(method)) {
                _method = me;

            }
        }

        List<String> annotations = extractAnnotations(_method, parameter);
        String tipo = "";

        String urlDoc = extractUrlDoc( _method);

        int errorCode = 0;
        StringBuilder msgError = new StringBuilder();

        if (annotations.stream().anyMatch(s -> s.endsWith("Filtro"))) {
            tipo = "filter";
            errorCode = Response.Status.BAD_REQUEST.getStatusCode();
            msgError.append("Bad Request:");
        }

        if (annotations.stream().anyMatch(s -> s.endsWith("Resource"))) {
            tipo = "resource";
            errorCode = Response.Status.NOT_FOUND.getStatusCode();
            msgError.append("Not Found:");
        }

        if (annotations.stream().anyMatch(s -> s.endsWith("HttpBody"))) {
            tipo = "Object Body";
            errorCode = Response.Status.PRECONDITION_FAILED.getStatusCode();
            msgError.append("Pre-Condition Failed:");
        }

        msgError.append(" ");
        msgError.append(condition);
        
        String validationMessage = getValidationMessage(_method, parameter);

        Message msg = new Message(errorCode, "", validationMessage, parameter, tipo);
        msg.setDocNumber(urlDoc);
        msg.setType(tipo);

        return msg;

    }
}
