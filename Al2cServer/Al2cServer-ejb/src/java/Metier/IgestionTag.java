package Metier;

import Entities.Contact;
import Entities.Tag;
import Exception.noContactExistsException;
import Exception.noTagsFoundException;
import Exception.tagAlreadyExistsException;
import Exception.unknowUserIdException;
import java.util.Collection;
import java.util.List;
import javax.ejb.Local;

/**
 * Interface de la gestion des tags
 *
 * @author fez
 * @author Alexandre Bertrand
 */
@Local
public interface IGestionTag {
    
    /**
     * Retourne la liste de tags associés à un utilisateur
     * @param idUtilisateur Identifiant de l'utilisateur
     * @return La liste des tags associés à un utilisateur
     * @throws noTagsFoundException Si aucun tags n'existe pour l'utilisateur
     * @throws unknowUserIdException Si l'utilisateur n'est pas trouvé
     */
    public List<Tag> getListeTags(int idUtilisateur)
            throws noTagsFoundException, unknowUserIdException;
    
    /**
     * Créer un tag pour un utilisateur en vérifiant que le tag n'existe pas
     * déjà
     * @param libelle Libelle du tag
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws tagAlreadyExistsException Si le libelle existe déjà dans la liste
     *                                   des tags user
     * @throws unknowUserIdException Si le user n'est pas trouvé
     */
    public void creerTag(String libelle, int idUtilisateur)
            throws tagAlreadyExistsException, unknowUserIdException; 
    
    /**
     * Modifie le libelle d'un tag après avoir vérifié que celui-ci existe bien
     * pour l'utilisateur
     * @param idTag Identifiant du tag
     * @param libelle Libelle du tag
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws noTagsFoundException
     * @throws unknowUserIdException
     */
    public void modifierTag(int idTag, String libelle, int idUtilisateur)
            throws noTagsFoundException , unknowUserIdException;
    
    /**
     * Supprime un tag après avoir vérifié que celui ci appartient bien à
     * l'utilisateur donné
     * @param idTag Identifiant du tag
     * @param idUtilisateur Identifiant de l'utilisateur
     * @throws noTagsFoundException
     * @throws unknowUserIdException
     */
    public void supprimerTag(int idTag, int idUtilisateur)
            throws noTagsFoundException, unknowUserIdException;
    
    /**
     * Créer l'association entre un contact et un tag après avoir vérifié que le
     * contact et le tag existent bien
     * @param idTag Identifiant du tag
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContact Identifiant du contact
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException
     */
    public void affecterTagAContact(int idTag, int idUtilisateur, int idContact)
            throws noTagsFoundException, noContactExistsException,
            unknowUserIdException; 
    
    /**
     * Supprime l'association entre un contact et un tag après avoir vérifié que
     * le contact et le tag existent bien
     * @param idTag Identifiant du tag
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContact Identifiant du contact
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException
     */
    public void desaffecterTagAContact(int idTag, int idUtilisateur,
            int idContact) throws noTagsFoundException,
            noContactExistsException, unknowUserIdException; 
    
    /**
     * Retourne la liste des contacts associés à un tag donné 
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idTag Identifiant du tag
     * @return Retourne la liste des contacts associés à un tag donné
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException 
     */  
    public Collection<Contact> getListeContactByTag(int idUtilisateur,
            int idTag) throws noTagsFoundException, noContactExistsException,
            unknowUserIdException;
    
    /**
     * Retourne la liste des tags auquel un contact donné est associé
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContact Identifiant du contact
     * @return Retourne la liste des tags auquel un contact donné est associé
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException 
     */
    public List<Tag> getListeTagByContact(int idUtilisateur, int idContact)
            throws noTagsFoundException, noContactExistsException,
            unknowUserIdException;
     
    /**
     * Retourne la liste des tags auquel un contact donné n'est pas associé
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContact Identifiant du contact
     * @return Retourne la liste des tags auquel un contact donné n'est pas
     *         associé
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException 
     */
    public List<Tag> getInvertListeTagByContact(int idUtilisateur,
            int idContact) throws noTagsFoundException,
            noContactExistsException, unknowUserIdException;
    
}
