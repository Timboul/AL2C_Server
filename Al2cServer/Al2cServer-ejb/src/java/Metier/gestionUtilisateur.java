/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Controllers.UtilisateurFacade;
import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Exception.mailAlreadyUsedException;
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
    public void inscriptionClient(String nom, String prenom, String mail, String mdp) throws mailAlreadyUsedException{
        
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setAdresseMail(mail);
        u.setMotDePasse(mdp);
        
        if(uf.isMailExists(mail)){
            throw new mailAlreadyUsedException();
        }
    
        uf.create(u);
    }

    @Override
    public void authentificationClient(String mail, String mdp) throws failAuthentificationException {
        //TODO fixer token ici 
        
        if(!uf.authentification(mail, mdp))
                throw new failAuthentificationException();
        
    }

}
