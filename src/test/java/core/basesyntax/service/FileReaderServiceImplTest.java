package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class FileReaderServiceImplTest {

    private final FileReaderServiceImpl reader = new FileReaderServiceImpl();

    @Test
    void readExistingFile_returnsLines() throws IOException {
        Path tempFile = Files.createTempFile("test_input", ".csv");
        Files.writeString(tempFile, "line1\nline2\nline3");

        try {
            List<String> lines = reader.read(tempFile.toString());
            assertEquals(List.of("line1", "line2", "line3"), lines);
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }

    @Test
    void readNonExistentFile_throwsException() {
        assertThrows(IOException.class, () ->
                reader.read("/non/existent/file.csv"));
    }

    @Test
    void readEmptyFile_returnsEmptyList() throws IOException {
        Path tempFile = Files.createTempFile("test_empty", ".csv");

        try {
            List<String> lines = reader.read(tempFile.toString());
            assertTrue(lines.isEmpty());
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
