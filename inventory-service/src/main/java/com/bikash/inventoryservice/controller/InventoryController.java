package com.bikash.inventoryservice.controller;

import com.bikash.inventoryservice.dto.InventoryResponse;
import com.bikash.inventoryservice.model.Inventory;
import com.bikash.inventoryservice.dto.InventoryRequest;
import com.bikash.inventoryservice.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/api/inventory/{orderName}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInventoryPresent(@PathVariable("orderName") String orderName){
        log.info("Order Name is {} ", inventoryService.isProductPresent(orderName).getOrderName());
        return !inventoryService.isProductPresent(orderName).getOrderName().isEmpty();
    }

//    @PostMapping("/api/inventory")
//    @ResponseStatus(HttpStatus.CREATED)
//    public String saveInventory(@RequestBody InventoryRequest inventoryRequest){
//        inventoryService.addInventory(inventoryRequest);
//        return "Inventory has been successfully added.";
//    }
}

