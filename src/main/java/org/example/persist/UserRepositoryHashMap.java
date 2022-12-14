package org.example.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserRepositoryHashMap implements UserRepository {
    private final Map<Long, User> userMap = new ConcurrentHashMap<>();

    private final AtomicLong identity = new AtomicLong(0);

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User findById(long id) {
        return userMap.get(id);
    }

    @Override
    public void insert(User user) {
        long id = identity.getAndIncrement();
        user.setId(id);
        userMap.put(id, user);
    }

    @Override
    public void update(User user) {
        userMap.put(user.getId(), user);
    }

    @Override
    public void delete(long id) {
        userMap.remove(id);
    }

    @Override
    public List<String> findAllRoles() {
        return null;
    }
}
