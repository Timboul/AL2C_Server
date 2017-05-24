<%-- 
    Document   : jspInvitationMail
    Created on : 20 mai 2017, 14:18:24
    Author     : Alexandre Bertrand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content="width=device-width,initial-scale=1" name="viewport">
        <meta content="SafeTheDate" name="author">
        <link href="<c:url value="/assets/css/bootstrap.min.css" />" rel="stylesheet">
        <link href="<c:url value="/assets/css/styles.css" />" rel="stylesheet">
        <title>Safe The Date - Vous êtes invités</title>
    </head>
    <body>
        <div class="std-header" style="background-image: url(<c:url value="/assets/img/background.png" />)"></div>
        <div class="container">
            <h1>Vous êtes invité</h1>
            <p>Bonjour ${invite} ! Vous êtes invité à l'évènement ${requestScope['evenement'].getIntitule()} organisé par
                ${requestScope['evenement'].getUtilisateurId().getPrenom()} ${requestScope['evenement'].getUtilisateurId().getNom()}.</p>
            <p>L'évènement aura lieu <strong>${periode}</strong> à l'adresse suivante : </p>
            <p>
                <strong>
                    ${requestScope['evenement'].getLieuId().getAdresse()} <br />
                    ${requestScope['evenement'].getLieuId().getComplement()} <br />
                    ${requestScope['evenement'].getLieuId().getCodePostal()}, ${requestScope['evenement'].getLieuId().getVille()}
                </strong>
            </p>
            <br />
            <p class="libelle-carton">Carton d'invitation : </p>
            <div class='carton-invitation'>
                <p class='texte-invitation'>
                    ${requestScope['evenement'].getMessageInvitation()}
                </p>
            </div>
            <br />
            <form action="/Al2cServer-war/vous-etes-invite?token=${token}" method="Post">
                Serez-vous présent ?
                <select name="reponse">
                    <option value='true'>Oui</option>
                    <option value='false'>Non</option>
                </select>
                <br />
                <br />
                <button class="btn btn-primary " type="submit" name="action" value="valider"> Valider votre réponse </button>
            </form>
            <br />
            <br />
        </div>
    </body>
</html>
