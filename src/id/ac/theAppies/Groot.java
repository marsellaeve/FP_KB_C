package id.ac.theAppies;

public class Groot extends Shape {
	private int dx;
    private int dy;
    
    public Groot(int x, int y) {
        super(x, y);
        initGroot();
    }
    
    private void initGroot() {
        loadImage("image/groot.png"); 
        getImageDimensions();
    }
    
    public void move() {
    	x += dx;
        y += dy;
    }
    
    @Override
    public Shape clone() {
    	return new Groot(x,y);
    }
}
