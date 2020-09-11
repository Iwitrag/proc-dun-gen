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

    <h2 class="display-4">Input configuration manually</h2>

    <form:form method="POST" action="drawManual">
        <div class="form-group">
            <form:label path="stringConfiguration">Configuration</form:label>
            <form:textarea path="stringConfiguration" cssClass="form-control mb-2" rows="15" placeholder="Input dungeon configuration there..." />
            <form:label path="supportedFileType">Parse as file type</form:label>
            <form:select path="supportedFileType" cssClass="form-control">
                <form:options/>
            </form:select>

            <%@ include file = "parts/draw_options.jsp" %>
        </div>
        <input type="submit" class="btn btn-primary mt-2" value="Generate"/>
    </form:form>

</section>

<%@ include file = "parts/body_footer.jsp" %>
</body>
</html>
