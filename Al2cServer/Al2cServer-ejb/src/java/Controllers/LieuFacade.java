package Controllers;

import Entities.Lieu;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des lieux
 * 
 * @author Alexandre Bertrand
 */
@Stateless
public class LieuFacade extends AbstractFacade<Lieu> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LieuFacade() {
        super(Lieu.class);
    }
    
}
