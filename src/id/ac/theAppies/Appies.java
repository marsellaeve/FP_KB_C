package id.ac.theAppies;

public class Appies extends Shape {
	private int dx;
    private int dy;
    
    public Appies(int x, int y) {
        super(x, y);
        initAppies();
    }
    
    private void initAppies() {
        loadImage("image/kuning.png"); 
        getImageDimensions();
    }
    
    public void move() {
    	x += dx;
        y += dy;
    }
    
    @Override
    public Shape clone() {
    	return new Appies(x,y);
    }
}
