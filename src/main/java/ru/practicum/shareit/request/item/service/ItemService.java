package ru.practicum.shareit.request.item.service;

import ru.practicum.shareit.request.item.dto.ItemDto;
import ru.practicum.shareit.request.item.model.Item;

import java.util.List;

public interface ItemService {
    List<ItemDto> findAll(long userId);

    ItemDto findItem(long itemId);
//
    List<ItemDto> searchItem(String text);

    ItemDto create(long userId, ItemDto itemDto);

    ItemDto update(long userId, long itemId, Item item);
}
