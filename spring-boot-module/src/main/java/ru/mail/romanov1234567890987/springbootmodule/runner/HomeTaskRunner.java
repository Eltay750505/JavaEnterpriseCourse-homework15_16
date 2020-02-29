package ru.mail.romanov1234567890987.springbootmodule.runner;


import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.mail.romanov1234567890987.service.ItemAndShopService;
import ru.mail.romanov1234567890987.service.ItemService;
import ru.mail.romanov1234567890987.service.ShopService;
import ru.mail.romanov1234567890987.service.model.ItemDTO;
import ru.mail.romanov1234567890987.service.model.ItemAndShopDTO;
import ru.mail.romanov1234567890987.service.model.ShopDTO;

import java.math.BigDecimal;

@Component
public class HomeTaskRunner implements ApplicationRunner {
    private final ItemService itemService;
    private final ShopService shopService;
    private final ItemAndShopService itemAndShopService;


    public HomeTaskRunner(ItemService itemService, ShopService shopService, ItemAndShopService itemAndShopService) {
        this.itemService = itemService;
        this.shopService = shopService;
        this.itemAndShopService = itemAndShopService;
    }
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Integer addedItem = getAddedItemId();
        Integer addedShop = getAddedShopId();

        ItemAndShopDTO itemAndShopDTO = new ItemAndShopDTO();
        itemAndShopDTO.setIdItem(addedItem);
        itemAndShopDTO.setIdShop(addedShop);
        itemAndShopService.addItemAndShopChain(itemAndShopDTO);

        itemService.deleteItemsWhichHavingShopDependency(); //too late((
    }

    private Integer getAddedItemId() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("Item Name");
        itemDTO.setDescription("Item Description");
        itemDTO.setPrice(BigDecimal.valueOf(10000));
        ItemDTO addedItemDTO = itemService.addItem(itemDTO);
        return addedItemDTO.getId();
    }

    private Integer getAddedShopId() {
        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName("Shop Name");
        shopDTO.setLocation("Shop Location");
        ShopDTO addedShopDTO = shopService.addShop(shopDTO);
        return addedShopDTO.getId();
    }


}
