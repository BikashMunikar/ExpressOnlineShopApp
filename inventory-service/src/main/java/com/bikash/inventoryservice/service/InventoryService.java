package com.bikash.inventoryservice.service;

import com.bikash.inventoryservice.dto.InventoryResponse;
import com.bikash.inventoryservice.model.Inventory;
import com.bikash.inventoryservice.repository.InventoryServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryServiceRepository repo;


    public InventoryResponse isProductPresent(String orderName){
        return mapInventoryToInventoryResponse(repo.findByOrderName(orderName));
    }

    private Inventory mapInventoryToDto(InventoryResponse inventoryResponse) {
        return Inventory.builder()
                .orderName(inventoryResponse.getOrderName())
                .location(inventoryResponse.getLocation())
                .build();
    }

    public InventoryResponse mapInventoryToInventoryResponse(Inventory inventory){
        return InventoryResponse.builder()
                .orderName(inventory.getOrderName())
                .location(inventory.getLocation())
                .build();
    }

}
