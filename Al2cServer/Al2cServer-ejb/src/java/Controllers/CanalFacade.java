package Controllers;

import Entities.Canal;
import Entities.util.TypeCanal;
import java.util.List;
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
    
    /**
     * Recherche un canal par valeur
     * @param valeur Valeur du canal
     * @return Retourne le Canal trouvé
     */
    public Canal findByValeur(String valeur) {
        try {
            return (Canal) em
                    .createNamedQuery("Canal.findByValeur")
                    .setParameter("valeur", valeur)
                    .getSingleResult();
        }catch(Exception e){
            Canal canal = new Canal();
            canal.setValeur("null");
            return canal;
        }
    }
    
    public Canal findByConversationId(String conversationId) {
        try {
            return (Canal) em
                    .createNamedQuery("Canal.findByConversationId")
                    .setParameter("conversationId", conversationId)
                    .getSingleResult();
        }catch(Exception e){
            Canal canal = new Canal();
            canal.setValeur("null");
            return canal;
        }
    }

    public int findByIdContactAndTypeCanal(int idContact, TypeCanal typeCanal) {
        try {
            return (int) em.createNativeQuery("SELECT c.id FROM canal c WHERE c.contact_id = "
                    + idContact + " AND c.type_canal = '" + typeCanal.toString() + "';").getResultList().get(0);
        }catch(Exception e){
            return 0;
        }
    }
    
}
