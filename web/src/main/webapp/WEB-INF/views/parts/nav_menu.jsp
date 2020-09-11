<%--@elvariable id="currentMenu" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar text-nowrap navbar-expand-md navbar-dark fixed-top bg-dark">
    <div class="container">
        <span class="navbar-brand mb-0 h1">Visualiser of dungeons</span>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Přepnout nabídku">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
            <ul class="navbar-nav ml-5">
                <li class="nav-item${currentMenu.matches("(?i)index") ? ' active' : ''}">
                    <a class="nav-link" href="<c:url value="/" />">Home</a>
                </li>
                <li class="nav-item${currentMenu.matches("(?i)manual") ? ' active' : ''}">
                    <a class="nav-link" href="<c:url value="/manual" />">Manual input</a>
                </li>
                <li class="nav-item${currentMenu.matches("(?i)fromFile") ? ' active' : ''}">
                    <a class="nav-link" href="<c:url value="/fromFile" />">From file</a>
                </li>
                <li class="nav-item${currentMenu.matches("(?i)about") ? ' active' : ''}">
                    <a class="nav-link" href="<c:url value="/about" />">About this web</a>
                </li>
            </ul>
        </div>
    </div>
</nav>