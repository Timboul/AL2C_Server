package Metier;

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
     * @param idContact Identifiant du contact
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param valeur Valeur associée au canal
     * @param typeCanal Type de canal associé à la valeur
     * @throws notFoundUtilisateurException 
     */
    public void ajouterCanal(int idContact, int idUtilisateur, String valeur,
            String typeCanal) throws notFoundUtilisateurException; 
    
    /**
     * Modifie le canal dont l'identifiant est passé en paramètres
     * @param idCanal Identifiant du canal
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param valeur Valeur associée au canal
     * @param typeCanal Type de canal associé à la valeur
     * @throws noCanalFoundException 
     */
    public void modifierCanal(int idCanal, int idUtilisateur, String valeur,
            String typeCanal) throws noCanalFoundException; 
    
    /**
     * Canal le canal dont l'identifiant est passé en paramètres
     * @param idCanal Identifiant du canal
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws noCanalFoundException 
     */
    public void supprimerCanal(int idCanal, int idUtilisateur)
            throws noCanalFoundException; 
    
}
