package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void handleBalance_Ok() {
        BalanceOperation operation = new BalanceOperation();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        operation.handle(transaction);

        assertEquals(10, Storage.inventory.get("apple"));
    }
}
