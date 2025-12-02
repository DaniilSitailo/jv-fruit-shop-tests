package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.List;
import java.util.stream.Collectors;

public class DataConverterImplTest implements DataConverter {
    @Override
    public List<FruitTransaction> convertToTransaction(List<String> lines) {
        if (lines == null) {
            return List.of();
        }

        return lines.stream()
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .skip(1)
                .map(this::parseLine)
                .collect(Collectors.toList());
    }

    private FruitTransaction parseLine(String line) {
        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException(
                    "Invalid line format: expected 3 comma-separated values, got "
                            + parts.length + " in: " + "'" + line + "'");
        }

        String opCode = parts[0].trim();
        String fruit = parts[1].trim();
        String qtyStr = parts[2].trim();

        FruitTransaction.Operation operation = switch (opCode) {
            case "b" -> FruitTransaction.Operation.BALANCE;
            case "s" -> FruitTransaction.Operation.SUPPLY;
            case "p" -> FruitTransaction.Operation.PURCHASE;
            case "r" -> FruitTransaction.Operation.RETURN;
            default -> throw new IllegalArgumentException("Unknown operation code: '"
                    + opCode + "'");
        };

        int quantity;
        try {
            quantity = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid quantity value: '" + qtyStr + "'", e);
        }

        return new FruitTransaction(operation, fruit, quantity);
    }
}
