package ru.mail.romanov1234567890987.repository.impl;

import org.springframework.stereotype.Repository;
import ru.mail.romanov1234567890987.repository.ItemDetailsRepository;
import ru.mail.romanov1234567890987.repository.model.ItemDetails;

import java.sql.*;

@Repository
public class ItemDetailsRepositoryImpl implements ItemDetailsRepository {
    @Override
    public ItemDetails add(Connection connection, ItemDetails itemDetails) throws SQLException {
        try (
                PreparedStatement statement = connection.prepareStatement(
                        "INSERT INTO item_details(item_id, price) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setInt(1, itemDetails.getIdItem());
            statement.setBigDecimal(2, itemDetails.getPrice());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating itemDetails failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    itemDetails.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating itemDetails failed, no ID obtained.");
                }
            }
            return itemDetails;
        }
    }
}
