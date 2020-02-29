package ru.mail.romanov1234567890987.repository;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

public interface GeneralRepository<T> {
    T add(Connection connection, T t) throws SQLException;

    //int delete(Connection connection, Serializable id) throws SQLException;

    //void update(Connection connection, T t) throws SQLException;
}
