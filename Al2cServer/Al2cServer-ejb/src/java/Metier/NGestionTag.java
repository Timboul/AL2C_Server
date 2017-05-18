package Metier;

import Controllers.ContactFacade;
import Controllers.TagFacade;
import Controllers.UtilisateurFacade;
import Entities.Contact;
import Entities.Tag;
import Entities.Utilisateur;
import Exception.noContactExistsException;
import Exception.noTagsFoundException;
import Exception.tagAlreadyExistsException;
import Exception.unknowUserIdException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des tags
 * 
 * @author fez
 * @author Alexandre Bertrand
 */
@Stateless
public class NGestionTag implements NIGestionTag {

    @EJB
    private TagFacade tagFacade;

    @EJB
    private UtilisateurFacade utilisateurFacade;

    @EJB
    private ContactFacade contactFacade;

    @Override
    public List<Tag> getListeTags(int idUtilisateur)
            throws noTagsFoundException, unknowUserIdException {
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            if (utilisateur.getTagCollection().isEmpty())
                throw new noTagsFoundException();
            return (List<Tag>) utilisateur.getTagCollection();
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void creerTag(String libelle, int idUtilisateur)
            throws tagAlreadyExistsException, unknowUserIdException {
        try {
            if (isTagAlreadyExistsForUser(idUtilisateur, libelle))
                throw new tagAlreadyExistsException();
            Tag tag = new Tag();
            tag.setLibelle(libelle);
            tag.setUtilisateurId(utilisateurFacade.find(idUtilisateur));
            tagFacade.create(tag);
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void modifierTag(int idTag, String libelle, int idUtilisateur)
            throws noTagsFoundException, unknowUserIdException {
        try {
            if (!isTagExistsForUser(idUtilisateur, idTag))
                throw new noTagsFoundException();
            Tag tag = tagFacade.find(idTag);
            tag.setLibelle(libelle);
            tagFacade.edit(tag);
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void supprimerTag(int idTag, int idUtilisateur)
            throws noTagsFoundException, unknowUserIdException {
        try {
            if (!isTagExistsForUser(idUtilisateur, idTag))
                throw new noTagsFoundException();
            Tag tag = tagFacade.find(idTag);
            tagFacade.remove(tag);
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void affecterTagAContact(int idTag, int idUtilisateur, int idContact)
            throws noTagsFoundException, noContactExistsException,
            unknowUserIdException {
        try {
            if (!isTagExistsForUser(idUtilisateur, idTag))
                throw new noTagsFoundException();
            Tag tag = tagFacade.find(idTag);
            if (!isContactExistsOnUserListOfContacts(idUtilisateur, idContact))
                throw new noContactExistsException();
            Contact contact = contactFacade.find(idContact);
            contact.getTagCollection().add(tag);
            contactFacade.edit(contact);
            List<Contact> contacts = (List<Contact>) tag.getContactCollection();
            contacts.add(contact);
            tag.setContactCollection(contacts);
            tagFacade.edit(tag);
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void desaffecterTagAContact(int idTag, int idUtilisateur,
            int idContact) throws noTagsFoundException,
            noContactExistsException, unknowUserIdException {
        try {
            if (!isTagExistsForUser(idUtilisateur, idTag))
                throw new noTagsFoundException();
            Tag tag = tagFacade.find(idTag);
            if (!isContactExistsOnUserListOfContacts(idUtilisateur, idContact))
                throw new noContactExistsException();
            Contact contact = contactFacade.find(idContact);
            contact.getTagCollection().remove(tag);
            contactFacade.edit(contact);
            List<Contact> contacts = (List<Contact>) tag.getContactCollection();
            contacts.remove(contact);
            tag.setContactCollection(contacts);
            tagFacade.edit(tag);
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }
    
    @Override
    public Collection<Contact> getListeContactByTag(int idUtilisateur,
            int idTag) throws noTagsFoundException, noContactExistsException,
            unknowUserIdException {
        try {
            if (!isTagExistsForUser(idUtilisateur, idTag))
                throw new noTagsFoundException();
            Tag tag = tagFacade.find(idTag);
            if(!tag.getContactCollection().isEmpty())
                 return tag.getContactCollection();
            throw new noContactExistsException();
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

     @Override
    public List<Tag> getListeTagByContact(int idUtilisateur, int idContact)
            throws noTagsFoundException, noContactExistsException,
            unknowUserIdException {
        try {
            if (!isContactExistsOnUserListOfContacts(idUtilisateur, idContact))
                throw new noContactExistsException();
            List<Tag> tags = (List<Tag>) contactFacade.find(idContact)
                    .getTagCollection();
            if(!tags.isEmpty())
                 return tags;
            throw new noTagsFoundException();
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public List<Tag> getInvertListeTagByContact(int idUtilisateur,
            int idContact) throws noTagsFoundException,
            noContactExistsException, unknowUserIdException {
        try {
            if (!isContactExistsOnUserListOfContacts(idUtilisateur, idContact))
                throw new noContactExistsException();
            List<Tag> tagsContact = (List<Tag>) contactFacade.find(idContact)
                    .getTagCollection();
            List<Tag> tags = tagFacade.findAll();
            Iterator<Tag> iterator = tags.iterator();
            while(iterator.hasNext()){
                Tag currentTag = iterator.next();
                for (Tag t: tagsContact) {
                    if(currentTag.getId() == t.getId())
                        iterator.remove();
                }
            }            
            if(!tags.isEmpty())
                 return tags;
            throw new noTagsFoundException();
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }
    
    /**
     * Vérifie si un tag existe dans la liste des tags de l'utilisateur
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idTag Identifiant du tag
     * @return true si le tag existe, false sinon
     * @throws unknowUserIdException si l'iduser est non trouvé
     */
    private boolean isTagExistsForUser(int idUtilisateur, int idTag)
            throws unknowUserIdException {
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            List<Tag> tags = (List<Tag>) utilisateur.getTagCollection();
            for (Tag tag : tags)
                if (tag.getId().equals(idTag))
                    return true;
            return false;
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Vérifie si le libelle du tag n'existe pas déjà dans la liste des tags
     * de l'utilisateur
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param libelle Libellé du tag
     * @return true si le tag existe déjà
     *         sinon retourne false
     * @throws unknowUserIdException si l'identifiant de l'utilisateur est
     *                               inconnu
     */
    private boolean isTagAlreadyExistsForUser(int idUtilisateur, String libelle)
            throws unknowUserIdException {
        try {
            List<Tag> tags = (List<Tag>) utilisateurFacade.find(idUtilisateur)
                    .getTagCollection();
            for (Tag tag : tags)
                if (tag.getLibelle().equals(libelle)) 
                    return true;
            return false;
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }
    
    /**
     * Vérifie l'appartenance d'un contact à l'utilisateur
     * @param idUtilisateur Identifiant de l'utilisateur
     * @param idContact Identifiant du contact
     * @return true si le contact appartient à l'utilisateur
     *         sinon retourne false
     * @throws unknowUserIdException
     */
    private boolean isContactExistsOnUserListOfContacts(int idUtilisateur,
            int idContact) throws unknowUserIdException {
        try {
            Utilisateur utilisateur = utilisateurFacade.find(idUtilisateur);
            for (Contact contact : utilisateur.getContactCollection())
                if (contact.getId().equals(idContact))
                    return true;
            return false;
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }
    
}
