package kz.mersys.service;


import kz.mersys.model.Photo;
import kz.mersys.model.Slide;
import kz.mersys.model.Slideshow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Service {
    private int photoSize;
    private List<Photo> origPhotos = new ArrayList<>();
    private List<Photo> photos = new ArrayList<>();


    public void readInputFile(Path path) {
//        System.out.printf("readInputFile: %s \n", path.getFileName().toString());

        //TODO read input file and assign constraints
        try (BufferedReader br = Files.newBufferedReader(path)) {
            photoSize = Integer.parseInt(br.readLine());

            for (int i = 0; i < photoSize; i++) {
                String[] line = br.readLine().split(" ");
                Photo photo = new Photo();
                photo.index = i;
                photo.orientation = line[0];
                photo.tagSize = Integer.parseInt(line[1]);
                List<String> tags = new ArrayList<>();

                for (int j = 0; j < photo.tagSize; j++) {
                    tags.add(line[2 + j]);
                }

                photo.tags = tags;

                photos.add(photo);
                origPhotos.add(photo);
            }

//            photos.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Slideshow process() {
//        System.out.printf("process: \n");


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
//        System.out.printf("writeOutputFile: %s \n", path.getFileName().toString());

        try {
            Files.createDirectories(path.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(String.format("%d", slideshow.slides.size()));
            writer.newLine();
            for (Slide slide : slideshow.slides) {
                String collectedIndexes = slide.photoIndexes.stream().map(x -> x.toString()).collect(joining(" "));
                writer.write(String.format("%s", collectedIndexes));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInterestFactor(Photo p1, Photo p2) {
        int intersectionSize = 0;
        Set<String> p1Tags = new HashSet<>(p1.tags);
        for (String tag : p2.tags) {
            if (!p1Tags.add(tag)) {
                intersectionSize++;
            }
        }
        int minInterval = Math.min(p1.tagSize - intersectionSize, p2.tagSize - intersectionSize);
        return Math.min(minInterval, intersectionSize);
    }

    // horizontal get max interest point

    public Slideshow prepareSlideshow() {
        Slideshow slideshow = new Slideshow();

        List<Photo> vp = photos.stream().filter(x -> x.orientation.equals("H")).collect(toList());

        long maxInterestFactor = 0;
        int interestIndex = 0;
        long score = 0;

        Set<Integer> usedIndex = new HashSet<>();
        usedIndex.add(0);
        slideshow.slides = new ArrayList<>();
        for (int i = 0; i < vp.size(); i++) {
            Slide slide = new Slide();
            for (int j = 0; j < vp.size() && !usedIndex.contains(j) && j != i; j++) {
                long interestFactor = getInterestFactor(vp.get(i), vp.get(j));
                if (maxInterestFactor < interestFactor) {
                    maxInterestFactor = Math.max(maxInterestFactor, interestFactor);
                    interestIndex = j;
                    usedIndex.add(interestIndex);
                }
            }
            score += maxInterestFactor;
            slide.photoIndexes.add(interestIndex);
        }

        return slideshow;
    }

    public Slideshow prepareSlideshowB() {
        Slideshow slideshow = new Slideshow();
        photos = photos.stream().filter(x -> x.orientation.equals("H")).collect(toList());

        Photo photo = photos.remove(0);
        Slide firstSlide = new Slide();
        firstSlide.photoIndexes.add(0);
        slideshow.slides.add(firstSlide);

        int maxInterestFactor = 0;
        int interestIndex = 0;
        long score = 0;

        while (!photos.isEmpty()||photos.size()!=2) {
            for (int i = 0; i < photos.size(); i++) {
                int interestFactor = getInterestFactor(photo, photos.get(i));
                if (maxInterestFactor < interestFactor) {
                    maxInterestFactor = Math.max(maxInterestFactor, interestFactor);
                    interestIndex = photos.get(i).index;
                }
            }

            score += maxInterestFactor;

            int finalInterestIndex = interestIndex;

            Optional<Photo> first = photos.stream()
                    .filter(photo1 -> photo1.index == finalInterestIndex)
                    .findFirst();
            if (first.isPresent()) {
                photo = first.get();
                photos.removeIf(p-> p.index == finalInterestIndex);

                Slide slide = new Slide();
                slide.photoIndexes.add(interestIndex);
                slideshow.slides.add(slide);
            }

        }

        return slideshow;
    }
}
