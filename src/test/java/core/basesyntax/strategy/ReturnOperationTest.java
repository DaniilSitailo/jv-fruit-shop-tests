package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void handleReturn_ExistingFruit_Ok() {
        Storage.setBalance("apple", 5);
        ReturnOperation operation = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 3);

        operation.handle(transaction);

        assertEquals(8, Storage.inventory.get("apple"));
    }

    @Test
    void handleReturn_NonExistingFruit_Ok() {
        ReturnOperation operation = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 7);

        operation.handle(transaction);

        assertEquals(7, Storage.inventory.get("banana"));
    }
}
