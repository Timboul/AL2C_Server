/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Exception.mailAlreadyUsedException;
import Exception.notFoundUtilisateurException;
import javax.ejb.Local;

/**
 *
 * @author fez
 */
@Local
public interface IgestionUtilisateur {
    
    public void inscriptionClient(String nom, String prenom, String mail, String mdp)throws mailAlreadyUsedException; 
    
    public int authentificationClient(String mail, String mdp) throws failAuthentificationException; 
    
    public Utilisateur afficherInformationsCompteUtilisateur(int id) throws notFoundUtilisateurException;
    
    public void modifierInformationsCompteUtilisateur(int id, String nom, String prenom, String mail, String mdp) throws notFoundUtilisateurException;
}
