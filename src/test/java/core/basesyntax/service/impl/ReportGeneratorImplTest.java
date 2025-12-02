package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {

    @BeforeEach
    @AfterEach
    void setUp() {
        Storage.inventory.clear();
    }

    @Test
    void getReport_Ok() {
        Storage.setBalance("apple", 10);
        Storage.supply("banana", 5);

        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        String report = generator.getReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.contains("apple,10"));
        assertTrue(report.contains("banana,5"));
    }

    @Test
    void getReportEmpty_Ok() {
        ReportGeneratorImpl generator = new ReportGeneratorImpl();
        String report = generator.getReport();

        assertTrue(report.contains("fruit,quantity"));
        assertTrue(report.endsWith(System.lineSeparator()));
    }
}
