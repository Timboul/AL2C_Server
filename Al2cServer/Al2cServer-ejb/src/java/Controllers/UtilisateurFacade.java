package Controllers;

import Entities.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Façade des utilisateurs
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> {

    @PersistenceContext(unitName = "Al2cServer-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    /**
     * Méthode d'authentification d'un utilisateur. La requête appelé ci-dessous
     * est écrite dans l'entité utilisateur
     * @param mail Adresse mail de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @return Retourne l'identifiant de l'utilisateur
     */
    public int authentification(String mail, String motDePasse) {
        try {
            Utilisateur utiliateur = em
                    .createNamedQuery("Utilisateur.findByMailAndMotDePasse",
                            Utilisateur.class)
                    .setParameter("adresseMail", mail)
                    .setParameter("motDePasse", motDePasse)
                    .getSingleResult();
            return utiliateur.getId();
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Informe de l'existance d'un mail dans la base de données
     * @param mail Adresse mail de l'utilisateur
     * @return Retourne true si le mail existe déja
     *         sinon retourne false
     */
    public boolean isMailExists(String mail) {
        try {
            Utilisateur utilisateur = (Utilisateur) em
                    .createNamedQuery("Utilisateur.findByAdresseMail")
                    .setParameter("adresseMail", mail)
                    .getSingleResult();
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
