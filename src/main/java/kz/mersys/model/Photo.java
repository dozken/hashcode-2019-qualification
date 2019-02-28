package kz.mersys.model;

import static java.util.stream.Collectors.joining;

import java.util.List;

public class Photo {
	public int index;
	public int indexOfSecondV;
	public String orientation;
	public int tagSize;
	public List<String> tags;

	@Override
	public String toString() {
		return String.format("%s %d %s", orientation, tagSize, tags.stream().collect(joining(" ")));
	}
}
