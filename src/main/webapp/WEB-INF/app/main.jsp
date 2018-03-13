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

    <jsp:attribute name="content">
        <div id="hauptfenster">
        <%-- Suchfilter --%>
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_text" value="${param.search_text}" placeholder="Stichwortsuche"/>
            <button class="icon-search" type="submit">
                Suchen
            </button>
   
                        
     
                </div>
    </jsp:attribute>
</template:base>