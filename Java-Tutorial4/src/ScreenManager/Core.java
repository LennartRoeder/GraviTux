package ScreenManager;

/**
 * 
 * @author olaf
 * @version 1.0
 * @since 2008
 * 
 * Implements the abstract core
 */

import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;

public abstract class Core {

	private static DisplayMode modes[]={
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0),
		
	}; 
	private boolean running;
	protected ScreenManeger s;
	
	// public method
	/**
	 * Stop the game Loop
	 */
	public void stop(){
		running = false;
	}
	// call init an gameloop
	/**
	 * Run the game loop an init the Screen Manager 
	 */
	public void run(){
		try{
		init();
		gameLoop();
		}finally{
			s.restoreScreen();
		}
	}
	//
	/**
	 * set to full screen
	 */
	public void init(){
		s= new ScreenManeger();
		DisplayMode dm = s.findFirstCompatibleMode(modes);
		s.setFullScreen(dm);
		
		Window w = s.getFullScreenWindow();
		w.setFont(new Font("Arial",Font.PLAIN,20));
		w.setBackground(Color.GREEN);
		w.setForeground(Color.WHITE);
		running = true;
		
	}
	//
	/**
	 * main gameLoop
	 */
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long cumTime = startTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			
			update(timePassed);
			
			updateGame();
			Graphics2D g = s.getGraphics();
			draw(g);
			s.update();
			try{
				Thread.sleep(10);
			}catch(Exception ex){}
			
		}
		
		
	}
	
	
	/**
	 * Game update function
	 */
	public abstract void updateGame();
		
	
	
	
	/**
	 * update animation
	 * @param timePassed long 
	 */
	public void update(long timePassed){
		
	}
	
	
	/**
	 * draws to the screen
	 * @param g Graphics2D
	 */
	public abstract void draw(Graphics2D g);
		
	
}
