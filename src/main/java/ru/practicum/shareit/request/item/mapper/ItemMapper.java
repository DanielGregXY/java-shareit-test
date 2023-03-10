package ru.practicum.shareit.request.item.mapper;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.shareit.request.item.dto.ItemDto;
import ru.practicum.shareit.request.item.model.Item;

@Getter
@Setter
public class ItemMapper {

    public static ItemDto toItemDto(Item item) {
        return new ItemDto(item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable()
        );
    }
//
    public static Item toItem(ItemDto itemDto, long userId) {
        return new Item(itemDto.getId(),
                itemDto.getName(),
                itemDto.getDescription(),
                itemDto.getAvailable(),
                userId);
    }
}
