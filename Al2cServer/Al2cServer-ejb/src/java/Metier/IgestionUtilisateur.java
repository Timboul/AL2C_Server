package Metier;

import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Exception.mailAlreadyUsedException;
import Exception.notFoundUtilisateurException;
import javax.ejb.Local;

/**
 * Interface de la gestion des utilisateurs
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Local
public interface IGestionUtilisateur {
    
    /**
     * Créer un utilisateur dans le cas ou son mail n'existe pas déjà en base 
     * @param nom Nom de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param mail Adresse mail de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @throws mailAlreadyUsedException 
     */
    public void inscriptionClient(String nom, String prenom, String mail,
            String motDePasse)throws mailAlreadyUsedException; 
    
    /**
     * Authentifie l'utilisateur dont les informations de connexion sont passées
     * en paramètres
     * @param mail Adresse mail de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @return Retourne l'identifiant de l'utilisateur
     * @throws failAuthentificationException 
     */
    public int authentificationClient(String mail, String motDePasse)
            throws failAuthentificationException; 
    
    /**
     * Renvoie l'utilisateur dont l'identifiant est passé en paramètres 
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return Retourne l'utilisateur dont l'identifiant est passé en paramètres
     * @throws notFoundUtilisateurException 
     */
    public Utilisateur afficherInformationsCompteUtilisateur(int idUtilisateur)
            throws notFoundUtilisateurException;
    
    /**
     * Modifie les informations de l'utilisateur dont l'identifiant est passé en
     * paramètres 
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param nom Nom de l'utilisateur
     * @param prenom Prénom de l'utilisateur
     * @param mail Adresse mail de l'utilisateur
     * @param motDePasse Mot de passe de l'utilisateur
     * @throws notFoundUtilisateurException 
     */
    public void modifierInformationsCompteUtilisateur(int idUtilisateur,
            String nom, String prenom, String mail, String motDePasse)
            throws notFoundUtilisateurException;
    
}
