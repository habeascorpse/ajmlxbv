/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alan
 */
@Entity
@XmlRootElement
@NamedQueries( {
    @NamedQuery(name = "Group.getAllByUser",query = "SELECT g FROM MocGroup g, UserGroup ug WHERE ug.mocUser = :user AND g IN (ug.mocGroup)"),
    @NamedQuery(name = "Group.getByName",query = "SELECT g FROM MocGroup g WHERE g.name = :name "),
    @NamedQuery(name = "Group.getAllByUserAndName",query = "SELECT g FROM MocGroup g, UserGroup ug WHERE ug.mocUser = :user AND g.name = :group AND ug.mocGroup = g")
})
public class MocGroup implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    @NotNull
    private String name;
    
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(nullable = false)
    private Date initDate;
    
    @OneToMany(mappedBy = "mocGroup",cascade = CascadeType.PERSIST)
    private List<UserGroup> listUserGroup;

    public MocGroup(String name) {
        this.name = name;
        this.initDate = new Date(System.currentTimeMillis());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getInitDate() {
        return initDate;
    }

    public Long getId() {
        return id;
    }

    public MocGroup() {
        listUserGroup = new ArrayList<UserGroup>();
        initDate = new Date(System.currentTimeMillis());
    }

    public static MocGroup newGroupFromContact(MocUser user1, MocUser user2) {
        
        MocGroup group = new MocGroup(user1.getLogin() + user2.getLogin());
        
        List<UserGroup> userGroupList = new ArrayList<UserGroup>();
        userGroupList.add(new UserGroup(group, user1));
        userGroupList.add(new UserGroup(group,user2));
        
        group.setListUserGroup(userGroupList);
        
        return group;
    }

    @XmlTransient
    public List<UserGroup> getListUserGroup() {
        return listUserGroup;
    }

    private void setListUserGroup(List<UserGroup> listUserGroup) {
        this.listUserGroup = listUserGroup;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 83 * hash + (this.initDate != null ? this.initDate.hashCode() : 0);
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
        final MocGroup other = (MocGroup) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.initDate != other.initDate && (this.initDate == null || !this.initDate.equals(other.initDate))) {
            return false;
        }
        return true;
    }
    
    
    
}
