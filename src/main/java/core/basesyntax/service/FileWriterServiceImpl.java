package core.basesyntax.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileWriterServiceImpl implements FileWriterService {

    @Override
    public void write(String report, String filePath) throws IOException {
        Files.writeString(Paths.get(filePath), report);
    }
}
