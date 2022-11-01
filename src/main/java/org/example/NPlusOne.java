package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import org.example.entities.KindUser;
import org.example.entities.Order;
import org.example.entities.Role;
import org.example.entities.User;
import org.example.repositories.RoleRepository;
import org.example.repositories.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class NPlusOne {
    private static EntityManager entityManager;

    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new org.hibernate.cfg.Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        entityManager = entityManagerFactory.createEntityManager();
        UserRepository userRepository = new UserRepository(entityManager);
        RoleRepository roleRepository = new RoleRepository(entityManager);

        //insert
//        String uniqueNick = "q";
//        Set<Role> roles = new HashSet<>(roleRepository.findAll());
//        List<Order> orders = new ArrayList<>();
//        for (int i = 0; i < 10; i++, uniqueNick = uniqueNick + ".") {
//            userRepository.saveOrUpdate(new KindUser("qq", "qq", uniqueNick, 1, roles, orders, "q"));
//        }

        //init N+1
//        List<User> all = userRepository.findAll();
//        for (User user : all) {
//            System.out.println(user.getRoles());
//        }

        //lazyInitEx
//        SessionFactory sessionFactory = new Configuration()
//                .configure(new File("D:\\Java\\AstonTraineeThree\\src\\main\\resources\\hibernate.cfg.xml"))
//                .buildSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        Set<Role> listOfRoles = new HashSet<>(session.createQuery("select r from Role r", Role.class)
//                .getResultList());
//        List<Order> orders = new ArrayList<>();
//
//        session.getTransaction().begin();
//        session
//                .persist(new KindUser("qq", "qq", "q", 1, listOfRoles, orders, "q"));
//        session.getTransaction().commit();
//
//        session.getTransaction().begin();
//        User user = session.createQuery("select u from User u where u.nickName = :nick", User.class)
//                .setParameter("nick", "q")
//                .getSingleResult();
//        session.getTransaction().commit();
//        session.close();
//
//        Set<Role> userRoles = user.getRoles();
//        for (Role userRole : userRoles) {
//            System.out.println(userRole.getId());
//        }

        //20_000 strings
        //insert
//        Random random = new Random();
//        List<Order> orders = new ArrayList<>();
//        for (int i = 0; i < 20000; i++) {
//            userRepository.saveOrUpdate(
//                    new KindUser("q", "q", null, random.nextInt(100_000), null, orders, "q")
//            );
//        }

        //select (time without index - 750, with index - 600)
//        long start = System.currentTimeMillis();
//        int size = entityManager.createQuery("select u from User u where u.age > 50000")
//                .getResultList().size();
//        long finish = System.currentTimeMillis();
//        long elapsed = finish - start;
//
//        System.out.println("Прошло времени, мс: " + elapsed);
//        System.out.println(size);
    }
}