package ru.mail.romanov1234567890987.repository.impl;

import org.springframework.stereotype.Service;
import ru.mail.romanov1234567890987.repository.ShopRepository;
import ru.mail.romanov1234567890987.repository.model.Shop;

import java.sql.*;

@Service
public class ShopRepositoryImpl implements ShopRepository {
    @Override
    public Shop add(Connection connection, Shop shop) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO shop(name, locations) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, shop.getName());
            statement.setString(2, shop.getLocation());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shop.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating shop failed, no ID obtained.");
                }
            }
            return shop;
        }
    }
}
