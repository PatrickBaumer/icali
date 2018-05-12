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
        <jsp:invoke fragment="menu"/>
        <%-- Sidebar--%>
        <header>

            <c:if test="${not empty pageContext.request.userPrincipal}">
                <div class="nav-side-menu">
                    <div class="brand">iCali</div>
                    <i class="fa fa-bars fa-2x toggle-btn" data-toggle="collapse" data-target="#menu-content"></i>

                    <div class="menu-list">
                        <ul id="menu-content" class="menu-content collapse out">
                            <c:choose>
                                <c:when test="${mk_activated}">
                                    <li>
                                        <a href="<c:url value="/app/wochenansicht/"/>"> <i class="placeholder_switch"></i> Wochenansicht / Monatsansicht </a>                              
                                    </li>
                                </c:when>
                                <c:otherwise>
                                    <li>
                                        <a href="<c:url value="/app/kalender/"/>"> <i class="placeholder_switch"></i> Wochenansicht / Monatsansicht </a>
                                    </li>
                                </c:otherwise>
                            </c:choose>
                            <li>
                                <a href="<c:url value="/app/erstelleTermin/"/>"> <i class="placeholder_create_date"></i> Termin erstellen </a>
                            </li>
                            <li>
                                <a href="<c:url value="/app/gruppenkalender/new/"/>"> <i class="placeholder_create_cal"></i> Kalender erstellen </a>
                            </li> 
                            <li>
                                <a href="<c:url value="/app/kalenderSuchen/"/>"> <i class="placeholder_search"></i> Kalendergruppen suchen </a>
                            </li>

                           
                                <c:if test="${sidebar_kalender.isEmpty()}">
                                    <li><i class="placeholder_search"></i><b>Kein Kalender</b></li>
                                </c:if>
                            
                            <c:forEach var="sidebar_kalender" items="${sidebar_kalender}">
                                <li>
                                    <a href="<c:url value="/app/gruppenkalender/${sidebar_kalender.getKalenderTitel()}/"/>"><i class="placeholder_search"></i>${sidebar_kalender.getKalenderTitel()}</a>
                                </li>
                            </c:forEach>
                            <li>
                                <a href="<c:url value="/logout/"/>" class="icon-logout"> <i class="placeholder_logout"></i> Logout ${pageContext.request.userPrincipal} </a>
                            </li>
                        </ul>
                    </div>
                </c:if>
                <%-- Menü --%>
        </header>

        <%-- Hauptinhalt der Seite --%>
        <main>    
            <jsp:invoke fragment="content"/>
        </main>
    </body>
</html>
