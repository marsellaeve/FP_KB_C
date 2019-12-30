package id.ac.theAppies;

public class Sonic extends Shape {
	private int dx;
    private int dy;
    
    public Sonic(int x, int y) {
        super(x, y);
        initSonic();
    }
    
    private void initSonic() {
        loadImage("image/sonic.png"); 
        getImageDimensions();
    }
    
    public void move() {
    	x += dx;
        y += dy;
    }
    
    @Override
    public Shape clone() {
    	return new Sonic(x,y);
    }
}
