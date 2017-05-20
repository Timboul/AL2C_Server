<%-- 
    Document   : jspInvitationMail
    Created on : 20 mai 2017, 14:18:24
    Author     : Alexandre Bertrand
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="std-header" style="background-image: url(<c:url value="/assets/img/background.png" />)"></div>
        <div class="container">
            <form action="action">
                Présence : <input type="text" />
                <br />
                <button class="btn  btn-primary " type="submit" name="action" value="valider"> Valider votre réponse </button>
            </form>
        </div>
    </body>
</html>
