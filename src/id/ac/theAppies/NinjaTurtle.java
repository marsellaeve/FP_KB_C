package id.ac.theAppies;

public class NinjaTurtle extends Shape{
	private int dx;
    private int dy;
    
    public NinjaTurtle(int x, int y) {
        super(x, y);
        initNinjaTurtle();
    }
    
    private void initNinjaTurtle() {
        loadImage("image/turtle.png"); 
        getImageDimensions();
    }
    public void move() {
    	x += dx;
        y += dy;
    }
    
    @Override
    public Shape clone() {
    	return new NinjaTurtle(x,y);
    }
}
