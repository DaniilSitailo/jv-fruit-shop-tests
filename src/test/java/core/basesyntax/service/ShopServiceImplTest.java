package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(strategy);
    }

    @AfterEach
    void tearDown() {
        Storage.inventory.clear();
    }

    @Test
    void process_withVariousOperations_shouldCalculateCorrectly() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 50),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 30),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 15),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20)
        );

        shopService.process(transactions);

        assertEquals(85, Storage.inventory.get("apple"));
        assertEquals(30, Storage.inventory.get("banana"));
    }

    @Test
    void process_withOnlyBalance_shouldSetQuantity() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "orange", 80)
        );

        shopService.process(transactions);

        assertEquals(80, Storage.inventory.get("orange"));
    }

    @Test
    void process_emptyList_shouldDoNothing() {
        List<FruitTransaction> transactions = List.of();

        shopService.process(transactions);

        assertEquals(0, Storage.inventory.size());
    }
}
