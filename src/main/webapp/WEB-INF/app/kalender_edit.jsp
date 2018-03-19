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
        <div id="hauptfenster">
                Kalender erstellen
                <div id="m1">
                    <form method="POST" class="kalendererstellen">
                       <div id="m1"><input type="text" name="kalenderTitel" value="" placeholder="Kalendername"/></div> 
                    
                    <div id="kalenderBeschreibung">
                        <textarea name="Beschreibung" value="" placeholder="Beschreibung">Beschreibung</textarea>

                    </div>
                    </form>
                </div>
                
                
                <div id="m1"><button class="icon-erstelleK" type="submit">Erstellen</button></div>
                        
     
        </div>
    </jsp:attribute>
</template:base>