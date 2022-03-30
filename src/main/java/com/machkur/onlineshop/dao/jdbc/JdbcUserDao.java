package com.machkur.onlineshop.dao.jdbc;

import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.dao.mapper.UserRowMapper;
import com.machkur.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
@RequiredArgsConstructor
public class JdbcUserDao implements UserDao {

    private static final String ADD_USER_SQL = "INSERT INTO users (email, password, date, salt) VALUES (?, ?, ?, ?);";
    private static final String FIND_BY_EMAIL_AND_PASSWORD_SQL = "SELECT id, email, password, salt, role, date FROM users WHERE email = ? AND password = ?;";
    private static final String FIND_BY_EMAIL_SQL = "SELECT id, email, password, salt, role, date FROM users WHERE email = ?;";
    private final DataSource dataSource;
    private final static UserRowMapper USER_ROW_MAPPER = new UserRowMapper();

    @Override
    public void addUser(User user) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER_SQL)) {
            if (user.getEmail() != null && user.getEmail().length() > 0 &&
                    user.getPassword() != null && user.getPassword().length() > 0) {
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setDate(3, Date.valueOf(user.getCreationDate()));
                preparedStatement.setString(4, user.getSalt());
                preparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Fields login and password cannot be empty");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add user to database", e);
        }
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_AND_PASSWORD_SQL)) {
            if (email != null && email.length() > 0 && password != null && password.length() > 0) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, password);
            }
            User user = null;
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = USER_ROW_MAPPER.mapRow(resultSet);
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find user with email " + email, e);
        }
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_EMAIL_SQL)) {
            if (email != null && email.length() > 0) {
                preparedStatement.setString(1, email);
            }
            User user = null;
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = USER_ROW_MAPPER.mapRow(resultSet);
                }
                return user;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find user with email " + email, e);
        }
    }

}
