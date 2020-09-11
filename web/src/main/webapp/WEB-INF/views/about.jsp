<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <%@ include file = "parts/head.jsp" %>
</head>

<body>
<header>
    <%@ include file = "parts/nav_menu.jsp" %>
</header>

<section class="content container">

    <h2 class="display-4">About this web page</h2>

    <p>This website was created as part of the Bachelor thesis
        on topic Procedural Generation of Dungeon Type Structures
        on Brno University of Technology (BUT), Faculty of Information
        Technology (FIT). Main goal of this Bachelor thesis was to
        create library to generate simple dungeons. Purpose of this web
        is to visualise outputs from that library.</p>

    <h4>How to use this website</h4>

    <p>Simply go to <a href="<c:url value="/" />">index page</a> and choose,
        whether to input configuration manually or from file. Website will then use
        this configuration, pass it to library which generates dungeon and
        visualise output.</p>

    <h4>Requirements to use this website</h4>

    <p>This website is designed to use with any modern desktop/mobile web browser.
    In order to visualisation to work, enabled JavaScript is required.</p>

</section>

<%@ include file = "parts/body_footer.jsp" %>
</body>
</html>