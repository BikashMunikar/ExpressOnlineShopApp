package com.bikash.inventoryservice.service;

import com.bikash.inventoryservice.dto.InventoryResponse;
import com.bikash.inventoryservice.model.Inventory;
import com.bikash.inventoryservice.dto.InventoryRequest;
import com.bikash.inventoryservice.repository.InventoryServiceRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryServiceRepository repo;

    @SneakyThrows
    public InventoryResponse isProductPresent(String orderName){
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        return mapInventoryToInventoryResponse(repo.findByOrderName(orderName));
    }

//    public void addInventory(InventoryRequest inventoryRequest){
//        repo.save(mapInventoryToDto(inventoryRequest));
//        log.info("Inventory {} has been logged. Please visit the store at {}", inventoryRequest.getOrderName(), inventoryRequest.getLocation());
//    }

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
