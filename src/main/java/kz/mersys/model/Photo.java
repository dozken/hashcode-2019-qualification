package kz.mersys.model;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Photo {
    public int index;
    public String orientation;
    public int tagSize;
    public List<String> tags;


    @Override
    public String toString() {
        return String.format("%s %d %s", orientation, tagSize, tags.stream().collect(joining(" ")));
    }
}
