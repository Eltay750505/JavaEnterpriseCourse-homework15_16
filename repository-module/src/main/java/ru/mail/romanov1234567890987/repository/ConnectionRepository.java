package ru.mail.romanov1234567890987.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionRepository {
    Connection getConnection() throws SQLException;
}
