package org.example;

import org.example.persist.User;
import org.example.persist.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("id", id);
            getServletContext().getRequestDispatcher("/user_form.jsp").forward(req, resp);
        } else if (req.getParameter("action").equals("delete")) {
            int id = Integer.parseInt(req.getParameter("id"));
            userRepository.delete(id);
            resp.sendRedirect("user.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        if (request.getParameter("id") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            User user = userRepository.findById(id);
            user.setUsername(request.getParameter("username"));
            userRepository.update(user);
        } else {
            String name = request.getParameter("username");
            User user = new User(name);
            userRepository.insert(user);
        }
        response.sendRedirect("user.jsp");
    }
}
