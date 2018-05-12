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
        <link rel="stylesheet" href="<c:url value="/css/calendar.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="menu">

    </jsp:attribute>

    <jsp:attribute name="content">
        <div id="hauptfenster">
            <%-- Suchfilter --%>
            <form method="GET" class="horizontal" id="search">

                <div id="backAndForth">
                    <button type="submit" name="calendar_button" value="week_back">◄</button>
                    <button type="submit" name="calendar_button" value="week_forth">►</button>
                    <button type="submit" name="calendar_button" value="today">Heute</button>  
                </div>
            </form>  
            <div class="week_calendar">
                <table border="1">
                    <caption> <b>Wochenansicht ${shown_month} ${shown_year}</b></caption>
                    <tr>
                        <th>KW${week_of_year}</th>
                            <c:forEach var="week_key" items="${week.keySet()}">

                            <th>${week_key.getDayOfMonth()}.${week_key.getMonthValue()} ${week_key.getDayOfWeek()}</th>
                            </c:forEach>
                        </tr>
                        <tr>
                            <td>Termine:</td>
                        <c:forEach var="week_key" items="${week.keySet()}">
                            <td>
                            <c:forEach var="termin" items="${week.get(week_key)}">
                                        <a href="<c:url value="/app/erstelleTermin/${termin.terminId}/"/>">${termin.getTerminId()} | ${termin.getTerminTitel()} | Start:${termin.getStartUhrzeit()}</a> <br />
                            </c:forEach>
                            </td>

                        
                        </c:forEach>
                            </tr>

                


                </table>
            </div>
        </div>
    </jsp:attribute>
</template:base>