package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Play extends BasicGameState
{
	////deceleration of global variables

	//Image arrays used for animations
	private Image[] bottomStand, topStand, leftStand, rightStand, bottomWalkLeft, bottomWalkRight, topWalkLeft,
			topWalkRight, leftWalkUp, leftWalkDown, rightWalkUp, rightWalkDown;
	//Animations for tux movement
	private Animation tux, bottomStanding, bottomMovingLeft, bottomMovingRight, topStanding, topMovingLeft, topMovingRight,
			leftStanding, leftMovingUp, leftMovingDown, rightStanding, rightMovingUp, rightMovingDown;
	private TiledMap[] worldMap;  //Level in the background
	private int levelCurrent, levelMax;      //number of current and total levels
	private boolean menu;       //states if menu is open and if gravityAcc is reversed
	private String gravity;
	private boolean[][] blocked, deadly, levelEnd, storm;   //2 dimensional arrays for collision detection
	private float tuxX, tuxY, vSpeed;           //tux position and falling speed
	private static final int duration = 150;    //length of the walk animation
	private static final int size = 40;         //tiled size in px
	private static final int tuxSizeX = 23;     //tux is illuminaticly wide
	private static final int tuxSizeY = 42;     //tux size has the answer to the world, the universe and all the rest.
	private static final float gravityAcc = 0.1f;  //tux acceleration speed when falling
	private static final float vSpeedMax = 7f;  //tux maximum falling speed
	private static final float moveSpeed = 0.3f;   //tux movement speed
	//private Sound background;

	////constructor
	public Play(int state)
	{
		menu = false;       //Menu not open
		gravity = "bottom"; //default gravity direktion
		tuxX = 79f;         //tux start coordinates (90 = default)
		tuxY = 518f;        //518 is default
		vSpeed = 0f;        //tux current falling speed
		worldMap = new TiledMap[5];
		levelCurrent = 1;   //current level
		levelMax = 5;       //max level
		bottomStand = new Image[1]; //initialisation of arrays
		bottomWalkLeft = new Image[8];
		bottomWalkRight = new Image[8];
		topStand = new Image[1];
		topWalkLeft = new Image[8];
		topWalkRight = new Image[8];
		leftStand = new Image[1];
		leftWalkUp = new Image[8];
		leftWalkDown = new Image[8];
		rightStand = new Image[1];
		rightWalkUp = new Image[8];
		rightWalkDown = new Image[8];
	}

	////INIT METHOD
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		////loads levels
		for (int i = 0; i < levelMax; i++)
		{
			worldMap[i] = new TiledMap("res/GraviTux/level/level_0" + (i + 1) + ".tmx");
		}

		//background = new Sound("res/GraviTux/sounds/");
		//background.play();

		////Filling Image arrays for standing animation
		bottomStand[0] = new Image("GraviTux/tux/Tux_stand.png");   //tux standing on ground
		topStand[0] = bottomStand[0].getFlippedCopy(false, true);   //tux standing upside down
		leftStand[0] = bottomStand[0];                            //tux standing left
		leftStand[0].rotate(90);
		rightStand[0] = bottomStand[0];                            //tux standing right
		rightStand[0].rotate(-90);

		////Filling Image arrays for moving animation
		for (int i = 0; i < 8; i++)
		{
			bottomWalkLeft[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png"); //walk left
			bottomWalkRight[i] = bottomWalkLeft[i].getFlippedCopy(true, false);     //walk right
			topWalkLeft[i] = bottomWalkLeft[i].getFlippedCopy(false, true);         //walk left upside down
			topWalkRight[i] = bottomWalkRight[i].getFlippedCopy(false, true);       //walk right upside down
			leftWalkUp[i] = bottomWalkLeft[i];      //walk up on the left side
			leftWalkUp[i].rotate(90);
			leftWalkDown[i] = bottomWalkRight[i];   //walk down on the left side
			leftWalkDown[i].rotate(90);
			rightWalkUp[i] = bottomWalkRight[i];    //walk up on the right side
			rightWalkUp[i].rotate(-90);
			rightWalkDown[i] = bottomWalkLeft[i];    //walk down on the right side
			rightWalkDown[i].rotate(-90);
		}

		////filling animation variables

		//each animation takes array of images, duration for each image, and autoUpdate (just set to false)
		bottomStanding = new Animation(bottomStand, duration, false);
		topStanding = new Animation(topStand, duration, false);
		leftStanding = new Animation(leftStand, duration, false);
		rightStanding = new Animation(rightStand, duration, false);
		bottomMovingLeft = new Animation(bottomWalkLeft, duration, false);
		bottomMovingRight = new Animation(bottomWalkRight, duration, false);
		topMovingLeft = new Animation(topWalkLeft, duration, false);
		topMovingRight = new Animation(topWalkRight, duration, false);
		leftMovingUp = new Animation(leftWalkUp, duration, false);
		leftMovingDown = new Animation(leftWalkDown, duration, false);
		rightMovingUp = new Animation(rightWalkUp, duration, false);
		rightMovingDown = new Animation(rightWalkDown, duration, false);

		tux = bottomStanding; //tux looks towards the player, when the game starts

		//// build collision maps based on tile properties in the Tiled map
		blocked = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		deadly = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		levelEnd = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		storm = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];

		for (int xAxis = 0; xAxis < worldMap[levelCurrent].getWidth(); xAxis++)
		{
			for (int yAxis = 0; yAxis < worldMap[levelCurrent].getHeight(); yAxis++)
			{
				int tileID = worldMap[levelCurrent].getTileId(xAxis, yAxis, 0);

				////wall contact array
				String wall = worldMap[levelCurrent].getTileProperty(tileID, "blocked", "false");
				if ("true".equals(wall))
				{
					blocked[xAxis][yAxis] = true;
				}
				////deadly contact array
				String death = worldMap[levelCurrent].getTileProperty(tileID, "die", "false");
				if ("true".equals(death))
				{
					deadly[xAxis][yAxis] = true;
				}
				////level finished array
				String level = worldMap[levelCurrent].getTileProperty(tileID, "fish", "false");
				if ("true".equals(level))
				{
					levelEnd[xAxis][yAxis] = true;
				}
				////storm to rotate gravity
				String rotate = worldMap[levelCurrent].getTileProperty(tileID, "rotate", "false");
				if ("true".equals(rotate))
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
		worldMap[levelCurrent].render(0, 0); //draw the map at 0,0 to start
		tux.draw(tuxX, tuxY); //draws tux at 90, 520 (bottom left)
		//g.drawString("Tux X: " + (int) tuxX + "\nTux Y: " + (int) tuxY, 650, 50); //tux position indicator

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
			switch (gravity)
			{
				case "bottom":
					tux = bottomMovingLeft;
					break;
				case "top":
					tux = topMovingLeft;
					break;
				case "left":
					break;
				case "right":
					break;
			}
			if (!(isBlocked(tuxX - speed, tuxY, blocked) || isBlocked(tuxX - speed, tuxY + tuxSizeY - 1, blocked)))
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
			switch (gravity)
			{
				case "bottom":
					tux = bottomMovingRight;
					break;
				case "top":
					tux = topMovingRight;
					break;
				case "left":
					break;
				case "right":
					break;
			}
			if (!(isBlocked(tuxX + tuxSizeX + speed, tuxY, blocked)
					|| isBlocked(tuxX + tuxSizeX + speed, tuxY + tuxSizeY - 1, blocked)))
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
		////move up, when gravity is rotated by +/- 90%
		if (input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W))
		{
			switch (gravity)
			{
				case "bottom":
					break;
				case "top":
					break;
				case "left":
					tux = leftMovingUp;
					break;
				case "right":
					tux = rightMovingUp;
					break;
			}
			if (!(isBlocked(tuxX, tuxY - speed, blocked) || isBlocked(tuxX + tuxSizeX - 1, tuxY - speed, blocked)))
			{
				tux.update(delta);
				// The lower the delta the slowest the sprite will animate.
				tuxY -= speed;
			}
		}
		////move down, when gravity is rotated by +/- 90%
		if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S))
		{
			switch (gravity)
			{
				case "bottom":
					break;
				case "top":
					break;
				case "left":
					tux = leftMovingDown;
					break;
				case "right":
					tux = rightMovingDown;
					break;
			}
			if (!(isBlocked(tuxX, tuxY + tuxSizeY + speed, blocked)
					|| isBlocked(tuxX + tuxSizeX - 1, tuxY + tuxSizeY + speed, blocked)))
			{
				tux.update(delta);
				tuxY += speed;
			}
		}

		////flip gravity, but only when touching the ground
		if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && vSpeed == 0)
		{
			///reverse gravity
			switch (gravity)
			{
				case "bottom":
					gravity = "top";
					tux = topStanding;
					break;
				case "top":
					gravity = "bottom";
					tux = bottomStanding;
					break;
				case "left":
					gravity = "right";
					tux = rightStanding;
					break;
				case "right":
					gravity = "left";
					tux = leftStanding;
					break;
			}
		}

		////Gravity
		if ((!(isBlocked(tuxX, tuxY + tuxSizeY + (vSpeed + gravityAcc * delta), blocked)
				|| isBlocked(tuxX + tuxSizeX - 1, tuxY + tuxSizeY + (vSpeed + gravityAcc * delta), blocked))) && (gravity.equals("bottom")))
		{
			////accelerate falling
			vSpeed += gravityAcc * delta;
			////limit falling speed
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY += vSpeed;
		}
		////negative gravity
		else if ((!(isBlocked(tuxX, tuxY - (vSpeed + gravityAcc * delta), blocked)
				|| isBlocked(tuxX + tuxSizeX - 1, tuxY - (vSpeed + gravityAcc * delta), blocked))) && (gravity.equals("top")))
		{
			vSpeed += gravityAcc * delta;
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxY -= vSpeed;
		}
		////gravity set left
		else if (!(isBlocked(tuxX - (vSpeed + gravityAcc * delta), tuxY, blocked)
				|| isBlocked(tuxX - (vSpeed + gravityAcc * delta), tuxY + tuxSizeY - 1, blocked)) && (gravity.equals("left")))
		{
			vSpeed += gravityAcc * delta;
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxX -= vSpeed;
		}
		//gravity set right
		else if (!(isBlocked(tuxX + tuxSizeX + (vSpeed + gravityAcc * delta), tuxY, blocked)
				|| isBlocked(tuxX + tuxSizeX + (vSpeed + gravityAcc * delta), tuxY + tuxSizeY - 1, blocked)) && (gravity.equals("right")))
		{
			vSpeed += gravityAcc * delta;
			if (vSpeed > vSpeedMax)
			{
				vSpeed = vSpeedMax;
			}
			tux.update(delta);
			tuxX += vSpeed;
		}
		////when not falling
		else
		{
			vSpeed = 0;
		}

		////Death event
		if (isBlocked(tuxX, tuxY, deadly) || isBlocked(tuxX + tuxSizeX, tuxY, deadly)
				|| isBlocked(tuxX, tuxY + tuxSizeY, deadly) || isBlocked(tuxX + tuxSizeX, tuxY + tuxSizeY, deadly))
		{
			//System.out.println("YOU ARE DEAD DEAD DEAD! ... Ps. no I was not to lazy to implement a popper event jet. Stop asking questions, you are DEAD!");
		}

		////level done event
		if (isBlocked(tuxX + 1, tuxY, levelEnd) || isBlocked(tuxX + tuxSizeX, tuxY, levelEnd)
				|| isBlocked(tuxX + 1, tuxY + tuxSizeY, levelEnd) || isBlocked(tuxX + tuxSizeX, tuxY + tuxSizeY, levelEnd))
		{
			//System.out.println("YOU MADE IT, NOW OF TO THE NEXT LEVEL!");

			if (levelCurrent < levelMax)
			{
				levelCurrent++;    //loads new level
				tuxX = 79;          //puts tux to default position
				tuxY = 518;
				gc.reinit();
			}
		}

		////Storm rotation event
		if (isBlocked(tuxX + tuxSizeX / 2, tuxY + tuxSizeY / 2, storm))
		//if (isBlocked(tuxX + 1, tuxY, storm) || isBlocked(tuxX + tuxSizeX, tuxY, storm) || isBlocked(tuxX + 1, tuxY + tuxSizeY, storm) || isBlocked(tuxX + tuxSizeX, tuxY + tuxSizeY, storm))
		{
			//BUGY, will fix it tomorrow
			switch (gravity)
			{
				case "bottom":
					gravity = "left";
					tux = leftStanding;
					break;
				case "top":
					gravity = "right";
					tux = rightStanding;
					break;
				case "left":
					gravity = "top";
					tux = topStanding;
					break;
				case "right":
					gravity = "bottom";
					tux = bottomStanding;
					break;
			}
		}

		////escape key hit for in game menu
		if (input.isKeyDown(Input.KEY_ESCAPE))
		{
			menu = true;
		}
		////when player hit escape
		if (menu)
		{
			///continue playing
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
	}

	////check if collision happened with any object
	private boolean isBlocked(float x, float y, boolean[][] z) throws SlickException
	{
		int xBlock = (int) (x / size);
		int yBlock = (int) (y / size);

		return z[xBlock][yBlock];
	}

	////get state ID
	@Override
	public int getID()
	{
		return 1;
	}
}