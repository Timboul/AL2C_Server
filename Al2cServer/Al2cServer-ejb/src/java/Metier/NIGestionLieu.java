package Metier;

import Exception.noLieuFoundException;

/**
 * Interface de la gestion des lieux
 *
 * @author Alexandre Bertrand
 */
public interface NIGestionLieu {
    
    /**
     * Crée le lieu
     * @param adresse Adresse du lieu
     * @param complement Complement de l'adresse du lieu
     * @param codePostal Code postal du lieu
     * @param ville Ville du lieu
     * @return Retourne l'identifiant du lieu
     */
    public int ajouterLieu(String adresse, String complement,
            String codePostal, String ville); 
    
    /**
     * Modifie le lieu dont l'identifiant de l'évènement lié est passé en
     * paramètres
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param adresse Adresse du lieu
     * @param complement Complement de l'adresse du lieu
     * @param codePostal Code postal du lieu
     * @param ville Ville du lieu
     * @throws noLieuFoundException 
     */
    public void modifierLieu(int idEvenement, int idUtilisateur, String adresse,
            String complement, String codePostal, String ville)
            throws noLieuFoundException; 
    
}