package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void process_Ok() {
        TestOperationStrategy testStrategy = new TestOperationStrategy();
        ShopServiceImpl shopService = new ShopServiceImpl(testStrategy);

        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 10);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 5);

        List<FruitTransaction> transactions = Arrays.asList(transaction1, transaction2);

        shopService.process(transactions);

        assertEquals(2, testStrategy.getProcessCallCount());
    }

    private static class TestOperationStrategy implements OperationStrategy {
        private int processCallCount = 0;

        @Override
        public void process(FruitTransaction transaction) {
            processCallCount++;
        }

        public int getProcessCallCount() {
            return processCallCount;
        }
    }
}
