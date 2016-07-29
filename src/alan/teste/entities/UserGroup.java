/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alan
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"mocUser", "group"}))
@NamedQueries( {
    @NamedQuery(name = "UserGroup.getByUserAndGroup",query = "SELECT ug FROM UserGroup ug WHERE ug.mocGroup = :group and ug.mocUser = :user")
})
@XmlRootElement
public class UserGroup implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private MocUser mocUser;
    
    @ManyToOne
    private MocGroup mocGroup;
    
    private String status;

    public Long getId() {
        return id;
    }

    public MocUser getMocUser() {
        return mocUser;
    }

    public void setMocUser(MocUser mocUser) {
        this.mocUser = mocUser;
    }
    
    public String getStatus() {
        return status;
    }

    public MocGroup getMocGroup() {
        return mocGroup;
    }

    public void setMocGroup(MocGroup mocGroup) {
        this.mocGroup = mocGroup;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UserGroup(MocGroup group, MocUser user) {
        this.mocGroup = group;
        this.mocUser = user;
    }

    public UserGroup() {
    }
    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.mocUser != null ? this.mocUser.hashCode() : 0);
        hash = 67 * hash + (this.mocGroup != null ? this.mocGroup.hashCode() : 0);
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
        final UserGroup other = (UserGroup) obj;
        if (this.mocUser != other.mocUser && (this.mocUser == null || !this.mocUser.equals(other.mocUser))) {
            return false;
        }
        if (this.mocGroup != other.mocGroup && (this.mocGroup == null || !this.mocGroup.equals(other.mocGroup))) {
            return false;
        }
        return true;
    }

    
    
    
    
    
}
