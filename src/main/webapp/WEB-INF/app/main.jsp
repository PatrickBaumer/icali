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
            <%-- beide folgende Links müssen noch auf die buttons--%>
            <div>
                <a href="<c:url value="/app/erstelleKalender/"/>" class="icon-erstelleKalender">Erstelle Kalender ${pageContext.request.userPrincipal}</a>
            </div>
            <div>
                <a href="<c:url value="/app/erstelleTermin/"/>" class="icon-erstelleTermin">Erstelle Termin ${pageContext.request.userPrincipal}</a>
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
                <h1>CSS Calendar</h1>

                <div class="month">
                    <ul>
                        <li class="prev">&#10094;</li>
                        <li class="next">&#10095;</li>
                        <li>
                            August<br>
                            <span style="font-size:18px">2017</span>
                        </li>
                    </ul>
                </div>

                <ul class="weekdays">
                    <li>Mo</li>
                    <li>Tu</li>
                    <li>We</li>
                    <li>Th</li>
                    <li>Fr</li>
                    <li>Sa</li>
                    <li>Su</li>
                </ul>

                <ul class="days">
                    <li>1</li>
                    <li>2</li>
                    <li>3</li>
                    <li>4</li>
                    <li>5</li>
                    <li>6</li>
                    <li>7</li>
                    <li>8</li>
                    <li>9</li>
                    <li><span class="active">10</span></li>
                    <li>11</li>
                    <li>12</li>
                    <li>13</li>
                    <li>14</li>
                    <li>15</li>
                    <li>16</li>
                    <li>17</li>
                    <li>18</li>
                    <li>19</li>
                    <li>20</li>
                    <li>21</li>
                    <li>22</li>
                    <li>23</li>
                    <li>24</li>
                    <li>25</li>
                    <li>26</li>
                    <li>27</li>
                    <li>28</li>
                    <li>29</li>
                    <li>30</li>
                    <li>31</li>
                </ul>


        </div>
    </jsp:attribute>
</template:base>