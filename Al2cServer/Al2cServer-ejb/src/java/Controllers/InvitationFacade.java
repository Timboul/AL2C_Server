package Controllers;

import Entities.Invitation;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des invitations
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class InvitationFacade extends AbstractFacade<Invitation> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InvitationFacade() {
        super(Invitation.class);
    }
    
    public List<Integer> getNotInvitedContacts(int idUtilisateur, int idEvenement) {
        try {
            return em.createNativeQuery("SELECT c.id FROM contact c WHERE c.utilisateur_id = " + idUtilisateur + " AND c.id NOT IN(" +
                "SELECT DISTINCT d.id from contact d, invitation i, evenement e where d.id = i.contact_id AND i.evenement_id = " + idEvenement + ");").getResultList();
        }catch(Exception e){
            return null;
        }
    }
    
    public List<Integer> getNotInvitedTags(int idUtilisateur, int idEvenement) {
        try {
            return em.createNativeQuery("SELECT t.id FROM tag t WHERE t.utilisateur_id = " + idUtilisateur + " AND t.id IN (" +
                "SELECT a.tag_id FROM affectation_tag a WHERE a.contact_id IN (" +
                "SELECT c.id FROM contact c WHERE c.utilisateur_id = " + idUtilisateur + " AND c.id NOT IN(" +
                "SELECT DISTINCT d.id from contact d, invitation i, evenement e where d.id = i.contact_id AND i.evenement_id = " + idEvenement + ")));").getResultList();
        }catch(Exception e){
            return null;
        }
    }
    
    public int getInvitationEnAttente(int idContact) {
        try {
            return (int) em.createNativeQuery("SELECT i.evenement_id FROM invitation i WHERE i.contact_id = "
                    + idContact + " AND i.reponse = 'EN_ATTENTE';").getResultList().get(0);
        }catch(Exception e){
            return 0;
        }
    }
    
}
