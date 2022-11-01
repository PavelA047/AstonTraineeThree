package org.example;

import org.example.entities.AngryUser;
import org.example.entities.KindUser;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.example.repositories.RoleRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@WebServlet(urlPatterns = "/user")
public class UserServlet extends HttpServlet {

    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Override
    public void init() {
        userRepository = (UserRepository) getServletContext().getAttribute("userRepository");
        roleRepository = (RoleRepository) getServletContext().getAttribute("roleRepository");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("modify")) {
            long id = Long.parseLong(req.getParameter("id"));
            req.setAttribute("id", id);
            req.setAttribute("firstName", req.getParameter("firstName"));
            req.setAttribute("lastName", req.getParameter("lastName"));
            req.setAttribute("nickname", req.getParameter("nickname"));
            req.setAttribute("age", req.getParameter("age"));
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
            long userId = Long.parseLong(request.getParameter("id"));
            User user = userRepository.findById(userId);
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            user.setNickName(request.getParameter("nickname"));
            user.setAge(Integer.parseInt(request.getParameter("age")));
            String roleName = request.getParameter("select");
            if (roleName == null) {
                userRepository.saveOrUpdate(user);
            } else {
                Long roleId = Long.parseLong(roleName);
                Role role = roleRepository.findById(roleId);
                Set<Role> roles = user.getRoles();
                roles.add(role);
                user.setRoles(roles);
                userRepository.saveOrUpdate(user);
            }
        } else {
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String nickname = request.getParameter("nickname");
            int age = Integer.parseInt(request.getParameter("age"));
            String roleName = request.getParameter("select");
            if (roleName == null) {
                User user = new KindUser(firstName, lastName, nickname, age, null, new ArrayList<>(), "hello ;)");
                userRepository.saveOrUpdate(user);
            } else {
                Long roleId = Long.parseLong(roleName);
                Role role = roleRepository.findById(roleId);
                Set<Role> roles = new HashSet<>();
                roles.add(role);
                User user = new AngryUser(firstName, lastName, nickname, age, roles, new ArrayList<>(), "f*** offf");
                userRepository.saveOrUpdate(user);
            }
        }
        response.sendRedirect("user.jsp");
    }
}
