package com.bikash.inventoryservice.repository;

import com.bikash.inventoryservice.dto.InventoryResponse;
import com.bikash.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryServiceRepository extends JpaRepository<Inventory, Integer> {

    public Inventory findByOrderName(String orderName);
}
