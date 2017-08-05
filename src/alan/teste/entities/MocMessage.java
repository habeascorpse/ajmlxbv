/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alan.teste.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author alan
 */
@Entity
@XmlRootElement
@NamedQueries( {
    @NamedQuery(name = "Message.getMessagesByUserGroup",query = "SELECT m FROM MocMessage m where m.userGroup = :userGroup ORDER BY m.sendDate DESC"),
    @NamedQuery(name = "Message.getMessagesByGroup",query = "SELECT m FROM MocMessage m where m.userGroup.mocGroup = :group ORDER BY m.sendDate DESC")
})
public class MocMessage implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 1000)
    private String text = " ";
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date sendDate;
    
    @ManyToOne
    private UserGroup userGroup;
    
    @XmlTransient
    @Transient
    public int length;

    public MocMessage() {
        
        sendDate = new Date(System.currentTimeMillis());
    }

    public MocMessage(String text, UserGroup userGroup) {
        this.text = text;
        this.length = text.length();
        this.userGroup = userGroup;
        sendDate = new Date(System.currentTimeMillis());
    }

    public Long getId() {
        return id;
    }

    public /*@ pure @*/ String getText() {
        return text;
    }

    @Transient
    public int getLength() {
        return length;
    }
    

    public void setText(String text) {
        this.text = text;
    }
    
    public UserGroup getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroup userGroup) {
        this.userGroup = userGroup;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        final MocMessage other = (MocMessage) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    
    
    
    
}
