package org.example;

import org.example.persist.UserRepository;
import org.example.persist.UserRepositoryDb;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

@WebListener
public class BootstrapListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(BootstrapListener.class);
    private static DataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            Context ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/users");
        } catch (ClassNotFoundException | NamingException e) {
            logger.error("", e);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserRepository userRepository = new UserRepositoryDb(dataSource);
        sce.getServletContext().setAttribute("userRepository", userRepository);
    }
}
