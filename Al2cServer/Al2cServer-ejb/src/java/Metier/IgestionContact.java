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
public interface IGestionContact {
    
    /**
     * Vérifie que l'utilisateur dispose de contacts
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne true si l'utilisateur a déja des contacts
     *         sinon retourne false
     */
    public boolean hasContact(int idUtilisateur);
    
    /**
     * Récupère la liste de tous les contacts de l'utilisateur dont
     * l'identifiant est passé en paramètres
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne la liste de tous les contacts de l'utilisateur
     * @throws noContactExistsException
     * @throws unknowUserIdException
     */
    public List<Contact> getListeContacts(int idUtilisateur) 
        throws noContactExistsException, unknowUserIdException;
    
    /**
     * Crée un nouveau contact pour l'utilisateur dont l'identifiant est passé
     * en paramètres
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param nom Nom du contact à créer
     * @param prenom Prénom du contact à créer
     * @return Retourne l'identifiant du contact qui a été créé
     * @throws notFoundUtilisateurException 
     */
    public int ajouterContact(int idUtilisateur, String nom, String prenom)
            throws notFoundUtilisateurException; 
    
    /**
     * Modifie le contact dont l'identifiant est passé en paramètres
     * @param idContact Identifiant du contact
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param nom Nom du contact à modifier 
     * @param prenom Prénom du contact à modifier 
     * @throws noContactExistsException 
     */
    public void modifierContact(int idContact, int idUtilisateur, String nom,
            String prenom) throws noContactExistsException; 
    
    /**
     * Récupère le contact dont l'identifiant est passé en paramètres
     * @param idContact Identifiant du contact
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne le contact dont l'identifiant est passé en paramètres
     * @throws noContactExistsException 
     */
    public Contact afficherContact(int idContact, int idUtilisateur)
            throws noContactExistsException;
    
    /**
     * Supprime le contact dont l'identifiant est passée en paramètres
     * @param idContact Identifiant du contact
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws noContactExistsException 
     */
    /*
    public void supprimerContact(int idContact, int idUtilisateur)
        throws noContactExistsException;
    */
    
}
