package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    private ReportGeneratorImpl generator;

    @BeforeEach
    void setUp() {
        Storage.inventory.clear();
        generator = new ReportGeneratorImpl();
    }

    @Test
    void getReport_Ok() {
        Storage.setBalance("apple", 10);
        Storage.supply("banana", 5);
        String report = generator.getReport();
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,5"));
    }

    @Test
    void getReportEmpty_Ok() {
        String report = generator.getReport();
        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.endsWith("\n"));
    }
}
