package com.bikash.inventoryservice.repository;

import com.bikash.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryServiceRepository extends JpaRepository<Inventory, Integer> {
    public List<Inventory> findAllByOrderName(String orderName);
}
