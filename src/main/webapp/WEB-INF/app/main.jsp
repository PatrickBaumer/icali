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
        <link rel="stylesheet" href="<c:url value="/css/calendar.css"/>" />
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
            <%-- beide folgende Links müssen noch auf die buttons--%>
            <div>
                <a href="<c:url value="/app/erstelleKalender/"/>" class="icon-erstelleKalender">Erstelle Kalender ${pageContext.request.userPrincipal}</a>
            </div>
            <div>
                <a href="<c:url value="/app/erstelleTermin/"/>" class="icon-erstelleTermin">Erstelle Termin ${pageContext.request.userPrincipal}</a>
            </div>

        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div id="hauptfenster">
            <%-- Suchfilter --%>
            <form method="GET" class="horizontal" id="search">
                <input type="text" name="search_text" value="${param.search_text}" placeholder="Stichwortsuche"/>
                <button class="icon-search" type="submit">
                    Suchen
                </button>
                <h1>Monatsansicht</h1>

                <div class="month_year">
                    ${shown_month} ${shown_year}
                </div>
                
                <div id="backAndForth">
                    <button type="submit" name="button" value="back">◄</button>
                    <button type="submit" name="button" value="forth">►</button>
                    <button type="submit" name="button" value="today">Heute</button>  
                </div>
                <div class="month_calendar">
                    <table>
                        <tr>
                            <th>Mo</th>
                            <th>Di</th>
                            <th>Mi</th>
                            <th>Do</th>
                            <th>Fr</th>
                            <th>Sa</th>
                            <th>So</th>
                        </tr>
                        <tr>
                            <c:forEach var="week1" items="${week1}">
                                <td>${week1}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week2" items="${week2}">
                                <td>${week2}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week3" items="${week3}">
                                <td>${week3}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week4" items="${week4}">
                                <td>${week4}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week5" items="${week5}">
                                <td>${week5}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week6" items="${week6}">
                                <td>${week6}</td>
                            </c:forEach>
                        </tr>                        
                    </table>
                </div>
        </div>
    </jsp:attribute>
</template:base>