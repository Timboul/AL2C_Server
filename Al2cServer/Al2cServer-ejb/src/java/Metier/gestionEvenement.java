/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Controllers.EvenementFacade;
import Controllers.UtilisateurFacade;
import Entities.Evenement;
import Entities.Utilisateur;
import Exception.notFoundEvenementException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author fez
 */
@Stateless
public class gestionEvenement implements IgestionEvenement {

    @EJB
    private EvenementFacade eF;
    
    @EJB 
    private UtilisateurFacade uF;
    
    @Override
    public List<Evenement> getListeEvenements(int idUser) throws notFoundEvenementException {
        
        try{
             Utilisateur u = uF.find(idUser);  
             if(u.getEvenementCollection().isEmpty())
                 throw new notFoundEvenementException();
             
             return (List<Evenement>) u.getEvenementCollection();            
        }catch(Exception e){
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void creationEvenement(int idUser, String intitule, String description, String dateDebut, String dateFin, String lieu, int nbInvite, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifierEvenement(int idEvent, int idUser, String intitule, String description, String dateDebut, String dateFin, String lieu, int nbInvite, String msg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimerEvenement(int idEvent, int idUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Evenement afficherEvenement(int idEvent, int idUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
