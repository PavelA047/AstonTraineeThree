package org.example;

import org.example.persist.User;
import org.example.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

//then
@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() {
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("modify")) {
            long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("id", id);
            req.setAttribute("username", req.getParameter("username"));
            getServletContext().getRequestDispatcher("/user_form.jsp").forward(req, resp);
        } else if (req.getParameter("action").equals("delete")) {
            long id = Long.parseLong(req.getParameter("id"));
            userRepository.delete(id);
            resp.sendRedirect("user.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (request.getParameter("id") != null) {
            long id = Long.parseLong(request.getParameter("id"));
            User user = userRepository.findById(id);
            user.setUsername(request.getParameter("username"));
            String role = request.getParameter("select");
            if (role == null) {
                userRepository.update(user);
            } else {
                Set<String> roles = new HashSet<>();
                roles.add(role);
                user.setRole(roles);
                userRepository.update(user);
            }
        } else {
            String name = request.getParameter("username");
            String role = request.getParameter("select");
            if (role == null) {
                User user = new User(name);
                userRepository.insert(user);
            } else {
                Set<String> roles = new HashSet<>();
                roles.add(role);
                User user = new User(name, roles);
                userRepository.insert(user);
            }
        }
        response.sendRedirect("user.jsp");
    }
}
