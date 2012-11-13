package GraviTux;

import com.sun.corba.se.spi.ior.ObjectKeyTemplate;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	Animation tux, movingUp, movingDown, movingLeft, movingRight; //4 animations, tux will be set to one
    TiledMap worldMap = null;
	boolean quit = false;
	int[] duration = {200, 200}; //duration or length of the frame
	float tuxPositionX = 0; //tux will start at coordinates 0,0
	float tuxPositionY = 0;
	float shiftX = tuxPositionX + 320; //this will shift the screen so tux appears in middle
	float shiftY = tuxPositionY + 160; //half the length and half the width of the screen

	public Play(int state)
	{
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
        worldMap = new TiledMap("res/level/level_1.tmx");
		//Image[] walkUp = {new Image("res/buckysBack.png"), new Image("res/buckysBack.png")}; //these are the images to be used in the "walkUp" animation
		//Image[] walkDown = {new Image("res/buckysFront.png"), new Image("res/buckysFront.png")};
		Image[] walkLeft = {new Image("res/figuren/tux_left.png"), new Image("res/figuren/tux_left.png")};
		Image[] walkRight = {new Image("res/figuren/tux_right.png"), new Image("res/figuren/tux_right.png")};

		//movingUp = new Animation(walkUp, duration, false); //each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		//movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, false);
		movingRight = new Animation(walkRight, duration, false);
		tux = movingRight; //by default as soon as game loads, tux will be facing right
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//worldMap.draw(tuxPositionX, tuxPositionY); //draw the map at 0,0 to start
        worldMap.render((int) tuxPositionX, (int) tuxPositionY); //draw the map at 0,0 to start
		tux.draw(shiftX, shiftY); //draw tux at 320, 160 (center of the screen)
		g.drawString("Tuxs X: " + tuxPositionX + "\nTuxs Y: " + tuxPositionY, 400, 20); //indicator to see where tux is in his world

		//when they press escape
		if (quit == true)
		{
			g.drawString("Resume (R)", 250, 100);
			g.drawString("Main Menu (M)", 250, 150);
			g.drawString("Quit Game (Q)", 250, 200);
			if (quit == false)
			{
				g.clear();
			}
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();

		//during the game if the user hits the up arrow...

		if (input.isKeyDown(Input.KEY_UP))
		{
			/*
			tux = movingUp; //change tux to up image
			tuxPositionY += delta * .1f; //increase the Y coordinates of tux (move him up)
			if (tuxPositionY > 162)
			{
				tuxPositionY -= delta * .1f; //dont let him keep going up if he reaches the top
			}
			*/
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
			/*
			tux = movingDown;
			tuxPositionY -= delta * .1f;
			if (tuxPositionY < -600)
			{
				tuxPositionY += delta * .1f;
			}
			*/
		}
		if (input.isKeyDown(Input.KEY_LEFT))
		{
			tux = movingLeft;
			tuxPositionX += delta * .1f;
			if (tuxPositionX > 324)
			{
				tuxPositionX -= delta * .1f;
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			tux = movingRight;
			tuxPositionX -= delta * .1f;
			if (tuxPositionX < -840)
			{
				tuxPositionX += delta * .1f;
			}
		}

		//escape
		if (input.isKeyDown(Input.KEY_ESCAPE))
		{
			quit = true;
		}

		//when they hit escape
		if (quit == true)
		{
			if (input.isKeyDown(Input.KEY_R))
			{
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M))
			{
				sbg.enterState(0);
				try
				{
					Thread.sleep(250);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			if (input.isKeyDown(Input.KEY_Q))
			{
				System.exit(0);
			}
		}
	}

	public int getID()
	{
		return 1;
	}
}