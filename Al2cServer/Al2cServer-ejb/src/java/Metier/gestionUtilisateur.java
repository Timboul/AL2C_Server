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
import Exception.notFoundUtilisateurException;
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
    public void inscriptionClient(String nom, String prenom, String mail, String mdp) throws mailAlreadyUsedException {

        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setAdresseMail(mail);
        u.setMotDePasse(mdp);

        if (uf.isMailExists(mail)) {
            throw new mailAlreadyUsedException();
        }

        uf.create(u);
    }

    @Override
    public int authentificationClient(String mail, String mdp) throws failAuthentificationException {
        //TODO fixer token ici 
        int uId = uf.authentification(mail, mdp);
        if ( uId == -1) {
            throw new failAuthentificationException();
        }
        return uId;
    }

    @Override
    public Utilisateur afficherInformationsCompteUtilisateur(int id) throws notFoundUtilisateurException {
        try {
            return uf.find(id);
        } catch (Exception e) {
            throw new notFoundUtilisateurException();
        }
    }

    @Override
    public void modifierInformationsCompteUtilisateur(int id, String nom, String prenom, String mail, String mdp) throws notFoundUtilisateurException {
        try {
            Utilisateur u = uf.find(id);
            u.setNom(nom);
            u.setPrenom(prenom);
            u.setMotDePasse(mdp);
            
            if(!u.getAdresseMail().equals(mail))
                if(!uf.isMailExists(mail))
                u.setAdresseMail(mail);
            
            
            System.out.println(mail);
            uf.edit(u);
           
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new notFoundUtilisateurException();
        }
    }

}
