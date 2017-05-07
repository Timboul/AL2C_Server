/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Entities.Utilisateur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fez
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
     * Méthode d'authentification d'un utilisateur La requête appelé ci dessous
     * est écrite dans l'entité utilisateur
     *
     * @param mail
     * @param mdp
     * @return id de l'utilisateur
     */
    public int authentification(String mail, String mdp) {
        try {
            Utilisateur u = em.createNamedQuery("Utilisateur.findByMailAndMotDePasse", Utilisateur.class)
                    .setParameter("adresseMail", mail)
                    .setParameter("motDePasse", mdp)
                    .getSingleResult();
            System.out.println("ok");
            return u.getId();
        } catch (Exception e) {
            //System.out.println(e.getCause());
            return -1;
        }

    }

    public boolean isMailExists(String mail) {
        try {
            Utilisateur u = (Utilisateur) em.createNamedQuery("Utilisateur.findByAdresseMail")
                    .setParameter("adresseMail", mail)
                    .getSingleResult();
            
            return true;
        }catch(Exception e){
            return false;
        }
    }

}
