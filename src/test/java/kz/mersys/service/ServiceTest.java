package kz.mersys.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class ServiceTest {
    private Service service;
    private Path inputPath;
    private Path outputPath;

    private List<Object> list;

    @BeforeEach
    void setUp() {
        service = new Service();
        inputPath = Paths.get("src", "test", "resources", "in");
        outputPath = Paths.get("src", "test", "resources", "out");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void readInputFile() {
        service.readInputFile(inputPath.resolve("test.in"));
    }

    @Test
    void process() {
        list = service.process();
    }

    @Test
    void writeOutputFile() {
        service.writeOutputFile(outputPath.resolve("test.out"), list);
    }
}