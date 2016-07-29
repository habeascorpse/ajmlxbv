/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.services;

import alan.teste.entities.Country;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alan
 */
@Stateless
public class CountryService extends GenericService<Country> {

    @PersistenceContext
    private EntityManager em;
    public CountryService() {
        super(Country.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
    
    
}
