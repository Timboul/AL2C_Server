package Metier;

import Entities.Contact;
import Entities.Tag;
import Exception.noContactExistsException;
import Exception.notFoundEvenementException;
import java.util.List;

/**
 * Interface de la gestion des invitations
 *
 * @author Alexandre Bertrand
 */
public interface IGestionInvitation {
    
    /**
     * Récupère la liste des contacts invités
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne la liste des contacts invités
     * @throws notFoundEvenementException
     */
    public List<Contact> getContactsInvites(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException;
    
    /**
     * Récupère la liste des tags pour lequels un ou plusieurs contacts ne sont
     * pas invités
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne la liste des tags pour lequels un ou plusieurs contacts
     *         ne sont pas invités
     * @throws notFoundEvenementException
     */
    /*
    public List<Tag> getTagsNonInvites(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException;
    */
    
    /**
     * Récupère la liste des contacts qui ne sont pas invités
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne la liste des contacts qui ne sont pas invités
     * @throws notFoundEvenementException
     */
    public List<Contact> getContactsNonInvites(int idEvenement,
            int idUtilisateur) throws notFoundEvenementException;
    
    /**
     * Crée des invitations pour tous les contacts passés en paramètres
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContacts Identifiants des contacts
     * @throws noContactExistsException 
     */
    public void inviterContacts(int idEvenement, int idUtilisateur,
            List<String> idContacts) throws noContactExistsException;
    
    /**
     * Crée des invitations pour tous les contacts présents dans les tags passés
     * en paramètres
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idTags Identifiants des tags
     * @throws noContactExistsException 
     */
    public void inviterTags(int  idEvenement, int idUtilisateur,
            List<String> idTags) throws noContactExistsException;
    
    /**
     * Supprime les invitations de tous les contacts passés en paramètres
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContacts Identifiants des contacts
     * @throws noContactExistsException 
     */
    public void supprimerInvitationContacts(int idEvenement, int idUtilisateur,
            List<String> idContacts) throws noContactExistsException;
    
    
    /**
     * Supprime les invitations de tous les contacts présents dans les tags
     * passés en paramètres
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idTags Identifiants des tags
     * @throws noContactExistsException 
     */
    public void supprimerInvitationTags(int  idEvenement, int idUtilisateur,
            List<String> idTags) throws noContactExistsException;
    
}
