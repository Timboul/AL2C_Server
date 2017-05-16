package Controllers;

import Entities.Invitation;
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
    
}
