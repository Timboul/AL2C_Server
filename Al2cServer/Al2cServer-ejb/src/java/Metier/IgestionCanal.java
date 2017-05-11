package Metier;

import Entities.util.TypeCanal;
import Exception.noCanalFoundException;
import Exception.notFoundUtilisateurException;

/**
 * Interface de la gestion des canaux
 *
 * @author Alexandre Bertrand
 */
public interface IgestionCanal {
    
    /**
     * Crée le canal dont l'identifiant est passé en paramètres
     * @param idContact identifiant du contact
     * @param idUtilisateur identifiant de l'utilisateur
     * @param valeur valeur associée au canal
     * @param typeCanal type de canal associé à la valeur
     * @throws notFoundUtilisateurException 
     */
    public void ajouterCanal(int idContact, int idUtilisateur, String valeur,
            TypeCanal typeCanal) throws notFoundUtilisateurException; 
    
    /**
     * Modifie le canal dont l'identifiant est passé en paramètres
     * @param idCanal identifiant du canal
     * @param idUtilisateur identifiant de l'utilisateur
     * @param valeur valeur associée au canal
     * @param typeCanal type de canal associé à la valeur
     * @throws noCanalFoundException 
     */
    public void modifierCanal(int idCanal, int idUtilisateur, String valeur,
            TypeCanal typeCanal) throws noCanalFoundException; 
    
    /**
     * Canal le canal dont l'identifiant est passé en paramètres
     * @param idCanal identifiant du canal
     * @param idUtilisateur identifiant de l'utilisateur
     * @throws noCanalFoundException 
     */
    public void supprimerCanal(int idCanal, int idUtilisateur)
            throws noCanalFoundException; 
    
}
