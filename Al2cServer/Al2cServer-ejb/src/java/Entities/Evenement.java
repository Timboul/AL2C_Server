package Entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entité des evenements
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Entity
@Table(name = "evenement")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evenement.findAll", query = "SELECT e FROM Evenement e")
    , @NamedQuery(name = "Evenement.findById", query = "SELECT e FROM Evenement e WHERE e.id = :id")
    , @NamedQuery(name = "Evenement.findByIntitule", query = "SELECT e FROM Evenement e WHERE e.intitule = :intitule")
    , @NamedQuery(name = "Evenement.findByEtatEvenement", query = "SELECT e FROM Evenement e WHERE e.etatEvenement = :etatEvenement")
    , @NamedQuery(name = "Evenement.findByDateDebut", query = "SELECT e FROM Evenement e WHERE e.dateDebut = :dateDebut")
    , @NamedQuery(name = "Evenement.findByDateFin", query = "SELECT e FROM Evenement e WHERE e.dateFin = :dateFin")
    , @NamedQuery(name = "Evenement.findByNombreInvites", query = "SELECT e FROM Evenement e WHERE e.nombreInvites = :nombreInvites")})
public class Evenement implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "intitule")
    private String intitule;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "description")
    private String description;
    
    @Column(name = "etat_evenement")
    private String etatEvenement;
    
    @Column(name = "date_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;
    
    @Column(name = "date_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateFin;
    
    @Column(name = "nombre_invites")
    private Integer nombreInvites;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "message_invitation")
    private String messageInvitation;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evenement")
    private Collection<Liste> listeCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evenement")
    private Collection<Invitation> invitationCollection;
    
    @JoinColumn(name = "utilisateur_id", referencedColumnName = "id")
    @ManyToOne
    private Utilisateur utilisateurId;
    
    @JoinColumn(name = "lieu_id", referencedColumnName = "id")
    @ManyToOne
    private Lieu lieuId;

    public Evenement() {
    }

    public Evenement(Integer id) {
        this.id = id;
    }

    public Evenement(Integer id, String intitule) {
        this.id = id;
        this.intitule = intitule;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtatEvenement() {
        return etatEvenement;
    }

    public void setEtatEvenement(String etatEvenement) {
        this.etatEvenement = etatEvenement;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Integer getNombreInvites() {
        return nombreInvites;
    }

    public void setNombreInvites(Integer nombreInvites) {
        this.nombreInvites = nombreInvites;
    }

    public String getMessageInvitation() {
        return messageInvitation;
    }

    public void setMessageInvitation(String messageInvitation) {
        this.messageInvitation = messageInvitation;
    }
    
    @XmlTransient
    public Collection<Liste> getListeCollection() {
        return listeCollection;
    }

    public void setListeCollection(Collection<Liste> listeCollection) {
        this.listeCollection = listeCollection;
    }

    @XmlTransient
    public Collection<Invitation> getInvitationCollection() {
        return invitationCollection;
    }

    public void setInvitationCollection(Collection<Invitation> invitationCollection) {
        this.invitationCollection = invitationCollection;
    }

    public Utilisateur getUtilisateurId() {
        return utilisateurId;
    }

    public void setUtilisateurId(Utilisateur utilisateurId) {
        this.utilisateurId = utilisateurId;
    }
    
    public Lieu getLieuId() {
        return lieuId;
    }

    public void setLieuId(Lieu lieuId) {
        this.lieuId = lieuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evenement)) {
            return false;
        }
        Evenement other = (Evenement) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Evenement[ id=" + id + " ]";
    }
    
}
