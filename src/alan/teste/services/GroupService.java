/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.entities.MocGroup;
import alan.teste.entities.MocUser;
import alan.teste.entities.UserGroup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan
 */
@Stateless
public class GroupService extends GenericService<MocGroup> {
    
    @PersistenceContext
    private EntityManager em;

    public GroupService() {
        super(MocGroup.class);
    }

    public List<MocGroup> getAllFromUser(MocUser user, int max) {
        
        if (max == 0)
            max = 10;
            List<MocGroup> groups = getEntityManager().createNamedQuery("Group.getAllByUser")
                    .setParameter("user", user)
                    .setMaxResults(max)
                    .getResultList();
            return groups;
        
    }
    
    public MocGroup newGroupFromContactConfirmation(MocUser user1, MocUser user2) {
        
        MocGroup group = MocGroup.newGroupFromContact(user1, user2);
        
        
        return this.save(group);
    }
    
    public MocGroup getGroupByName(MocUser user, String groupName) {
        
        return (MocGroup) getEntityManager().createNamedQuery("Group.getAllByUserAndName")
                .setParameter("user", user)
                .setParameter("group", groupName)
                .setMaxResults(50)
                .getSingleResult();
        
    }
    
    public MocGroup createGroup(MocUser user, String name) {
        MocGroup group = new MocGroup();
        group.setName(name);
        group.getListUserGroup().add(new UserGroup(group, user));
        
        this.save(group);
        return group;
    }
    
    public void addUserToGroup(MocUser user, MocGroup group) {
        group.getListUserGroup().add(new UserGroup(group, user));
        
        this.merge(group);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
}
