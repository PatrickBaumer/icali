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
        <link href="//netdna.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//netdna.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

        <jsp:invoke fragment="head"/>
    </head>
    <body>
        <%-- Kopfbereich --%>
        <header>

            <c:if test="${not empty pageContext.request.userPrincipal}">
                <div class="nav-side-menu">
                    <div class="brand">iCali</div>
                    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>

                    <div class="menu-list">
                        <ul id="menu-content" class="menu-content collapse out">
                            <li>
                                <a href="<c:url value="/app/wochenansicht/"/>"><i class="placeholder_switch"></i> Wochenansicht / Monatsansicht </a>                              
                            </li>
                            <li>
                                <a href="<c:url value="/app/erstelleTermin/"/>"><i class="placeholder_create_date"></i> Termin erstellen </a>
                            </li>
                            <li>
                                <a href="<c:url value="/app/erstelleKalender/"/>"><i class="placeholder_create_cal"></i> Kalender erstellen </a>
                            </li>  
                            <li data-toggle="collapse" data-target="#new" class="collapsed">
                                <a href="#"><i class="placeholder_search"></i> Kalendergruppen suchen <span class="arrow"></span></a>
                            </li>
                            
                            <ul class="sub-menu collapse" id="new">
                                <li>New New 1</li>
                                <li>New New 2</li>
                                <li>New New 3</li>
                            </ul>
                            <li>
                                <a href="#"> <i class="placeholder_edit_profile"></i> Profil bearbeiten?!</a>
                            </li>
                                <li>
                                    <a href="<c:url value="/logout/"/>" class="icon-logout"> <i class="placeholder_logout"></i> Logout ${pageContext.request.userPrincipal} </a>
                                </li>
                        </ul>
                    </div>
                </c:if>
                <%-- Menü --%>
        </header>
        <!--
                    <div class="sidenav">
                            <button type="submit" name="sidebar_button" value="view_month_week">Wochenansicht / Monatsansicht</button>
                            <button type="submit" name="sidebar_button" value="create_termin">neuen Termin erstellen</button>
                            <button type="submit" name="sidebar_button" value="create_calendar">neuen Kalender erstellen  </button>
                            <button type="submit" name="sidebar_button" value="search_calendar">Kalendergruppen suchen</button>
        <%-- beide folgende Links müssen noch auf die buttons--%>
        <div>
            <a href="<c:url value="icali/app/erstelleKalender/"/>" class="icon-erstelleKalender">Erstelle Kalender ${pageContext.request.userPrincipal}</a>
        </div>
        <div>
            <a href="<c:url value="icali/app/erstelleTermin/"/>" class="icon-erstelleTermin">Erstelle Termin ${pageContext.request.userPrincipal}</a>
        </div>
    </div>  -->

        <%-- Hauptinhalt der Seite --%>
        <main>    
            <jsp:invoke fragment="content"/>
        </main>
    </body>
</html>
