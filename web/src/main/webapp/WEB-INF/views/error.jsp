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

    <h2 class="display-4">Error occured :(</h2>

    <div class="alert alert-danger text-break" role="alert">
        ${message}
    </div>
    <p class="text-break">${stack}</p>

</section>

<%@ include file = "parts/body_footer.jsp" %>
</body>
</html>
