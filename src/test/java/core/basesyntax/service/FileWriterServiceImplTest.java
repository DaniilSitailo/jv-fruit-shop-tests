package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class FileWriterServiceImplTest {

    private final FileWriterServiceImpl writer = new FileWriterServiceImpl();

    @Test
    void write_writesContentToFile() throws IOException {
        Path tempFile = Files.createTempFile("test_output", ".csv");

        try {
            String content = "fruit,quantity\napple,10\nbanana,5";
            writer.write(content, tempFile.toString());

            String written = Files.readString(tempFile);
            assertEquals(content, written);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void write_overwritesExistingFile() throws IOException {
        Path tempFile = Files.createTempFile("test_existing", ".csv");

        try {
            Files.writeString(tempFile, "old content");
            writer.write("new content", tempFile.toString());

            assertEquals("new content", Files.readString(tempFile));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void write_writesEmptyString() throws IOException {
        Path tempFile = Files.createTempFile("test_empty", ".csv");

        try {
            writer.write("", tempFile.toString());
            assertEquals("", Files.readString(tempFile));
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
