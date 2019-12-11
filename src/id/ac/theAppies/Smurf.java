package id.ac.theAppies;

public class Smurf extends Shape {
	private int dx;
    private int dy;
    
    public Smurf(int x, int y) {
        super(x, y);
        initSmurf();
    }
    
    private void initSmurf() {
        loadImage("image/smurf.png"); 
        getImageDimensions();
    }
    public void move() {
		x += dx;
		y += dy;
    }
    
    @Override
    public Shape clone() {
    	return new Smurf(x,y);
    }
}
