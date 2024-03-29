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
        firstSlide.photoIndexes.add(photo.index);
        slideshow.slides.add(firstSlide);

        
        
        long score = 0;

        while (!photos.isEmpty()) {
        	int maxInterestFactor = -1;
        	int maxIdx = -1;
            for (int i = 0; i < photos.size(); i++) {
                int interestFactor = getInterestFactor(photo, photos.get(i));
                if (maxInterestFactor < interestFactor) {
                    maxInterestFactor = interestFactor;
                    maxIdx = i;
                }
            }
            Photo nextPhoto = photos.remove(maxIdx);
            score += maxInterestFactor;
            
            Slide slide = new Slide();
            slide.photoIndexes.add(nextPhoto.index);
            slideshow.slides.add(slide);
            
            photo = nextPhoto;
            if (photo.orientation.equals("V"))
            	System.out.println(score);
        }
        System.out.println(score);
        return slideshow;
    }

	public Slideshow prepareSlideshowC() {
		Slideshow slideshow = new Slideshow();
		List<Photo> photosH = photos.stream().filter(x -> x.orientation.equals("H")).collect(toList());
		List<Photo> photosDoubleV = getPhotosDoubleV();
		photosH.addAll(photosDoubleV);
		photos = photosH;
		
		Photo photo = photos.remove(0);
        Slide firstSlide = new Slide();
        firstSlide.photoIndexes.add(photo.index);
        slideshow.slides.add(firstSlide);

        long score = 0;

        while (!photos.isEmpty()) {
        	int maxInterestFactor = -1;
        	int maxIdx = -1;
            for (int i = 0; i < photos.size(); i++) {
                int interestFactor = getInterestFactor(photo, photos.get(i));
                if (maxInterestFactor < interestFactor) {
                    maxInterestFactor = interestFactor;
                    maxIdx = i;
                }
            }
            Photo nextPhoto = photos.remove(maxIdx);
            score += maxInterestFactor;
            
            Slide slide = new Slide();
            slide.photoIndexes.add(nextPhoto.index);
            if (nextPhoto.orientation.equals("V")) {
                slide.photoIndexes.add(nextPhoto.indexOfSecondV);
            }
            slideshow.slides.add(slide);
            
            photo = nextPhoto;
            System.out.println(score);
        }
        System.out.println(score);
        return slideshow;
	}

	private List<Photo> getPhotosDoubleV() {
		List<Photo> photosV = photos.stream().filter(x -> x.orientation.equals("V")).collect(toList());
		List<Photo> result = new ArrayList<>();
		while (photosV.size() > 1) {
			int maxSize = 0;
			int maxIdx = 0;
			Photo photo = photosV.remove(0);
			Set<String> tags = new HashSet<>();
			for (int i = 0; i < photosV.size(); i++) {
				tags = getUnionSize(photo, photosV.get(i));
				if (maxSize < tags.size()) {
					maxSize = tags.size();
					maxIdx = i;
				}
			}
			Photo secondV = photosV.remove(maxIdx);
			Photo newPhoto = new Photo();
			newPhoto.index = photo.index;
			newPhoto.indexOfSecondV = secondV.index;
			newPhoto.tags = new ArrayList<>(tags);
			newPhoto.orientation = "V";
			newPhoto.tagSize = tags.size();
			result.add(newPhoto);
		}
		return result;
	}

	private Set<String> getUnionSize(Photo p1, Photo p2) {
		Set<String> p1Tags = new HashSet<>(p1.tags);
		p1Tags.addAll(p2.tags);
		return p1Tags;
	}
}
