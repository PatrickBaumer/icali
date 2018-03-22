<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@tag pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@attribute name="title"%>
<%@attribute name="head" fragment="true"%>
<%@attribute name="menu" fragment="true"%>
<%@attribute name="content" fragment="true"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8" />

        <title>iCali: ${title}</title>

        <link rel="stylesheet" href="<c:url value="/fontello/css/fontello.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/main.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/form.css"/>" />

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <%-- Kopfbereich --%>
        <header>
            <%-- Titelzeile --%>
            <div id="titlebar">
                <div class="appname">
                    iCali
                </div>
                <c:if test="${not empty pageContext.request.userPrincipal}">
                    <div id="menuitem1">
                        <a href="<c:url value="/logout/"/>" class="icon-logout">Logout ${pageContext.request.userPrincipal}</a>
                    </div>
                </c:if>
            </div>
            <jsp:invoke fragment="menu"/>
            <%-- Menü --%>
        </header>
        <c:if test="${not empty pageContext.request.userPrincipal}">
            <div class="sidenav">
                    <button type="submit" name="sidebar_button" value="view_month_week">Wochenansicht / Monatsansicht</button>
                    <button type="submit" name="sidebar_button" value="create_termin">neuen Termin erstellen</button>
                    <button type="submit" name="sidebar_button" value="create_calendar">neuen Kalender erstellen  </button>
                    <button type="submit" name="sidebar_button" value="search_calendar">Kalendergruppen suchen</button>
                <%-- beide folgende Links müssen noch auf die buttons--%>
                <div>
                    <a href="<c:url value="/app/erstelleKalender/"/>" class="icon-erstelleKalender">Erstelle Kalender ${pageContext.request.userPrincipal}</a>
                </div>
                <div>
                    <a href="<c:url value="/app/erstelleTermin/"/>" class="icon-erstelleTermin">Erstelle Termin ${pageContext.request.userPrincipal}</a>
                </div>
            </div>
        </c:if>
        <%-- Hauptinhalt der Seite --%>
        <main>    
            <jsp:invoke fragment="content"/>
        </main>
    </body>
</html>