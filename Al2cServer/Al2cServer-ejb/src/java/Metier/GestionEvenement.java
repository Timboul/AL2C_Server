package Metier;

import Entities.util.EtatEvenement;
import Controllers.EvenementFacade;
import Controllers.LieuFacade;
import Controllers.UtilisateurFacade;
import Entities.Evenement;
import Entities.Lieu;
import Entities.Utilisateur;
import Exception.notFoundEvenementException;
import Exception.notFoundUtilisateurException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des évènements
 *
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class GestionEvenement implements IGestionEvenement {

    @EJB
    private EvenementFacade evenementFacade;

    @EJB
    private UtilisateurFacade utilisateurFacade;

    @EJB
    private LieuFacade lieuFacade;

    @Override
    public List<Evenement> getListeEvenements(int idUtilisateur)
            throws notFoundEvenementException {
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            if (utilisateur.getEvenementCollection().isEmpty()) {
                throw new notFoundEvenementException();
            }
            return (List<Evenement>) utilisateur.getEvenementCollection();
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void creationEvenement(int idUtilisateur, int idLieu,
            String intitule, String description, String dateDebut,
            String dateFin, int nombreInvite)
            throws notFoundUtilisateurException {
        // Si controles non effectué sur le coté client vérifier date > datenow 
        // vérifier qu'aucun des champs n'est vides
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            Lieu lieu = lieuFacade.find(idLieu);

            Evenement evenement = new Evenement();
            evenement.setIntitule(intitule);
            evenement.setDescription(description);
            evenement.setDateDebut(new Date(Long.parseLong(dateDebut)));
            SimpleDateFormat formater = null;
            String periode;
            if (dateFin != null) {
                evenement.setDateFin(new Date(Long.parseLong(dateFin)));
                formater = new SimpleDateFormat("'du' dd MMMM yyyy 'à' hh:mm:ss");
                periode = formater.format(new Date(Long.parseLong(dateDebut)));
                formater = new SimpleDateFormat("'au' dd MMMM yyyy 'à' hh:mm:ss");
                periode += (" " + formater.format(new Date(Long.parseLong(dateFin))));
            } else {
                formater = new SimpleDateFormat("'le' dd MMMM yyyy 'à' hh:mm:ss");
                periode = formater.format(new Date(Long.parseLong(dateDebut)));
            }
            evenement.setNombreInvites(nombreInvite);
            evenement.setMessageInvitation("Bonjour ! Nous sommes heureux de "
                    + "vous annoncer que vous êtes invités à l'évènement " 
                    + intitule + " organisé par " + utilisateur.getPrenom() 
                    + " " + utilisateur.getNom() + " qui aura lieu " + periode 
                    + " à l'adresse :" + "\n" + lieu.getAdresse() + "\n" 
                    + lieu.getComplement() + "\n" + lieu.getCodePostal() + ", " 
                    + lieu.getVille() + "\n" + "Souhaitez-vous y participer ?");
            evenement.setUtilisateurId(utilisateur);
            evenement.setEtatEvenement(EtatEvenement.EN_PREPARATION.toString());
            evenement.setLieuId(lieu);
            evenementFacade.create(evenement);
        } catch (Exception e) {
            throw new notFoundUtilisateurException();
        }
    }

    @Override
    public void modifierEvenement(int idEvenement, int idUtilisateur,
            String intitule, String description, String dateDebut,
            String dateFin, int nombreInvite)
            throws notFoundEvenementException {
        // TODO notifier le robot du changement pour prévenir les invités 
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur)) {
                throw new notFoundEvenementException();
            }

            Evenement evenement = evenementFacade.find(idEvenement);
            evenement.setIntitule(intitule);
            evenement.setDescription(description);
            if (isEvenementEnPreparation(idEvenement)) {
                evenement.setDateDebut(new Date(Long.parseLong(dateDebut)));
                if (dateFin != null) {
                    evenement.setDateFin(new Date(Long.parseLong(dateFin)));
                }
            }
            evenement.setNombreInvites(nombreInvite);

            evenementFacade.edit(evenement);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void modifierMessageInvitation(int idEvenement, int idUtilisateur,
            String message)
            throws notFoundEvenementException {
        // TODO notifier le robot du changement pour prévenir les invités 
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur)) {
                throw new notFoundEvenementException();
            }

            Evenement evenement = evenementFacade.find(idEvenement);
            evenement.setIntitule(message);

            evenementFacade.edit(evenement);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public Evenement afficherEvenement(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur)) {
                throw new notFoundEvenementException();
            }
            return evenementFacade.find(idEvenement);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public void annulerEvenement(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException {
        // on vérifie que l'user à bien cet évenement 
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur)) {
                throw new notFoundEvenementException();
            }

            Evenement evenement = evenementFacade.find(idEvenement);
            evenement.setEtatEvenement(EtatEvenement.ANNULE.toString());

            evenementFacade.edit(evenement);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public boolean isEvenementEnPreparation(int idEvenement) {
        try {
            Evenement evenement = evenementFacade.find(idEvenement);
            if (evenement.getEtatEvenement() == EtatEvenement.EN_PREPARATION
                    .toString()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Vérifie l'appartenance de l'évènement à l'utilisateur
     *
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return true si l'évènement appartient à l'utilisateur sinon retourne
     * false
     */
    private boolean isEventExistsOnUserEvents(int idEvenement,
            int idUtilisateur) {
        try {
            List<Evenement> evenements = (List<Evenement>) utilisateurFacade.find(idUtilisateur)
                    .getEvenementCollection();
            for (Evenement evenement : evenements) {
                if (evenement.getId().equals(idEvenement)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

}
