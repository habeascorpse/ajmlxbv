/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alan
 */



@Entity
@XmlRootElement
@Table
@NamedQueries( {
    @NamedQuery(name = "User.getByLogin",query = "SELECT u FROM MocUser u WHERE u.login = :login"),
    @NamedQuery(name = "User.getByEmail",query = "SELECT u FROM MocUser u WHERE u.email = :email"),
    @NamedQuery(name = "User.search",query = "SELECT u FROM MocUser u WHERE (u.email like :search or u.login like :search) and u.status = 1  "),
    @NamedQuery(name = "User.authenticate",query = "SELECT u FROM MocUser u WHERE u.login = :login and u.password = :password and u.status = 1"),
    @NamedQuery(name = "User.getByLoginAndEmail",query = "SELECT u FROM MocUser u WHERE u.login = :login and u.email = :email")
})
public class MocUser implements Serializable {
    
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    
    @Column(length = 255)
    private String name;
    
    @Column(unique = true)
    private String email;
    
    @Column(unique = true)
    @NotNull
    private String login;
    
    @Column
    private String password;
    
    @ManyToMany
    private List<MocUser> contacts;
    
    @OneToMany(mappedBy = "mocUser")
    private List<UserGroup> listGroups;
    
    @XmlElement(name = "country")
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Country country;
    
    @Column(nullable = false)
    private Integer status;

    public MocUser(String name, String email, String login, Country country) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.country = country;
        this.status = -1;
    }

    public MocUser() {
        this.status = -1;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    public /*@ pure @*/ String getName() {
        return name;
    }
    
    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlTransient
    public List<MocUser> getContacts() {
        return contacts;
    }

    public void setContacts(List<MocUser> contacts) {
        this.contacts = contacts;
    }
    
    @XmlTransient
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public  String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @XmlTransient
    public List<UserGroup> getListGroups() {
        return listGroups;
    }

    public void setListGroups(List<UserGroup> listGroups) {
        this.listGroups = listGroups;
    }
    
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.email != null ? this.email.hashCode() : 0);
        hash = 83 * hash + (this.login != null ? this.login.hashCode() : 0);
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
        final MocUser other = (MocUser) obj;
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.login == null) ? (other.login != null) : !this.login.equals(other.login)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MocUser{" + "id=" + id + ", name=" + name + ", email=" + email + ", login=" + login + ", password=" + password + ", contacts=" + contacts + ", listGroups=" + listGroups + ", country=" + country + ", status=" + status + '}';
    }
    
    
    
    
    
}
