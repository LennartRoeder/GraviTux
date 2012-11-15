package ScreenManager;

/**
 * 
 * @author olaf
 * @version 1.0
 * @since 2008
 * 
 * Implements the Main
 */

import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends Core implements KeyListener{
	/**
	 * Run the Application
	 * @param args
	 */
	public static void main(String[] args){
	 new Main().run();
		
	}

	
	
	/**
	 * init method form super Class Core
	 */
	public void init(){
		super.init();
		
		Window w = s.getFullScreenWindow();
		w.addKeyListener(this);
		w.setVisible(true);
		
	}
	
	
	/**
	 *  updateGame method from super Class Core
	 */
	 @Override
	public void updateGame(){
		
	
	}
	
	
	 /**
	  * Draw method from super Class Core
	  */
	 @Override
	public void draw(Graphics2D g) {
		
		
		g.drawString("Full Sreen", 10.0f, 30.0f);
		
	}
	
	 /**
	  * Key Listerner
	  * 
	  * Key Pressed Method ESC to stop the FullScreen and the GameLoop
	  */
	@Override
	public void keyPressed(KeyEvent key) {
		int keyCode = key.getKeyCode();
		
		if(keyCode == KeyEvent.VK_ESCAPE){
			stop();
		}
		
	}

	/**
	 * Key Listerner
	 */
	public void keyReleased(KeyEvent key) {
		System.out.println(key.getKeyLocation());
	}

	/**
	 * Key Listerner
	 */
	public void keyTyped(KeyEvent key) {}




}
