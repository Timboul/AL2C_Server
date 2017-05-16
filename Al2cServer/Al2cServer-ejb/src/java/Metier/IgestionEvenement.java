package Metier;

import Entities.Evenement;
import Exception.notFoundEvenementException;
import Exception.notFoundUtilisateurException;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface de la gestion des évènements
 *
 * @author fez
 * @author Alexandre Bertrand
 */
@Local
public interface IgestionEvenement {
    
    /**
     * Retourne la liste des évènements de l'utilisateur
     * @param idUser Identifiant de l'utilisateur
     * @return La liste des évènements de l'utilisateur
     * @throws notFoundEvenementException 
     */
    public List<Evenement> getListeEvenements(int idUser) 
            throws notFoundEvenementException;
    
    /**
     * Crée un nouvel évènement pour un utilisateur donné
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param intitule Intitulé de l'évènement
     * @param description Description de l'évènement
     * @param dateDebut Date de début de l'évènement
     * @param dateFin Date de fin de l'évènement
     * @param nombreInvite Nombre d'invité à l'évènement
     * @param message Message d'invitation de l'évènement
     * @throws notFoundUtilisateurException 
     */
    public void creationEvenement(int idUtilisateur, String intitule,
            String description, String dateDebut, String dateFin,
            int nombreInvite, String message)
            throws notFoundUtilisateurException; // THROWS  quoi mettre qu'elle erreur?? 
    
    /**
     * Modifie un évènement pour un utilisateur donné
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param intitule Intitulé de l'évènement
     * @param description Description de l'évènement
     * @param dateDebut Date de début de l'évènement
     * @param dateFin Date de fin de l'évènement
     * @param nombreInvite Nombre d'invité à l'évènement
     * @param message Message d'invitation de l'évènement
     * @throws notFoundEvenementException 
     */
    public void modifierEvenement(int idEvenement,int idUtilisateur,
            String intitule, String description, String dateDebut,
            String dateFin, int nombreInvite, String message)
            throws notFoundEvenementException; // THROWS  quoi mettre qu'elle erreur?? 
    
    /**
     * Retourne un évènement donné
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne lévènement dont l'id est passé en paramètres
     * @throws notFoundEvenementException 
     */
    public Evenement afficherEvenement(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException; // THROWS  quoi mettre qu'elle erreur?? 
    
    /**
     * Annulle un évènement donné
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws notFoundEvenementException 
     */
    public void annulerEvenement(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException;
    
}
