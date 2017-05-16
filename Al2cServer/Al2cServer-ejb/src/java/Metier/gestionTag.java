/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author fez
 */
@Stateless
public class gestionTag implements IgestionTag {

    @EJB
    private TagFacade tF;

    @EJB
    private UtilisateurFacade uF;

    @EJB
    private ContactFacade cF;

    /**
     * Retourne si le tag id existe bien dans la liste des tags User
     *
     * @param idUser
     * @param idTags
     * @return true si le tag existe, false sinon
     * @throws unknowUserIdException si l'iduser est non trouvé
     */
    private boolean isTagExistsForUser(int idUser, int idTags)
            throws unknowUserIdException {

        try {
            Utilisateur u = uF.find(idUser);
            List<Tag> tags = (List<Tag>) u.getTagCollection();

            for (Tag t : tags) {
                if (t.getId().equals(idTags)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Vérifie si le libelle du tag n'existe pas déjà dans la liste des tags
     * user
     *
     * @param idUser
     * @param idTags
     * @return true si le tag existe déjà, false sinon
     * @throws unknowUserIdException si l'id user est inconnu
     */
    private boolean isTagAlreadyExistsForUser(int idUser, String libelle)
            throws unknowUserIdException {

        try {
            Utilisateur u = uF.find(idUser);
            List<Tag> tags = (List<Tag>) u.getTagCollection();

            for (Tag t : tags) {
                if (t.getLibelle().equals(libelle)) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Retourne la liste de tags associés à un utilisateur
     *
     * @param idUser
     * @return List<Tag>
     * @throws noTagsFoundException si aucun tags dans la liste utilisateur
     * @throws unknowUserIdException si user n'est pas trouvé lors du find
     */
    @Override
    public List<Tag> getListeTags(int idUser) throws noTagsFoundException, unknowUserIdException {

        try {
            // on récupère l'utilisateur
            Utilisateur u = uF.find(idUser);

            // on regarde que sa liste n'est pas vide 
            if (u.getTagCollection().isEmpty()) {
                throw new noTagsFoundException(); // vide on retourne exception
            }
            // retourne la liste qui a passé les filtres 
            return (List<Tag>) u.getTagCollection();

        } catch (Exception e) {
            throw new unknowUserIdException(); // si on est dans le catch
            // c'est que le find n'a pas fonctionné => l'user n'existe pas 
        }

    }

    /**
     * Créer un tag pour un user en vérifiant que celui-ci n'existe pas déjà
     *
     * @param libelle
     * @param idUser
     * @throws tagAlreadyExistsException si le libelle existe déjà dans la liste
     * des tags user
     * @throws unknowUserIdException si le user n'est pas trouvé
     */
    @Override
    public void creerTag(String libelle, int idUser) throws tagAlreadyExistsException, unknowUserIdException {

        try {
            if (!isTagAlreadyExistsForUser(idUser, libelle)) {
                // Si le libelle n'existe pas déjà dans la liste des tags user 
                // on le crée
                Tag t = new Tag();
                t.setLibelle(libelle);
                t.setUtilisateurId(uF.find(idUser));

                tF.create(t);

            } else {
                throw new tagAlreadyExistsException();
            }
        } catch (Exception e) {
            throw new unknowUserIdException(); // Si on arrive ici c'est qu'aucun
            //user n'a été trouvé 
        }

    }

    /**
     * Modifie le libelle d'un tag après avoir vérifier que celui-ci existe bien
     * pour l'utilisateur
     *
     * @param idTag
     * @param newLibelle
     * @param idUser
     * @throws noTagsFoundException
     * @throws unknowUserIdException
     */
    @Override
    public void modifierTag(int idTag, String newLibelle, int idUser) throws noTagsFoundException, unknowUserIdException {
        try {
            // on vérifie que la tg id existe bien pour cet utilisateur 
            if (!isTagExistsForUser(idUser, idTag)) {
                throw new noTagsFoundException();
            }

            // on récupère le tag
            Tag t = tF.find(idTag);

            t.setLibelle(newLibelle);

            tF.edit(t);

        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Supprime un tag après avoir vérifier que celui ci existe bien pour User
     *
     * @param idTag
     * @param idUser
     * @throws noTagsFoundException
     * @throws unknowUserIdException
     */
    @Override
    public void supprimerTag(int idTag, int idUser) throws noTagsFoundException, unknowUserIdException {
        try {
            // on vérifie que la tg id existe bien pour cet utilisateur 
            if (!isTagExistsForUser(idUser, idTag)) {
                throw new noTagsFoundException();
            }

            // on récupère le tag
            Tag t = tF.find(idTag);
            tF.remove(t);
            System.out.println("okok");
        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Créer l'association entre un contact et l'utilisateur après avoir vérifié
     * que le contact et le tag existe bien
     *
     * @param idTag
     * @param idUser
     * @param IdContact
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException
     */
    @Override
    public void affecterTagAContact(int idTag, int idUser, int IdContact) throws noTagsFoundException, noContactExistsException, unknowUserIdException {

        try {
            // on vérifie que le tag existe bien
            if (!isTagExistsForUser(idUser, idTag)) {
                throw new noTagsFoundException();
            }

            Tag t = tF.find(idTag);

            // on vérifie que le contact existe bien
            if (!isContactExistsOnUserListOfContacts(idUser, IdContact)) {
                throw new noContactExistsException();
            }

            Contact c = cF.find(IdContact);

            // on créer l'association 
            c.getTagCollection().add(t);
            cF.edit(c);

            List<Contact> contacts = (List<Contact>) t.getContactCollection();
            contacts.add(c);
            t.setContactCollection(contacts);

            tF.edit(t);

        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public void desaffecterTagAContact(int idTag, int idUser, int IdContact) throws noTagsFoundException, noContactExistsException, unknowUserIdException {

        try {
            // on vérifie que le tag existe bien
            if (!isTagExistsForUser(idUser, idTag)) {
                throw new noTagsFoundException();
            }

            Tag t = tF.find(idTag);

            // on vérifie que le contact existe bien
            if (!isContactExistsOnUserListOfContacts(idUser, IdContact)) {
                throw new noContactExistsException();
            }

            Contact c = cF.find(IdContact);

            // on créer l'association 
            c.getTagCollection().remove(t);
            cF.edit(c);

            List<Contact> contacts = (List<Contact>) t.getContactCollection();
            contacts.remove(c);
            t.setContactCollection(contacts);

            tF.edit(t);

        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    /**
     * Checke l'existance d'un contact pour un utilisateur
     *
     * @param idUser
     * @param idContact
     * @return
     * @throws unknowUserIdException
     */
    private boolean isContactExistsOnUserListOfContacts(int idUser, int idContact) throws unknowUserIdException {
        try {
            Utilisateur u = uF.find(idUser);

            for (Contact c : u.getContactCollection()) {
                if (c.getId().equals(idContact)) {
                    return true;
                }

            }
            return false;

        } catch (Exception e) {
            throw new unknowUserIdException();
        }

    }

    /**
     * Retourne la liste des contacts appartenant à ce tag 
     * @param idUser
     * @param idTag
     * @return
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException 
     */  
    @Override
    public Collection<Contact> getListeContactByTag(int idUser, int idTag)
            throws noTagsFoundException, noContactExistsException, unknowUserIdException {

        try {
            // on vérifie que le tag existe bien
            if (!isTagExistsForUser(idUser, idTag)) {
                throw new noTagsFoundException();
            }

            Tag t = tF.find(idTag);
            
            if(!t.getContactCollection().isEmpty()){
                 return t.getContactCollection();
            }

            throw new noContactExistsException();

        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }
    /**
     * Retourne la liste des tags d'un contacts 
     * @param idUser
     * @param idContact
     * @return
     * @throws noTagsFoundException
     * @throws noContactExistsException
     * @throws unknowUserIdException 
     */
     @Override
    public List<Tag> getListeTagByContact(int idUser, int idContact)
            throws noTagsFoundException, noContactExistsException, unknowUserIdException {

        try {
            // on vérifie que le tag existe bien
            if (!isContactExistsOnUserListOfContacts(idUser, idContact)) {
                throw new noContactExistsException();
            }
                   
            List<Tag> tags = (List<Tag>) cF.find(idContact).getTagCollection();
            
            if(!tags.isEmpty()){
                 return tags;
            }

            throw new noTagsFoundException();

        } catch (Exception e) {
            throw new unknowUserIdException();
        }
    }

    @Override
    public List<Tag> getInvertListeTagByContact(int idUser, int idContact) throws noTagsFoundException, noContactExistsException, unknowUserIdException {
        try {
            // on vérifie que le tag existe bien
            if (!isContactExistsOnUserListOfContacts(idUser, idContact)) {
                throw new noContactExistsException();
            }
                   
            List<Tag> tagsContact = (List<Tag>) cF.find(idContact).getTagCollection();
            List<Tag> tags = tF.findAll();
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
    
}
