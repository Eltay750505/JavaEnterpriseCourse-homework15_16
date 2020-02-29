package ru.mail.romanov1234567890987.repository.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import ru.mail.romanov1234567890987.repository.ItemRepository;
import ru.mail.romanov1234567890987.repository.model.Item;

import java.lang.invoke.MethodHandles;
import java.sql.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public Item add(Connection connection, Item item) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO item(name, description) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, item.getName());
            statement.setString(2, item.getDescription());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
            return item;
        }
    }
}
