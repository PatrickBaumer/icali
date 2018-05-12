
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Gruppenkalender suchen
    </jsp:attribute>

    <jsp:attribute name="head">
        <%--        <link rel="stylesheet" href="<c:url value="/css/main.css"/>" /> --%>
        <link rel="stylesheet" href="<c:url value="/css/kalender_list.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="content">
        <div class="search_and_result">
        <form method="GET" class="horizontal" id="search">
            <input type="text" name="search_titel" value="${param.search_titel}" placeholder="Kalendername"/>
            
            <button class="icon-search" type="submit">
                Suchen
            </button>
        </form>

        <%-- Gefundene Aufgaben --%>
        <c:choose>
            <c:when test="${empty kalender}">
                <p>
                    Es wurden kein Kalender gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <div>
                <table>
                    <thead>
                        <tr>
                            <th>Titel</th>
                            <th>Kalenderadmin</th>
                            <th>Beschreibung</th>
                        </tr>
                    </thead>
                        <tr>
                            <td>
                                <a href="<c:url value="/app/kalenderBeitreten/${kalender.kalenderTitel}/"/>">
                                    ${kalender.kalenderTitel}
                                </a>
                            </td>
                            <td>
                                ${kalender.kalenderAdmin.username}
                            </td>
                            <td>
                                ${kalender.beschreibung}
                            </td>
                        </tr>
                </table>
                </div>
                
            </c:otherwise>
        </c:choose>
        </div>
    </jsp:attribute>
</template:base>