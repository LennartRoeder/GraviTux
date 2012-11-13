package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	Animation tux, movingUp, movingDown, movingLeft, movingRight; //4 animations, tux will be set to one
    TiledMap worldMap;
	boolean quit;
	int[] duration;
	float tuxPositionX;
	float tuxPositionY;

	public Play(int state)
	{
		worldMap = null;
		quit = false;
		duration = new int[]{200, 200}; //duration or length of the frame
		tuxPositionX = 0;               //tux will start at coordinates 0,0
		tuxPositionY = 0;
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
        worldMap.render((int) tuxPositionX, (int) tuxPositionY); //draw the map at 0,0 to start
		tux.draw(90, 520); //draws tux at 90, 520 (unten links)
		g.drawString("Tuxs X: " + tuxPositionX + "\nTuxs Y: " + tuxPositionY, 650, 50); //indicator to see where tux is in his world

		//when they press escape
		if (quit)
		{
			g.drawString("Weiter spielen (S)", 324, 200);
			g.drawString("Hauptmenü (M)", 324, 250);
			g.drawString("Spiel beenden (Q)", 324, 300);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();

		//during the game if the user hits the up arrow. "*.25f" is the speed.
		if (input.isKeyDown(Input.KEY_UP))
		{
			/*
			tux = movingUp; //change tux to up image
			tuxPositionY += delta * .25f; //increase the Y coordinates of tux (move him up)
			if (tuxPositionY > 162)
			{
				tuxPositionY -= delta * .25f; //dont let him keep going up if he reaches the top
			}
			*/
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
			/*
			tux = movingDown;
			tuxPositionY -= delta * .25f;
			if (tuxPositionY < -600)
			{
				tuxPositionY += delta * .25f;
			}
			*/
		}
		if (input.isKeyDown(Input.KEY_LEFT))
		{
			tux = movingLeft;
			tuxPositionX += delta * .25f;
			if (tuxPositionX > 102) //Tux touches left
			{
				tuxPositionX -= delta * .25f;
			}
		}
		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			tux = movingRight;
			tuxPositionX -= delta * .25f;
			if (tuxPositionX < -683)    //Tux touches right
			{
				tuxPositionX += delta * .25f;
			}
		}
		//escape
		if (input.isKeyDown(Input.KEY_ESCAPE))
		{
			quit = true;
		}
		//when they hit escape
		if (quit)
		{
			if (input.isKeyDown(Input.KEY_S))
			{
				quit = false;
			}
			if (input.isKeyDown(Input.KEY_M))
			{
				sbg.enterState(0);
				quit = false;
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