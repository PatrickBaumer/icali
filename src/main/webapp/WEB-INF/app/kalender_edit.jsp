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
                Gruppenkalender bearbeiten
            </c:when>
            <c:otherwise>
                Gruppenkalender erstellen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>


    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/termin_edit.css"/>" />
    </jsp:attribute>


    <jsp:attribute name="menu">
        <div class="menuitem">
            <div>
                <a href="<c:url value="/app/kalender/"/>" class="icon-zuruck">zurück</a>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <script language="JavaScript">
            function toggle(source) {
                var checkboxes = document.querySelectorAll('input[type="checkbox"]');
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i] != source)
                        checkboxes[i].checked = source.checked;
                }
            }
        </script>
        <div id="hauptfenster">



            <c:choose>
                <c:when test="${idAnzeige}">
                </c:when>  
                <c:otherwise>
                    <c:choose>
                        <c:when test="${edit}">
                            <div><h2>Gruppenkalender bearbeiten</h2></div>
                        </c:when>
                        <c:otherwise>
                            <div><h2>Gruppenkalender erstellen</h2></div>
                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

            <div id="m1">
                <form method="POST" class="kalendererstellen">
                    <c:choose>
                        <c:when test="${idAnzeige}">
                            <h2>Ihre Kalender-ID: ${kId} !</h2> 
                            <h3>Diese wird benötigt damit andere Benutzer ihren Gruppenkalender finden und beitreten können.</h3>
                            <div>
                                <button  type="submit" name="action" value="verify">OK</button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <label for="kalender_titel">
                                Kalendertitel:
                                <span class="required">*</span>
                            </label>
                             <div class="side-by-side">
                                <input type="text" name="kalenderTitel" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kalenderTitel"][0]}" placeholder="Kalendername"/></div> 
                                </div>
                            <label for="kalender_titel">
                                Kalenderbeschreibung:
                            </label>
                                <div class="side-by-side">
                                <input type="text" name="kalenderBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kB"][0]}" placeholder="Beschreibung">
                            </div>
                            <label for="kalender_password1">
                                Einschreibeschlüssel:
                                <span class="required">*</span>
                            </label>
                            <div class="side-by-side">
                                <input type="password" name="password1" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["password1"][0]}">
                            </div>
                            <label for="kalender_password2">
                                Einschreibeschlüssel (wdh.):
                                <span class="required">*</span>
                                <div class="side-by-side">
                                    <input type="password" name="password2" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["password2"][0]}">
                                </div>
                            </label>
                            <div>
                                <table>
                                    <tr>
                                        <th>Farbe</th>
                                        <th>Bezeichnung</th>
                                    </tr>
                                    <tr>
                                        <td><label>Gelb</label></td>
                                        <td><input type="text" name="gelbBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][0]}"></td>
                                    </tr>
                                    <tr>
                                        <td><label>Grün</label></td>
                                        <td><input type="text" name="gruenBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][1]}"></td>
                                        
                                    </tr>
                                    <tr>
                                        <td><label>Blau</label></td>
                                        <td><input type="text" name="blauBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][2]}"></td>
                                        
                                    </tr>
                                    <tr>
                                        <td><label>Rot</label></td>
                                        <td><input type="text" name="rotBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][3]}"></td>
                                        
                                    </tr>
                                    <tr>
                                        <td><label>Lila</label></td>
                                        <td><input type="text" name="lilaBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][4]}"></td>
                                        
                                    </tr>
                                    <tr>
                                        <td><label>Braun</label></td>
                                        <td><input type="text" name="braunBeschreibung" ${readonly ? 'readonly="readonly"' : ''} value="${kalender_form.values["kategorie"][5]}"></td>
                                        
                                    </tr>
                                </table>
                            </div>
                            <div>
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
                            </div>

                            <c:if test="${!empty kalender_form.errors}">
                                <ul class="errors">
                                    <c:forEach items="${kalender_form.errors}" var="error">
                                        <li>${error}</li>
                                        </c:forEach>
                                </ul>
                            </c:if>

                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </jsp:attribute>
</template:base>
