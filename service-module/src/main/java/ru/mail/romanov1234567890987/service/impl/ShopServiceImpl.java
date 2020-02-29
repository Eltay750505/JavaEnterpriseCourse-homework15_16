package ru.mail.romanov1234567890987.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.repository.ShopRepository;
import ru.mail.romanov1234567890987.repository.model.Shop;
import ru.mail.romanov1234567890987.service.ShopService;
import ru.mail.romanov1234567890987.service.model.ShopDTO;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ShopServiceImpl implements ShopService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionRepository connectionRepository;
    private final ShopRepository shopRepository;

    public ShopServiceImpl(ConnectionRepository connectionRepository, ShopRepository shopRepository) {
        this.connectionRepository = connectionRepository;
        this.shopRepository = shopRepository;
    }

    @Override
    public ShopDTO addShop(ShopDTO shopDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Shop databaseShop = convertShopDTOToShop(shopDTO);
                Shop addedShop = shopRepository.add(connection, databaseShop);
                connection.commit();
                shopDTO.setId(addedShop.getId());
                return shopDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    private Shop convertShopDTOToShop(ShopDTO shopDTO) {
        Shop databaseShop = new Shop();
        databaseShop.setName(shopDTO.getName());
        databaseShop.setLocation(shopDTO.getLocation());
        return databaseShop;
    }
}
