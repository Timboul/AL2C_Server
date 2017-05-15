/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metier;

import Entities.util.enumEtatEvenement;
import Controllers.EvenementFacade;
import Controllers.UtilisateurFacade;
import Entities.Evenement;
import Entities.Utilisateur;
import Exception.notFoundEvenementException;
import Exception.notFoundUtilisateurException;
import java.sql.Date;
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

  /*  public enum etat {
        A_VENIR, EN_COURS, PASSE, ANNULE
    };*/

    @Override
    public List<Evenement> getListeEvenements(int idUser) throws notFoundEvenementException {

        try {
            Utilisateur u = uF.find(idUser);
            if (u.getEvenementCollection().isEmpty()) {
                throw new notFoundEvenementException();
            }

            return (List<Evenement>) u.getEvenementCollection();
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void creationEvenement(int idUser, String intitule, String description, String dateDebut, String dateFin, String lieu, int nbInvite, String msg)
            throws notFoundUtilisateurException {

        // Si controles non effectué sur le coté client vérifier date > datenow 
        // vérifier qu'aucun des champs n'est vides
        try {

            Utilisateur u = uF.find(idUser);

            Evenement event = new Evenement();
            event.setIntitule(intitule);
            event.setDescription(description);
            event.setDateDebut(Date.valueOf(dateDebut));

            if (dateFin != null) {
                event.setDateFin(Date.valueOf(dateFin));
            }

            event.setLieu(lieu);
            event.setNombreInvites(nbInvite);
            event.setMessageInvitation(msg);
            event.setUtilisateurId(u);

            event.setEtatEvenement(enumEtatEvenement.A_VENIR.toString());

            eF.create(event);

        } catch (Exception e) {
            System.out.println("test " + e.getMessage());
            throw new notFoundUtilisateurException();
        }

    }

    @Override
    public void modifierEvenement(int idEvent, int idUser, String intitule, String description, String dateDebut, String dateFin, String lieu, int nbInvite, String msg)
            throws notFoundEvenementException {
        // TODO notifier le robot du changement pour prévenir les invités 
        try {
            
             if (!isEventExistsOnUserEvents(idEvent, idUser)) {
                throw new notFoundEvenementException();
            }
            
            Evenement event = eF.find(idEvent);

            event.setIntitule(intitule);
            event.setDescription(description);
            event.setDateDebut(Date.valueOf(dateDebut));

            if (dateFin != null) {
                event.setDateFin(Date.valueOf(dateFin));
            }

            event.setLieu(lieu);
            event.setNombreInvites(nbInvite);
            event.setMessageInvitation(msg);

            eF.edit(event);

        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public Evenement afficherEvenement(int idEvent, int idUser) throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvent, idUser)) {
                throw new notFoundEvenementException();
            }
            return eF.find(idEvent);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void annulerEvenement(int idEvent, int idUser) throws notFoundEvenementException {
        // on vérifie que l'user à bien cet évenement 
        try {
            if (!isEventExistsOnUserEvents(idEvent, idUser)) {
                throw new notFoundEvenementException();
            }

            Evenement event = eF.find(idEvent);
            event.setEtatEvenement(enumEtatEvenement.ANNULE.toString());

            eF.edit(event);

        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

// retour vrai si existe 
    private boolean isEventExistsOnUserEvents(int idEvent, int idUser) {
        try {
            List<Evenement> events = (List<Evenement>) uF.find(idUser).getEvenementCollection();

            for (Evenement e : events) {
                if (e.getId().equals(idEvent)) {
                    return true;
                }
            }
             return false;
        } catch (Exception e) {
            return false;
        }
    }
}
