package com.bikash.inventoryservice.service;

import com.bikash.inventoryservice.model.Inventory;
import com.bikash.inventoryservice.model.InventoryRequest;
import com.bikash.inventoryservice.repository.InventoryServiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
public class InventoryService {

    @Autowired
    private InventoryServiceRepository repo;

    public boolean isProductPresent(String orderName){
        return repo.findAllByOrderName(orderName).size() > 0;
    }

    public void addInventory(InventoryRequest inventoryRequest){
        repo.save(mapInventoryToDto(inventoryRequest));
        log.info("Inventory {} has been logged. Please visit the store at {}", inventoryRequest.getOrderName(), inventoryRequest.getLocation());
    }

    private Inventory mapInventoryToDto(InventoryRequest inventoryRequest) {
        return Inventory.builder()
                .orderName(inventoryRequest.getOrderName())
                .location(inventoryRequest.getLocation())
                .price(inventoryRequest.getPrice())
                .build();
    }
}
