/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Entities.Evenement;
import Exception.notFoundEvenementException;
import Exception.notFoundUtilisateurException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author fez
 */
@Local
public interface IgestionEvenement {
    
    public List<Evenement> getListeEvenements(int idUser) 
            throws notFoundEvenementException;
    
    public void creationEvenement(int idUser, String intitule, String description, 
            String dateDebut, String dateFin,String lieu, int nbInvite, String msg) throws notFoundUtilisateurException; // THROWS  quoi mettre qu'elle erreur?? 
    
    public void modifierEvenement(int idEvent,int idUser, String intitule, String description, 
            String dateDebut, String dateFin,String lieu, int nbInvite, String msg) throws notFoundEvenementException;; // THROWS  quoi mettre qu'elle erreur?? 
    
    public Evenement afficherEvenement(int idEvent, int idUser) throws notFoundEvenementException; // THROWS  quoi mettre qu'elle erreur?? 
    
    public void annulerEvenement(int idEvent, int idUser) throws notFoundEvenementException;
}
