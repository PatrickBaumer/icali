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
        <link rel="stylesheet" href="<c:url value="/css/kalender_list.css"/>" />
        <link rel="stylesheet" href="<c:url value="/css/calendar.css"/>" />
    </jsp:attribute>




    <jsp:attribute name="content">
        <div id="hauptfenster">
            <%-- Monatskalender --%>
            <div id="headline">
                <h1 id="month_year">${shown_month} ${shown_year}</h1>
                <h1 id="caption">Monatsansicht</h1>
            </div>
                <div id="month_buttons">
                <form method="GET" class="horizontal" id="buttons">
                        <button type="submit" name="calendar_button" value="year_back">◄◄</button>
                        <button type="submit" name="calendar_button" value="month_back">◄</button>
                        <button type="submit" name="calendar_button" value="month_forth">►</button>
                        <button type="submit" name="calendar_button" value="year_forth">►►</button>
                        <button type="submit" name="calendar_button" value="today">Heute</button>  
                </form>
                </div>

                <div class="month_calendar">
                    <table border="1">
                        <tr>
                            <th>Mo</th>
                            <th>Di</th>
                            <th>Mi</th>
                            <th>Do</th>
                            <th>Fr</th>
                            <th>Sa</th>
                            <th>So</th>
                        </tr>
                        <!-- Prüft, ob Tag in der Woche größer 15 ist, wenn ja dann wird er grau-->
                        <tr>
                            <c:forEach var="week1" items="${week1}">
                                <c:choose>
                                    <c:when test="${week1.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week1.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week1.getDayOfMonth()}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week2" items="${week2}">
                                <c:choose>
                                    <c:when test="${week2.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week2.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week2.getDayOfMonth()}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week3" items="${week3}">
                                <td>${week3.getDayOfMonth()}</td>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week4" items="${week4}">
                                <td>${week4.getDayOfMonth()}</td>
                            </c:forEach>
                        </tr>
                        <!-- Prüft, ob Tag in der Woche kleiner ist als 15, wenn ja dann grau -->
                        <tr>
                            <c:forEach var="week5" items="${week5}">
                                <c:choose>
                                    <c:when test="${week5.getDayOfMonth()<15}">
                                        <td><font color="#D3D3D3">${week5.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week5.getDayOfMonth()}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>
                        <tr>
                            <c:forEach var="week6" items="${week6}">
                                <c:choose>
                                    <c:when test="${week6.getDayOfMonth()<15}">
                                        <td><font color="#D3D3D3">${week6.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week6.getDayOfMonth()}</td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </tr>                        
                    </table>
                </div>
            </div>
        </jsp:attribute>
    </template:base>