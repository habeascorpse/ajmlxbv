/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.entities.MocMessage;
import alan.teste.entities.UserGroup;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan
 */
@Stateless
public class MessageService extends GenericService<MocMessage> {
    
    @PersistenceContext
    private EntityManager em;

    public MessageService() {
        super(MocMessage.class);
    }
    
    public List<MocMessage> getMessageByGroup(UserGroup userGroup, int maxResult) {
        
        if (maxResult == 0) {
            maxResult = 50;
        }
        
        List<MocMessage> list = getEntityManager().createNamedQuery("Message.getMessagesByGroup")
                .setParameter("group", userGroup.getMocGroup())
                .setMaxResults(maxResult)
                .getResultList();
        
        Collections.reverse(list);
        
        return list;
        
    } 
    
    public void sendMessage(MocMessage message) {
        this.save(message);
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
