package Metier;

import Controllers.ContactFacade;
import Controllers.EvenementFacade;
import Controllers.InvitationFacade;
import Controllers.TagFacade;
import Controllers.UtilisateurFacade;
import Entities.Contact;
import Entities.Evenement;
import Entities.Invitation;
import Entities.InvitationPK;
import Entities.Tag;
import Exception.noContactExistsException;
import Exception.notFoundEvenementException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Implémentation de la gestion des invitations
 *
 * @author Alexandre Bertrand
 */
@Stateless
public class GestionInvitation implements IGestionInvitation {

    @EJB
    private InvitationFacade invitationFacade;
    
    @EJB
    private UtilisateurFacade utilisateurFacade;
    
    @EJB
    private ContactFacade contactFacade;
    
    @EJB
    private TagFacade tagFacade;
    
    @EJB
    private EvenementFacade evenementFacade;
    
    @Override
    public List<Contact> getContactsInvites(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Invitation> invitations = (List<Invitation>)
                    evenementFacade.find(idEvenement).getInvitationCollection();
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Invitation invitation: invitations)
                contacts.add(invitation.getContact());
            return (List<Contact>) contacts;
        } catch (Exception e) {
            System.err.println(e.getCause());
            throw new notFoundEvenementException();
        }
    }

    /*
    @Override
    public List<Tag> getTagsNonInvites(int idEvenement, int idUtilisateur) 
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Invitation> invitations = (List<Invitation>)
                    evenementFacade.find(idEvenement).getInvitationCollection();
            List<Contact> contacts = (List<Contact>) utilisateurFacade.find(idUtilisateur).getContactCollection();
            for (Contact contact: contacts) {
                for (Invitation invitation: invitations) {
                    if (contact.getId() == invitation.getContact().getId())
                        contacts.remove(contact);
                }
            }
            return contacts;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }
    */

    @Override
    public List<Contact> getContactsNonInvites(int idEvenement, int idUtilisateur) 
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            return invitationFacade.getNotInvitedContacts(idUtilisateur, idEvenement);
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }
    
    @Override
    public void inviterContacts(int idEvenement, int idUtilisateur,
            List<String> idContacts) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setEvenementId(idEvenement);
            for (String idContact: idContacts) {
                if (!isContactExistsInUtilisateurContacts(
                        Integer.parseInt(idContact), idUtilisateur))
                    throw new noContactExistsException();
                Contact contact = contactFacade
                        .find(Integer.parseInt(idContact));
                invitationPK.setContactId(Integer.parseInt(idContact));
                
                if (!isLigneInvitationExist(invitationPK)) {
                    Invitation invitation = new Invitation();
                    invitation.setContact(contact);
                    invitation.setEvenement(evenement);
                    invitation.setPresence(false);
                    invitation.setInvitationPK(invitationPK);

                    invitationFacade.create(invitation);
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }

    @Override
    public void inviterTags(int idEvenement, int idUtilisateur,
            List<String> idTags) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setEvenementId(idEvenement);
            for (String idTag: idTags) {
                if (!isTagExistsInUtilisateurTags(Integer.parseInt(idTag),
                        idUtilisateur))
                    throw new noContactExistsException();
                List<Contact> contacts = (List<Contact>)
                        tagFacade.find(Integer.parseInt(idTag));
                for (Contact contact: contacts) {
                    if (!isContactExistsInUtilisateurContacts(
                            contact.getId(), idUtilisateur))
                        throw new noContactExistsException();
                    invitationPK.setContactId(contact.getId());

                    if (!isLigneInvitationExist(invitationPK)) {
                        Invitation invitation = new Invitation();
                        invitation.setContact(contact);
                        invitation.setEvenement(evenement);
                        invitation.setPresence(false);
                        invitation.setInvitationPK(invitationPK);

                        invitationFacade.create(invitation);
                    }
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }

    @Override
    public void supprimerInvitationContacts(int idEvenement, int idUtilisateur,
            List<String> idContacts) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setEvenementId(idEvenement);
            for (String idContact: idContacts) {
                if (!isContactExistsInUtilisateurContacts(
                        Integer.parseInt(idContact), idUtilisateur))
                    throw new noContactExistsException();
                Contact contact = contactFacade
                        .find(Integer.parseInt(idContact));
                invitationPK.setContactId(Integer.parseInt(idContact));
                
                if (isLigneInvitationExist(invitationPK)) {
                    Invitation invitation = invitationFacade.find(invitationPK);
                    invitationFacade.remove(invitation);
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }

    @Override
    public void supprimerInvitationTags(int idEvenement, int idUtilisateur,
            List<String> idTags) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setEvenementId(idEvenement);
            for (String idTag: idTags) {
                if (!isTagExistsInUtilisateurTags(Integer.parseInt(idTag),
                        idUtilisateur))
                    throw new noContactExistsException();
                List<Contact> contacts = (List<Contact>)
                        tagFacade.find(Integer.parseInt(idTag));
                for (Contact contact: contacts) {
                    if (!isContactExistsInUtilisateurContacts(
                            contact.getId(), idUtilisateur))
                        throw new noContactExistsException();
                    invitationPK.setContactId(contact.getId());

                    if (isLigneInvitationExist(invitationPK)) {
                        Invitation invitation = invitationFacade
                                .find(invitationPK);
                        invitationFacade.remove(invitation);
                    }
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
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
    
    /**
     * Vérification de l'appartenance d'un contact à un utilisateur
     * @param idContact identifiant du contact
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si le contact existe et appartien à l'utilisateur
     *         sinon retourn false
     */
    private boolean isContactExistsInUtilisateurContacts(int idContact,
            int idUtilisateur) {
        try {
            List<Contact> contacts = (List<Contact>) utilisateurFacade
                .find(idUtilisateur).getContactCollection();
            for(Contact contact: contacts)
                if (contact.getId().equals(idContact))
                    return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Vérification de l'appartenance d'un tag à un utilisateur
     * @param idTag identifiant du tag
     * @param idUtilisateur identifiant de l'utilisateur
     * @return true si le tag existe et appartien à l'utilisateur
     *         sinon retourn false
     */
    private boolean isTagExistsInUtilisateurTags(int idTag,
            int idUtilisateur) {
        try {
            List<Tag> tags = (List<Tag>) utilisateurFacade
                .find(idUtilisateur).getTagCollection();
            for(Tag tag: tags)
                if (tag.getId().equals(idTag))
                    return true;
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Informe de l'existance d'une invitation
     * @param invitationPK Clé primaire de l'invitation
     * @return false si la ligne d'invitation existe sinon retourne true
     */
    private boolean isLigneInvitationExist(InvitationPK invitationPK) {
        try {
            Invitation invitation = invitationFacade.find(invitationPK);
            if (invitation == null)
                return false;
            return true;
        } catch (Exception e) {
            return true;
        }
    }
    
}