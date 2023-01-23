package ru.practicum.shareit.request.item.storage;

import ru.practicum.shareit.request.item.dto.ItemDto;
import ru.practicum.shareit.request.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemStorage {
    List<ItemDto> findAll(long userId);

    Optional<ItemDto> findItem(long itemId);

    Optional<ItemDto> findItemForUpdate(long userId, long itemId);

    List<ItemDto> searchItem(String text);

    ItemDto create(long userId, ItemDto itemDto);
    //
    ItemDto update(long userId, long itemId, Item item);
}
