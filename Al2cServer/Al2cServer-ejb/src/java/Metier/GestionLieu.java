package Metier;

import Controllers.EvenementFacade;
import Controllers.LieuFacade;
import Controllers.UtilisateurFacade;
import Entities.Evenement;
import Entities.Lieu;
import Exception.noLieuFoundException;
import Exception.notFoundEvenementException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des lieux
 *
 * @author Alexandre Bertrand
 */
@Stateless
public class GestionLieu implements IGestionLieu {

    @EJB
    private LieuFacade lieuFacade;
    
    @EJB
    private EvenementFacade evenementFacade;
    
    @EJB
    private UtilisateurFacade utilisateurFacade;
    
    @Override
    public int ajouterLieu(String adresse, String complement, String codePostal,
            String ville) {
        Lieu lieu = new Lieu();
        lieu.setAdresse(adresse);
        if (complement != null)
            lieu.setComplement(complement);
        lieu.setCodePostal(codePostal);
        lieu.setVille(ville);
        
        lieuFacade.create(lieu);
        return lieu.getId();        
    }

    @Override
    public void modifierLieu(int idEvenement, int idUtilisateur, String adresse,
            String complement, String codePostal, String ville)
            throws noLieuFoundException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Lieu lieu = evenementFacade.find(idEvenement).getLieuId();
            lieu.setAdresse(adresse);
            if (complement != null)
                lieu.setComplement(complement);
            lieu.setCodePostal(codePostal);
            lieu.setVille(ville);

            lieuFacade.edit(lieu);
        } catch (Exception e) {
            throw new noLieuFoundException();
        }
    }
    
    /**
     * Vérifie l'appartenance de l'évènement à l'utilisateur
     * @param idEvenement Identifiant de l'évènement
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return true si l'évènement appartient à l'utilisateur
     *         sinon retourne false
     */
    private boolean isEventExistsOnUserEvents(int idEvenement,
            int idUtilisateur) {
        try {
            List<Evenement> evenements = (List<Evenement>)
                    utilisateurFacade.find(idUtilisateur)
                    .getEvenementCollection();
            for (Evenement evenement : evenements)
                if (evenement.getId().equals(idEvenement))
                    return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
}
