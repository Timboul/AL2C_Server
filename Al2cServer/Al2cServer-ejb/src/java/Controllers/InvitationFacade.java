package Controllers;

import Entities.Contact;
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
    
    public List<Contact> getNotInvitedContacts(int idUtilisateur, int idEvenement) {
        try {
            return (List<Contact>) em
                    .createNamedQuery("Invitation.findNotInvited")
                    .setParameter("utilisateurId", idUtilisateur)
                    .setParameter("evenementId", idEvenement).getResultList();
        }catch(Exception e){
            System.err.println("coucou");
            System.err.println(e.getMessage());
            return null;
        }
    }
    
}
