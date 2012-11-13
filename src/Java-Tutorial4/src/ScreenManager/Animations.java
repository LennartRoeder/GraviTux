package ScreenManager;
import java.awt.Image;
import java.util.ArrayList;

/**
 * 
 * @author olaf
 * @version 1.0
 * @since 2008
 * 
 * Implements Animations
 */
public class Animations {

	private ArrayList<OneScene> scenes;
	private int sceneIndex;
	private long movieTime;
	private long totalTime;
	
	
	
	/**
	 * CONSTRUCTOR
	 */
	public Animations(){
		
		scenes = new ArrayList<OneScene>();
		totalTime = 0;
		start();
	}
	
	
	/**
	 *  add scene to ArrayList and set time for each scene
	 * @param i
	 * @param t
	 */
	public synchronized void addSceen(Image i, long t){
		totalTime += t;
		scenes.add(new OneScene(i,totalTime));
	}
	/**
	 * Start the Animation
	 */
	public synchronized void start(){
		movieTime = 0;
		sceneIndex = 0;
	}
	//
	/**
	 * chage scenes
	 * @param timePassed
	 */
	public synchronized void update(long timePassed){
		if(scenes.size()>1){
			movieTime += timePassed;
			if(movieTime >= totalTime){
				movieTime = 0;
				sceneIndex = 0;
			}
			while(movieTime > getScene(sceneIndex).endTime){
				sceneIndex ++;
			}
			
		}
		
	}
	//
	/**
	 *  get animations current scene(aka image)
	 * @return
	 */
	public synchronized Image getImage(){
		if(scenes.size()==0){
			return null;
		}else{
			return getScene(sceneIndex).pic;
		}
	}
	// 
	/**
	 * get scene
	 * @param x
	 * @return
	 */
	private OneScene getScene(int x){
		return (OneScene)scenes.get(x);
		
	}
	
	
	/**
	 * PRIVATE INNER CLASS 
	 * @author olaf
	 * @version 1.0
	 * @since 2008
	 */
	private class OneScene{
		
		Image pic;
		long endTime;
		public OneScene(Image pic, long endTime){
			this.pic=pic;
			this.endTime = endTime;
			
		}
	}
	
}
