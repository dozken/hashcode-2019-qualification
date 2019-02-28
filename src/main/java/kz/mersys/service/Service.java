package kz.mersys.service;


import kz.mersys.model.Photo;
import kz.mersys.model.Slideshow;

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
                photo.orientation = line[0];
                photo.tagSize = Integer.parseInt(line[1]);
                List<String> tags = new ArrayList<>();

                for (int j = 0; j < photo.tagSize; j++) {
                    tags.add(line[2 + j]);
                }

                photo.tags = tags;

                photos.add(photo);
            }

            photos.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Slideshow process() {
        System.out.printf("process: \n");


        Slideshow slideshow = new Slideshow();
        slideshow.slideCount = 3;
        slideshow.slides = new ArrayList<>();


        //TODO some really cool algorithm

        return slideshow;
    }

    public void writeOutputFile(Path path, Slideshow slideshow) {
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
    
    public long getInterestFactor(Photo p1, Photo p2) {
    	// TODO
    	return 0;
    }


    // horizontal get max interest point

}
