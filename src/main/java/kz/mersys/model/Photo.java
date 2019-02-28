package kz.mersys.model;

import java.util.Arrays;

public class Photo {
    private String orientation;
    private int tagSize;
    private String[] tags;

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

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }


    @Override
    public String toString() {
        return String.format("%s %d %s", orientation, tagSize, Arrays.toString(tags));
    }
}
