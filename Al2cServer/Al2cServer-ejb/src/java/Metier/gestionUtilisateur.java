package Metier;

import Controllers.UtilisateurFacade;
import Entities.Utilisateur;
import Exception.failAuthentificationException;
import Exception.mailAlreadyUsedException;
import Exception.notFoundUtilisateurException;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des utilisateurs
 *
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class GestionUtilisateur implements IGestionUtilisateur {

    @EJB
    private UtilisateurFacade utilisateurFacade;

    @Override
    public void inscriptionClient(String nom, String prenom, String mail,
            String motDePasse) throws mailAlreadyUsedException {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom(nom);
        utilisateur.setPrenom(prenom);
        utilisateur.setAdresseMail(mail);
        utilisateur.setMotDePasse(motDePasse);
        if (utilisateurFacade.isMailExists(mail))
            throw new mailAlreadyUsedException();
        utilisateurFacade.create(utilisateur);
    }

    @Override
    public int authentificationClient(String mail, String motDePasse)
            throws failAuthentificationException {
        int authentification = utilisateurFacade
                .authentification(mail, motDePasse);
        if (authentification == -1)
            throw new failAuthentificationException();
        return authentification;
    }

    @Override
    public Utilisateur afficherInformationsCompteUtilisateur(int idUtilisateur)
            throws notFoundUtilisateurException {
        try {
            return utilisateurFacade.find(idUtilisateur);
        } catch (Exception e) {
            throw new notFoundUtilisateurException();
        }
    }

    @Override
    public void modifierInformationsCompteUtilisateur(int idUtilisateur,
            String nom, String prenom, String mail, String motDePasse)
            throws notFoundUtilisateurException {
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            utilisateur.setNom(nom);
            utilisateur.setPrenom(prenom);
            utilisateur.setMotDePasse(motDePasse);
            
            if(!utilisateur.getAdresseMail().equals(mail))
                if(!utilisateurFacade.isMailExists(mail))
                    utilisateur.setAdresseMail(mail);
            
            utilisateurFacade.edit(utilisateur);
        } catch (Exception e) {
            throw new notFoundUtilisateurException();
        }
    }

}
