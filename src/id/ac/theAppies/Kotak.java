package id.ac.theAppies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Kotak {
	public static final int WIDTH = 80;
	public static final int HEIGHT = 80;
	public static final int ARC_WIDTH = 80;
	public static final int ARC_HEIGHT = 80;
	
	private int value;
	private BufferedImage gambarKotak;
	private Color background;
	private Color text;
	private Color font;
	private int x;
	private int y;
	
	public Kotak(int value, int x, int y) {
		this.value = value;
		this.x = x;
		this.y = y;
		gambarKotak = new BufferedImage (WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);		
		drawImage();
	}
	
	private void drawImage() {
		Graphics2D g = (Graphics2D)gambarKotak.getGraphics();
		//untuk gambar 
		if(value == 1) {
			background = new Color(0x9e9e9);
		}
		//untuk gambar
		else if (value == 2) {
			background = new Color(0xe6daab);
		}
		//untuk gambar
		else if (value == 3) {
			background = new Color(0xf79d3d);
		}
		//untuk gambar
		else if (value == 4) {
			background = new Color(0xf28007);
		}
		//untuk gambar
		else if (value == 5) {
			background = new Color(0xf55e3b);
		}
		//kalo g ditaruh gambar
		else {
			background = Color.black;
		}
		
		g.setColor(new Color(0, 0, 0, 0));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.setColor(background);
		g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
		
		if (value <= 5) {
			
		}
	}
}
