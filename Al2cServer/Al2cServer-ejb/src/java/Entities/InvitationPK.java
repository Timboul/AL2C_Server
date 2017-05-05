/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author fez
 */
@Embeddable
public class InvitationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "evenement_id")
    private int evenementId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "contact_id")
    private int contactId;

    public InvitationPK() {
    }

    public InvitationPK(int evenementId, int contactId) {
        this.evenementId = evenementId;
        this.contactId = contactId;
    }

    public int getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(int evenementId) {
        this.evenementId = evenementId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) evenementId;
        hash += (int) contactId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InvitationPK)) {
            return false;
        }
        InvitationPK other = (InvitationPK) object;
        if (this.evenementId != other.evenementId) {
            return false;
        }
        if (this.contactId != other.contactId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.InvitationPK[ evenementId=" + evenementId + ", contactId=" + contactId + " ]";
    }
    
}
