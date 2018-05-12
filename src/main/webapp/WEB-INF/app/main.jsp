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
                    <caption> <b>Monatsansicht ${shown_month} ${shown_year}</b></caption>
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
                            
                           <c:forEach var="week1_key" items="${week1.keySet()}">
                                <c:choose>
                                    <c:when test="${week1_key.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week1_key.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week1_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week1.get(week1_key).isEmpty()}">
                                            <c:forEach var="termin1" items="${week1.get(week1_key)}">
                                                <a href="<c:url value="/app/erstelleTermin/${termin1.terminId}/"/>">${termin1.getTerminId()} | ${termin1.getTerminTitel()}</a> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </tr>
                        <tr>
                           <c:forEach var="week2_key" items="${week2.keySet()}">
                                <c:choose>
                                    <c:when test="${week2_key.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week2_key.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week2_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week2.get(week2_key).isEmpty()}">
                                            <c:forEach var="termin2" items="${week2.get(week2_key)}">
                                                <font color="'${termin2.getTerminKategorie().getKategorieFarbe().getColor()}'"><a style="" href="<c:url value="/app/erstelleTermin/${termin2.terminId}/"/>">${termin2.getTerminId()} | ${termin2.getTerminTitel()}</a></font> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </tr>   
                        
                        <tr>
                           <c:forEach var="week3_key" items="${week3.keySet()}">
                                
                                        <td>${week3_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week3.get(week3_key).isEmpty()}">
                                            <c:forEach var="termin3" items="${week3.get(week3_key)}">
                                                <a href="<c:url value="/app/erstelleTermin/${termin3.terminId}/"/>">${termin3.getTerminId()} | ${termin3.getTerminTitel()}</a> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                            </c:forEach>  
                        </tr> 
                        <tr>
                           <c:forEach var="week4_key" items="${week4.keySet()}">
                                
                                        <td>${week4_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week4.get(week4_key).isEmpty()}">
                                            <c:forEach var="termin4" items="${week4.get(week4_key)}">
                                                <a href="<c:url value="/app/erstelleTermin/${termin4.terminId}/"/>">${termin4.getTerminId()} | ${termin4.getTerminTitel()}</a> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                            </c:forEach>  
                        </tr> 
                        <!-- Prüft, ob Tag in der Woche kleiner ist als 15, wenn ja dann grau -->
                        <tr>
                           <c:forEach var="week5_key" items="${week5.keySet()}">
                                <c:choose>
                                    <c:when test="${week5_key.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week5_key.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week5_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week5.get(week5_key).isEmpty()}">
                                            <c:forEach var="termin5" items="${week5.get(week5_key)}">
                                                <a href="<c:url value="/app/erstelleTermin/${termin5.terminId}/"/>">${termin5.getTerminId()} | ${termin5.getTerminTitel()}</a> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </tr>   
                        <tr>
                           <c:forEach var="week6_key" items="${week6.keySet()}">
                                <c:choose>
                                    <c:when test="${week6_key.getDayOfMonth()>15}">
                                        <td><font color="#D3D3D3">${week6_key.getDayOfMonth()}</font></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td>${week6_key.getDayOfMonth()}
                                        <br />
                                        <c:if test="${!week6.get(week6_key).isEmpty()}">
                                            <c:forEach var="termin6" items="${week6.get(week6_key)}">
                                                <a href="<c:url value="/app/erstelleTermin/${termin6.terminId}/"/>">${termin6.getTerminId()} | ${termin6.getTerminTitel()}</a> <br />
                                            </c:forEach>
                                        </c:if>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>  
                        </tr>   
                    </table>
                </div>
            </div>
        </jsp:attribute>
    </template:base>