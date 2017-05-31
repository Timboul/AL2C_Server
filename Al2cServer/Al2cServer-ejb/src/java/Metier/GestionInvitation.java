package Metier;

import Controllers.CanalFacade;
import Controllers.ContactFacade;
import Controllers.EvenementFacade;
import Controllers.InvitationFacade;
import Controllers.TagFacade;
import Controllers.UtilisateurFacade;
import Entities.Canal;
import Entities.Contact;
import Entities.Evenement;
import Entities.Invitation;
import Entities.InvitationPK;
import Entities.Tag;
import Entities.util.EtatEvenement;
import Entities.util.EtatInvitation;
import Entities.util.TypeCanal;
import Exception.deleteNotPossible;
import Exception.noContactExistsException;
import Exception.notFoundEvenementException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.*;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.lang3.RandomStringUtils;
import sun.nio.cs.ext.ISO2022_CN;

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
    private CanalFacade canalFacade;
    
    @EJB
    private TagFacade tagFacade;
    
    @EJB
    private EvenementFacade evenementFacade;

    @Override
    public List<Contact> getInvitesPresents(int idEvenement, int idUtilisateur)
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Invitation> invitations = (List<Invitation>)
                    evenementFacade.find(idEvenement).getInvitationCollection();
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Invitation invitation: invitations)
                if (invitation.getPresence() == true &&
                    invitation.getReponse()
                            .equals(EtatInvitation.REPONDU.toString()))
                    contacts.add(invitation.getContact());
            return (List<Contact>) contacts;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public List<Contact> getInvitesNonPresents(int idEvenement,
            int idUtilisateur) throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Invitation> invitations = (List<Invitation>)
                    evenementFacade.find(idEvenement).getInvitationCollection();
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Invitation invitation: invitations)
                if (invitation.getPresence() == false &&
                    invitation.getReponse()
                            .equals(EtatInvitation.REPONDU.toString()))
                    contacts.add(invitation.getContact());
            return (List<Contact>) contacts;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }

    @Override
    public List<Contact> getInvitesSansReponse(int idEvenement,
            int idUtilisateur) throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Invitation> invitations = (List<Invitation>)
                    evenementFacade.find(idEvenement).getInvitationCollection();
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (Invitation invitation: invitations)
                if (!invitation.getReponse()
                            .equals(EtatInvitation.REPONDU.toString()))
                    contacts.add(invitation.getContact());
            return (List<Contact>) contacts;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }
    
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
            throw new notFoundEvenementException();
        }
    }

    @Override
    public List<Contact> getContactsNonInvites(int idEvenement,
            int idUtilisateur) throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Integer> idContacts = invitationFacade
                    .getNotInvitedContacts(idUtilisateur, idEvenement);
            ArrayList<Contact> contacts = new ArrayList<Contact>();
            for (int idContact: idContacts)
                contacts.add(contactFacade.find(idContact));
            return (List<Contact>) contacts;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }
    
    @Override
    public List<Tag> getTagsNonInvites(int idEvenement, int idUtilisateur) 
            throws notFoundEvenementException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            List<Integer> idTags = invitationFacade
                    .getNotInvitedTags(idUtilisateur, idEvenement);
            ArrayList<Tag> tags = new ArrayList<Tag>();
            for (int idTag: idTags)
                tags.add(tagFacade.find(idTag));
            return (List<Tag>) tags;
        } catch (Exception e) {
            throw new notFoundEvenementException();
        }
    }
    
    @Override
    public void inviterContacts(int idEvenement, int idUtilisateur,
            List<Integer> idContacts) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            for (Integer idContact: idContacts) {
                if (!isContactExistsInUtilisateurContacts(idContact,
                        idUtilisateur))
                    throw new noContactExistsException();
                Contact contact = contactFacade.find(idContact);
                InvitationPK invitationPK = new InvitationPK();
                invitationPK.setEvenementId(idEvenement);
                invitationPK.setContactId(idContact);
                if (!isLigneInvitationExist(invitationPK)) {
                    Invitation invitation = new Invitation();
                    invitation.setContact(contact);
                    invitation.setEvenement(evenement);
                    invitation.setToken(RandomStringUtils.randomAlphanumeric(12));
                    invitation.setReponse(EtatInvitation.NON_DEMANDEE.toString());
                    invitation.setPresence(false);
                    invitation.setInvitationPK(invitationPK);

                    invitationFacade.create(invitation);
                }
                if (evenement.getEtatEvenement().equals(EtatEvenement.A_VENIR)) {
                    // TODO envoyer les invitations
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }

    @Override
    public void inviterTags(int idEvenement, int idUtilisateur,
            List<Integer> idTags) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            for (Integer idTag: idTags) {
                if (!isTagExistsInUtilisateurTags(idTag, idUtilisateur))
                    throw new noContactExistsException();
                List<Contact> contacts = (List<Contact>)
                        tagFacade.find(idTag).getContactCollection();
                for (Contact contact: contacts) {
                    if (!isContactExistsInUtilisateurContacts(
                            contact.getId(), idUtilisateur))
                        throw new noContactExistsException();
                    InvitationPK invitationPK = new InvitationPK();
                    invitationPK.setEvenementId(idEvenement);
                    invitationPK.setContactId(contact.getId());
                    if (!isLigneInvitationExist(invitationPK)) {
                        Invitation invitation = new Invitation();
                        invitation.setContact(contact);
                        invitation.setEvenement(evenement);
                        invitation.setToken(RandomStringUtils.randomAlphanumeric(12));
                        invitation.setReponse(EtatInvitation.NON_DEMANDEE.toString());
                        invitation.setPresence(false);
                        invitation.setInvitationPK(invitationPK);
                        invitationFacade.create(invitation);
                    }
                    if (evenement.getEtatEvenement().equals(EtatEvenement.A_VENIR)) {
                        // TODO envoyer les invitations
                    }
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }

    @Override
    public void supprimerInvitationContacts(int idEvenement, int idUtilisateur,
            List<Integer> idContacts) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            if (!evenement.getEtatEvenement().equals(EtatEvenement
                    .EN_PREPARATION.toString()))
                throw new deleteNotPossible();
            for (Integer idContact: idContacts) {
                if (!isContactExistsInUtilisateurContacts(idContact,
                        idUtilisateur))
                    throw new noContactExistsException();
                Contact contact = contactFacade.find(idContact);
                InvitationPK invitationPK = new InvitationPK();
                invitationPK.setEvenementId(idEvenement);
                invitationPK.setContactId(idContact);
                
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
            List<Integer> idTags) throws noContactExistsException {
        try {
            if (!isEventExistsOnUserEvents(idEvenement, idUtilisateur))
                throw new notFoundEvenementException();
            Evenement evenement = evenementFacade.find(idEvenement);
            if (!evenement.getEtatEvenement().equals(EtatEvenement
                    .EN_PREPARATION.toString()))
                throw new deleteNotPossible();
            for (Integer idTag: idTags) {
                if (!isTagExistsInUtilisateurTags(idTag, idUtilisateur))
                    throw new noContactExistsException();
                List<Contact> contacts = (List<Contact>)
                        tagFacade.find(idTag).getContactCollection();
                for (Contact contact: contacts) {
                    if (!isContactExistsInUtilisateurContacts(
                            contact.getId(), idUtilisateur))
                        throw new noContactExistsException();
                    InvitationPK invitationPK = new InvitationPK();
                    invitationPK.setEvenementId(idEvenement);
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

    @Override
    public void validerReponseInvitationMail(String tokenComplet, boolean reponse)
            throws noContactExistsException {
        try {
            String token = tokenComplet.substring(0, tokenComplet.indexOf("_"));
            String idContact = tokenComplet.substring(tokenComplet.indexOf("_")
                    + 1, tokenComplet.lastIndexOf("_"));
            String idEvenement = tokenComplet.substring(tokenComplet
                    .lastIndexOf("_") + 1);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setContactId(Integer.parseInt(idContact));
            invitationPK.setEvenementId(Integer.parseInt(idEvenement));
            Invitation invitation = invitationFacade.find(invitationPK);
            if (invitation != null) {
                if(!invitation.getToken().equals(token))
                    throw new noContactExistsException();
                invitation.setReponse(EtatInvitation.REPONDU.toString());
                invitation.setPresence(reponse);
                invitationFacade.edit(invitation);
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }
    
    @Override
    public void validerReponseInvitation(String conversationId, boolean reponse)
            throws noContactExistsException {
        try {
            Canal canal = canalFacade.findByConversationId(conversationId);
            canal.setReponse(true);
            canalFacade.edit(canal);
            InvitationPK invitationPK = new InvitationPK();
            invitationPK.setContactId(canal.getContactId().getId());
            invitationPK.setEvenementId(invitationFacade.getInvitationEnAttente(canal.getContactId().getId()));
            Invitation invitation = invitationFacade.find(invitationPK);
            if (invitation != null) {
                invitation.setReponse(EtatInvitation.REPONDU.toString());
                invitation.setPresence(reponse);
                invitationFacade.edit(invitation);
                if (evenementFacade.findNextEvenementAVenirByIdContact(canal.getContactId().getId()) > 0) {
                    InvitationPK newInvitationPK = new InvitationPK();
                    newInvitationPK.setContactId(canal.getContactId().getId());
                    newInvitationPK.setEvenementId(evenementFacade.findNextEvenementAVenirByIdContact(canal.getContactId().getId()));
                    Invitation newInvitation = invitationFacade.find(newInvitationPK);
                    creerMessageInvitation(newInvitation, canal);
                }
            }
        } catch (Exception e) {
            throw new noContactExistsException();
        }
    }
    
    public void creerMessageInvitation(Invitation invitation, Canal canal) {
        try {
            if (invitation.getReponse()
                    .equals(EtatInvitation.NON_DEMANDEE.toString())) {
                if (invitationFacade.getInvitationEnAttente(
                        invitation.getContact().getId()) == 0) {
                    Boolean contacte = false;
                    if (canal.getTypeCanal().equals(TypeCanal.FACEBOOK.toString()) ||
                        canal.getTypeCanal().equals(TypeCanal.SMS.toString())) {
                        getJsonInvitation(canal, invitation.getEvenement());
                        contacte = true;
                    } else if (canal.getTypeCanal().equals(TypeCanal.MAIL.toString())) {
                        Contact contact = invitation.getContact();
                        Evenement evenement = invitation.getEvenement();
                        String subject = "Invitation a l'evenement " + evenement.getIntitule();
                        String text = "Bonjour " + contact.getPrenom() + " " + contact.getNom() + " !\n" +
                                "Je souhaite t'inviter a mon evenement \"" + evenement.getIntitule() + "\".\n" +
                                "Pour repondre a mon invitation, je t'invite a cliquer sur le lien suivant : " +
                                "http://www.savethedate-al2c.fr/vous-etes-invite?token=" + invitation.getToken() + "_" + contact.getId() + "_" + evenement.getId() + " \n" +
                                "A bientot !";
                        sendMail(canal.getValeur(), subject, text);
                    }
                    if (contacte) {
                        invitation.setReponse(EtatInvitation.EN_ATTENTE.toString());
                        invitationFacade.edit(invitation);
                    }
                }
            }
        } catch (Exception e) {
        }
    }
    
    @Override
    public String creerListeMessagesInvitations(int evenementId) {
        try {
            JSONArray array = new JSONArray();
            Evenement evenement = evenementFacade.find(evenementId);
            List<Invitation> invitations = (List<Invitation>)
                    evenement.getInvitationCollection();
            for (Invitation invitation: invitations) {
                if (invitation.getReponse()
                        .equals(EtatInvitation.NON_DEMANDEE.toString())) {
                    if (invitationFacade.getInvitationEnAttente(
                            invitation.getContact().getId()) == 0) {
                        Canal canal = new Canal();
                        Boolean contacte = false;
                        if (canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.FACEBOOK) > 0) {
                            canal = canalFacade.find(canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.FACEBOOK));
                            getJsonInvitation(canal, evenement);
                            contacte = true;
                        } else if (canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.SMS) > 0) {
                            canal = canalFacade.find(canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.SMS));
                            if(canal.getReponse()) {
                                getJsonInvitation(canal, evenement);
                            } else {
                                JSONObject obj = new JSONObject();
                                Contact contact = invitation.getContact();
                                obj.put("numero", canal.getValeur());
                                obj.put("message", "Bonjour " + contact.getPrenom() + " " + contact.getNom() + " !\\n" +
                                        "Je souhaite t'inviter à mon évènement \"" + evenement.getIntitule() + "\".\\n" +
                                        "Pour répondre à mon invitation tu peux contacter le service SaveTheDate de plusieurs façons :\\n" +
                                        " - par Messenger en suivant le lien suivant : m.me/SaveTheDateAL2C \\n" +
                                        " - par SMS en envoyant \"SaveTheDate\" au 0644632239 \\n" +
                                        "A bientôt !");
                                array.put(obj);
                            }
                            contacte = true;
                        } else if (canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.MAIL) > 0) {
                            canal = canalFacade.find(canalFacade.findByIdContactAndTypeCanal(invitation.getContact().getId(), TypeCanal.MAIL));
                            Contact contact = invitation.getContact();
                            String subject = "Invitation a l'evenement " + invitation.getEvenement().getIntitule();
                            if (canal.getReponse()) {
                                String text = "Bonjour " + contact.getPrenom() + " " + contact.getNom() + " !\n" +
                                        "Je souhaite t'inviter a mon evenement \"" + evenement.getIntitule() + "\".\n" +
                                        "Pour repondre a mon invitation, je t'invite a cliquer sur le lien suivant : " +
                                        "http://www.savethedate-al2c.fr/vous-etes-invite?token=" + invitation.getToken() + "_" + contact.getId() + "_" + evenement.getId() + " \n" +
                                        "A bientot !";
                                sendMail(canal.getValeur(), subject, text);
                            } else {
                                String text = "Bonjour " + contact.getPrenom() + " " + contact.getNom() + " !\n" +
                                        "Je souhaite t'inviter a mon evenement \"" + evenement.getIntitule() + "\".\n" +
                                        "Pour repondre a mon invitation tu peux contacter le service SaveTheDate de plusieurs facons :\n" +
                                        " - par Messenger en suivant le lien suivant : m.me/SaveTheDateAL2C \n" +
                                        " - en suivant le lien : http://www.savethedate-al2c.fr/vous-etes-invite?token=" 
                                        + invitation.getToken() + "_" + contact.getId() + "_" + evenement.getId() + " \n" +
                                        "A bientot !";
                                sendMail(canal.getValeur(), subject, text);
                            }
                            // TODO Gestion de l'envoi des mails
                            canal.setReponse(true);
                            canalFacade.edit(canal);
                            contacte = true;
                        }
                        if (contacte) {
                            invitation.setReponse(EtatInvitation.EN_ATTENTE.toString());
                            invitationFacade.edit(invitation);
                        }
                    }
                }
            }
            return array.toString();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public void creerInvitationPremierContact(String data) {
        try {
            JSONObject infos = new JSONObject(data);
            Canal canal = new Canal();
            int idC = 0;
            Boolean isFacebook = false;
            Boolean isSms = false;
            /* Facebook */
            if (infos.has("nom") && infos.has("prenom")) {
                idC = contactFacade.findContactByNomAndPrenom(
                        infos.getString("nom"), infos.getString("prenom"));
                if (idC > 0) isFacebook = true;
            }
            /* SMS */
            if (infos.has("numero")) {
                canal = canalFacade.findByValeur(infos.getString("numero"));
                if (canal != null) isSms = true;
            }
            if (isFacebook) {
                Contact c = contactFacade.find(idC);
                if (c != null) {
                    canal.setContactId(c);
                    canal.setReponse(true);
                    canal.setValeur(c.getPrenom() + c.getNom());
                    canal.setTypeCanal(TypeCanal.FACEBOOK.toString());
                    canal.setConversationId(infos.getString("conversationId"));
                    canalFacade.create(canal);
                }
            }
            if (isSms) {
                canal.setReponse(true);
                canal.setConversationId(infos.getString("conversationId"));
                canalFacade.edit(canal);
            }
            if (isFacebook || isSms) {
                int idContact = contactFacade.findContactByConversationId(infos.getString("conversationId"));
                int idEvenement = invitationFacade.getInvitationEnAttente(idContact);
                if (idEvenement > 0) {
                    Evenement evenement = evenementFacade.find(idEvenement);
                    getJsonInvitation(canal, evenement);
                }
            }
        } catch (JSONException e) {
        }
    }
    
    public void getJsonInvitation(Canal canal, Evenement evenement) {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        JSONObject message = new JSONObject();
        message.put("type", "text");
        message.put("content", evenement.getMessageInvitation());
        array.put(message);
        obj.put("messages", array);
        envoyerMessage(canal.getConversationId(), obj);
    }
    
    public void envoyerMessage(String conversationId, JSONObject obj) {
        HttpURLConnection connection = null;
        try {
            String a="";
            //Create connection
            URL url = new URL("https://api.recast.ai/connect/v1/conversations/" + conversationId + "/messages");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", 
                "Token 8eacd9988bf88405c98adff0f6304ab2");
            connection.setRequestProperty("Content-Type", 
                "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(obj.toString());
            wr.flush();
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
              String ligne;
              while ((ligne = reader.readLine()) != null) {
                  a += ligne;
              }
        } catch (IOException e) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
    
private void sendMail(String addresse, String subject, String text) throws AddressException, MessagingException {

        final String username = "savethedate.al2c";
        final String password = "al2cmiage";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session;
        session = Session.getInstance(props, null);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("savethedate.al2c@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(addresse));
            message.setSubject(subject);
            message.setText(text);

            Transport transport = session.getTransport("smtp");
            String mfrom = username;
            transport.connect("smtp.gmail.com", mfrom, password);
            transport.sendMessage(message, message.getAllRecipients());

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
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
