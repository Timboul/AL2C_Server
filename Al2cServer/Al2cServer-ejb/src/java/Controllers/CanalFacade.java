package Controllers;

import Entities.Canal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des canaux
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class CanalFacade extends AbstractFacade<Canal> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CanalFacade() {
        super(Canal.class);
    }
    
}
