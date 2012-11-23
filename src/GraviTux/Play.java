package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	Animation tux, movingUp, movingDown, movingLeft, movingRight, standingRight, standingLeft, standing; //4 animations, tux will be set to one
	TiledMap worldMap;
	boolean quit;
    int duration;
	float tuxPositionX;
	float tuxPositionY;

	public Play(int state)
	{
		worldMap = null;
		quit = false;
		duration = 200;
        tuxPositionX = 90;   //tux will start at coordinates 0,0
		tuxPositionY = 520;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		worldMap = new TiledMap("res/GraviTux/level/level_1.tmx");
		//Image[] walkUp = {new Image("res/buckysBack.png"), new Image("res/buckysBack.png")}; //these are the images to be used in the "walkUp" animation
		//Image[] walkDown = {new Image("res/buckysFront.png"), new Image("res/buckysFront.png")};
		Image[] walkLeft = {new Image("GraviTux/tux/Tux_links01.png"), new Image("GraviTux/tux/Tux_links02.png"), new Image("GraviTux/tux/Tux_links03.png"), new Image("GraviTux/tux/Tux_links04.png")};
		Image[] walkRight = {new Image("GraviTux/tux/Tux_01.png"), new Image("GraviTux/tux/Tux_02.png"), new Image("GraviTux/tux/Tux_03.png"), new Image("GraviTux/tux/Tux_04.png")};
        Image[] standLeft = {new Image("GraviTux/tux/tux_left.png")};
        Image[] standRight = {new Image("GraviTux/tux/tux_right.png")};
		//movingUp = new Animation(walkUp, duration, false); //each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		//movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, true);
		movingRight = new Animation(walkRight, duration, true);
        standingLeft = new Animation(standLeft, duration, false);
        standingRight = new Animation(standRight, duration, false);
        standing = standingRight;
        tux = standingRight;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
        worldMap.render(0, 0); //draw the map at 0,0 to start
		tux.draw(tuxPositionX, tuxPositionY); //draws tux at 90, 520 (unten links)
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
			tuxPositionY -= delta * .25f; //increase the Y coordinates of tux (move him up)
			if (tuxPositionY < 0)
			{
				tuxPositionY += delta * .25f; //dont let him keep going up if he reaches the top
			}
            */
		}
		if (input.isKeyDown(Input.KEY_DOWN))
		{
            /*
			tux = movingDown;
			tuxPositionY += delta * .25f;
			if (tuxPositionY > 560)
			{
				tuxPositionY -= delta * .25f;
			}
            */
		}
		if (input.isKeyDown(Input.KEY_LEFT))
		{
			tux = movingLeft;
            standing = standingLeft;
			tuxPositionX -= delta * .25f;
			if (tuxPositionX < -10) //Tux touches left
			{
				tuxPositionX += delta * .25f;
			}
		}
        else
            tux = standing;

		if (input.isKeyDown(Input.KEY_RIGHT))
		{
			tux = movingRight;
            standing = standingRight;
			tuxPositionX += delta * .25f;
			if (tuxPositionX > 770)    //Tux touches right
			{
				tuxPositionX -= delta * .25f;
			}
		}
        else
            tux = standing;
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