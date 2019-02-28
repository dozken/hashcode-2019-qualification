package kz.mersys;

import kz.mersys.service.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class Main {

    public static void main(String[] args) {
        final Service service = new Service();

        try {
            Path inputPath = Paths.get("src", "main", "resources", "in");
            Path outputPath = Paths.get("src", "main", "resources", "out");

            Files.newDirectoryStream(inputPath,
                    path -> path.toString().endsWith(".in"))
                    .forEach(path -> {
                        service.readInputFile(path);
                        List<Object> list = service.process();
                        service.writeOutputFile(outputPath.resolve(path.getFileName()), list);
                        System.out.println();
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }

//        service.readInputFile("a_exam[");
    }
}
