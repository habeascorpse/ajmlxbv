/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.config;

import alan.teste.entities.Message;

/**
 *
 * @author alan
 */

public class AjmlPostConditionParser {
    
    public static Message parser(String error) {
        
        Message msg = new Message(500, null, error, null, null);
        
        return msg;
    }
    
}
