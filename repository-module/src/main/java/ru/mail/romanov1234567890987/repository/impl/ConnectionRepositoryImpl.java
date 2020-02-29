package ru.mail.romanov1234567890987.repository.impl;

import org.springframework.stereotype.Repository;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Repository
public class ConnectionRepositoryImpl implements ConnectionRepository {
    private final DataSource dataSource;

    public ConnectionRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
