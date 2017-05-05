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
     * Méthode d'authentification d'un utilisateur
     * La requête appelé ci dessous est écrite dans 
     * l'entité utilisateur
     * @param mail
     * @param mdp
     * @return id de l'utilisateur
     */
    public int authentification(String mail, String mdp){
        return em.createNamedQuery("findByMailAndMotDePasse")
                .setParameter(":adresseMail", mail)
                .setParameter(":motDePasse", mdp)
                .getFirstResult();
    }
    
}
