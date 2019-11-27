package id.ac.theAppies;
import java.lang.Math;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private boolean gameover=false;
    private final int ICRAFT_Xgroot = 40;
    private final int ICRAFT_Ygroot = 60;
    private final int ICRAFT_Xsmurf = 100;
    private final int ICRAFT_Ysmurf = 60;
    private final int ICRAFT_Xturtle = 160;
    private final int ICRAFT_Yturtle = 60;
    private final int DELAY = 10;
    private Timer timer;
    private Groot groot;
    private NinjaTurtle turtle;
    private Smurf smurf;
    private Point mousePrevLocation;
    private boolean pressG;
    private boolean pressS;
    private boolean pressT;
	
    public Board() {
        initBoard();
    }
    private void initBoard() {
    	this.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			Point currentMouseLocation=e.getPoint();
    			int xGroot=currentMouseLocation.x-groot.getX();
    			int yGroot=currentMouseLocation.y-groot.getY();
    			int xSmurf=currentMouseLocation.x-smurf.getX();
    			int ySmurf=currentMouseLocation.y-smurf.getY();
    			int xTurtle=currentMouseLocation.x-turtle.getX();
    			int yTurtle=currentMouseLocation.y-turtle.getY();
    			if(Math.sqrt(xGroot*xGroot+yGroot*yGroot)<20.0) {
    				pressG=true;
    			}
    			if(Math.sqrt(xSmurf*xSmurf+ySmurf*ySmurf)<20.0) {
    				pressS=true;
    			}
    			if(Math.sqrt(xTurtle*xTurtle+yTurtle*yTurtle)<20.0) {
    				pressT=true;
    			}
    			mousePrevLocation=currentMouseLocation;
    		}
    		
    		@Override
    		public void mouseReleased(MouseEvent e) {
    			pressS=false;
    			pressG=false;
    			pressT=false;
    		}
    	});
    	this.addMouseMotionListener(new MouseAdapter() {
    		@Override
    		public void mouseDragged(MouseEvent e) {
    			if(pressG) {
    				Point currentMouseLocation=e.getPoint();
    				groot.addX(currentMouseLocation.x-mousePrevLocation.x);
    				groot.addY(currentMouseLocation.y-mousePrevLocation.y);
    				mousePrevLocation=currentMouseLocation;
    			}
    			if(pressS) {
    				Point currentMouseLocation=e.getPoint();
    				smurf.addX(currentMouseLocation.x-mousePrevLocation.x);
    				smurf.addY(currentMouseLocation.y-mousePrevLocation.y);
    				mousePrevLocation=currentMouseLocation;
    			}
    			if(pressT) {
    				Point currentMouseLocation=e.getPoint();
    				turtle.addX(currentMouseLocation.x-mousePrevLocation.x);
    				turtle.addY(currentMouseLocation.y-mousePrevLocation.y);
    				mousePrevLocation=currentMouseLocation;
    			}
    		}
    	});
    	pressS=false;
    	pressG=false;
    	pressT=false;
        setBackground(Color.BLACK);
        setFocusable(true);

        groot = new Groot(ICRAFT_Xgroot, ICRAFT_Ygroot);
        smurf = new Smurf(ICRAFT_Xsmurf, ICRAFT_Ysmurf);
        turtle = new NinjaTurtle(ICRAFT_Xturtle, ICRAFT_Yturtle);
        timer = new Timer(DELAY, this);
        timer.start();
    
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        
        if(!gameover)g2d.drawImage(groot.getImage(), groot.getX()-groot.getWidth()/2,
        		groot.getY()-groot.getHeight()/2, this);
        if(!gameover)g2d.drawImage(smurf.getImage(), smurf.getX()-smurf.getWidth()/2,
        		smurf.getY()-smurf.getHeight()/2, this);
        if(!gameover)g2d.drawImage(turtle.getImage(), turtle.getX()-turtle.getWidth()/2,
        		turtle.getY()-turtle.getHeight()/2, this);
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		updateGroot();
		updateSmurf();
		updateTurtle();
        repaint();
	}
    private void updateGroot() {
        groot.move();
        if(groot.isVisible()==false) {
        	gameover=true;
        	repaint();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	gameover=false;
        	groot.setVisible(true);
        }
    }
    private void updateSmurf() {
    	smurf.move();
        if(smurf.isVisible()==false) {
        	gameover=true;
        	repaint();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	gameover=false;
        	smurf.setVisible(true);
        }
    }
    private void updateTurtle() {
    	turtle.move();
        if(turtle.isVisible()==false) {
        	gameover=true;
        	repaint();
        	try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	gameover=false;
        	turtle.setVisible(true);
        }
    }
}
