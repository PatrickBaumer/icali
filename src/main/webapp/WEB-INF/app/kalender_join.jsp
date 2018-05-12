
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Gruppenkalender beitreten
    </jsp:attribute>


    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/kalender_join.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="content">
        <div id="hauptfenster">      
            <div><h2>Gruppenkalender beitreten</h2></div>


            <div id="m1">
                <form method="POST" class="kalendererstellen">


                    <label for="kalenderTitel">
                        Titel:
                        <div class="side-by-side">
                            <p>${kalenderTitel}</p>
                        </div>
                    </label>

                    <label for="kalenderBeschreibung">
                        Beschreibung:
                        <div class="side-by-side">
                            <p>${kalenderBeschreibung}</p>
                        </div>
                    </label>

                    <label for="kalenderAdmin">
                        Kalenderadmin:
                        <div class="side-by-side">
                            <p>${kalenderAdmin}</p>
                        </div>
                    </label>

                    <c:choose>
                        <c:when test="${mitglied}">
                            <div>
                                <button  type="submit" name="action" value="leave">Kalender verlassen</button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <label for="join_password1">
                                Einschreibeschlüssel:
                                <span class="required">*</span>
                            </label>
                            <div class="side-by-side">
                                <input type="password" name="password1" >
                            </div>
                            <label for="join_password2">
                                Einschreibeschlüssel (wdh.):
                                <span class="required">*</span>
                                <div class="side-by-side">
                                    <input type="password" name="password2" >
                                </div>
                            </label>
                            <div>
                                <button  type="submit" name="action" value="join">Beitreten</button>
                            </div>
                        </c:otherwise>
                    </c:choose>


                    <c:if test="${!empty kalenderjoin_form.errors}">
                        <ul class="errors">
                            <c:forEach items="${kalenderjoin_form.errors}" var="error">
                                <li>${error}</li>
                                </c:forEach>
                        </ul>
                    </c:if>

                </form>
            </div>
        </div>
    </jsp:attribute>
</template:base>
