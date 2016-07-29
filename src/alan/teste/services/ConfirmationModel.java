/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.entities.ConfirmationUser;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan
 */
@Stateless
public class ConfirmationModel extends GenericService<ConfirmationUser> {

    @PersistenceContext
    private EntityManager em;
    public ConfirmationModel() {
        super(ConfirmationUser.class);
    }
    
    public ConfirmationUser getByHash(String hash) {
        
        try {
            return (ConfirmationUser)
            getEntityManager().createNamedQuery("ConfirmationUser.getByHash")
                .setParameter("hash", hash)
                .getSingleResult();
        }
        catch (NoResultException ex) {
            return null;
        }
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
