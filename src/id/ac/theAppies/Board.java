package id.ac.theAppies;
import java.lang.Math;
import javafx.util.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private boolean gameover=false;
    private ArrayList<Point> shapePlaces;
    private ArrayList<Shape> places;
    private ArrayList<Point> locations;
    private ArrayList<Edge> edges;
    private final int DELAY = 10;
    private Timer timer;
    private ArrayList<Shape> availableShapes;
    private ArrayList<Integer> countShapes;
    private Shape draggedImage;
    private Point mousePrevLocation;
	
    public Board() {
        initBoard();
    }
    private void initBoard() {
    	this.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			Point currentMouseLocation=e.getPoint();
    			
    			for(int i=0;i<shapePlaces.size();i++) {
    				int x=shapePlaces.get(i).x-currentMouseLocation.x;
    				int y=shapePlaces.get(i).y-currentMouseLocation.y;
    				if(Math.sqrt(x*x+y*y)<20.0 && countShapes.get(i)>0) {
    					draggedImage=availableShapes.get(i).clone();
        				countShapes.set(i,countShapes.get(i)-1);
    				}
    			}
    			
    			for(int i=0;i<locations.size();i++) {
    				int x=locations.get(i).x-currentMouseLocation.x;
    				int y=locations.get(i).y-currentMouseLocation.y;
    				if(Math.sqrt(x*x+y*y)<20.0 && places.get(i)!=null) {
        				draggedImage=places.get(i);
        				places.set(i, null);
        			}
    			}
    			mousePrevLocation=currentMouseLocation;
    		}
    		
    		@Override
    		public void mouseReleased(MouseEvent e) {
    			Point currentMouseLocation=e.getPoint();
    			if(draggedImage==null) return;
    			boolean placed=false;
    			for(int i=0;i<locations.size();i++) {
	    			int x=currentMouseLocation.x-locations.get(i).x;
	    			int y=currentMouseLocation.y-locations.get(i).y;
	    			if(Math.sqrt(x*x+y*y)<20) {
	    				if(places.get(i)!=null) {
		    				for(int j=0;j<availableShapes.size();j++) {
			    				if(availableShapes.get(j).getClass()==places.get(i).getClass()) {
			    					countShapes.set(j, countShapes.get(j)+1);
			    				}
			    			}
	    				}
	    				draggedImage.setX(locations.get(i).x);
	    				draggedImage.setY(locations.get(i).y);
	    				places.set(i, draggedImage);
	    				placed=true;
	    			}	
    			}
    			if(!placed) {
	    			for(int i=0;i<availableShapes.size();i++) {
	    				if(availableShapes.get(i).getClass()==draggedImage.getClass()) {
	    					countShapes.set(i, countShapes.get(i)+1);
	    				}
	    			}
    			}
    			draggedImage=null;
    		}
    	});
    	this.addMouseMotionListener(new MouseAdapter() {
    		@Override
    		public void mouseDragged(MouseEvent e) {
    			if(draggedImage!=null) {
    				Point currentMouseLocation=e.getPoint();
    				draggedImage.addX(currentMouseLocation.x-mousePrevLocation.x);
    				draggedImage.addY(currentMouseLocation.y-mousePrevLocation.y);
    				mousePrevLocation=currentMouseLocation;	
    			}
    		}
    	});
    	
        shapePlaces = new ArrayList();
        shapePlaces.add(new Point(320,80));
        shapePlaces.add(new Point(400,80));
        shapePlaces.add(new Point(480,80));
        
    	availableShapes= new ArrayList();
    	availableShapes.add(new Groot(shapePlaces.get(0).x,shapePlaces.get(0).y));
    	availableShapes.add(new Smurf(shapePlaces.get(1).x,shapePlaces.get(1).y));
    	availableShapes.add(new NinjaTurtle(shapePlaces.get(2).x,shapePlaces.get(2).y));
        
    	countShapes=new ArrayList();
    	countShapes.add(7);
    	countShapes.add(1);
    	countShapes.add(4);
    	
    	places=new ArrayList();
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	
    	locations=new ArrayList();
    	locations.add(new Point(250,300));
    	locations.add(new Point(400,300));
    	locations.add(new Point(400,450));
    	locations.add(new Point(550,450));
    	locations.add(new Point(400,225));
    	locations.add(new Point(600,225));
    	locations.add(new Point(250,500));
    	locations.add(new Point(200,450));
    	locations.add(new Point(250,400));
    	locations.add(new Point(200,225));
    	locations.add(new Point(500,400));
    	locations.add(new Point(500,500));
    	
    	edges=new ArrayList();
    	edges.add(new Edge(0, 1));
    	edges.add(new Edge(0, 9));
    	edges.add(new Edge(0, 2));
    	edges.add(new Edge(0, 8));
    	edges.add(new Edge(1, 2));
    	edges.add(new Edge(1, 10));
    	edges.add(new Edge(1, 4));
    	edges.add(new Edge(2, 3));
    	edges.add(new Edge(2, 7));
    	edges.add(new Edge(3, 10));
    	edges.add(new Edge(4, 5));
    	edges.add(new Edge(6, 8));
    	edges.add(new Edge(7, 8));
    	edges.add(new Edge(10, 11));
    	
    	setBackground(Color.BLACK);
        setFocusable(true);
        timer = new Timer(DELAY, this);
        timer.start();
        draggedImage=null;
    
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    
    private void doDrawing(Graphics g) {
    	Stroke stroke = new BasicStroke(4f);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.WHITE);
        g2d.setStroke(stroke);
        
        for(int i=0;i<edges.size();i++) {
        	int key=edges.get(i).key;
        	int value=edges.get(i).value;
        	if(places.get(key)==null || places.get(value)==null) {
        		g.setColor(Color.WHITE);
        	}
        	else if(places.get(key).getClass()==places.get(value).getClass()) {
        		g.setColor(Color.RED);
        	}
        	else {
        		g.setColor(Color.GREEN);
        	}
    		g.drawLine(locations.get(key).x, locations.get(key).y,
    				locations.get(value).x,locations.get(value).y);
        }
        g.setColor(Color.WHITE);

        for(int i=0;i<places.size();i++) {
	        if(places.get(i)==null) {
	        	g2d.fillOval( locations.get(i).x-10,locations.get(i).y-10,20,20);
	        }
	        else {
	        	g2d.drawImage(places.get(i).getImage(),places.get(i).getX()-places.get(i).getWidth()/2,
	        			places.get(i).getY()-places.get(i).getHeight()/2,this);
	        }
        }
        
        for(int i=0;i<countShapes.size();i++) {
        	if(countShapes.get(i)>0)g2d.drawImage(availableShapes.get(i).getImage(), availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2,
        			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2, this);
        }
        if(draggedImage!=null) {
        	g2d.drawImage(draggedImage.getImage(),draggedImage.getX()-draggedImage.getWidth()/2,
        			draggedImage.getY()-draggedImage.getHeight()/2,this);
        }
    }
	@Override
	public void actionPerformed(ActionEvent arg0) {
        repaint();
	}
}
