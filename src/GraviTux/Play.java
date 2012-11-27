package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	private Animation tux, movingUp, movingDown, movingLeft, movingRight, standing; //animations, tux will be set to one
	private TiledMap worldMap;  //Level in the background
	private boolean quit, revGravi;   //states if game is running
	private boolean[][] blocked;    //2 dimensional array for collision detection
	private float tuxX, tuxY, vSpeed;   //tux position and falling speed
	private static final int duration = 150;   //length of the walk animation
	private static final int size = 40; //tiled size
	private static final float gravity = 0.1f;  //tux acceleration speed when falling
	private static final float vSpeedMax = 7f;  //tux maximum falling speed
	private static final float moveSpeed = 0.3f;   //tux movement speed

	public Play(int state)
	{
		quit = false;   // Menu not open
		revGravi = false;   // true, when tux walks upside down
		tuxX = 79f;   //tux start coordinates (90 = default)
		tuxY = 520f;  //520 is default
		vSpeed = 0f;   //tux current falling speed
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		worldMap = new TiledMap("res/GraviTux/level/level_2.tmx");

		//Image[] walkUp = {new Image("res/buckysBack.png"), new Image("res/buckysBack.png")}; //these are the images to be used in the "walkUp" animation
		//Image[] walkDown = {new Image("res/buckysFront.png"), new Image("res/buckysFront.png")};

		//Image[] walkLeft = {new Image("GraviTux/tux/Tux_links01.png"), new Image("GraviTux/tux/Tux_links02.png"), new Image("GraviTux/tux/Tux_links03.png"), new Image("GraviTux/tux/Tux_links04.png")};
		//Image[] walkRight = {new Image("GraviTux/tux/Tux_01.png"), new Image("GraviTux/tux/Tux_02.png"), new Image("GraviTux/tux/Tux_03.png"), new Image("GraviTux/tux/Tux_04.png")};
		//Image[] stand = {new Image("GraviTux/tux/tux_standing.png")};

		Image[] walkLeft = {new Image("GraviTux/block_v1_pureblue.png")};
		Image[] walkRight = {new Image("GraviTux/block_v1_pureblue.png")};
		Image[] stand = {new Image("GraviTux/block_v1_pureblue.png")};

		//movingUp = new Animation(walkUp, duration, false); //each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		//movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, false);
		movingRight = new Animation(walkRight, duration, false);
		standing = new Animation(stand, duration, false);

		tux = standing; //tux looks towards the player, when the game starts

		// build a collision map based on tile properties in the TileD map
		blocked = new boolean[worldMap.getWidth()][worldMap.getHeight()];

		for (int xAxis = 0; xAxis < worldMap.getWidth(); xAxis++)
		{
			for (int yAxis = 0; yAxis < worldMap.getHeight(); yAxis++)
			{
				int tileID = worldMap.getTileId(xAxis, yAxis, 0);
				String value = worldMap.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(value))
				{
					blocked[xAxis][yAxis] = true;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		worldMap.render(0, 0); //draw the map at 0,0 to start
		tux.draw(tuxX, tuxY); //draws tux at 90, 520 (unten links)
		g.drawString("Tux X: " + (int) tuxX + "\nTux Y: " + (int) tuxY, 650, 50); //indicator to see where tux is in his world

		//when they press escape
		if (quit)
		{
			g.drawString("Weiter spielen (S)", 324, 200);
			g.drawString("Hauptmenü (M)", 324, 250);
			g.drawString("Spiel beenden (Q)", 324, 300);
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		float speed = delta * moveSpeed;

		/*
		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
		{
			tux = movingUp;
			if (!(isBlocked(tuxX, tuxY - speed) || isBlocked(tuxX + size - 1, tuxY - speed)))
			{
				tux.update(delta);
				// The lower the delta the slowest the sprite will animate.
				tuxY -= speed;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
		{
			tux = movingDown;
			if (!(isBlocked(tuxX, tuxY + size + speed) || isBlocked(tuxX + size - 1, tuxY + size + speed)))
			{
				tux.update(delta);
				tuxY += speed;
			}
		} else
		*/
		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
		{
			tux = movingLeft;
			if (!(isBlocked(tuxX - speed, tuxY) || isBlocked(tuxX - speed, tuxY + size - 1)))
			{
				tux.update(delta);
				tuxX = tuxX - speed;
			}
			else
			{
				tux.update(delta);
				tuxX -= tuxX % size;
			}
		}
		else if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
		{
			tux = movingRight;
			if (!(isBlocked(tuxX + size + speed, tuxY) || isBlocked(tuxX + size + speed, tuxY + size - 1)))
			{
				tux.update(delta);
				tuxX = tuxX + speed;
			}
			else
			{
				tux.update(delta);
				tuxX = tuxX - (tuxX % size) + size - 1;
			}
		}
		//tux changes Gravity, but only when he touches the ground
		else if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && vSpeed == 0)
		{
			if (!revGravi)
			{
				revGravi = true;
			}
			else
			{
				revGravi = false;
			}
		}
		else
		{
			tux = standing;
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

		//Gravity
		if ((!(isBlocked(tuxX, tuxY + size + (vSpeed + gravity * delta)) || isBlocked(tuxX + size - 1, tuxY + size + (vSpeed + gravity * delta)))) && (!revGravi))
		{
			//accelerate falling
			vSpeed += gravity * delta;
			//max falling speed
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY += vSpeed;
		}
		else if ((!(isBlocked(tuxX, tuxY - (vSpeed + gravity * delta)) || isBlocked(tuxX + size - 1, tuxY - (vSpeed + gravity * delta)))) && (revGravi))
		{
			//accelerate falling
			vSpeed += gravity * delta;
			//max falling speed
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY -= vSpeed;
		}
		else
		{
			vSpeed = 0;
		}
	}

	private boolean isBlocked(float x, float y) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return blocked[xBlock][yBlock];
	}

	@Override
	public int getID()
	{
		return 1;
	}
}