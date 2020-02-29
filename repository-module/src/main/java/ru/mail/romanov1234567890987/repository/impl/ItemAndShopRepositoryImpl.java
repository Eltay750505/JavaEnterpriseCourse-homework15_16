package ru.mail.romanov1234567890987.repository.impl;

import org.springframework.stereotype.Repository;
import ru.mail.romanov1234567890987.repository.ItemAndShopRepository;
import ru.mail.romanov1234567890987.repository.model.ItemAndShop;

import java.sql.*;

@Repository
public class ItemAndShopRepositoryImpl implements ItemAndShopRepository {
    @Override
    public ItemAndShop add(Connection connection, ItemAndShop itemAndShop) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO item_and_shop(item_id, shop_id) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setInt(1, itemAndShop.getIdItem());
            statement.setInt(2, itemAndShop.getIdShop());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating itemShop failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    itemAndShop.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating itemShop failed, no ID obtained.");
                }
            }
            return itemAndShop;
        }
    }
}
