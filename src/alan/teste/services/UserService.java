/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.config.HashGenerator;
import alan.teste.config.SendMail;
import alan.teste.entities.ConfirmationUser;
import alan.teste.entities.MocUser;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author habea
 */
@Stateless
public class UserService extends GenericService<MocUser>  {
    
    @PersistenceContext
    private EntityManager em;

    public UserService() {
        super(MocUser.class);
    }
    
    @EJB
    private GroupService groupService;
    @EJB
    private ConfirmationModel confirmationModel;
    
    
    public MocUser getByLogin(String login) {

        try {
            return (MocUser) getEntityManager()
                    .createNamedQuery("User.getByLogin")
                    .setParameter("login", login)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    
    public List<MocUser> search(String search, MocUser user) {

        try {
            
            List<MocUser> contacts =  getEntityManager()
                    .createNamedQuery("User.search")
                    .setParameter("search", "%"+search+"%")
                    .getResultList();
            contacts.remove(user);
            contacts.removeAll(user.getContacts());
            
            return contacts;
            
        } catch (NoResultException e) {
            return new ArrayList<MocUser>();
        }

    }
    
    public MocUser Authenticate(String login, String password) {
        
        password = HashGenerator.generateHash(password);

        
            return (MocUser) getEntityManager()
                    .createNamedQuery("User.authenticate")
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();

    }
    
    public MocUser getByEmail(String email) {

        try {
            return (MocUser) getEntityManager()
                    .createNamedQuery("User.getByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }
    
    private void sendMailConfirmation(ConfirmationUser confirm) {
        
        // Todo Enviar email com confirmação de usuário e senha Moc23P)(44 mocsysbr
        String msgMail = "Hello "+ confirm.getMocUser().getName() +", Thank You for choosen MOC, a PQP member<br />"
                + "please click <a href=\"http://cloudmessenger.com.br/moc/rs/users/confirm/" + confirm.getConfirmationHash() + "\"> here </a> to confirm your account!";
        SendMail mail = new SendMail("CloudMessenger", confirm.getMocUser().getEmail(), "MOC - Account Confirmation", msgMail);
        mail.start();
        
    }

    public MocUser createUser(MocUser user) {

        if ((getByLogin(user.getLogin()) != null) || (getByEmail(user.getEmail()) != null)) {
            throw new RuntimeException("Usuário já existe");
        }
        user.setStatus(-1);
        System.out.println(user.getPassword());
        user.setPassword(HashGenerator.generateHash(user.getPassword()));
        ConfirmationUser confirmation = new ConfirmationUser(user);
        
        confirmationModel.merge(confirmation);
        
        this.sendMailConfirmation(confirmation);

        return user;

        
    }
    
    
    public boolean confirmUser(String hash) {
        ConfirmationUser confirm = confirmationModel.getByHash(hash);
        if (confirm != null) {
            confirm.getMocUser().setStatus(1);
            this.merge(confirm.getMocUser());
            
            confirmationModel.remove(confirm);
            return true;
        }
        else        
            return false;
    }
    
    private Boolean hasContact(MocUser user, MocUser contact) {
        if (user.getId().equals(contact.getId()))
            return Boolean.TRUE;
        
        for (MocUser mu :user.getContacts())
            if (mu.getId().equals(contact.getId()))
                return Boolean.TRUE;
        
        return Boolean.FALSE;
    }
    
    public void addContact(MocUser user, MocUser contact) {
        
        if (hasContact(user, contact))
            throw new RuntimeException("Contato já adicionado");
        
        if (user.getContacts() == null)
            user.setContacts(new ArrayList<MocUser>());
        
        user.getContacts().add(contact);
        
        user  = merge(user);
        confirmContact(contact, user);
    }
    
    public void confirmContact(MocUser user, MocUser contact) {
        
        if (!hasContact(user, contact)) {
            if (user.getContacts() == null)
                user.setContacts(new ArrayList<MocUser>());
            user.getContacts().add(contact);
            user = merge(user);
            groupService.newGroupFromContactConfirmation(user, contact);
            
            
        }
        else {
            throw new RuntimeException("Contato não encontrado");
        }
        
        
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
