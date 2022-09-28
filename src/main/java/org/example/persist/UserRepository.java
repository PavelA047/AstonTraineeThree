package org.example.persist;

import java.util.List;

public interface UserRepository {
    void insert(User user);

    List<User> findAll();

    User findById(long id);

    void update(User user);

    void delete(long id);

    List<String> findAllRoles();
}
