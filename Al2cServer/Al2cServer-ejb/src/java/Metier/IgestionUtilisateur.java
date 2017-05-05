/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Exception.failAuthentificationException;
import javax.ejb.Local;

/**
 *
 * @author fez
 */
@Local
public interface IgestionUtilisateur {
    
    public void inscriptionClient(String nom, String prenom, String mail, String mdp); // TODO EXCEPTIONS
    
    public int authentificationClient(String mail, String mdp) throws failAuthentificationException; 
    
    
}
