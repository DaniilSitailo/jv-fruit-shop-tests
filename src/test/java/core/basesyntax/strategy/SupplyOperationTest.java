package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void handleSupply_ExistingFruit_Ok() {
        Storage.setBalance("apple", 5);
        SupplyOperation operation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);

        operation.handle(transaction);

        assertEquals(15, Storage.inventory.get("apple"));
    }

    @Test
    void handleSupply_NonExistingFruit_Ok() {
        SupplyOperation operation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 7);

        operation.handle(transaction);

        assertEquals(7, Storage.inventory.get("banana"));
    }
}
