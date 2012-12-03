package GraviTux;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

class Play extends BasicGameState
{
	////deceleration of global variables

	////Image arrays used for animations
	private final Image[] bottomStand, topStand, leftStand, rightStand, bottomWalkLeft, bottomWalkRight,
			topWalkLeft, topWalkRight, leftWalkUp, leftWalkDown, rightWalkUp, rightWalkDown;
	////Animations for tux movement
	public Animation tux, bottomStanding, bottomMovingLeft, bottomMovingRight, topStanding, topMovingLeft, topMovingRight,
			leftStanding, leftMovingUp, leftMovingDown, rightStanding, rightMovingUp, rightMovingDown;
	private TiledMap[] worldMap;  //Level in the background
	private int levelCurrent;   //number of current level
	private float gravitySpeed;
	public static final float gravityAcc = 0.1f;  //tux acceleration speed when falling
	public int tuxWidth, tuxHeight;
	private boolean menu;       //states if menu is open and if gravityAcc is reversed
	public boolean[][] blocked;
	private boolean[][] deadly, levelEnd, storm;   //2 dimensional arrays for collision detection
	private Timer timer;        //timer to prevent things from going too fast
	public float tuxX, tuxY;     //tux position and falling speed
	private static final int duration = 150;    //length of the walk animation
	public static final int size = 40;         //tiled size in px
	private static final int levelMax = 8;       //max level
	private static final float moveSpeed = 0.25f;   //tux movement speed
	public char gravity;       //indicates direction of gravity

	//private Sound background;
	Gravity gr;

	////constructor
	public Play()
	{
		menu = false;       //Menu not open
		gravity = 'b'; //default gravity direktion
		tuxWidth = 23;       //tux is illuminati wide
		tuxHeight = 42;       //tux size has the answer to the world, the universe and all the rest.
		tuxX = 79f;         //tux start coordinates (79 = start)
		tuxY = 418f;        //518 is default
		gravitySpeed = 0f;        //tux current falling speed
		levelCurrent = 1;   //current level (Default = 1)
		worldMap = new TiledMap[levelMax];  //array for levels
		bottomStand = new Image[1]; //initialisation of arrays
		topStand = new Image[1];
		leftStand = new Image[1];
		rightStand = new Image[1];
		bottomWalkLeft = new Image[8];
		bottomWalkRight = new Image[8];
		topWalkLeft = new Image[8];
		topWalkRight = new Image[8];
		leftWalkUp = new Image[8];
		leftWalkDown = new Image[8];
		rightWalkUp = new Image[8];
		rightWalkDown = new Image[8];
	}

	////INIT METHOD
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		for (int i = 0; i < levelMax; i++)  //loads levels
		{
			worldMap[i] = new TiledMap("res/GraviTux/level/level_" + (i + 1) + ".tmx");
		}

		//background = new Sound("res/GraviTux/sounds/");
		//background.play();

		timer = new Timer();    //timer to prevent some things from happening too fast
		gr = new Gravity();

		////Filling Image arrays for standing animation
		bottomStand[0] = new Image("GraviTux/tux/Tux_stand.png");   //tux standing on ground
		topStand[0] = bottomStand[0].getFlippedCopy(false, true);   //tux standing upside down
		leftStand[0] = new Image("GraviTux/tux/Tux_stand.png");     //tux standing left
		leftStand[0].rotate(90f);
		rightStand[0] = new Image("GraviTux/tux/Tux_stand.png");    //tux standing right
		rightStand[0].rotate(-90f);

