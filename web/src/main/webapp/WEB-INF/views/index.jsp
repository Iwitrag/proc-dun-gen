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

    <h2 class="display-4">Welcome</h2>

    <div class="mt-5 mx-md-2 mx-lg-5">
    <a href="<c:url value="/manual" />" class="btn btn-outline-primary btn-block">Input configuration manually</a>
    <a href="<c:url value="/fromFile" />" class="btn btn-outline-primary btn-block">Load configuration from file</a>
    <a href="<c:url value="/about" />" class="btn btn-outline-secondary btn-block">About this website</a>
    </div>

</section>

<%@ include file = "parts/body_footer.jsp" %>
</body>
</html>
