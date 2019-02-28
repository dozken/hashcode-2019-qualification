package kz.mersys.service;


import kz.mersys.model.Photo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private int photoSize;
    private List<Photo> photos = new ArrayList<>();

    public void readInputFile(Path path) {
        System.out.printf("readInputFile: %s \n", path.getFileName().toString());

        //TODO read input file and assign constraints
        try (BufferedReader br = Files.newBufferedReader(path)) {
            photoSize = Integer.parseInt(br.readLine());

            for (int i = 0; i < photoSize; i++) {
                String[] line = br.readLine().split(" ");
                Photo photo = new Photo();
                photo.setOrientation(line[0]);
                photo.setTagSize(Integer.parseInt(line[1]));
                String[] tags = new String[photo.getTagSize()];

                for (int j = 0; j < photo.getTagSize(); j++) {
                    tags[j] = line[2 + j];
                }

                photo.setTags(tags);

                photos.add(photo);
            }

            photos.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<Photo> process() {
        System.out.printf("process: \n");

        List<Photo> list = photos;

        //TODO some really cool algorithm

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
            //TODO write data to output file
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
