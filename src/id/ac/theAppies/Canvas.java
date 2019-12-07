package id.ac.theAppies;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Canvas {
	
	public void HowToPlay() {
		// TODO Auto-generated method stub
		
	}

	public void NextLevel() {
		// TODO Auto-generated method stub
		
	}

	public void Reload() {
		places=new ArrayList();
	    places.add(null);
	    places.add(null);
	    places.add(null);
	}

	public void Music() {
		MusicPlayer player = new MusicPlayer ("LALALA");
		player.play();
	}
}