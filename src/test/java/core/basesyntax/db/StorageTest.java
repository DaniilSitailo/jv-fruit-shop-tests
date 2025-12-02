package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.inventory.clear();
    }

    @Test
    void setBalance_Ok() {
        Storage.setBalance("apple", 10);
        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void supply_ExistingFruit_Ok() {
        Storage.setBalance("apple", 10);
        Storage.supply("apple", 5);
        assertEquals(15, Storage.inventory.get("apple"));
    }

    @Test
    void supply_NonExistingFruit_Ok() {
        Storage.supply("apple", 5);
        assertEquals(5, Storage.inventory.get("apple"));
    }

    @Test
    void purchase_EnoughFruits_Ok() {
        Storage.setBalance("apple", 10);
        Storage.purchase("apple", 3);
        assertEquals(7, Storage.inventory.get("apple"));
    }

    @Test
    void purchase_NotEnoughFruits_ExceptionThrown() {
        Storage.setBalance("apple", 2);
        assertThrows(IllegalArgumentException.class, () -> {
            Storage.purchase("apple", 5);
        });
    }

    @Test
    void returnFruits_ExistingFruit_Ok() {
        Storage.setBalance("apple", 5);
        Storage.returnFruits("apple", 3);
        assertEquals(8, Storage.inventory.get("apple"));
    }

    @Test
    void returnFruits_NonExistingFruit_Ok() {
        Storage.returnFruits("apple", 3);
        assertEquals(3, Storage.inventory.get("apple"));
    }

    @Test
    void directInventoryAccess_Ok() {
        Storage.setBalance("apple", 5);
        Storage.supply("banana", 3);

        assertEquals(5, Storage.inventory.get("apple"));
        assertEquals(3, Storage.inventory.get("banana"));

        assertEquals(2, Storage.inventory.size());
    }
}
