package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class MainIntegrationTest {

    @Disabled
    @Test
    void testMainMethod() throws IOException {
        String outputPath = "finalReport.csv";
        Path outputFilePath = Paths.get(outputPath);

        if (Files.exists(outputFilePath)) {
            Files.delete(outputFilePath);
        }

        Main.main(new String[]{});

        assertTrue(Files.exists(outputFilePath));

        String content = Files.readString(outputFilePath);
        assertTrue(content.contains("fruit,quantity"));
    }
}
