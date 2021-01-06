<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<header>
    <h1>Welcome!</h1>
    <h2>Please authenticate.</h2>
</header>

<main>
    <form action="${pageContext.servletContext.contextPath}/login" method="post">
        Username: <input type="text" name="username" value="Andrea"><br>
        <button type="submit">Login</button>
    </form>
</main>
</body>
</html>