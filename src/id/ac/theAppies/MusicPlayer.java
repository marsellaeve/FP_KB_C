package id.ac.theAppies;
import  sun.audio.*;   
import java.io.*;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable {

	private ArrayList<String> musicFiles;
	private int currentSongIndex;
	
	public MusicPlayer() {
		musicFiles = new ArrayList<String>();
		musicFiles.add("image/videoplayback.wav");
	}
	
	private void playSound(String fileName) {
		try {
			File soundFile = new File(fileName);
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat format = ais.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, format);
			Clip clip = (Clip) AudioSystem.getLine(info);
			clip.open(ais);
			
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(-10);
			clip.start();
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println(fileName);
		}
	}
	
	@Override
	public void run() {
		playSound(musicFiles.get(0));
	}
	
	 public void play() 
	 {
	      Thread t = new Thread(this);
	      t.run();
	 }
	
}