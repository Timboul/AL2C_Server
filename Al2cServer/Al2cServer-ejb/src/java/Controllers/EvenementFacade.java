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
    
}
