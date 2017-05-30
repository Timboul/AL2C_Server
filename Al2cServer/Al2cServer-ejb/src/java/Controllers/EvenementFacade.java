package Controllers;

import Entities.Evenement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des évènements
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class EvenementFacade extends AbstractFacade<Evenement> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EvenementFacade() {
        super(Evenement.class);
    }
    
    public int findNextEvenementAVenirByIdContact(int idContact) {
        try {
            return (int) em.createNativeQuery("SELECT DISTINCT e.id FROM evenement e, invitation i WHERE i.evenement_id = e.id AND i.contact_id = "
                    + idContact + " AND e.etat_evenement = 'A_VENIR' ORDER BY e.date_debut;").getResultList().get(0);
        }catch(Exception e){
            return 0;
        }
    }
    
}
