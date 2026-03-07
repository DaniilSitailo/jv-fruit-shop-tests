package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Test;

class MainIntegrationTest {
    @Test
    void testMainMethod() throws IOException {
        Path input = Paths.get("reportToRead.csv");
        Files.writeString(input, String.join("\n",
                "type,fruit,quantity",
                "b,banana,20",
                "b,apple,100",
                "s,banana,100",
                "p,banana,13",
                "r,apple,10",
                "p,apple,20",
                "p,banana,5",
                "s,banana,50"
        ));

        Path out = Paths.get("finalReport.csv");
        Files.deleteIfExists(out);

        System.out.println("Input file exists: " + Files.exists(input));
        System.out.println("Input file size: " + Files.size(input) + " bytes");

        try {
            System.out.println("Calling Main.main()...");
            Main.main(new String[]{"reportToRead.csv", "finalReport.csv"});
            System.out.println("Main.main() completed");
        } catch (Exception e) {
            System.err.println("Exception occurred while running Main.main(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

        System.out.println("Output file exists after main: " + Files.exists(out));

        if (!Files.exists(out)) {
            System.out.println("ERROR: Output file was not created!");
            try {
                System.out.println("Files in current directory:");
                Files.list(Paths.get(".")).forEach(path -> System.out.println("  " + path));
            } catch (IOException e) {
                System.out.println("Could not list directory contents: " + e.getMessage());
            }
        }

        assertTrue(Files.exists(out), "Output file 'finalReport.csv'"
                + " was not created by Main.main()");

        String content = Files.readString(out);
        assertTrue(content.contains("fruit,quantity"), "Header missing from output file");
        assertTrue(content.contains("banana,152"), "Banana total incorrect in output file");
        assertTrue(content.contains("apple,90"), "Apple total incorrect in output file");

        Files.deleteIfExists(input);
        Files.deleteIfExists(out);
    }
}
