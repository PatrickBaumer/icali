<%-- 
    Copyright © 2018 Dennis Schulmeister-Zimolong

    E-Mail: dhbw@windows3.de
    Webseite: https://www.wpvs.de/

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
                <jsp:attribute name="title">
        Übersicht
    </jsp:attribute>
    

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/task_list.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="menu">
        <div class="menuitem">
            <div id="m1">
            <button class="icon-switch" type="submit">Wochenansicht/Monatsansicht   </button>
            </div>
            <%--<c:if test=abfrage> --%>
            <div id="m2">
                <form>
                <table id="kalender">
                    <tr>
                        <td><input type="checkbox" name="leer"></td>
                        <td>Meine Kalender</td>
                        <td>ID</td>
                    </tr>
                    
                    <tr>
                        <td><input type="checkbox" name="leer"></td>
                        <td>$_{KNamAausDB}</td>
                        <td>$_{idAusDB}</td>    
                    </tr>
                    <tr></tr>
                </table>
                </form>
            </div>
            <%--</c:if>--%>
            <div id="m2">
            <button class="icon-erstelleTermin" type="submit">neuen Termin erstellen   </button>
            </div>
            <div id="m3">
            <button class="icon-erstellenKalender" type="submit">neuen Kalender erstellen  </button>
            </div>
            <div id="m4">
            <button class="icon-hinzufuegenKalender" type="submit">Kalender hinzufügen   </button>
            </div>
            
        </div>
    </jsp:attribute>
<div id="hauptfenster">
        <jsp:attribute name="content"> Termin erstellen
                        
    

        
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="terminTitel" value="" placeholder="Terminname"/>
            <c:choose>
            <c:when test="${empty tasks}">
                <p>
                    Es wurden keine Anzeigen gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="icali.javaee.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Bezeichnung</th>
                            <th>Kategorie</th>
                            <th>Eigentümer</th>
                            <th>Erstellt am</th>
                            <th>Preis in €</th>
                        </tr>
                    </thead>
                    <c:forEach items="${kalender}" var="kalender">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/kalender/${kalender.name}/"/>">
                                    <c:out value="${task.shortText}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${task.category.name}"/>
                            </td>
                            <td>
                                <c:out value="${task.owner.username}"/>
                            </td>
                            <td>
                                <c:out value="${utils.formatDate(task.dueDate)}"/>
                                <c:out value="${utils.formatTime(task.dueTime)}"/>
                            </td>
                            <td>
                                <c:out value="${task.price}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Stichwortsuche"/>
            <input type="date" name="anfangsDatum" value="" placeholder="AnfangsDatum"/>
            <input type="date" name="endDatum" value="" placeholder="EndDatum"/>
            <input type="time" name="anfangszeit" value="" placeholder="anfangszeit"/>
            <input type="time" name="Endzeit" value="" placeholder="Endzeit"/>
            <button class="icon-search" type="submit">
                Suchen
            </button>
            
            
            
    </jsp:attribute>
</template:base>