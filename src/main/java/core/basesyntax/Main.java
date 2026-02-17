package core.basesyntax;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java Main <input_file> <output_file>");
            return;
        }

        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);

        Map<String, Integer> fruitReport = new LinkedHashMap<>();

        try {
            List<String> lines = Files.readAllLines(inputPath);

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i).trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length < 3) {
                    continue;
                }

                String type = parts[0].trim().toLowerCase(); // на випадок великих літер
                String fruit = parts[1].trim();
                int quantity = Integer.parseInt(parts[2].trim());

                fruitReport.putIfAbsent(fruit, 0);

                if ("p".equals(type)) {
                    fruitReport.put(fruit, fruitReport.get(fruit) - quantity);
                } else {
                    // b, s, r — всі додають
                    fruitReport.put(fruit, fruitReport.get(fruit) + quantity);
                }
            }

            StringBuilder outputContent = new StringBuilder();
            outputContent.append("fruit,quantity\n");

            for (Map.Entry<String, Integer> entry : fruitReport.entrySet()) {
                if (entry.getValue() > 0) {
                    outputContent.append(entry.getKey())
                            .append(",")
                            .append(entry.getValue())
                            .append("\n");
                }
            }

            Files.writeString(outputPath, outputContent.toString());
            System.out.println("Звіт успішно створено: " + outputPath);

        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing quantity: " + e.getMessage());
        }
    }
}
