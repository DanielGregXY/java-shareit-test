package ru.practicum.shareit.request.item.storage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.ObjectNotFoundException;
import ru.practicum.shareit.request.item.dto.ItemDto;
import ru.practicum.shareit.request.item.model.Item;
import ru.practicum.shareit.request.item.mapper.ItemMapper;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRepositoryImpl implements ItemStorage {
    private final Map<Long, List<Item>> items = new HashMap<>();
    private long idCounter = 1;

    @Override
    public List<ItemDto> findAll(long userId) {
        List<Item> userItems = items.getOrDefault(userId, Collections.emptyList());
        return userItems.stream()
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ItemDto> findItem(long itemId) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((user, items1) -> allItems.addAll(items1));
        return allItems.stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .map(ItemMapper::toItemDto);
    }

    @Override
    public Optional<ItemDto> findItemForUpdate(long userId, long itemId) {
        return items.getOrDefault(userId, Collections.emptyList()).stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst()
                .map(ItemMapper::toItemDto);
    }

    @Override
    public List<ItemDto> searchItem(String text) {
        List<Item> allItems = new ArrayList<>();
        items.forEach((userId, items1) -> allItems.addAll(items.get(userId)));
        return allItems.stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase()) ||
                        item.getDescription().toLowerCase().contains(text.toLowerCase()))
                .filter(Item::getAvailable)
                .map(ItemMapper::toItemDto)
                .collect(Collectors.toList());
    }
//
    @Override
    public ItemDto create(long userId, ItemDto itemDto) {
        itemDto.setId(idCounter++);
        Item item = ItemMapper.toItem(itemDto, userId);
        items.compute(userId, (id, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        return ItemMapper.toItemDto(item);
    }

    @Override
    public ItemDto update(long userId, long itemId, Item item) {
        Item repoItem = items.get(userId).stream()
                .filter(item1 -> item1.getId() == itemId)
                .findFirst().orElseThrow(() -> {
                    log.warn("???????? ?? itemId{} ???? ??????????????", itemId);
                    throw new ObjectNotFoundException("???????? ???? ??????????????");
                })
                ;
            if (item.getName() != null) {
                repoItem.setName(item.getName());
        }
            if (item.getDescription() != null) {
                repoItem.setDescription(item.getDescription());
        }
            if (item.getAvailable() != null) {
                repoItem.setAvailable(item.getAvailable());
        }
        items.get(userId).removeIf(item1 -> item1.getId() == itemId);
        items.get(userId).add(repoItem);
        return ItemMapper.toItemDto(repoItem);
    }
}
