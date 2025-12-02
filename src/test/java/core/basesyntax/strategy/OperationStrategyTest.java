package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void process_ValidOperation_Ok() {
        OperationStrategyImpl strategy = new OperationStrategyImpl(
                java.util.Map.of(
                        FruitTransaction.Operation.BALANCE, new BalanceOperation()
                ));

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        strategy.process(transaction);

        assertEquals(10, Storage.inventory.get("apple"));
    }

    @Test
    void process_InvalidOperation_ExceptionThrown() {
        OperationStrategyImpl strategy = new OperationStrategyImpl(
                java.util.Map.of());

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);

        assertThrows(IllegalArgumentException.class, () -> {
            strategy.process(transaction);
        });
    }
}