		////filling image arrays for moving animation
		for (int i = 0; i < 8; i++)
		{
			bottomWalkLeft[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png"); //walk left
			bottomWalkRight[i] = bottomWalkLeft[i].getFlippedCopy(true, false);     //walk right
			topWalkLeft[i] = bottomWalkLeft[i].getFlippedCopy(false, true);         //walk left upside down
			topWalkRight[i] = bottomWalkRight[i].getFlippedCopy(false, true);       //walk right upside down
			leftWalkUp[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png");     //walk up on the left side
			leftWalkUp[i].rotate(90f);
			leftWalkDown[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png").getFlippedCopy(true, false);   //walk down on the left side
			leftWalkDown[i].rotate(90f);
			rightWalkUp[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png").getFlippedCopy(true, false);    //walk up on the right side
			rightWalkUp[i].rotate(-90f);
			rightWalkDown[i] = new Image("GraviTux/tux/Tux_0" + (i + 1) + ".png");   //walk down on the right side
			rightWalkDown[i].rotate(-90f);
		}

		////filling animation variables with the image arrays
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
		g.drawString("Tux X: " + (int) tuxX + "\nTux Y: " + (int) tuxY, 650, 50); //tux position indicator

		if (menu)   ////when the player presses escape
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
		Input input = gc.getInput();    //gets keyboard input
		float speed = delta * moveSpeed;    //makes speed dependent on refresh rate
		timer.addTime(delta);   //starts timer

		////move left
		if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && (gr.getGravity() == 'b' || gr.getGravity() == 't'))
		{
			gr.getGravity();
			////changes the animation, so tux faces left
			switch (gr.getGravity())
			{
				case 'b':
					tux = bottomMovingLeft;
					break;
				case 't':
					tux = topMovingLeft;
					break;
				case 'l':
					break;
				case 'r':
					break;
			}
			if (!(collision(tuxX - speed, tuxY, blocked) || collision(tuxX - speed, tuxY + tuxHeight - 1, blocked)))
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
		////move right, when gravity is bottom or top
		if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) && (gr.getGravity() == 'b' || gr.getGravity() == 't'))
		{
			switch (gr.getGravity())
			{
				case 'b':
					tux = bottomMovingRight;
					break;
				case 't':
					tux = topMovingRight;
					break;
				case 'l':
					break;
				case 'r':
					break;
			}
			if (!(collision(tuxX + tuxWidth + speed, tuxY, blocked)
					|| collision(tuxX + tuxWidth + speed, tuxY + tuxHeight - 1, blocked)))
			{
				tux.update(delta);
				tuxX = tuxX + speed;
			}
			else
			{
				tux.update(delta);
				tuxX = tuxX - ((tuxX + tuxWidth) % size) + size - 1;
			}
		}
		////move up, when gravity is left or right
		if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) && (gr.getGravity() == 'l' || gr.getGravity() == 'r'))
		{
			switch (gr.getGravity())
			{
				case 'b':
					break;
				case 't':
					break;
				case 'l':
					tux = leftMovingUp;
					break;
				case 'r':
					tux = rightMovingUp;
					break;
			}
			if (!(collision(tuxX, tuxY - speed, blocked) || collision(tuxX + tuxWidth - 1, tuxY - speed, blocked)))
			{
				tux.update(delta);
				// The lower the delta the slowest the sprite will animate.
				tuxY -= speed;
			}
			else
			{
				//tux.update(delta);
				//tuxY -= tuxY % size;
			}
		}
		////move down, when gravity is rotated by +/- 90%
		if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) && (gr.getGravity() == 'l' || gr.getGravity() == 'r'))
		{
			switch (gr.getGravity())
			{
				case 'b':
					break;
				case 't':
					break;
				case 'l':
					tux = leftMovingDown;
					break;
				case 'r':
					tux = rightMovingDown;
					break;
			}
			if (!(collision(tuxX, tuxY + tuxHeight + speed, blocked)
					|| collision(tuxX + tuxWidth - 1, tuxY + tuxHeight + speed, blocked)))
			{
				tux.update(delta);
				tuxY += speed;
			}
			else
			{
				//tux.update(delta);
				//tuxY = tuxY - ((tuxY + tuxHeight) % size) + size - 1;
			}
		}

		////flip gravity, but only when touching the ground
		if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && gravitySpeed == 0 && timer.isTimeElapsed())
		{
			gr.flipGravity();  ///reverse gravity
			timer.reset();
		}

		////Gravity bottom
		if ((!(collision(tuxX, tuxY + tuxHeight + (gravitySpeed + gravityAcc * delta), blocked)
				|| collision(tuxX + tuxWidth - 1, tuxY + tuxHeight + (gravitySpeed + gravityAcc * delta), blocked))) && gravity == 'b')
		{
			gr.fall(gravitySpeed, delta);
		}
		////gravity top
		else if ((!(collision(tuxX, tuxY - (gravitySpeed + gravityAcc * delta), blocked)
				|| collision(tuxX + tuxWidth - 1, tuxY - (gravitySpeed + gravityAcc * delta), blocked))) && gravity == 't')
		{
			////accelerate falling
			gravitySpeed += gravityAcc * delta;

			//tux.update(delta);

			switch (gravity)
			{
				case 'b':
					tuxY += gravitySpeed;
					break;
				case 't':
					tuxY -= gravitySpeed;
					break;
				case 'l':
					tuxX -= gravitySpeed;
					break;
				case 'r':
					tuxX += gravitySpeed;
					break;
			}
		}
		////gravity left
		else if (!(collision(tuxX - (gravitySpeed + gravityAcc * delta), tuxY, blocked)
				|| collision(tuxX - (gravitySpeed + gravityAcc * delta), tuxY + tuxHeight - 1, blocked)) && gravity == 'l')
		{
			gr.fall(gravitySpeed, delta);
		}
		//gravity right
		else if (!(collision(tuxX + tuxWidth + (gravitySpeed + gravityAcc * delta), tuxY, blocked)
				|| collision(tuxX + tuxWidth + (gravitySpeed + gravityAcc * delta), tuxY + tuxHeight - 1, blocked)) && gravity == 'r')
		{
			gr.fall(gravitySpeed, delta);
		}
		////when not falling
		else
		{
			gravitySpeed = 0;
		}

		////Death event
		////THE +/- 2 IS ONLY A QUICK AND DIRTY THING
		int collX = 11;
		int collY = 21;
		if (collision(tuxX + collX, tuxY + collY, deadly) || collision(tuxX + tuxWidth - collX, tuxY + collY, deadly)
				|| collision(tuxX + collX, tuxY + tuxHeight - collY, deadly)
				|| collision(tuxX + tuxWidth - collX, tuxY + tuxHeight - collY, deadly) && timer.isTimeElapsed())
		{
			tuxX = 79;          //puts tux to default position
			tuxY = 518;
			gr.setGravity('b');
			tux = bottomStanding;
			tuxWidth = tux.getWidth();
			tuxHeight = tux.getHeight();

			timer.reset();
		}

		////level done event
		if (collision(tuxX + 1, tuxY, levelEnd) || collision(tuxX + tuxWidth, tuxY, levelEnd)
				|| collision(tuxX + 1, tuxY + tuxHeight, levelEnd)
				|| collision(tuxX + tuxWidth, tuxY + tuxHeight, levelEnd) && timer.isTimeElapsed())
		{
			if (levelCurrent < levelMax)
			{
				levelCurrent++;    //loads new level
				tuxX = 79;          //puts tux to default position
				tuxY = 518;
				gr.setGravity('b');
				tux = bottomStanding;
				tuxWidth = tux.getWidth();
				tuxHeight = tux.getHeight();
				gc.reinit();
			}
			timer.reset();
		}

		////Storm rotation event
		if (collision(tuxX + tuxWidth / 2, tuxY + tuxHeight / 2, storm) && timer.isTimeElapsed())
		{
			gr.rotateGravity();
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
	public boolean collision(float x, float y, boolean[][] z)
	{
		int xBlock = 0;
		int yBlock = 0;

		switch (gravity)
		{
			case 'b':
				xBlock = (int) (x / size);
				yBlock = (int) (y / size);
				break;
			case 't':
				xBlock = (int) (x / size);
				yBlock = (int) (y / size);
				break;
			case 'l':
				xBlock = (int) ((x - 11) / size);
				yBlock = (int) ((y + 10) / size);
				break;
			case 'r':
				xBlock = (int) ((x - 11) / size);
				yBlock = (int) ((y + 10) / size);
				break;
		}
		return z[xBlock][yBlock];
	}

	private void moveTux()
	{
		//here all the redundant code will come
	}

	////get state ID
	@Override
	public int getID()
	{
		return 1;
	}
}