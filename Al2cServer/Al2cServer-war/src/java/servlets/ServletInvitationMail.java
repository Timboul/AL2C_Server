/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Entities.Evenement;
import Exception.notFoundEvenementException;
import Metier.IGestionEvenement;
import java.io.IOException;
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
    private IGestionEvenement gestionEvenement;
    
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
            throws ServletException, IOException, notFoundEvenementException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if (action == null) {
            actionChargement(request, response);
        } else if ("valider".equals(action)) {
            actionValider(request, response);
        }
    }
    
    private void actionChargement(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, notFoundEvenementException {
        // Récupération de l'id de commande 
        String idEvenement = request.getParameter("idEvenement");
        String idUtilisateur = request.getParameter("idUtilisateur");
        // on passe l'id en variable session pour la durée de la commande
        request.getSession().removeAttribute("idEvenement");
        request.getSession().setAttribute("idEvenement", idEvenement);
        request.getSession().removeAttribute("idUtilisateur");
        request.getSession().setAttribute("idUtilisateur", idUtilisateur);

        // Récupération des infos de la commande et les lignes de celle-ci
        Evenement evenement = gestionEvenement.afficherEvenement(2, 1);

        // Redirection vers la page jSP
        request.setAttribute("message", evenement.getMessageInvitation());
        request.getRequestDispatcher("jspInvitationMail.jsp").forward(request, response);
    }
    
    private void actionValider(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      /*  // on valide la commande => retirer montant du coup pamazone
        // on retire la quantité du stock de produit
        Client c = (Client) request.getSession().getAttribute("client");

        System.out.println(request.getSession().getAttribute("idCmd"));
        int idCmd = Integer.parseInt(request.getSession().getAttribute("idCmd").toString());
        
        try{
            gestCmd.acquitterCommande(idCmd);
        }catch(ConstraintViolationException e){
            System.out.println(e.getCause());
        }
        

        // on détruit la var session 
        request.getSession().removeAttribute("idCmd");
        request.setAttribute("retourCmd", true);
        // on mène à la page d'accueil*/
        request.getRequestDispatcher("jspInvitationMailValidee.jsp").forward(request, response);
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
