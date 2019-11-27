package id.ac.theAppies;

public class NinjaTurtle extends Shape{
	private int dx;
    private int dy;
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
    
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
    public void addX(int x) {
    	this.x+=x;
    }
    public void addY(int y) {
    	this.y+=y;
    }

    public void setX(int x) {
    	this.x=x;
    }
    public void setY(int y) {
    	this.y=y;
    }
}
