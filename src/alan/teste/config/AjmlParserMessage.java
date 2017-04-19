/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config;

import alan.teste.config.Mapper.PreconditionMapper;
import alan.teste.entities.Message;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alan
 */
public class AjmlParserMessage {
    
    public static Message parser(String error) {
        
        
        String m = error;
        // Extraindo o nome do parâmetro
        int i = m.indexOf("when") + 8;
        m = m.substring(i);
        i = m.indexOf("'");
        m = m.substring(0, i);

        String parameter = m;

        // Extraindo o nome do método
        m = error;
        i = m.indexOf("by method") + 10;
        m = m.substring(i);
        i = m.indexOf(" ");
        String method = m.substring(0, i);

        //Extraindo nome da Classe
        i = method.lastIndexOf(".");
        String classe = method.substring(0, i);

        // Setando nome da classe
        method = method.substring(i + 1);

        StringBuilder filter = new StringBuilder();

        try {
            Class cl = Class.forName(classe);

            for (Method me : cl.getMethods()) {
                if (me.getName().equals(method)) {
                    for (Parameter pm : me.getParameters()) {
                        if (pm.getName().equals(parameter)) {
                            for (Annotation a : pm.getDeclaredAnnotations()) {
                                filter.append(a.annotationType().getName() + ";");
                            }
                        }
                    }
                }
            }
            
            
            Message msg = new Message("403","", "Bad request", parameter, "filter",filter.toString());
            
            return msg;

        } catch (ClassNotFoundException ex1) {
            Logger.getLogger(PreconditionMapper.class.getName()).log(Level.SEVERE, null, ex1);
        } catch (SecurityException ex1) {
            Logger.getLogger(PreconditionMapper.class.getName()).log(Level.SEVERE, null, ex1);
        }
        
        return null;
        
    }
}
