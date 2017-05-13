package Metier;

import Entities.Contact;
import Exception.noContactExistsException;
import Exception.notFoundUtilisateurException;
import Exception.unknowUserIdException;
import java.util.List;

/**
 * Interface de la gestion des contacts
 *
 * @author Alexandre Bertrand
 */
public interface IgestionContact {
    
    /**
     * Vérifie quel'utilisateur dispose de contacts
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si l'utilisateur a déja des contacts
     *         sinon retourne false
     */
    public boolean hasContact(int idUtilisateur);
    
    /**
     * Récupère la liste de tous les contacts de l'utilisateur dont
     * l'identifiant estpassé en paramètres
     * @param idUtilisateur identifiant de l'utilisateur
     * @return liste de tous les contacts de l'utilisateur
     * @throws noContactExistsException
     * @throws unknowUserIdException
     */
    public List<Contact> getListeContacts(int idUtilisateur) 
        throws noContactExistsException, unknowUserIdException;
    
    /**
     * Crée un nouveau contact pour l'utilisateur dont l'identifiant est passé
     * en paramètres
     * @param idUtilisateur identifiant de l'utilisateur
     * @param nom nom du contact à créer
     * @param prenom prénom du contact à créer
     * @return l'identifiant du contact qui a été créé
     * @throws notFoundUtilisateurException 
     */
    public int ajouterContact(int idUtilisateur, String nom, String prenom)
            throws notFoundUtilisateurException; 
    
    /**
     * Modifie le contact dont l'identifiant est passé en paramètres
     * @param idContact identifiant du contact
     * @param idUtilisateur identifiant de l'utilisateur
     * @param nom nom du contact à modifier 
     * @param prenom prénom du contact à modifier 
     * @throws noContactExistsException 
     */
    public void modifierContact(int idContact, int idUtilisateur, String nom,
            String prenom) throws noContactExistsException; 
    
    /**
     * Récupère le contact dont l'identifiant est passé en paramètres
     * @param idContact identifiant du contact
     * @param idUtilisateur identifiant de l'utilisateur
     * @return contact dont l'identifiant est passé en paramètres
     * @throws noContactExistsException 
     */
    public Contact afficherContact(int idContact, int idUtilisateur)
            throws noContactExistsException;
    
    /**
     * Supprime le contact dont la doc est passée
     * @param idContact
     * @param idUtilisateur
     * @throws noContactExistsException 
     */
    /*
    public void supprimerContact(int idContact, int idUtilisateur)
        throws noContactExistsException;
    */
    
}
