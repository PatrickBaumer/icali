<%-- 
    Document   : basis
    Created on : 02.03.2018, 11:19:02
    Author     : Patrick Baumer
--%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="titleSeite"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="menu" fragment="true"%>
<%@attribute name="content" fragment="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />

        <title>iCali ${titleSeite}</title>

        <link rel="stylesheet" href="<c:url value="/css/main.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />

        <jsp:invoke fragment="head"/>
    </head>
                <%-- Kopfbereich --%>
        <header>
            <%-- Titelzeile --%>
            <div id="titlebar">
                <div class="appname">
                    iCali
                </div>
                <div class="content">
                    ${titleSeite}
                </div>
            </div>

            <%-- Menü --%>
            <div id="menubar">
                <jsp:invoke fragment="menu"/>

                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <div class="menuitem">
                        <a href="<c:url value="/logout/"/>" class="icon-logout">Logout ${pageContext.request.userPrincipal}</a>
                    </div>
                </c:if>
            </div>
        </header>
    <body>
        


        <%-- Hauptinhalt der Seite --%>
          
        <main>    
            <jsp:invoke fragment="content"/>
        </main>
  
    </body>
</html>