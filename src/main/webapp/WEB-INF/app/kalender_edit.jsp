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
                Kalender erstellen
                <div id="m1">
                    <form method="POST" class="kalendererstellen">
                       <div id="m1"><input type="text" name="kalenderTitel" value="" placeholder="Kalendername"/></div> 
                    
                    <div id="kalenderBeschreibung">
                        <textarea name="kalenderBeschreibung" value="" placeholder="Beschreibung">Beschreibung</textarea>

                    </div>
                       <div>
                           <table>
                               <tr>
                                   <th>Farbe</th>
                                   <th>Bezeichnung</th>
                                   <th><input type="checkbox" name="checkall" onClick="toggle(this)"></th>
                               </tr>
                               <tr>
                                   <td><label>Gelb</label></td>
                                   <td><input type="text" name="gelbBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkGelb" value="gelb"></td>
                               </tr>
                               <tr>
                                   <td><label>Grün</label></td>
                                   <td><input type="text" name="gruenBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkGruen" value="gruen"></td>
                               </tr>
                               <tr>
                                   <td><label>Blau</label></td>
                                   <td><input type="text" name="blauBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkBlau" value="blau"></td>
                               </tr>
                               <tr>
                                   <td><label>Rot</label></td>
                                   <td><input type="text" name="rotBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkRot" value="rot"></td>
                               </tr>
                               <tr>
                                   <td><label>Lila</label></td>
                                   <td><input type="text" name="lilaBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkLila" value="lila"></td>
                               </tr>
                               <tr>
                                   <td><label>Braun</label></td>
                                   <td><input type="text" name="braunBeschreibung" value=""></td>
                                   <td><input type="checkbox" name="checkBraun" value="braun"></td>
                               </tr>
                           </table>
                       </div>
                    </form>
                </div>
                <div id="m1">
                    <button class="icon-erstelleK" type="submit" name="action" value="save">Erstellen</button>
                </div>
                        
     
        </div>
    </jsp:attribute>
</template:base>
        