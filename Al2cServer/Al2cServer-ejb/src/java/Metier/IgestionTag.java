/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author fez
 */
@Local
public interface IgestionTag {
    
    public List<Tag> getListeTags(int idUser) throws noTagsFoundException, unknowUserIdException;
    
    public void creerTag(String libelle, int idUser) throws tagAlreadyExistsException, unknowUserIdException; 
    
    public void modifierTag(int idTag, String newLibelle, int idUser) throws noTagsFoundException , unknowUserIdException;
    
    public void supprimerTag(int idTag, int idUser) throws noTagsFoundException, unknowUserIdException;
    
    // TODO Vraiment ici ??? pas dans utilisateur ou contact ?? 
    public void affecterTagAContact(int idTag, int idUser, int IdContact)
            throws noTagsFoundException, noContactExistsException, unknowUserIdException; 
    
    public void desaffecterTagAContact(int idTag, int idUser, int IdContact)
            throws noTagsFoundException, noContactExistsException, unknowUserIdException; 
    
    public Collection<Contact> getListeContactByTag(int idUser, int idTag) 
            throws noTagsFoundException, noContactExistsException, unknowUserIdException;
    
    
     public List<Tag> getListeTagByContact(int idUser, int idContact)
            throws noTagsFoundException, noContactExistsException, unknowUserIdException;
    
}
