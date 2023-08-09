package com.dhbinh.yummybites.menuitem.controller;

import com.dhbinh.yummybites.menuitem.service.MenuItemService;
import com.dhbinh.yummybites.menuitem.service.dto.MenuItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@Validated
@RequestMapping(value = "/menuitems")
@PreAuthorize("hasRole('OWNER')")
public class MenuItemResource {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<MenuItemDTO> createMenuItem(@Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO dto = menuItemService.create(menuItemDTO);
        return ResponseEntity.created(URI.create("/api/menuitems" + dto.getId())).body(dto);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> findAll(){
        return ResponseEntity.ok(menuItemService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MenuItemDTO> findByID(@PathVariable("id") Long id){
        return ResponseEntity.ok(menuItemService.findById(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<MenuItemDTO> update(@PathVariable("id") Long id,
                                              @RequestBody MenuItemDTO menuItemDTO){
        return ResponseEntity.ok(menuItemService.update(id, menuItemDTO));
    }
}
