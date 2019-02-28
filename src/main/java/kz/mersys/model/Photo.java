package kz.mersys.model;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class Photo {
    private String orientation;
    private int tagSize;
    private List<String> tags;

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public int getTagSize() {
        return tagSize;
    }

    public void setTagSize(int tagSize) {
        this.tagSize = tagSize;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return String.format("%s %d %s", orientation, tagSize, tags.stream().collect(joining(" ")));
    }
}
