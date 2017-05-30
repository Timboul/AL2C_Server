package Controllers;

import Entities.Contact;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des contacts
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class ContactFacade extends AbstractFacade<Contact> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactFacade() {
        super(Contact.class);
    }
    
    public int findContactByNomAndPrenom(String nom, String prenom) {
        try {
            return (int) em.createNativeQuery("SELECT c.id FROM contact c WHERE c.nom = '"
                    + nom + "' AND c.prenom = '" + prenom + "';").getResultList().get(0);
        }catch(Exception e){
            return 0;
        }
    }
    
    public int findContactByConversationId(String conversation_id) {
        try {
            return (int) em.createNativeQuery("SELECT co.id FROM contact co, canal ca WHERE ca.contact_id = co.id AND ca.conversation_id = '"
                    + conversation_id + "';").getResultList().get(0);
        }catch(Exception e){
            return 0;
        }
    }
    
}
