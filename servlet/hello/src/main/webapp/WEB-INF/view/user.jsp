<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>User</title>
</head>
<body>
<header>
    <h1>Hi, ${sessionScope.username}!</h1>
</header>

<main>
    How are you?
</main>

<footer>
    <form action="${pageContext.servletContext.contextPath}/restricted/logout">
        <button type="submit">Logout</button>
    </form>
</footer>
</body>
</html>