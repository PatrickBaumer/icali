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

    </jsp:attribute>

    <jsp:attribute name="content">
        <div id="hauptfenster">
            <%-- Suchfilter --%>
            <form method="GET" class="horizontal" id="search">
                <!--
                <input type="text" name="search_text" value="${param.search_text}" placeholder="Stichwortsuche"/>
                <button class="icon-search" type="submit">
                    Suchen
                </button>
                
                <h1>Wochenansicht</h1> -->

                <div class="month_year">
                    ${shown_year}
                </div>

                <div id="backAndForth">
                    <button type="submit" name="calendar_button" value="week_back">◄</button>
                    <button type="submit" name="calendar_button" value="week_forth">►</button>
                    <button type="submit" name="calendar_button" value="today">Heute</button>  
                </div>
            </form>  
            <div class="week_calendar">
                <table border="1">
                    <caption>Wochenansicht</caption>
                    <tr>
                        <th>KW${week_of_year}</th>
                        <th>${week[0].getDayOfMonth()}.${week[0].getMonthValue()} Montag</th>
                        <th>${week[1].getDayOfMonth()}.${week[1].getMonthValue()} Dienstag</th>
                        <th>${week[2].getDayOfMonth()}.${week[2].getMonthValue()} Mittwoch</th>
                        <th>${week[3].getDayOfMonth()}.${week[3].getMonthValue()} Donnerstag</th>
                        <th>${week[4].getDayOfMonth()}.${week[4].getMonthValue()} Freitag</th>
                        <th>${week[5].getDayOfMonth()}.${week[5].getMonthValue()} Samstag</th>
                        <th>${week[6].getDayOfMonth()}.${week[6].getMonthValue()} Sonntag</th>
                    </tr>
                    
                </table>
            </div>
        </div>
    </jsp:attribute>
</template:base>