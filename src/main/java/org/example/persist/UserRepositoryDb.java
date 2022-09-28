package org.example.persist;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class UserRepositoryDb implements UserRepository {
    private final DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;

    public UserRepositoryDb(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void insert(User user) {
        Set<String> roles = user.getRole();
        long userId = 0;
        long roleId = 0;
        ResultSet rs;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("INSERT INTO users_table (id, name) VALUES (DEFAULT, ?)");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.executeUpdate();
            if (user.getRole() == null) {
                connection.commit();
                return;
            }

            preparedStatement = connection.prepareStatement("SELECT id FROM users_table WHERE name = ?");
            preparedStatement.setString(1, user.getUsername());
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userId = rs.getLong(1);
            }

            for (String role : roles) {
                preparedStatement = connection.prepareStatement("SELECT id FROM roles_table WHERE name = ?");
                preparedStatement.setString(1, role);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    roleId = rs.getLong(1);
                }

                preparedStatement = connection
                        .prepareStatement("INSERT INTO users_roles (id, user_id, role_id) VALUES (DEFAULT, ?, ?)");
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, roleId);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        ResultSet rs;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement();
            rs = statement.executeQuery("SELECT * FROM users_table");
            while (rs.next()) {
                users.add(new User(rs.getLong(1), rs.getString(2)));
            }
            rs = statement.executeQuery("SELECT users.id as user_id, roles.name as role_name\n" +
                    "FROM users_table users\n" +
                    "         INNER JOIN users_roles ur on users.id = ur.user_id\n" +
                    "         INNER JOIN roles_table roles on ur.role_id = roles.id");
            Map<Long, HashSet<String>> rolesOfUsers = new HashMap<>();
            HashSet<String> innerList;
            while (rs.next()) {
                Long userId = rs.getLong(1);
                String role = rs.getString(2);
                if (rolesOfUsers.get(userId) == null) {
                    innerList = new HashSet<>();
                } else innerList = rolesOfUsers.get(userId);
                innerList.add(role);
                rolesOfUsers.put(userId, innerList);
            }
            users.forEach(user -> {
                user.setRole(rolesOfUsers.get(user.getId()));
            });
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }

    @Override
    public User findById(long id) {
        User user = null;
        try {
            connection = getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM users_table WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new User(rs.getLong(1), rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public void update(User user) {
        Set<String> roles = user.getRole();
        ResultSet rs;
        long userId = user.getId();
        long roleId = 0;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection
                    .prepareStatement("UPDATE users_table SET name = ? WHERE id = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
            if (roles == null) {
                connection.commit();
                return;
            }

            for (String role : roles) {
                preparedStatement = connection.prepareStatement("SELECT id FROM roles_table WHERE name = ?");
                preparedStatement.setString(1, role);
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    roleId = rs.getLong(1);
                }

                preparedStatement = connection
                        .prepareStatement("INSERT INTO users_roles (id, user_id, role_id) VALUES (DEFAULT, ?, ?)");
                preparedStatement.setLong(1, userId);
                preparedStatement.setLong(2, roleId);
                preparedStatement.executeUpdate();
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(long id) {
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement("DELETE FROM users_roles WHERE user_id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("DELETE FROM users_table WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> findAllRoles() {
        List<String> roles = new ArrayList<>();
        try {
            connection = getConnection();
            statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM roles_table");
            while (rs.next()) {
                roles.add(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return roles;
    }
}
