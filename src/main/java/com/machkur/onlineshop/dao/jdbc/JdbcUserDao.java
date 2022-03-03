package com.machkur.onlineshop.dao.jdbc;

import com.machkur.onlineshop.dao.ConnectionFactory;
import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.dao.mapper.UserRowMapper;
import com.machkur.onlineshop.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {

    private static final String ADD_USER_SQL = "INSERT INTO users (login, password, date) VALUES (?, ?, ?);";
    private static final String FIND_BY_LOGIN_AND_PASSWORD_SQL = "SELECT id, login, password, date FROM users WHERE login = ? AND password = ?;";
    private static final String FIND_ALL_USERS_SQL = "SELECT id, login, password, date FROM users;";
    private final ConnectionFactory connectionFactory;
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    public JdbcUserDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public void addUser(User user) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL)) {
            if (user.getLogin() != null && user.getLogin().length() > 0 && user.getPassword() != null && user.getPassword().length() > 0) {
                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setDate(3, Date.valueOf(user.getCreationDate()));
                preparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Fields login and password cannot be empty");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add user to database", e);
        }
    }

    @Override
    public User findUserByLoginAndPassword(String login, String password) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_AND_PASSWORD_SQL)) {
            if (login != null && login.length() > 0 && password != null && password.length() > 0) {
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, password);
            }
            User user = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = USER_ROW_MAPPER.mapRow(resultSet);
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find user with login " + login, e);
        }
    }

    @Override
    public List<User> findAllUsers() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_USERS_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<User> usersList = new ArrayList<>();
            while (resultSet.next()) {
                User user = USER_ROW_MAPPER.mapRow(resultSet);
                usersList.add(user);
            }

            return usersList;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get users", e);
        }
    }
}
