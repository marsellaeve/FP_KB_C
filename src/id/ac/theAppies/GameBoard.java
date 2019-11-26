package id.ac.theAppies;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameBoard {
	//berapa kolom dan baris
	public static final int ROWS = 4;
	public static final int COLS = 4;
	
	private final int kotakMulai = 2;
	//buat boardnya disimpan 2d array 
	private Kotak[][] board;
	private boolean dead;
	private boolean won;
	private BufferedImage gameBoard;
	private BufferedImage finalBoard;
	
	//koordinat xy kotaknya
	private int x;
	private int y;
	
	//untuk space antara kotak di board
	private static int SPACING = 10;
	
	public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Kotak.WIDTH;
	public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + COLS * Kotak.HEIGHT;
	
	public GameBoard (int x, int y) {
		this.x = x;
		this.y = y;
		board = new Kotak[ROWS][COLS];
		gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
		
		createBoardImage();
	}
	
	private void createBoardImage() {
		Graphics2D g = (Graphics2D) gameBoard.getGraphics();
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		g.setColor(Color.LIGHT_GRAY);
		
		for (int row = 0; row <ROWS; row++) {
			for (int col = 0; col <COLS; col++) {
				int x = SPACING + SPACING * col + Kotak.WIDTH;
				int y = SPACING + SPACING * row + Kotak.HEIGHT;
				g.fillRoundRect(x, y, Kotak.WIDTH, Kotak.HEIGHT, Kotak.ARC_WIDTH, Kotak.ARC_HEIGHT);
			}
		}
	}
	
	public void render(Graphics2D g) {
		Graphics2D g2d = (Graphics2D)finalBoard.getGraphics();
		
		g2d.drawImage(finalBoard, x, y, null);
		g2d.dispose();
	}
	
//	public void update() {
//		checkKeys();
//	}
//	
//	private void checkKeys() {
//		
//	}
}
