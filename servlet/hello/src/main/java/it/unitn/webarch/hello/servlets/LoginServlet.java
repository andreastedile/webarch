package it.unitn.webarch.hello.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");
        if (!isValidUsername(username)) {
            resp.sendRedirect(getServletContext().getContextPath() + "/restricted/user");
            return;
        }

        req.getSession(true)
                .setAttribute("username", username);

        resp.sendRedirect(getServletContext().getContextPath() + "/restricted/user");
    }

    boolean isValidUsername(String username) {
        return username != null && !username.isBlank();
    }
}
