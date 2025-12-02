package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void handlePurchase_EnoughFruits_Ok() {
        Storage.setBalance("apple", 10);
        PurchaseOperation operation = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 3);

        operation.handle(transaction);

        assertEquals(7, Storage.inventory.get("apple"));
    }

    @Test
    void handlePurchase_NotEnoughFruits_ExceptionThrown() {
        Storage.setBalance("apple", 2);
        PurchaseOperation operation = new PurchaseOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 5);

        assertThrows(IllegalArgumentException.class, () -> {
            operation.handle(transaction);
        });
    }
}
