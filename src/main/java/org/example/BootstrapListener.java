package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.entities.KindUser;
import org.example.entities.Order;
import org.example.entities.Role;
import org.example.persist.UserRepositoryDb;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebListener
public class BootstrapListener implements ServletContextListener {
//    private static final Logger logger = LoggerFactory.getLogger(BootstrapListener.class);
//    private static DataSource dataSource;
//
//    static {
//        try {
//            Class.forName("org.postgresql.Driver");
//            Context ctx = new InitialContext();
//            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/users");
//        } catch (ClassNotFoundException | NamingException e) {
//            logger.error("", e);
//        }
//    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        UserRepository userRepository = new UserRepositoryDb(dataSource);
//        sce.getServletContext().setAttribute("userRepository", userRepository);
        EntityManagerFactory entityManagerFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        RoleRepository roleRepository = new RoleRepository(entityManager);
        sce.getServletContext().setAttribute("userRepository", userRepository);
        sce.getServletContext().setAttribute("roleRepository", roleRepository);
    }
}