package id.ac.theAppies;

public class Groot extends Shape {
	private int dx;
    private int dy;
    private final int BOARD_WIDTH = 800;
    private final int BOARD_HEIGHT = 600;
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
