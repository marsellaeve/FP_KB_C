package id.ac.theAppies;
//import java.awt.event.MouseEvent;

public class Smurf extends Shape {
//	    private int dx;
//	    private int dy;

	    public Smurf(int x, int y) {
	        super(x, y);
	        initSmurf();
	    }
	    
	    private void initSmurf() {
	        loadImage("image/smurf.png"); 
	        getImageDimensions();
	    }

//	    public void move() {
//	        x += dx;
//	        y += dy;
//	    }
}
