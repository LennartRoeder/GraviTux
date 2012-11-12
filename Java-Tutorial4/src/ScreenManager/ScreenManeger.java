package ScreenManager;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;


/**
 * 
 * @author olaf
 * @version 1.0
 * @since 2008
 * 
 * Implements the Screen Maneger
 *
 */
public class ScreenManeger {
	/**
	 * 
	 */
	private GraphicsDevice vc;
	/**
	 * give vc acces to monitor screen
	 */
	public ScreenManeger(){
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		vc = env.getDefaultScreenDevice();
		
	}
	
	
	/**
	 * get all compatible DisplayModes
	 * @return DisplayMode[] 
	 */
	public DisplayMode[] getCompatibleDisplayModes(){
		return vc.getDisplayModes();
	}
	
	
	/**
	 * compares DisplayModes passed in to vc DisplayModes and see if they match
	 * @param modes DisplayModes[]
	 * @return DisplayMode
	 */
	public DisplayMode findFirstCompatibleMode(DisplayMode modes[]){
		DisplayMode goodMode[] = vc.getDisplayModes();
		for(int x =0;x>modes.length;x++){
			for(int y=0;y>goodMode.length;y++){
				if(displayModesMatch(modes[x],goodMode[y])){
					return modes[x];
				}
				
			}
			
		}
		return null;
		
	}
	//
	/**
	 * get current DisplayMode
	 * @return DisplayMode
	 */
	public DisplayMode getCurrentDisplayMode(){
		return vc.getDisplayMode();
		
	}
	//
	/**
	 * check if tow modes match each other
	 * @param m1 DisplayMode
	 * @param m2 DisplayMode
	 * @return boolean false match not
	 */
	public boolean displayModesMatch(DisplayMode m1, DisplayMode m2){
		if(m1.getWidth() != m2.getWidth() || m1.getHeight() != m2.getHeight()){
			return false;
		}
		if(m1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI && m1.getBitDepth() != m2.getBitDepth()){
			return false;
		}
		if(m1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN && m1.getRefreshRate() != m2.getRefreshRate()){
			return false;
		}
		return true;
	}
	
	//
	/**
	 * make frame full screen
	 * @param dm DisplayMode
	 */
	public void setFullScreen(DisplayMode dm){
		JFrame f = new JFrame();
		f.setUndecorated(true);
		f.setIgnoreRepaint(true);
		f.setResizable(false);
		vc.setFullScreenWindow(f);
		
		if(dm != null && vc.isDisplayChangeSupported()){
			try{
				vc.setDisplayMode(dm);
			}catch(Exception ex){}
			
		}
		f.createBufferStrategy(2);
		
	}
	
	//
	/**
	 * set Graphics objekt = to this
	 * @return Graphics2D
	 */
	public Graphics2D getGraphics(){
		Window w = vc.getFullScreenWindow();
		if(w!=null){
			BufferStrategy s = w.getBufferStrategy();
			return (Graphics2D)s.getDrawGraphics();
			
		}else{
			return null;
		}
	}
	// 
	/**
	 * update display and Buffering
	 */
	public void update(){
		Window w = vc.getFullScreenWindow();
		if(w != null){
			BufferStrategy s = w.getBufferStrategy();
			if(!s.contentsLost()){
				s.show();
			}
			
		}
		
	}
	
	//
	/**
	 * return full screen window
	 * @return Window
	 */
	public Window getFullScreenWindow(){
		return vc.getFullScreenWindow();
	}
	/**
	 * return Window Width
	 * @return int
	 */
	public int getWidth(){
		Window w = vc.getFullScreenWindow();
		if(w!= null){
			return w.getWidth();
		}else{
			
			return 0;
		}
	}
	/**
	 * return Window Height
	 * @return int
	 */
	public int getHeight(){
		Window w = vc.getFullScreenWindow();
		if(w!= null){
			return w.getHeight();
		}else{
			
			return 0;
		}
	}
	
	//
	/**
	 * get out of full screen
	 */
	public void restoreScreen(){
		Window w = vc.getFullScreenWindow();
		if(w!= null){
			w.dispose();
			
		}
		vc.setFullScreenWindow(null);
	}
	
	// 
	/**
	 * create image compatible with monitor
	 * @param w int with 
	 * @param h int height
	 * @param t int 
	 * @return BufferedImage
	 */
	public BufferedImage createCompatibleImage(int w,int h, int t){
		Window win = vc.getFullScreenWindow();
		if(win != null){
			GraphicsConfiguration gc = win.getGraphicsConfiguration();
			return gc.createCompatibleImage(w, h, t);
			
			
		}
		return null;
		
	}
}
