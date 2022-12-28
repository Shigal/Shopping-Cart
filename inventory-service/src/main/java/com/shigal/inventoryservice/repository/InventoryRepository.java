package com.shigal.inventoryservice.repository;/*
 *
 * @author Lawshiga
 *
 */

import com.shigal.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> finBySkuCode(String skuCode);
}
