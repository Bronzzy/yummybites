package com.dhbinh.yummybites.menuitem.service;

import com.dhbinh.yummybites.base.exception.ErrorMessage;
import com.dhbinh.yummybites.base.exception.InputValidationException;
import com.dhbinh.yummybites.base.exception.ResourceNotFoundException;
import com.dhbinh.yummybites.menuitem.entity.DishType;
import com.dhbinh.yummybites.menuitem.entity.MenuItem;
import com.dhbinh.yummybites.menuitem.repository.MenuItemRepository;
import com.dhbinh.yummybites.menuitem.service.dto.MenuItemDTO;
import com.dhbinh.yummybites.menuitem.service.mapper.MenuItemMapper;
import com.dhbinh.yummybites.restaurant.service.RestaurantService;
import com.dhbinh.yummybites.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantService restaurantService;

    private final MenuItemMapper menuItemMapper;

    private final Utils utils;

    public MenuItemDTO create(MenuItemDTO menuItemDTO) {
        verify(menuItemDTO);

        MenuItem menuItem = MenuItem.builder()
                .name(menuItemDTO.getName())
                .description(menuItemDTO.getDescription())
                .price(menuItemDTO.getPrice())
                .dishType(DishType.valueOf(menuItemDTO.getDishType()))
                .restaurant(restaurantService.findByNameIgnoreCase(menuItemDTO.getRestaurantName().trim()))
                .build();

        return menuItemMapper.toDTO(menuItemRepository.save(menuItem));
    }

    public MenuItemDTO findById(Long id) {
        return menuItemMapper.toDTO(menuItemRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.KEY_MENU_ITEM_NOT_FOUND,
                        ErrorMessage.MENU_ITEM_NOT_FOUND)));

    }

    public List<MenuItemDTO> findAll() {
        return menuItemMapper.toDTOList(menuItemRepository.findAll());
    }

    public MenuItemDTO update(Long id, MenuItemDTO menuItemDTO) {
        verify(menuItemDTO);

        MenuItem menuItem = menuItemRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.KEY_MENU_ITEM_NOT_FOUND,
                        ErrorMessage.MENU_ITEM_NOT_FOUND));

        menuItemMapper.mapFromDto(menuItemDTO, menuItem);
        return menuItemMapper.toDTO(menuItemRepository.save(menuItem));
    }

    public void verify(MenuItemDTO menuItemDTO) {
        if (menuItemDTO.getName() != null) {
            menuItemDTO.setName(utils.capitalizeFirstWordAndAfterWhitespace(menuItemDTO.getName().trim()));
        }

        if (menuItemDTO.getDescription() != null){
            menuItemDTO.setDescription(utils.capitalizeFirstWord(menuItemDTO.getDescription().trim()));
        }

        if(isNameExisted(menuItemDTO.getName())){
            throw new ResourceNotFoundException(
                    ErrorMessage.KEY_MENU_ITEM_ALREADY_EXIST,
                    ErrorMessage.MENU_ITEM_ALREADY_EXIST);
        }

        if(menuItemDTO.getPrice() < 0){
            throw new InputValidationException(
                    ErrorMessage.KEY_PRICE_LESS_THAN_ZERO,
                    ErrorMessage.PRICE_LESS_THAN_ZERO);
        }
    }

    private boolean isNameExisted(String name){
        boolean isExist = false;
        if(name != null){
            isExist = menuItemRepository.findByNameIgnoreCase(name.trim()).isPresent();
        }
        return isExist;
    }
    public MenuItemDTO findByName(String name) {
        return menuItemMapper.toDTO(menuItemRepository.findByNameIgnoreCase(name.trim())
                .orElseThrow(() -> new ResourceNotFoundException(
                        ErrorMessage.KEY_MENU_ITEM_NOT_FOUND,
                        ErrorMessage.MENU_ITEM_NOT_FOUND)));
    }
}
