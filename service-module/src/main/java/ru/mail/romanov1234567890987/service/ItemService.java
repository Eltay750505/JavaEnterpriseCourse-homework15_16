package ru.mail.romanov1234567890987.service;

import ru.mail.romanov1234567890987.service.model.ItemDTO;

public interface ItemService {
    void deleteItemsWhichHavingShopDependency();

    ItemDTO addItem(ItemDTO itemDTO);
}
