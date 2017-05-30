package Entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entité des invitations
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Entity
@Table(name = "invitation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Invitation.findAll", query = "SELECT i FROM Invitation i")
    , @NamedQuery(name = "Invitation.findByEvenementId", query = "SELECT i FROM Invitation i WHERE i.invitationPK.evenementId = :evenementId")
    , @NamedQuery(name = "Invitation.findByContactId", query = "SELECT i FROM Invitation i WHERE i.invitationPK.contactId = :contactId")
    , @NamedQuery(name = "Invitation.findByPresence", query = "SELECT i FROM Invitation i WHERE i.presence = :presence")})
public class Invitation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @EmbeddedId
    protected InvitationPK invitationPK;
    
    @Column(name = "reponse")
    private String reponse;
    
    @Column(name = "presence")
    private Boolean presence;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 12, max = 12)
    @Column(name = "token")
    private String token;
    
    @JoinColumn(name = "evenement_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evenement evenement;
    
    @JoinColumn(name = "contact_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contact contact;

    public Invitation() {
    }

    public Invitation(InvitationPK invitationPK) {
        this.invitationPK = invitationPK;
    }

    public Invitation(int evenementId, int contactId) {
        this.invitationPK = new InvitationPK(evenementId, contactId);
    }

    public InvitationPK getInvitationPK() {
        return invitationPK;
    }

    public void setInvitationPK(InvitationPK invitationPK) {
        this.invitationPK = invitationPK;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Boolean getPresence() {
        return presence;
    }

    public void setPresence(Boolean presence) {
        this.presence = presence;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (invitationPK != null ? invitationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Invitation)) {
            return false;
        }
        Invitation other = (Invitation) object;
        if ((this.invitationPK == null && other.invitationPK != null) || (this.invitationPK != null && !this.invitationPK.equals(other.invitationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Invitation[ invitationPK=" + invitationPK + " ]";
    }
    
}
