package Entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entité des lieux
 * 
 * @author Alexandre Bertrand
 */
@Entity
@Table(name = "lieu")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lieu.findAll", query = "SELECT l FROM Lieu l")
    , @NamedQuery(name = "Lieu.findById", query = "SELECT l FROM Lieu l WHERE l.id = :id")
    , @NamedQuery(name = "Lieu.findByAdresse", query = "SELECT l FROM Lieu l WHERE l.adresse = :adresse")
    , @NamedQuery(name = "Lieu.findByComplement", query = "SELECT l FROM Lieu l WHERE l.complement = :complement")
    , @NamedQuery(name = "Lieu.findByCodePostal", query = "SELECT l FROM Lieu l WHERE l.codePostal = :codePostal")
    , @NamedQuery(name = "Lieu.findByVille", query = "SELECT l FROM Lieu l WHERE l.ville = :ville")})
public class Lieu implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "adresse")
    private String adresse;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 70)
    @Column(name = "complement")
    private String complement;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 5, max = 5)
    @Column(name = "code_postal")
    private String codePostal;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "ville")
    private String ville;
    
    @OneToMany(mappedBy = "lieuId")
    private Collection<Evenement> evenementCollection;

    public Lieu() {
    }

    public Lieu(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    @XmlTransient
    public Collection<Evenement> getEvenementCollection() {
        return evenementCollection;
    }

    public void setEvenementCollection(Collection<Evenement> evenementCollection) {
        this.evenementCollection = evenementCollection;
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
        if (!(object instanceof Lieu)) {
            return false;
        }
        Lieu other = (Lieu) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entities.Lieu[ id=" + id + " ]";
    }
    
}
