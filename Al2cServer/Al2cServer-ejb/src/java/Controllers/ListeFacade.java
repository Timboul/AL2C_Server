package Controllers;

import Entities.Liste;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des listes
 * 
 * @author Alexandre Bertrand
 */
@Stateless
public class ListeFacade extends AbstractFacade<Liste> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ListeFacade() {
        super(Liste.class);
    }
    
}
