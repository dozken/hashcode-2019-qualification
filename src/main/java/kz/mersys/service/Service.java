package kz.mersys.service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private String a;

    public void readInputFile(Path path) {
        System.out.printf("readInputFile: %s \n", path.getFileName().toString());
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String[] given = br.readLine().split(" ");
            a = given[0];

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Object> process() {
        List<Object> list = new ArrayList<>();
        System.out.printf("process: %s\n", a);
        return list;
    }

    public void writeOutputFile(Path path, List<Object> items) {
        System.out.printf("writeOutputFile: %s \n", path.getFileName().toString());

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            if (items != null) {
                writer.write(String.format("%d", items.size()));
                writer.newLine();
                for (Object item : items) {
                    writer.write(item.toString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
