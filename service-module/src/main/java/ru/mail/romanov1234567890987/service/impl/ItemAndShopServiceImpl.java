package ru.mail.romanov1234567890987.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.repository.ItemAndShopRepository;
import ru.mail.romanov1234567890987.repository.model.ItemAndShop;
import ru.mail.romanov1234567890987.service.ItemAndShopService;
import ru.mail.romanov1234567890987.service.model.ItemAndShopDTO;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ItemAndShopServiceImpl implements ItemAndShopService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionRepository connectionRepository;
    private final ItemAndShopRepository itemAndShopRepository;

    public ItemAndShopServiceImpl(ConnectionRepository connectionRepository, ItemAndShopRepository itemAndShopRepository) {
        this.connectionRepository = connectionRepository;
        this.itemAndShopRepository = itemAndShopRepository;
    }

    @Override
    public void addItemAndShopChain(ItemAndShopDTO itemAndShopDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                ItemAndShop databaseItemAndShop = convertItemAndShopDTOToDatabaseShop(itemAndShopDTO);
                ItemAndShop addedItemShop = itemAndShopRepository.add(connection, databaseItemAndShop);
                connection.commit();
                itemAndShopDTO.setId(addedItemShop.getId());
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private ItemAndShop convertItemAndShopDTOToDatabaseShop(ItemAndShopDTO itemAndShopDTO) {
        ItemAndShop databaseItemAndShop = new ItemAndShop();
        databaseItemAndShop.setIdItem(itemAndShopDTO.getIdItem());
        databaseItemAndShop.setIdShop(itemAndShopDTO.getIdShop());
        return databaseItemAndShop;
    }
}
