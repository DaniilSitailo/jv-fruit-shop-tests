package core.basesyntax.service;

import core.basesyntax.db.Storage;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport() {
        StringBuilder report = new StringBuilder();
        report.append("fruit,quantity\n");

        for (Map.Entry<String, Integer> entry : Storage.inventory.entrySet()) {
            if (entry.getValue() > 0) {
                report.append(entry.getKey())
                        .append(",")
                        .append(entry.getValue())
                        .append("\n");
            }
        }
        return report.toString();
    }
}
