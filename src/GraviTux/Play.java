package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	////deceleration of global variables

	//Image arrays used for animations
	private Image[] bottomStand, topStand, leftStand, rightStand,
			bottomWalkLeft, bottomWalkRight,
			topWalkLeft, topWalkRight,
			leftWalkUp, leftWalkDown,
			rightWalkUp, rightWalkDown;

	//Animations for tux movement
	private Animation tux, bottomStanding, bottomMovingLeft, bottomMovingRight,
			topStanding, topMovingLeft, topMovingRight,
			leftStanding, leftMovingUp, leftMovingDown,
			rightStanding, rightMovingUp, rightMovingDown;

	private TiledMap worldMap;  //Level in the background
	private boolean menu, revGravi;   //states if menu is open and if gravity is reversed
	private boolean[][] blocked, deadly, levelEnd, storm;    //2 dimensional arrays for collision detection
	private float tuxX, tuxY, vSpeed;   //tux position and falling speed
	private static final int duration = 150;   //length of the walk animation
	private static final int size = 40; //tiled size in px
	private static final int tuxSizeX = 23; //tux is illuminaticly wide
	private static final int tuxSizeY = 42; //tux size has the answer to the world, the universe and all the rest.
	private static final float gravity = 0.1f;  //tux acceleration speed when falling
	private static final float vSpeedMax = 7f;  //tux maximum falling speed
	private static final float moveSpeed = 0.25f;   //tux movement speed
	//private Sound background;

	////constructor
	public Play(int state)
	{
		menu = false;   // Menu not open
		revGravi = false;   // true, when tux walks upside down
		tuxX = 79f;   //tux start coordinates (90 = default)
		tuxY = 518f;  //518 is default
		vSpeed = 0f;   //tux current falling speed
		bottomWalkLeft = new Image[8];
		bottomWalkRight = new Image[8];
		topWalkLeft = new Image[8];
		topWalkRight = new Image[8];
		//leftWalkUp = new Image[8];
		//leftWalkDown = new Image[8];
		//rightWalkUp = new Image[8];
		//rightWalkDown = new Image[8];
	}

	////INIT METHOD
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		worldMap = new TiledMap("res/GraviTux/level/level_5.tmx");
		//background = new Sound("res/GraviTux/sounds/");
		//background.play();

		////Filling Image arrays for animation
		bottomStand = new Image[]{new Image("GraviTux/tux/Tux_stand.png")};                          //tux standing on ground
		topStand = new Image[]{new Image("GraviTux/tux/Tux_stand.png").getFlippedCopy(false, true)}; //tux standing upside down
		//leftStand =   new Image[]{new Image("GraviTux/tux/tux_stand.png")};                        //tux standing left
		//rightStand =  new Image[]{new Image("GraviTux/tux/tux_stand.png")};                        //tux standing right
		for (int i = 0; i < 8; i++)
		{
			bottomWalkLeft[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png");                             //walk left
			bottomWalkRight[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png").getFlippedCopy(true, false);//walk right
			topWalkLeft[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png").getFlippedCopy(false, true);     //walk left upside down
			topWalkRight[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png").getFlippedCopy(true, true);   //walk right upside down
			//leftWalkUp =
			//leftWalkDown =
			//rightWalkUp =
			//rightWalkDown =
		}

		////filling animation variables

		//each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		bottomStanding = new Animation(bottomStand, duration, false);
		topStanding = new Animation(topStand, duration, false);
		//leftStanding = new Animation(leftStand, duration, false);
		//rightStanding = new Animation(rightStand, duration, false);

		tux = bottomStanding; //tux looks towards the player, when the game starts

		bottomMovingLeft = new Animation(bottomWalkLeft, duration, false);
		bottomMovingRight = new Animation(bottomWalkRight, duration, false);
		topMovingLeft = new Animation(topWalkLeft, duration, false);
		topMovingRight = new Animation(topWalkRight, duration, false);
		//leftMovingUp = new Animation(leftWalkUp, duration, false);
		//leftMovingDown = new Animation(leftWalkDown, duration, false);
		//rightMovingUp = new Animation(rightWalkUp, duration, false);
		//rightMovingDown = new Animation(rightWalkDown, duration, false);

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
			////changes the animation, so tux faces left
			if (!revGravi)
			{
				tux = bottomMovingLeft;
			}
			else
			{
				tux = topMovingLeft;
			}
			if (!(isBlocked(tuxX - speed, tuxY) || isBlocked(tuxX - speed, tuxY + tuxSizeY - 1)))
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
			if (!revGravi)
			{
				tux = bottomMovingRight;
			}
			else
			{
				tux = topMovingRight;
			}
			if (!(isBlocked(tuxX + tuxSizeX + speed, tuxY) || isBlocked(tuxX + tuxSizeX + speed, tuxY + tuxSizeY - 1)))
			{
				tux.update(delta);
				tuxX = tuxX + speed;
			}
			else
			{
				tux.update(delta);
				tuxX = tuxX - ((tuxX + tuxSizeX) % size) + size - 1;
			}
		}

		/*
		////move up, when gravity is rotated by 90%
		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
		{
			if (leftGravi)
			{
				tux = leftMovingUp;
			}
			else
			{
				tux = rightMovingUp;
			}
			if (!(isBlocked(tuxX, tuxY - speed) || isBlocked(tuxX + tuxSizeX - 1, tuxY - speed)))
			{
				tux.update(delta);
				// The lower the delta the slowest the sprite will animate.
				tuxY -= speed;
			}
		} else
		////move down, when gravity is rotated by 90%
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
		{
			if (leftGravi)
			{
				tux = leftMovingDown;
			}
			else
			{
				tux = rightMovingDown;
			}
			if (!(isBlocked(tuxX, tuxY + tuxSizeY + speed) || isBlocked(tuxX + tuxSizeX - 1, tuxY + tuxSizeY + speed)))
			{
				tux.update(delta);
				tuxY += speed;
			}
		} else
		*/

		////flip Gravity, but only when touching the ground
		if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && vSpeed == 0)
		{
			///reverse gravity
			if (!revGravi)
			{
				revGravi = true;
				tux = topStanding;
			}
			else
			{
				revGravi = false;
				tux = bottomStanding;
			}
		}

		////escape key hit for in game menu
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
		if ((!(isBlocked(tuxX, tuxY + tuxSizeY + (vSpeed + gravity * delta)) || isBlocked(tuxX + tuxSizeX - 1, tuxY + tuxSizeY + (vSpeed + gravity * delta)))) && (!revGravi))
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
		else if ((!(isBlocked(tuxX, tuxY - (vSpeed + gravity * delta)) || isBlocked(tuxX + tuxSizeX - 1, tuxY - (vSpeed + gravity * delta)))) && (revGravi))
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
		if (isDeadly(tuxX, tuxY) || isDeadly(tuxX + tuxSizeX, tuxY) || isDeadly(tuxX, tuxY + tuxSizeY) || isDeadly(tuxX + tuxSizeX, tuxY + tuxSizeY))
		{
			System.out.println("YOU ARE DEAD DEAD DEAD! ... Ps. no I was not to lazy to implement a popper event jet. Stop asking questions, you are DEAD!");
		}

		////level done event
		if (isLevelEnd(tuxX + 1, tuxY) || isLevelEnd(tuxX + tuxSizeX, tuxY) || isLevelEnd(tuxX + 1, tuxY + tuxSizeY) || isLevelEnd(tuxX + tuxSizeX, tuxY + tuxSizeY))
		{
			System.out.println("YOU MADE IT, NOW OF TO THE NEXT LEVEL!");
		}

		////Storm rotation event
		if (isLevelEnd(tuxX + 1, tuxY) || isLevelEnd(tuxX + tuxSizeX, tuxY) || isLevelEnd(tuxX + 1, tuxY + tuxSizeY) || isLevelEnd(tuxX + tuxSizeX, tuxY + tuxSizeY))
		{
			System.out.println("ROTATE");
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

	////Check if collision with LevelEnd object happened
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