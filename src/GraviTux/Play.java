package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	////deceleration of global variables
	private Animation tux, movingUp, movingDown, movingLeft, movingRight, standing; //walk animations
	private Image[] walkLeft, walkRight, walkUp, walkDown, stand;   //Image arrays used for animations
	private TiledMap worldMap;  //Level in the background
	private boolean menu, revGravi;   //states if menu is open and if gravity is reversed
	private boolean[][] blocked, deadly, levelEnd, storm;    //2 dimensional arrays for collision detection
	private float tuxX, tuxY, vSpeed;   //tux position and falling speed
	private static final int duration = 150;   //length of the walk animation
	private static final int size = 40; //tiled size in px
	private static final float gravity = 0.1f;  //tux acceleration speed when falling
	private static final float vSpeedMax = 7f;  //tux maximum falling speed
	private static final float moveSpeed = 0.3f;   //tux movement speed
	//private Sound background;

	////constructor
	public Play(int state)
	{
		menu = false;   // Menu not open
		revGravi = false;   // true, when tux walks upside down
		tuxX = 79f;   //tux start coordinates (90 = default)
		tuxY = 520f;  //520 is default
		vSpeed = 0f;   //tux current falling speed
	}

	////INIT METHOD
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		worldMap = new TiledMap("res/GraviTux/level/level_5.tmx");
		//background = new Sound("res/GraviTux/sounds/");
		//background.play();

		////Filling Image arrays for animation
		//walkUp = new Image[]{new Image("res/buckysBack.png"), new Image("res/buckysBack.png")}; //these are the images to be used in the "walkUp" animation
		//walkDown = new Image[]{new Image("res/buckysFront.png"), new Image("res/buckysFront.png")};
		walkLeft = new Image[]{new Image("GraviTux/tux/Tux_links01.png"), new Image("GraviTux/tux/Tux_links02.png"), new Image("GraviTux/tux/Tux_links03.png"), new Image("GraviTux/tux/Tux_links04.png")};
		walkRight = new Image[]{new Image("GraviTux/tux/Tux_01.png"), new Image("GraviTux/tux/Tux_02.png"), new Image("GraviTux/tux/Tux_03.png"), new Image("GraviTux/tux/Tux_04.png")};
		stand = new Image[]{new Image("GraviTux/tux/tux_standing.png")};

		////filling animation variables
		//movingUp = new Animation(walkUp, duration, false); //each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		//movingDown = new Animation(walkDown, duration, false);
		movingLeft = new Animation(walkLeft, duration, false);
		movingRight = new Animation(walkRight, duration, false);
		standing = new Animation(stand, duration, false);
		tux = standing; //tux looks towards the player, when the game starts

		//// build collision maps based on tile properties in the Tiled map
		blocked = new boolean[worldMap.getWidth()][worldMap.getHeight()];
		deadly = new boolean[worldMap.getWidth()][worldMap.getHeight()];
		levelEnd = new boolean[worldMap.getWidth()][worldMap.getHeight()];
		storm = new boolean[worldMap.getWidth()][worldMap.getHeight()];

		for (int xAxis = 0; xAxis < worldMap.getWidth(); xAxis++)
		{
			for (int yAxis = 0; yAxis < worldMap.getHeight(); yAxis++)
			{
				int tileID = worldMap.getTileId(xAxis, yAxis, 0);

				////wall contact array
				String wall = worldMap.getTileProperty(tileID, "blocked", "false");
				if ("true".equals(wall))
				{
					blocked[xAxis][yAxis] = true;
				}
				////deadly contact array
				String death = worldMap.getTileProperty(tileID, "die", "false");
				if ("true".equals(death))
				{
					deadly[xAxis][yAxis] = true;
				}
				////level finished array
				String level = worldMap.getTileProperty(tileID, "fish", "false");
				if ("true".equals(level))
				{
					levelEnd[xAxis][yAxis] = true;
				}
				////storm to rotate gravity
				String rotate = worldMap.getTileProperty(tileID, "rotate", "false");
				if ("true".equals(level))
				{
					storm[xAxis][yAxis] = true;
				}
			}
		}
	}

	////RENDER METHOD
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		worldMap.render(0, 0); //draw the map at 0,0 to start
		tux.draw(tuxX, tuxY); //draws tux at 90, 520 (bottom left)
		g.drawString("Tux X: " + (int) tuxX + "\nTux Y: " + (int) tuxY, 650, 50); //indicator to see where tux is in his world

		////when the player presses escape
		if (menu)
		{
			g.drawString("Weiter spielen (S)", 324, 200);
			g.drawString("Hauptmenü (M)", 324, 250);
			g.drawString("Spiel beenden (Q)", 324, 300);
		}
	}

	////UPDATE METHOD
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		Input input = gc.getInput();
		float speed = delta * moveSpeed;

		////move left
		if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A))
		{
			tux = movingLeft;   //changes the animation, so tux faces left
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
		////move right
		if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D))
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

		/*
	////move up, when gravity is rotated by 90%
		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
		{
			tux = movingUp;
			if (!(isBlocked(tuxX, tuxY - speed) || isBlocked(tuxX + size - 1, tuxY - speed)))
			{
				tux.update(delta);
				// The lower the delta the slowest the sprite will animate.
				tuxY -= speed;
			}
		} else
	////move down, when gravity is rotated by 90%
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
		{
			tux = movingDown;
			if (!(isBlocked(tuxX, tuxY + size + speed) || isBlocked(tuxX + size - 1, tuxY + size + speed)))
			{
				tux.update(delta);
				tuxY += speed;
			}
		} else
		*/

		////flip Gravity, but only when touching the ground
		if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && vSpeed == 0)
		{
			tux = standing;

			///reverse gravity
			if (!revGravi)
			{
				revGravi = true;
				////is supposed to flip images, worked once
				stand[0] = stand[0].getFlippedCopy(false, true);
				walkLeft[0].getFlippedCopy(false, true);
				walkLeft[1].getFlippedCopy(false, true);
				walkLeft[2].getFlippedCopy(false, true);
				walkLeft[3].getFlippedCopy(false, true);
				walkRight[0].getFlippedCopy(false, true);
				walkRight[1].getFlippedCopy(false, true);
				walkRight[2].getFlippedCopy(false, true);
				walkRight[3].getFlippedCopy(false, true);
			}

			else
			{
				revGravi = false;
				////this is supposed to flip images back
				stand[0] = stand[0].getFlippedCopy(false, false);
				walkLeft[0].getFlippedCopy(false, false);
				walkLeft[1].getFlippedCopy(false, false);
				walkLeft[2].getFlippedCopy(false, false);
				walkLeft[3].getFlippedCopy(false, false);
				walkRight[0].getFlippedCopy(false, false);
				walkRight[1].getFlippedCopy(false, false);
				walkRight[2].getFlippedCopy(false, false);
				walkRight[3].getFlippedCopy(false, false);
			}
		}

		////escape key hit for ingame menu
		if (input.isKeyDown(Input.KEY_ESCAPE))
		{
			menu = true;
		}
		////when player hit escape
		if (menu)
		{///continue playing
			if (input.isKeyDown(Input.KEY_S))
			{
				menu = false;
			}
			////open menu
			if (input.isKeyDown(Input.KEY_M))
			{
				sbg.enterState(0);
				menu = false;
				try
				{
					Thread.sleep(250);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			////quit game
			if (input.isKeyDown(Input.KEY_Q))
			{
				System.exit(0);
			}
		}

		////Gravity
		if ((!(isBlocked(tuxX, tuxY + size + (vSpeed + gravity * delta)) || isBlocked(tuxX + size - 1, tuxY + size + (vSpeed + gravity * delta)))) && (!revGravi))
		{
			////accelerate falling
			vSpeed += gravity * delta;
			////limit falling speed
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY += vSpeed;
		}
		////negative gravity
		else if ((!(isBlocked(tuxX, tuxY - (vSpeed + gravity * delta)) || isBlocked(tuxX + size - 1, tuxY - (vSpeed + gravity * delta)))) && (revGravi))
		{
			////accelerate falling
			vSpeed += gravity * delta;
			////limit falling speed
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY -= vSpeed;
		}
		////when not falling
		else
		{
			vSpeed = 0;
		}

		////Death event
		if (isDeadly(tuxX, tuxY) || isDeadly(tuxX + size, tuxY) || isDeadly(tuxX, tuxY + size) || isDeadly(tuxX + size, tuxY + size))
		{
			System.out.println("YOU ARE DEAD DEAD DEAD! ... Ps. no I was not to lazy to implement a popper event jet. Stop asking questions, you are DEAD!");
		}

		////level done event
		if (isLevelEnd(tuxX + 1, tuxY) || isLevelEnd(tuxX + size, tuxY) || isLevelEnd(tuxX + 1, tuxY + size) || isLevelEnd(tuxX + size, tuxY + size))
		{
			System.out.println("YOU MADE IT, NOW OF TO THE NEXT LEVEL!");
		}
	}

	////check if collision with wall happened
	private boolean isBlocked(float x, float y) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return blocked[xBlock][yBlock];
	}

	////Check if collision with deadly object happened
	private boolean isDeadly(float x, float y) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return deadly[xBlock][yBlock];
	}

	////Check if collision with deadly object happened
	private boolean isLevelEnd(float x, float y) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return levelEnd[xBlock][yBlock];
	}

	////Check if collision with storm happened
	private boolean isStorm(float x, float y) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return storm[xBlock][yBlock];
	}

	////get state ID
	@Override
	public int getID()
	{
		return 1;
	}
}