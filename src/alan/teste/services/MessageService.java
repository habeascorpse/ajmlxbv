package alan.teste.services;

import alan.teste.entities.MocUser;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class MessageService implements Serializable {
    
    @PersistenceContext(unitName = "recyclegramPU")
    private EntityManager em;

    public MessageService() {
    }
    
    public int calcular(int o) {
        
        
        em.find(MocUser.class, 1L);
        
        return o * 3;
    }

}
