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
        <c:choose>
            <c:when test="${edit}">
                Termin bearbeiten
            </c:when>
            <c:otherwise>
                Termin erstellen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>


    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/termin_edit.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="menu">
        <div class="menuitem">
            <div>
                <a href="<c:url value="/app/kalender/"/>" class="icon-zuruck">zurück </a>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div id="hauptfenster">
                    <c:choose>
                        <c:when test="${edit}">
                            <div><h2>Termin bearbeiten</h2></div>
                        </c:when>
                        <c:otherwise>
                            <div><h2>Termin erstellen</h2></div>
                        </c:otherwise>
                    </c:choose>

            <div id="m1">
                <form method="POST" class="terminerstellen">
                    <label for="kalender">Kalender:</label>
                    <div class="side-by-side">
                        <select name="kalenderWahl" ${edit ? 'readonly="readonly"' : ''}>
                            <c:if test="${allKalender.isEmpty()}">
                                <option value="">Keine Kalender</option>
                            </c:if>

                            <c:forEach items="${allKalender}" var="kalender">
                                <option value="${kalender.kalenderTitel}"${termin_form.values["termin_kalender"][0] == kalender.kalenderTitel ? 'selected' : ''}>
                                    <c:out value="${kalender.kalenderTitel}"/>
                                </option>
                            </c:forEach>
                        </select>
                            
                            <c:if test="${!edit}">
                                <div><input type="submit" value="Kalender wählen"></div>
                            </c:if>                        
                    </div>

                    <div id="m1"><input type="text" name="terminTitel" value="${termin_form.values['terminTitel'][0]}" ${readonly ? 'readonly="readonly"' : ''} placeholder="Terminname"/></div> 
                    <div id="m1"><input type="date" name="anfangsDatum" value="${termin_form.values['anfangsDatum'][0]}" ${readonly ? 'readonly="readonly"' : ''} placeholder="Anfangsdatum"/>
                        <input type="time" name="anfangsZeit" value="${termin_form.values['anfangsZeit'][0]}" ${readonly ? 'readonly="readonly"' : ''} placeholder="Anfangszeit"/>
                    </div>
                    <div id="m1">
                        <input type="date" name="endDatum" value="${termin_form.values['endDatum'][0]}" ${readonly ? 'readonly="readonly"' : ''} placeholder="Enddatum"/>
                        <input type="time" name="endZeit" value="${termin_form.values['endZeit'][0]}" ${readonly ? 'readonly="readonly"' : ''} placeholder="Endzeit"/>
                    </div>
                    <div id="terminBeschreibung">
                        <input type="text" name="beschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${termin_form.values['beschreibung'][0]}" placeholder="Beschreibung"></textarea>

                    </div>

                    <label for="termin_category" ${readonly ? 'readonly="readonly"' : ''}>Kategorie:</label>
                    <div class="side-by-side">
                        <select name="termin_category" >
                            <option value="">Keine Kategorie</option>

                            <c:forEach items="${categories}" var="category">
                                <option value="${category.id}" ${termin_form.values["termin_category"][0] == category.id ? 'selected' : ''}>
                                    <c:out value="${category.kategorieName}" />
                                </option>
                            </c:forEach>
                        </select>
                    </div>
            </div>
                 
               <c:if test="${!readonly}">
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit" name="action" value="save">
                            Sichern
                        </button>

                        <c:if test="${edit}">
                            <button class="icon-trash" type="submit" name="action" value="delete">
                                Löschen
                            </button>
                        </c:if>
                    </div>
                </c:if>
            
            <c:if test="${!empty termin_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${termin_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
            </c:if>
            
        </form>
    </div>
</jsp:attribute>
</template:base>