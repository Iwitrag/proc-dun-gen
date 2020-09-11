<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <%@ include file = "parts/head.jsp" %>
</head>

<body>
<header>
    <%@ include file = "parts/nav_menu.jsp" %>
</header>

<section class="content container">

    <h2 class="display-4">Input configuration from file</h2>

    <form:form method="POST" action="drawFromFile" enctype="multipart/form-data">
        <div class="form-group">
            <form:label path="fileConfiguration">Select file</form:label>
            <input type="file" class="form-control-file" name="fileConfiguration" />

            <%@ include file = "parts/draw_options.jsp" %>
        </div>
        <input type="submit" class="btn btn-primary" value="Generate"/>
    </form:form>

</section>

<%@ include file = "parts/body_footer.jsp" %>
</body>
</html>
