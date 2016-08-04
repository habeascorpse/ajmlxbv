/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.entities.MocGroup;
import alan.teste.entities.MocUser;
import alan.teste.entities.UserGroup;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan
 */
@Stateless
public class UserGroupService extends GenericService<UserGroup> {

    @PersistenceContext
    private EntityManager em;
    
    public UserGroupService() {
        super(UserGroup.class);
    }
    
    
    public UserGroup getByUserAndGroup(MocUser user, MocGroup group) {
        
        return (UserGroup) em.createNamedQuery("UserGroup.getByUserAndGroup")
                .setParameter("group", group)
                .setParameter("user", user)
                .getSingleResult();
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
