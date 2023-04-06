package com.bikash.inventoryservice.controller;

import com.bikash.inventoryservice.model.InventoryRequest;
import com.bikash.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @RequestMapping("api/inventory/{order-name:[a-zA-Z &+-]*}")
    public boolean isInventoryPresent(@PathVariable("order-name") String orderName){
        return inventoryService.isProductPresent(orderName);
    }

    @PostMapping("/api/inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public String saveInventory(@RequestBody InventoryRequest inventoryRequest){
        inventoryService.addInventory(inventoryRequest);
        return "Inventory has been successfully added.";
    }
}

