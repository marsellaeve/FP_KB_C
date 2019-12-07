package id.ac.theAppies;
import java.lang.Math;
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

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener{
	private ArrayList<Level> levels;
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
    private int choosedLevel;
    private Point nextButtonLocations;
    private Point prevButtonLocations;
    
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

    			int x=nextButtonLocations.x-currentMouseLocation.x;
    			int y=nextButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				nextLevel();
    			}
    			x=prevButtonLocations.x-currentMouseLocation.x;
    			y=prevButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				prevLevel();
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
    	choosedLevel=1;
    	initLevel();
        shapePlaces = new ArrayList();
        shapePlaces.add(new Point(320,80));
        shapePlaces.add(new Point(400,80));
        shapePlaces.add(new Point(480,80));

        nextButtonLocations=new Point(580,80);
        prevButtonLocations=new Point(240,80);
        
    	availableShapes= new ArrayList();
    	availableShapes.add(new Groot(shapePlaces.get(0).x,shapePlaces.get(0).y));
    	availableShapes.add(new Smurf(shapePlaces.get(1).x,shapePlaces.get(1).y));
    	availableShapes.add(new NinjaTurtle(shapePlaces.get(2).x,shapePlaces.get(2).y));

    	loadLevel();
    	
    	setBackground(Color.DARK_GRAY);
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
    
    void nextLevel() {
    	choosedLevel++;
    	choosedLevel%=levels.size();
    	loadLevel();
    }
    
    void prevLevel() {
    	choosedLevel+=levels.size();
    	choosedLevel--;
    	choosedLevel%=levels.size();
    	loadLevel();
    }
    
    void loadLevel() {
    	countShapes=(ArrayList<Integer>)levels.get(choosedLevel).getCountShapes().clone();
    	places=(ArrayList<Shape>)levels.get(choosedLevel).getPlaces().clone();
    	locations=(ArrayList<Point>)levels.get(choosedLevel).getLocations().clone();
    	edges=(ArrayList<Edge>)levels.get(choosedLevel).getEdges().clone();
    	
    }
    
	public void HowToPlay() {
		// TODO Auto-generated method stub
		
	}

	public void Reload() {
		countShapes=(ArrayList<Integer>)levels.get(choosedLevel).getCountShapes().clone();
    	places=(ArrayList<Shape>)levels.get(choosedLevel).getPlaces().clone();
    	locations=(ArrayList<Point>)levels.get(choosedLevel).getLocations().clone();
    	edges=(ArrayList<Edge>)levels.get(choosedLevel).getEdges().clone();
	}

	public void Music() {
		MusicPlayer player = new MusicPlayer ("LALALA");
		player.play();
	}
    
    private void doDrawing(Graphics g) {
    	Stroke stroke = new BasicStroke(4f);
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.white);
        g2d.setStroke(stroke);
        
        for(int i=0;i<edges.size();i++) {
        	int key=edges.get(i).key;
        	int value=edges.get(i).value;
        	if(places.get(key)==null || places.get(value)==null) {
        		g.setColor(Color.white);
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
        g.setColor(Color.white);

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
        	if(countShapes.get(i)>0) {
        		g2d.drawImage(availableShapes.get(i).getImage(), availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2,
        			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2, this);
        		g.drawString(countShapes.get(i).toString(), availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2,
            			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2);
        	}
        }
        if(draggedImage!=null) {
        	g2d.drawImage(draggedImage.getImage(),draggedImage.getX()-draggedImage.getWidth()/2,
        			draggedImage.getY()-draggedImage.getHeight()/2,this);
        }
        ImageIcon icon = new ImageIcon("image/right.png");
        ImageIcon icon1 = new ImageIcon("image/left.png");
        icon.paintIcon(this, g,nextButtonLocations.x-10,nextButtonLocations.y-10);
        icon1.paintIcon(this, g, prevButtonLocations.x-10,prevButtonLocations.y-10);
//    	g2d.fillOval( nextButtonLocations.x-10,nextButtonLocations.y-10,20,20);
//    	g2d.fillOval( prevButtonLocations.x-10,prevButtonLocations.y-10,20,20);
        
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
        repaint();
	}
	
	void initLevel() {	
		levels = new ArrayList();
		choosedLevel=0;
		
		countShapes=new ArrayList();
    	countShapes.add(1);
    	countShapes.add(1);
    	countShapes.add(1);
    	
        places=new ArrayList();
    	places.add(null);
    	places.add(null);
    	places.add(null);

        locations=new ArrayList();
	    locations.add(new Point(300,330));
    	locations.add(new Point(400,400));
    	locations.add(new Point(500,330));
    	
    	edges=new ArrayList();
    	edges.add(new Edge(0, 1));
    	edges.add(new Edge(1, 2));
    	levels.add(new Level(places,locations,edges,countShapes));
    	
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
		levels.add(new Level(places,locations,edges,countShapes));
		
		countShapes=new ArrayList();
    	countShapes.add(5);
    	countShapes.add(3);
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
    	locations.add(new Point(400,200)); //0a
    	locations.add(new Point(200,530)); //1b
    	locations.add(new Point(600,530)); //2c
    	locations.add(new Point(400,320)); //3d
    	locations.add(new Point(380,480)); //4e
    	locations.add(new Point(320,390)); //5f
    	locations.add(new Point(480,390)); //6g
    	locations.add(new Point(460,350)); //7h
    	locations.add(new Point(340,450)); //8i
    	locations.add(new Point(340,350)); //9j
    	locations.add(new Point(460,450)); //10k
    	locations.add(new Point(420,480)); //11L

        edges=new ArrayList();
        edges.add(new Edge(0, 1));
        edges.add(new Edge(0, 2));
        edges.add(new Edge(0, 3));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(1, 8));
        edges.add(new Edge(2, 10));
        edges.add(new Edge(3, 7));
        edges.add(new Edge(3, 9));
        edges.add(new Edge(4, 7));
        edges.add(new Edge(4, 8));
        edges.add(new Edge(4, 11));
        edges.add(new Edge(5, 9));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(5, 8));
        edges.add(new Edge(6, 7));
        edges.add(new Edge(6, 10));
        edges.add(new Edge(9, 11));
        edges.add(new Edge(11, 10));
		levels.add(new Level(places,locations,edges,countShapes));

        countShapes=new ArrayList();
    	countShapes.add(4);
    	countShapes.add(2);
    	countShapes.add(3);
    	
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
    	
        locations=new ArrayList();
        locations.add(new Point(270,460)); //0a
    	locations.add(new Point(490,460)); //1b
    	locations.add(new Point(560,410)); //2c
    	locations.add(new Point(560,300)); //3d
    	locations.add(new Point(220,300)); //4e
    	locations.add(new Point(490,200)); //5f
    	locations.add(new Point(310,260)); //6g
    	locations.add(new Point(400,230)); //7h
        locations.add(new Point(380,460)); //8i
        
        edges=new ArrayList();
        edges.add(new Edge(0,4));
        edges.add(new Edge(0,5));
        edges.add(new Edge(0,8));
        edges.add(new Edge(1,3));
        edges.add(new Edge(1,5));
        edges.add(new Edge(1,8));
        edges.add(new Edge(2,3));
        edges.add(new Edge(2,4));
        edges.add(new Edge(2,7));
        edges.add(new Edge(2,8));
        edges.add(new Edge(3,4));
        edges.add(new Edge(3,7));
        edges.add(new Edge(4,6));
        edges.add(new Edge(4,8));
        edges.add(new Edge(5,7));
        edges.add(new Edge(6,7));
        edges.add(new Edge(6,8));
        edges.add(new Edge(7,8));
		levels.add(new Level(places,locations,edges,countShapes));
		
		countShapes=new ArrayList();
    	countShapes.add(5);
    	countShapes.add(4);
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
    	places.add(null);
    	
        locations=new ArrayList();
        locations.add(new Point(280,470)); //0a
    	locations.add(new Point(400,480)); //1b
    	locations.add(new Point(520,470)); //2c
    	locations.add(new Point(400,230)); //3d
    	locations.add(new Point(350,430)); //4e
    	locations.add(new Point(450,430)); //5f
    	locations.add(new Point(330,350)); //6g
    	locations.add(new Point(470,350)); //7h
    	locations.add(new Point(400,300)); //8i
    	locations.add(new Point(260,290)); //9j
    	locations.add(new Point(540,290)); //10k
    	locations.add(new Point(480,530)); //11l
    	locations.add(new Point(330,530)); //12m
        
        edges=new ArrayList();
        edges.add(new Edge(0,4));
        edges.add(new Edge(0,9));
        edges.add(new Edge(0,11));
        edges.add(new Edge(1,8));
        edges.add(new Edge(1,11));
        edges.add(new Edge(1,12));
        edges.add(new Edge(2,5));
        edges.add(new Edge(2,10));
        edges.add(new Edge(2,12));
        edges.add(new Edge(3,8));
        edges.add(new Edge(3,9));
        edges.add(new Edge(3,10));
        edges.add(new Edge(4,7));
        edges.add(new Edge(4,8));
        edges.add(new Edge(5,6));
        edges.add(new Edge(5,8));
        edges.add(new Edge(6,7));
        edges.add(new Edge(6,9));
        edges.add(new Edge(7,10));
        edges.add(new Edge(7,11));
        edges.add(new Edge(9,12));
		levels.add(new Level(places,locations,edges,countShapes));

		countShapes=new ArrayList();
    	countShapes.add(4);
    	countShapes.add(3);
    	countShapes.add(3);
    	
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
    	
        locations=new ArrayList();
        locations.add(new Point(250,460)); //0a
    	locations.add(new Point(350,460)); //1b
    	locations.add(new Point(450,460)); //2c
    	locations.add(new Point(550,460)); //3d
    	locations.add(new Point(300,380)); //4e
    	locations.add(new Point(400,380)); //5f
    	locations.add(new Point(500,380)); //6g
    	locations.add(new Point(350,300)); //7h
    	locations.add(new Point(450,300)); //8i
    	locations.add(new Point(400,220)); //9j
        
        edges=new ArrayList();
        edges.add(new Edge(0, 1));
        edges.add(new Edge(0, 4));
        edges.add(new Edge(1, 4));
        edges.add(new Edge(1, 5));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(2, 5));
        edges.add(new Edge(2, 6));
        edges.add(new Edge(3, 6));
        edges.add(new Edge(4, 5));
        edges.add(new Edge(4, 7));
        edges.add(new Edge(5, 7));
        edges.add(new Edge(5, 8));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(6, 8));
        edges.add(new Edge(7, 8));
        edges.add(new Edge(7, 9));
        edges.add(new Edge(8, 9));
		levels.add(new Level(places,locations,edges,countShapes));

		countShapes=new ArrayList();
    	countShapes.add(6);
    	countShapes.add(2);
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
        locations.add(new Point(320,280)); //0a
    	locations.add(new Point(480,280)); //1b
    	locations.add(new Point(480,440)); //2c
    	locations.add(new Point(320,440)); //3d
    	locations.add(new Point(400,200)); //4e
    	locations.add(new Point(560,360)); //5f
    	locations.add(new Point(400,520)); //6g
    	locations.add(new Point(240,360)); //7h
    	locations.add(new Point(280,560)); //8i
    	locations.add(new Point(360,560)); //9j
    	locations.add(new Point(440,560)); //10k
    	locations.add(new Point(520,560)); //11L
        
        edges=new ArrayList();
        edges.add(new Edge(0, 1));
        edges.add(new Edge(0, 4));
        edges.add(new Edge(0, 3));
        edges.add(new Edge(0, 7));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(1, 4));
        edges.add(new Edge(1, 5));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(2, 5));
        edges.add(new Edge(2, 6));
        edges.add(new Edge(2, 11));
        edges.add(new Edge(3, 7));
        edges.add(new Edge(3, 6));
        edges.add(new Edge(3, 8));
        edges.add(new Edge(9, 8));
        edges.add(new Edge(9, 6));
        edges.add(new Edge(10, 6));
        edges.add(new Edge(10, 11));
		levels.add(new Level(places,locations,edges,countShapes));

		countShapes=new ArrayList();
    	countShapes.add(5);
    	countShapes.add(4);
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
    	places.add(null);
    	
        locations=new ArrayList();
    	locations.add(new Point(300,260));//0a
    	locations.add(new Point(300,460));//1b
    	locations.add(new Point(500,460));//2c
    	locations.add(new Point(500,260));//3d
    	locations.add(new Point(540,360));//4e
    	locations.add(new Point(400,500));//5f
    	locations.add(new Point(260,360));//6g
    	locations.add(new Point(400,220));//7h
    	locations.add(new Point(400,360));//8i
    	locations.add(new Point(460,340));//9j
    	locations.add(new Point(460,380));//10k
    	locations.add(new Point(340,340));//11l
    	locations.add(new Point(340,380));//12m

        edges=new ArrayList();
        edges.add(new Edge(0, 6));
        edges.add(new Edge(0, 1));
        edges.add(new Edge(0, 11));
        edges.add(new Edge(0, 8));
        edges.add(new Edge(0, 3));
        edges.add(new Edge(0, 7));
        edges.add(new Edge(1, 6));
        edges.add(new Edge(1, 12));
        edges.add(new Edge(1, 8));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(1, 5));
        edges.add(new Edge(2, 4));
        edges.add(new Edge(2, 5));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(2, 10));
        edges.add(new Edge(2, 8));
        edges.add(new Edge(3, 4));
        edges.add(new Edge(3, 7));
        edges.add(new Edge(3, 8));
        edges.add(new Edge(3, 9));
        edges.add(new Edge(8, 9));
        edges.add(new Edge(8, 10));
        edges.add(new Edge(8, 11));
        edges.add(new Edge(8, 12));
		levels.add(new Level(places,locations,edges,countShapes));

	}
}
