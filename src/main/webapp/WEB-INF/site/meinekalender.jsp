<%-- 
    Document   : meinekalender
    Created on : 02.03.2018, 11:18:30
    Author     : Patrick Baumer
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Meine Kalender
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/Meine_Kalender.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/tasks/"/>">Meine Kalender</a>
        </div>
    </jsp:attribute>
        
        <jsp:attribute name="content">
            
            test
        </jsp:attribute>
        
        
</template:base>