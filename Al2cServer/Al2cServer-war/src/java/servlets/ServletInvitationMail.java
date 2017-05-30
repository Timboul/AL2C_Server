/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Controllers.ContactFacade;
import Controllers.EvenementFacade;
import Controllers.InvitationFacade;
import Entities.Contact;
import Entities.Evenement;
import Entities.Invitation;
import Entities.InvitationPK;
import Exception.noContactExistsException;
import Exception.notFoundEvenementException;
import Metier.IGestionEvenement;
import Metier.IGestionInvitation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Alexandre Bertrand
 */
@WebServlet(name = "ServletInvitationMail", urlPatterns = {"/vous-etes-invite"})
public class ServletInvitationMail extends HttpServlet {

    @EJB
    private EvenementFacade evenementFacade;
    
    @EJB
    private ContactFacade contactFacade;
    
    @EJB
    private InvitationFacade invitationFacade;
    
    @EJB
    private IGestionInvitation gestionInvitation;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, notFoundEvenementException, noContactExistsException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            actionChargement(request, response);
        } else if (("valider").equals(action)) {
            actionValider(request, response);
        }
    }
    
    private void actionChargement(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            notFoundEvenementException {
        String param = request.getParameter("token");
        String token = param.substring(0, param.indexOf("_"));
        String idContact = param.substring(param.indexOf("_") + 1,
                param.lastIndexOf("_"));
        String idEvenement = param.substring(param.lastIndexOf("_") + 1);
        InvitationPK invitationPK = new InvitationPK();
        invitationPK.setContactId(Integer.parseInt(idContact));
        invitationPK.setEvenementId(Integer.parseInt(idEvenement));
        Invitation invitation = invitationFacade.find(invitationPK);
        if (invitation != null) {
            if(invitation.getToken().equals(token)) {
                Evenement evenement = evenementFacade
                        .find(Integer.parseInt(idEvenement));
                Contact contact = contactFacade
                        .find(Integer.parseInt(idContact));
                if (contact.getPrenom() != null && contact.getNom() != null)
                    request.setAttribute("invite", contact.getPrenom() + " " 
                            + contact.getNom());
                else if (contact.getPrenom() != null)
                    request.setAttribute("invite", contact.getPrenom());
                else if (contact.getNom() != null)
                    request.setAttribute("invite", contact.getNom());
                Date dateDebut = evenement.getDateDebut();
                Date dateFin = evenement.getDateFin();
                SimpleDateFormat formater = null;
                String periode;
                if (dateFin != null) {
                    formater = new SimpleDateFormat(
                            "'du' dd MMMM yyyy 'à' hh:mm:ss");
                    periode = formater.format(dateDebut);
                    formater = new SimpleDateFormat(
                            "'au' dd MMMM yyyy 'à' hh:mm:ss");
                    periode += (" " + formater.format(dateFin));
                } else {
                    formater = new SimpleDateFormat(
                            "'le' dd MMMM yyyy 'à' hh:mm:ss");
                    periode = formater.format(dateDebut);
                }
                request.setAttribute("periode", periode);
                request.setAttribute("evenement", evenement);
                request.setAttribute("token", param);
                request.getRequestDispatcher(
                        "WEB-INF/pages/jspInvitationMail.jsp")
                        .forward(request, response);
            }
        }
    }
    
    private void actionValider(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException,
            noContactExistsException {
        String reponse = request.getParameter("reponse");
        String token = request.getParameter("token");
        gestionInvitation.validerReponseInvitationMail(token,
                Boolean.parseBoolean(reponse));
        request.getRequestDispatcher(
                "WEB-INF/pages/jspInvitationMailValidee.jsp")
                .forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (notFoundEvenementException ex) {
            Logger.getLogger(ServletInvitationMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (noContactExistsException ex) {
            Logger.getLogger(ServletInvitationMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (notFoundEvenementException ex) {
            Logger.getLogger(ServletInvitationMail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (noContactExistsException ex) {
            Logger.getLogger(ServletInvitationMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
