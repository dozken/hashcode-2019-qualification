package kz.mersys.service;


import kz.mersys.model.Photo;
import kz.mersys.model.Slide;
import kz.mersys.model.Slideshow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

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
        slideshow.slideCount = 1;
        slideshow.slides = new ArrayList<>();
        Slide slide = new Slide();
        slide.photoIndexes = Arrays.asList(1, 2, 3);
        slideshow.slides.add(slide);


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
            writer.write(String.format("%d", slideshow.slideCount));
            writer.newLine();
            for(Slide slide: slideshow.slides){
                String collectedIndexes = slide.photoIndexes.stream().map(x -> x.toString()).collect(joining(" "));
                writer.write(String.format("%s", collectedIndexes));
                writer.newLine();
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
