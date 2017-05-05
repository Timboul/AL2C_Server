/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Controllers.UtilisateurFacade;
import Entities.Utilisateur;
import Exception.failAuthentificationException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author fez
 */
@Stateless
public class gestionUtilisateur implements IgestionUtilisateur {

    @EJB
    private UtilisateurFacade uf;

    @Override
    public void inscriptionClient(String nom, String prenom, String mail, String mdp) {
        
        // TODO vérifier utilisateur déjà inscrit => vérifier mail 
        // TODO renvoyer exception si déjà inscrit 
        // TODO try catch ?? autour du create ? 
    
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setAdresseMail(mail);
        u.setMotDePasse(mdp);
        
        uf.create(u);
    }

    @Override
    public int authentificationClient(String mail, String mdp) throws failAuthentificationException {
        return uf.authentification(mail, mdp);
    }

}
