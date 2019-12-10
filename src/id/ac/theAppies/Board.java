package id.ac.theAppies;

import java.lang.Math;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
    private ArrayList<Boolean> finish;
    private Shape draggedImage;
    private Point mousePrevLocation;
    private int choosedLevel;
    private Point nextButtonLocations;
    private Point prevButtonLocations;
    private Point musicButtonLocations;
    private Point restartButtonLocations;
    private Point levelButtonLocations;
    private MusicPlayer player;
    private boolean musicPlayed;
    private Point questionButtonLocations;
    private boolean isHelpPage;
    private boolean isMainPage;
    private Point mainButtonLocations;
    private Point smurfButtonLocations;
    private Point grootButtonLocations;
    private Point turtleButtonLocations;
    private Point helpbackButtonLocations;
    private boolean isLevelPage;
    private ArrayList<Point> levelLocations;
    
    public Board() {
        initBoard();
    }
    
    private void initBoard() {
    	isMainPage=true;
    	this.addMouseListener(new MouseAdapter() {
    		@Override
    		public void mousePressed(MouseEvent e) {
    			Point currentMouseLocation=e.getPoint();
    			int x=mainButtonLocations.x-currentMouseLocation.x+75;
    			int y=mainButtonLocations.y-currentMouseLocation.y+35;
    			if(isMainPage==true) {
    				if(x>=0&&x<=150&&y>=0&&y<=70) {
    					isMainPage=false;
    					isLevelPage=true;
    				}
    				return;
    			}
    			if(isLevelPage==true) {
    				for(int i=0;i<levelLocations.size();i++) {
    					x=levelLocations.get(i).x-currentMouseLocation.x;
        				y=levelLocations.get(i).y-currentMouseLocation.y;
        				if(Math.sqrt(x*x+y*y)<40.0 && (i==0||finish.get(i-1))) {
            				isLevelPage=false;
        					pageLevel(i);
        				}
    				}
    				return;
    			}
    			for(int i=0;i<shapePlaces.size();i++) {
    				x=shapePlaces.get(i).x-currentMouseLocation.x;
    				y=shapePlaces.get(i).y-currentMouseLocation.y;
    				if(Math.sqrt(x*x+y*y)<20.0 && countShapes.get(i)>0) {
    					draggedImage=availableShapes.get(i).clone();
        				countShapes.set(i,countShapes.get(i)-1);
    				}
    			}
    			
    			for(int i=0;i<locations.size();i++) {
    				x=locations.get(i).x-currentMouseLocation.x;
    				y=locations.get(i).y-currentMouseLocation.y;
    				if(Math.sqrt(x*x+y*y)<20.0 && places.get(i)!=null) {
        				draggedImage=places.get(i);
        				places.set(i, null);
        			}
    			}
    			x=levelButtonLocations.x-currentMouseLocation.x;
    			y=levelButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				isLevelPage=true;
    			}
    			x=nextButtonLocations.x-currentMouseLocation.x;
    			y=nextButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				nextLevel();
    			}
    			x=prevButtonLocations.x-currentMouseLocation.x;
    			y=prevButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				prevLevel();
    			}
    			x=musicButtonLocations.x-currentMouseLocation.x;
    			y=musicButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				Music();
    			}
    			x=restartButtonLocations.x-currentMouseLocation.x;
    			y=restartButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				Reload();
    			}
    			x=questionButtonLocations.x-currentMouseLocation.x;
    			y=questionButtonLocations.y-currentMouseLocation.y;
    			if(Math.sqrt(x*x+y*y)<20.0) {
    				isHelpPage=true;
    			}
    			x=helpbackButtonLocations.x-currentMouseLocation.x;
    			y=helpbackButtonLocations.y-currentMouseLocation.y;
    			if(isHelpPage==true) {
    				if(Math.sqrt(x*x+y*y)<=25.0) {
    					isHelpPage=false;
    				}
    				return;
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
    	musicPlayed=false;
    	player = new MusicPlayer ();
    	choosedLevel=1;
    	initLevel();
        shapePlaces = new ArrayList();
        shapePlaces.add(new Point(320,80));
        shapePlaces.add(new Point(400,80));
        shapePlaces.add(new Point(480,80));

        nextButtonLocations=new Point(720,80);
        prevButtonLocations=new Point(80,80);
        musicButtonLocations=new Point(640,80);
        restartButtonLocations=new Point(560,80);
        questionButtonLocations=new Point(160,78);
        levelButtonLocations=new Point(240,78);
        helpbackButtonLocations=new Point(393,518);
        
        mainButtonLocations = new Point(400,500);
        grootButtonLocations = new Point(130,200);
        smurfButtonLocations = new Point(330,200);
        turtleButtonLocations = new Point(530,200);
        
        levelLocations=new ArrayList();
        levelLocations.add(new Point(175,200));
        levelLocations.add(new Point(325,200));
        levelLocations.add(new Point(475,200));
        levelLocations.add(new Point(625,200));
        levelLocations.add(new Point(175,350));
        levelLocations.add(new Point(325,350));
        levelLocations.add(new Point(475,350));
        levelLocations.add(new Point(625,350));
        levelLocations.add(new Point(175,500));
        levelLocations.add(new Point(325,500));
        levelLocations.add(new Point(475,500));
        levelLocations.add(new Point(625,500));
        
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
    	finish= new ArrayList();
    	for(int i=0;i<levels.size();i++) {
    		finish.add(false);
    	}
    
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }
    
    void pageLevel(int thisLevel) {
		choosedLevel=thisLevel;
    	loadLevel();	
    }
    
    void nextLevel() {
    	if(finish.get(choosedLevel)) {
    		choosedLevel++;
        	choosedLevel%=levels.size();
        	loadLevel();	
    	}
    }

    void prevLevel() {
    	if(finish.get((choosedLevel-1+levels.size())%levels.size())) {
    		choosedLevel+=levels.size();
        	choosedLevel--;
        	choosedLevel%=levels.size();
        	loadLevel();	
    	}
    }
    
    void loadLevel() {
    	countShapes=(ArrayList<Integer>)levels.get(choosedLevel).getCountShapes().clone();
    	places=(ArrayList<Shape>)levels.get(choosedLevel).getPlaces().clone();
    	locations=(ArrayList<Point>)levels.get(choosedLevel).getLocations().clone();
    	edges=(ArrayList<Edge>)levels.get(choosedLevel).getEdges().clone();
    	
    }
    
	public void Reload() {
		countShapes=(ArrayList<Integer>)levels.get(choosedLevel).getCountShapes().clone();
    	places=(ArrayList<Shape>)levels.get(choosedLevel).getPlaces().clone();
    	locations=(ArrayList<Point>)levels.get(choosedLevel).getLocations().clone();
    	edges=(ArrayList<Edge>)levels.get(choosedLevel).getEdges().clone();
	}
	public void Music() {
		if(musicPlayed) {
			player.stop();
		}
		else player.play();
		musicPlayed=!musicPlayed;
	}
    
    private void doDrawing(Graphics g) {
    	Stroke stroke = new BasicStroke(4f);
    	Stroke stroke2 = new BasicStroke(6f);
    	Stroke stroke3 = new BasicStroke(2f);
        Font tr5 = new Font("Times New Roman", Font.BOLD, 80);
        Font tr6 = new Font("Times New Roman", Font.BOLD, 50);
        Font tr4 = new Font("Times New Roman", Font.BOLD, 30);
        Font t1 = new Font("Times New Roman", Font.PLAIN, 30);
        Font tkecil = new Font("Times New Roman", Font.PLAIN, 13);
        Font tr = new Font("Comic Sans", Font.BOLD, 26);
        Graphics2D g2d = (Graphics2D) g;
        
        if(isMainPage) {
        	g2d.setColor(Color.DARK_GRAY);
        	g2d.fillRect(0, 0, 800, 650);
        	g2d.setColor(Color.cyan);
        	g2d.fillRect( mainButtonLocations.x-75,mainButtonLocations.y-35, 150, 70);
        	g2d.setColor(Color.white);
        	g2d.drawRect( mainButtonLocations.x-75,mainButtonLocations.y-35, 150, 70);
            g2d.setFont(tr5);
        	g.drawString("The Appies",200,130);
            g2d.setFont(tr4);
        	g.drawString("Start",mainButtonLocations.x-30,mainButtonLocations.y+10);
            ImageIcon iconGroot = new ImageIcon("image/grootSedang.png");
            iconGroot.paintIcon(this, g,grootButtonLocations.x,grootButtonLocations.y);
            ImageIcon iconSmurf = new ImageIcon("image/smurfSedang.png");
            iconSmurf.paintIcon(this, g,smurfButtonLocations.x,smurfButtonLocations.y);
            ImageIcon iconTurtle = new ImageIcon("image/turtleSedang.png");
            iconTurtle.paintIcon(this, g,turtleButtonLocations.x,turtleButtonLocations.y);
            g2d.setFont(t1);
            g.setColor(Color.orange);
        	g.drawString("Groot",grootButtonLocations.x+35,grootButtonLocations.y+170);
            g.setColor(Color.cyan);
        	g.drawString("Smurf",smurfButtonLocations.x+35,smurfButtonLocations.y+170);
            g.setColor(Color.green);
        	g.drawString("Turtle",turtleButtonLocations.x+35,turtleButtonLocations.y+170);
        	return;
        }
        
        if(isLevelPage) {
        	g2d.setColor(Color.DARK_GRAY);
        	g2d.fillRect(0, 0, 800, 650);
            g2d.setFont(tr6);
            
        	for(int i=0;i<levelLocations.size();i++) {
        		if ((i%2==1||i==4||i==6)&&i!=5&&i!=7)
        			g2d.setColor(Color.CYAN);
        		else if (i%2==0 || i==5||i==7)
                	g2d.setColor(Color.BLUE);
        		g2d.fillOval(levelLocations.get(i).x-40,levelLocations.get(i).y-40, 80, 80);
            	g2d.setColor(Color.white);
        		g2d.drawOval(levelLocations.get(i).x-40,levelLocations.get(i).y-40, 80, 80);
            	g2d.setColor(Color.white);
        		String str = String.valueOf(i+1);
        		if(i>=9)
        			g.drawString(str,levelLocations.get(i).x-24,levelLocations.get(i).y+16);
        		else
            		g.drawString(str,levelLocations.get(i).x-12,levelLocations.get(i).y+16);
        		if(i!=0) {
        			if(!finish.get(i-1)) {
            	        ImageIcon iconLock = new ImageIcon("image/lock.png");
        				iconLock.paintIcon(this, g,levelLocations.get(i).x-40,levelLocations.get(i).y-40);
        			}
        		}
        	}
            g2d.setFont(tr6);
        	g2d.setColor(Color.white);
        	g.drawString("Choose Your Levels",190,100);
        	return;
        }
        
        g2d.setStroke(stroke2);
        g.setColor(Color.black);
        g2d.fillRect(0, 0, 800, 140);
        
        g.setColor(Color.LIGHT_GRAY);
        g2d.drawLine(0, 140, 800, 140);

        g2d.setStroke(stroke);
        g.setColor(Color.white);
        int count=0;
        for(int i=0;i<edges.size();i++) {
        	int key=edges.get(i).key;
        	int value=edges.get(i).value;
        	if(places.get(key)==null || places.get(value)==null) {
        		g.setColor(Color.lightGray);
        	}
        	else if(places.get(key).getClass()==places.get(value).getClass()) {
        		g.setColor(Color.RED);
        	}
        	else {
        		g.setColor(Color.GREEN);
        		count++;
        	}
    		g.drawLine(locations.get(key).x, locations.get(key).y,
    				locations.get(value).x,locations.get(value).y);
        }
        if(count==edges.size()) {
        	finish.set(choosedLevel, true);
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

        g2d.setFont(tkecil);
        for(int i=0;i<countShapes.size();i++) {
        	g.setColor(Color.DARK_GRAY);
    		g2d.setStroke(stroke3);
        	g2d.drawRect(availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2-1,
        			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2,50,50);
        	if(countShapes.get(i)>0) {
        		g2d.drawImage(availableShapes.get(i).getImage(), availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2,
        			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2, this);
                g.setColor(Color.DARK_GRAY);
        		g.fillRect(availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2-2,
            			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2-15,18,15);
        		g.setColor(Color.white);
        		g.drawString(countShapes.get(i).toString()+"x", availableShapes.get(i).getX()-availableShapes.get(i).getWidth()/2,
            			availableShapes.get(i).getY()-availableShapes.get(i).getHeight()/2-3);
        	}
        }
        if(draggedImage!=null) {
        	g2d.drawImage(draggedImage.getImage(),draggedImage.getX()-draggedImage.getWidth()/2,
        			draggedImage.getY()-draggedImage.getHeight()/2,this);
        }
        ImageIcon iconRight = new ImageIcon("image/right.png");
        ImageIcon iconLeft = new ImageIcon("image/left.png");
        ImageIcon iconMusic = new ImageIcon("image/music-player.png");
        ImageIcon iconReload = new ImageIcon("image/Restart.png");
        ImageIcon iconQuestion = new ImageIcon("image/question-mark.png");
        ImageIcon iconLevel = new ImageIcon("image/menu.png");
        iconRight.paintIcon(this, g,nextButtonLocations.x-10,nextButtonLocations.y-10);
        iconLeft.paintIcon(this, g, prevButtonLocations.x-10,prevButtonLocations.y-10);
    	iconMusic.paintIcon(this, g, musicButtonLocations.x-10,musicButtonLocations.y-10);
    	iconReload.paintIcon(this, g, restartButtonLocations.x-10,restartButtonLocations.y-10);
    	iconQuestion.paintIcon(this, g, questionButtonLocations.x-10,questionButtonLocations.y-10);
    	iconLevel.paintIcon(this, g, levelButtonLocations.x-12,levelButtonLocations.y-12);
        if(!musicPlayed) {
        	g.setColor(Color.red);
        	g.drawLine(musicButtonLocations.x-10, musicButtonLocations.y+20,musicButtonLocations.x+25, musicButtonLocations.y-10);
        }
        g.setColor(Color.white);
      //tulisan level
        g.fillRect(0, 0, 120, 40);
        g2d.setFont(tr);
        g.setColor(Color.black);
        g2d.drawString("Level "+ (choosedLevel+1), 10, 30);
        
        
        if(isHelpPage) {
        	g2d.setColor(Color.white);
        	g2d.drawRect(questionButtonLocations.x+80,questionButtonLocations.y-20, 303,500);
            g2d.setFont(tr6);
        	ImageIcon howToPlay = new ImageIcon("image/howToPlay.png");
	        howToPlay.paintIcon(this, g,questionButtonLocations.x+80,questionButtonLocations.y-20);
	        return;
        }
        
    }
    
	@Override
	public void actionPerformed(ActionEvent arg0) {
        repaint();
	}
	
	void initLevel() {	
		levels = new ArrayList();
		choosedLevel=0;
//level 1		
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
//level 2   	
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
//level 3		
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
//level 4
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
        locations.add(new Point(270,500)); //0a
    	locations.add(new Point(490,500)); //1b
    	locations.add(new Point(560,450)); //2c
    	locations.add(new Point(560,340)); //3d
    	locations.add(new Point(220,340)); //4e
    	locations.add(new Point(490,240)); //5f
    	locations.add(new Point(310,300)); //6g
    	locations.add(new Point(400,270)); //7h
        locations.add(new Point(380,500)); //8i
        
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
//level 5	
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
//level 6
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
//level 7
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
//level 8
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
//level 9		
        countShapes=new ArrayList();
    	countShapes.add(7);
    	countShapes.add(2);
    	countShapes.add(5);
    	
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
    	places.add(null);

        locations=new ArrayList();
    	locations.add(new Point(400,330));//0a
    	locations.add(new Point(400,430));//1b
    	locations.add(new Point(450,380));//2c
    	locations.add(new Point(500,380));//3d
    	locations.add(new Point(500,330));//4e
    	locations.add(new Point(500,430));//5f
    	locations.add(new Point(550,380));//6g
    	locations.add(new Point(450,230));//7h
    	locations.add(new Point(450,530));//8i
    	locations.add(new Point(400,520));//9j
    	locations.add(new Point(400,240));//10k
    	locations.add(new Point(370,280));//11l
    	locations.add(new Point(370,480));//12m
    	locations.add(new Point(250,380));//13n

        edges=new ArrayList();
        edges.add(new Edge(0, 2));
        edges.add(new Edge(0, 13));
        edges.add(new Edge(1, 2));
        edges.add(new Edge(1, 13));
        edges.add(new Edge(2, 13));
        edges.add(new Edge(2, 3));
        edges.add(new Edge(2, 4));
        edges.add(new Edge(2, 5));
        edges.add(new Edge(2, 7));
        edges.add(new Edge(2, 8));
        edges.add(new Edge(3, 4));
        edges.add(new Edge(3, 5));
        edges.add(new Edge(3, 6));
        edges.add(new Edge(3, 10));
        edges.add(new Edge(3, 9));
        edges.add(new Edge(4, 6));
        edges.add(new Edge(4, 11));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(5, 12));
        edges.add(new Edge(10, 7));
        edges.add(new Edge(10, 11));
        edges.add(new Edge(9, 8));
        edges.add(new Edge(9, 12));
        levels.add(new Level(places,locations,edges,countShapes));

//level 10
        countShapes=new ArrayList();
    	countShapes.add(7);
    	countShapes.add(7);
    	countShapes.add(2);
    	
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
    	places.add(null);
    	places.add(null);
    	places.add(null);

        locations=new ArrayList();
	    locations.add(new Point(300,250));//0a
    	locations.add(new Point(500,250));//1b
    	locations.add(new Point(500,500));//2c
    	locations.add(new Point(400,300));//3d
    	locations.add(new Point(400,350));//4e
    	locations.add(new Point(400,400));//5f
    	locations.add(new Point(400,450));//6g
    	locations.add(new Point(450,400));//7h
    	locations.add(new Point(500,450));//8i
    	locations.add(new Point(450,300));//9j
    	locations.add(new Point(450,350));//10k
    	locations.add(new Point(350,310));//11l
    	locations.add(new Point(400,500));//12m
    	locations.add(new Point(300,500));//13n
    	locations.add(new Point(350,350));//14o
    	locations.add(new Point(350,410));//15p
//    	locations.add(new Point(350,310));//16q

        edges=new ArrayList();
        edges.add(new Edge(0, 1));
        edges.add(new Edge(0, 13));
        edges.add(new Edge(0, 3));
        edges.add(new Edge(0, 11));
        edges.add(new Edge(8, 1));
        edges.add(new Edge(8, 2));
        edges.add(new Edge(8, 6));
        edges.add(new Edge(8, 12));
        edges.add(new Edge(3, 9));
        edges.add(new Edge(3, 4));
        edges.add(new Edge(4, 11));
        edges.add(new Edge(4, 10));
        edges.add(new Edge(4, 14));
        edges.add(new Edge(4, 15));
        edges.add(new Edge(4, 5));
        edges.add(new Edge(5, 7));
        edges.add(new Edge(5, 6));
        edges.add(new Edge(6, 15));
        edges.add(new Edge(6, 13));
        edges.add(new Edge(12, 13));
        edges.add(new Edge(12, 2));
        levels.add(new Level(places,locations,edges,countShapes));
        
//level 11
        countShapes=new ArrayList();
    	countShapes.add(8);
    	countShapes.add(4);
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
    	places.add(null);
    	places.add(null);
    	
    	locations=new ArrayList();
    	locations.add(new Point(430,350)); //0a
    	locations.add(new Point(375,350)); //1b
    	locations.add(new Point(325,350)); //2c
    	locations.add(new Point(275,350)); //3d
    	locations.add(new Point(425,200)); //4e
    	locations.add(new Point(425,300)); //5f
    	locations.add(new Point(525,450)); //6g
    	locations.add(new Point(475,500)); //7h
    	locations.add(new Point(430,450)); //8i
    	locations.add(new Point(525,350)); //9j
    	locations.add(new Point(375,400)); //10k
    	locations.add(new Point(375,500)); //11L
    	locations.add(new Point(325,450)); //12M
    	locations.add(new Point(275,400)); //13N
    	locations.add(new Point(420,540)); //14O

        edges=new ArrayList();
    	edges.add(new Edge(0, 1));
    	edges.add(new Edge(0, 6));
    	edges.add(new Edge(1, 2));
    	edges.add(new Edge(1, 5));
    	edges.add(new Edge(1, 10));
    	edges.add(new Edge(2, 3));
    	edges.add(new Edge(2, 10));
    	edges.add(new Edge(2, 12));
    	edges.add(new Edge(3, 4));
    	edges.add(new Edge(3, 13));
    	edges.add(new Edge(3, 12));
    	edges.add(new Edge(10, 13));
    	edges.add(new Edge(10, 12));
    	edges.add(new Edge(10, 11));
    	edges.add(new Edge(8, 9));
    	edges.add(new Edge(8, 7));
    	edges.add(new Edge(8, 12));
    	edges.add(new Edge(7, 6));
    	edges.add(new Edge(7, 11));
    	edges.add(new Edge(8, 6));
    	edges.add(new Edge(11, 14));
		levels.add(new Level(places,locations,edges,countShapes));

//level 12
        countShapes=new ArrayList();
    	countShapes.add(8);
    	countShapes.add(4);
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
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	places.add(null);
    	
    	locations=new ArrayList();
    	locations.add(new Point(250,250)); //0a
    	locations.add(new Point(550,250)); //1b
    	locations.add(new Point(350,300)); //2c
    	locations.add(new Point(450,300)); //3d
    	locations.add(new Point(350,450)); //4e
    	locations.add(new Point(450,450)); //5f
    	locations.add(new Point(500,450)); //6g
    	locations.add(new Point(550,450)); //7h
    	locations.add(new Point(550,400)); //8i
    	locations.add(new Point(250,450)); //9j
    	locations.add(new Point(300,500)); //10k
    	locations.add(new Point(350,400)); //11L
    	locations.add(new Point(250,350)); //12M
    	locations.add(new Point(300,380)); //13N
    	locations.add(new Point(500,330)); //14O

        edges=new ArrayList();
    	edges.add(new Edge(0, 12));
    	edges.add(new Edge(1, 8));
    	edges.add(new Edge(2, 3));
    	edges.add(new Edge(2, 12));
    	edges.add(new Edge(2, 13));
    	edges.add(new Edge(2, 11));
    	edges.add(new Edge(3, 14));
    	edges.add(new Edge(3, 5));
    	edges.add(new Edge(3, 11));
    	edges.add(new Edge(4, 5));
    	edges.add(new Edge(4, 14));
    	edges.add(new Edge(4, 11));
    	edges.add(new Edge(4, 9));
    	edges.add(new Edge(4, 10));
    	edges.add(new Edge(6, 5));
    	edges.add(new Edge(6, 7));
    	edges.add(new Edge(8, 7));
    	edges.add(new Edge(8, 14));
    	edges.add(new Edge(8, 11));
    	edges.add(new Edge(13, 12));
    	edges.add(new Edge(13, 11));
    	edges.add(new Edge(12, 9));
		levels.add(new Level(places,locations,edges,countShapes));

	}
}
