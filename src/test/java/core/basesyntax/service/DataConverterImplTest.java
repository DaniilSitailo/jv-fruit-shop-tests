package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {

    private final DataConverterImpl converter = new DataConverterImpl();

    @Test
    void convertToTransaction_validInput_shouldReturnTransactions() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,banana,20",
                "s,apple,10",
                "p,banana,5",
                "r,apple,3"
        );
        List<FruitTransaction> transactions = converter.convertToTransaction(input);

        assertEquals(4, transactions.size());

        assertEquals(FruitTransaction.Operation.BALANCE, transactions.get(0).getOperation());
        assertEquals("banana", transactions.get(0).getFruit());
        assertEquals(20, transactions.get(0).getQuantity());

        assertEquals(FruitTransaction.Operation.SUPPLY, transactions.get(1).getOperation());
        assertEquals("apple", transactions.get(1).getFruit());
        assertEquals(10, transactions.get(1).getQuantity());

        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(2).getOperation());
        assertEquals("banana", transactions.get(2).getFruit());
        assertEquals(5, transactions.get(2).getQuantity());

        assertEquals(FruitTransaction.Operation.RETURN, transactions.get(3).getOperation());
        assertEquals("apple", transactions.get(3).getFruit());
        assertEquals(3, transactions.get(3).getQuantity());
    }

    @Test
    void convertToTransaction_skipsHeaderAndEmptyLines() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "",
                "b,apple,5",
                "   ",
                "s,banana,3"
        );
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(2, transactions.size());
    }

    @Test
    void convertToTransaction_invalidFormat_skipsLine() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "b,apple",
                "s,banana,10,extra",
                "p,orange,7"
        );
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertEquals(1, transactions.size());
        assertEquals(FruitTransaction.Operation.PURCHASE, transactions.get(0).getOperation());
        assertEquals("orange", transactions.get(0).getFruit());
        assertEquals(7, transactions.get(0).getQuantity());
    }

    @Test
    void convertToTransaction_unknownOperationCode_throwsException() {
        List<String> input = List.of(
                "type,fruit,quantity",
                "x,apple,5"
        );
        assertThrows(IllegalArgumentException.class, () ->
                converter.convertToTransaction(input));
    }

    @Test
    void convertToTransaction_emptyList_returnsEmpty() {
        List<String> input = List.of();
        List<FruitTransaction> transactions = converter.convertToTransaction(input);
        assertTrue(transactions.isEmpty());
    }
}
