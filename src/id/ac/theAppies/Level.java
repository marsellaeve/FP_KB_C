package id.ac.theAppies;

import java.awt.Point;
import java.util.ArrayList;

public class Level {

	private ArrayList<Shape> places;
    private ArrayList<Point> locations;
    private ArrayList<Edge> edges;
    private ArrayList<Integer> countShapes;
    
    
    
    public Level(ArrayList<Shape> places, ArrayList<Point> locations,
			ArrayList<Edge> edges, ArrayList<Integer> countShapes) {
		super();
		this.places = places;
		this.locations = locations;
		this.edges = edges;
		this.countShapes = countShapes;
	}
	public ArrayList<Shape> getPlaces() {
		return places;
	}
	public void setPlaces(ArrayList<Shape> places) {
		this.places = places;
	}
	public ArrayList<Point> getLocations() {
		return locations;
	}
	public void setLocations(ArrayList<Point> locations) {
		this.locations = locations;
	}
	public ArrayList<Edge> getEdges() {
		return edges;
	}
	public void setEdges(ArrayList<Edge> edges) {
		this.edges = edges;
	}
	public ArrayList<Integer> getCountShapes() {
		return countShapes;
	}
	public void setCountShapes(ArrayList<Integer> countShapes) {
		this.countShapes = countShapes;
	}
}
