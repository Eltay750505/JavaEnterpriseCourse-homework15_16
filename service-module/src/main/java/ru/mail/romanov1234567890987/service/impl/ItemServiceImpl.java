package ru.mail.romanov1234567890987.service.impl;

import org.springframework.stereotype.Service;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.repository.ItemAndShopRepository;
import ru.mail.romanov1234567890987.repository.ItemDetailsRepository;
import ru.mail.romanov1234567890987.repository.ItemRepository;
import ru.mail.romanov1234567890987.repository.model.Item;
import ru.mail.romanov1234567890987.repository.model.ItemDetails;
import ru.mail.romanov1234567890987.service.ItemService;
import ru.mail.romanov1234567890987.service.model.ItemDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final ConnectionRepository connectionRepository;
    private final ItemRepository itemRepository;
    private final ItemDetailsRepository itemDetailsRepository;
    private final ItemAndShopRepository itemAndShopRepository;

    public ItemServiceImpl(ConnectionRepository connectionRepository, ItemRepository itemRepository, ItemDetailsRepository itemDetailsRepository, ItemAndShopRepository itemAndShopRepository) {
        this.connectionRepository = connectionRepository;
        this.itemRepository = itemRepository;
        this.itemDetailsRepository = itemDetailsRepository;
        this.itemAndShopRepository = itemAndShopRepository;
    }

    @Override
    public ItemDTO addItem(ItemDTO itemDTO) {
        try (Connection connection = connectionRepository.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Item databaseItem = convertItemDTOToItem(itemDTO);
                Item addedItem = itemRepository.add(connection, databaseItem);
                databaseItem.getItemDetails().setIdItem(addedItem.getId());
                itemDetailsRepository.add(connection, databaseItem.getItemDetails());
                connection.commit();
                itemDTO.setId(addedItem.getId());
                return itemDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void deleteItemsWhichHavingShopDependency() {

    }

    private Item convertItemDTOToItem(ItemDTO itemDTO) {
        Item databaseItem = new Item();
        databaseItem.setName(itemDTO.getName());
        databaseItem.setDescription(itemDTO.getDescription());
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setPrice(itemDTO.getPrice());
        databaseItem.setItemDetails(itemDetails);
        return databaseItem;
    }
}
