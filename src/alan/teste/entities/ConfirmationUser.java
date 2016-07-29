/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alan
 */
@XmlRootElement
@Entity
@Table
@NamedQueries( {
    @NamedQuery(name = "ConfirmationUser.getByHash",query = "SELECT cu FROM ConfirmationUser cu WHERE cu.confirmationHash = :hash")
})
public class ConfirmationUser implements Serializable{
    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(referencedColumnName = "id")
    private MocUser mocUser;
    
    private String confirmationHash;

    public ConfirmationUser(MocUser mocUser) {
        this.mocUser = mocUser;
        this.generateConfirmationHash();
    }

    public ConfirmationUser() {
    }

    public Long getId() {
        return id;
    }


    public MocUser getMocUser() {
        return mocUser;
    }

    public void setMocUser(MocUser mocUser) {
        this.mocUser = mocUser;
    }

    public String getConfirmationHash() {
        return confirmationHash;
    }
    
    private void generateConfirmationHash() {
        
        
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfirmationUser other = (ConfirmationUser) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
}
