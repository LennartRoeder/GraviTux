package GraviTux;

import org.lwjgl.input.Mouse;
import org.lwjgl.openal.AL;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

class Play extends BasicGameState
{
	private static Animation tux, bottomStanding, bottomMovingLeft, bottomMovingRight, topStanding, topMovingLeft,
			topMovingRight, leftStanding, leftMovingUp, leftMovingDown, rightStanding, rightMovingUp, rightMovingDown, snowStorm;
	private static final Image[] bottomWalkLeft = new Image[8], bottomWalkRight = new Image[8],
			topWalkLeft = new Image[8], topWalkRight = new Image[8], leftWalkUp = new Image[8],
			leftWalkDown = new Image[8], rightWalkUp = new Image[8], rightWalkDown = new Image[8];
	private static Image bg;
	private static Sound storms, win, gravitation, die;
	private static boolean[][] blocked, deadly, levelEnd, storm;   //2 dimensional arrays for collision detection
	private static char gravity;       //indicates direction of gravity
	private static int tuxWidth, tuxHeight, levelCurrent = 0;  //tux image size and number of current level
	private static Timer inputDelay, levelTime, gravityTimer;        //timer to prevent things from going too fast
	private static float tuxX, tuxY, gravitySpeed;     //tux position and falling speed
	private static final int duration = 300;    //length of the walk animation
	private static final int size = 40;         //tiled size in px
	private static final int levelMax = 16;       //max level
	private static final float moveSpeed = 0.25f;   //tux movement speed
	private static final float gravityAcc = 0.02f;  //tux acceleration speed when falling
	private static final float gravitySpeedMax = 7f;  //tux maximum falling speed
	private static final String[] highscore = new String[levelMax];
	private static final TiledMap[] worldMap = new TiledMap[levelMax];  //Level in the background
	private static Color color = Color.red;
	private static boolean dead = false;
	private static boolean reInit = false;

	////constructor
	public Play()
	{
	}

	////INIT METHOD
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		//lvl background
		bg = new Image("res/GraviTux/level/BG_v4.png");

		//lvl counter
		for (int i = 0; i < levelMax; i++)  //loads levels
		{
			worldMap[i] = new TiledMap("res/GraviTux/level/level_" + (i + 1) + ".tmx");
		}

		//sounds, for some reason ogg do not work correctly....
		storms = new Sound("res/GraviTux/sounds/eissturm.wav");
		win = new Sound("res/GraviTux/sounds/gewonnen.wav");
		gravitation = new Sound("res/GraviTux/sounds/gravitation.wav");
		die = new Sound("res/GraviTux/sounds/sterben.wav");
//		Sound bmusic = new Sound("res/GraviTux/sounds/hintergrund.wav");
//		Sound run = new Sound("res/GraviTux/sounds/laufen.wav");
//		Sound start = new Sound("res/GraviTux/sounds/start.wav");
//		Sound hole = new Sound("res/GraviTux/sounds/wasserloch.wav");

		//Filling Image arrays for standing animation
		Image[] bottomStand = {new Image("GraviTux/tux/Tux_stand.png")};   //tux standing on ground
		Image[] topStand = new Image[]{bottomStand[0].getFlippedCopy(false, true)};   //tux standing upside down
		Image[] leftStand = {new Image("GraviTux/tux/Tux_stand.png")};     //tux standing left
		leftStand[0].rotate(90f);
		Image[] rightStand = {new Image("GraviTux/tux/Tux_stand.png")};    //tux standing right
		rightStand[0].rotate(-90f);

		for (int i = 0; i < 8; i++) //filling image arrays for moving animation
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

		Image[] snowImages = new Image[]{new Image("GraviTux/snowstorm/eissturm_01.png"), new Image("GraviTux/snowstorm/eissturm_02.png"),
				new Image("GraviTux/snowstorm/eissturm_03.png"), new Image("GraviTux/snowstorm/eissturm_04.png")};
		snowStorm = new Animation(snowImages, 50, true);

		//filling animation variables with the image arrays
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

		//build collision maps based on tile properties in the Tiled map                                                                  <
		blocked = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		deadly = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		levelEnd = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];
		storm = new boolean[worldMap[levelCurrent].getWidth()][worldMap[levelCurrent].getHeight()];

		for (int xAxis = 0; xAxis < 20; xAxis++)
		{
			for (int yAxis = 0; yAxis < 15; yAxis++)
			{
				int tileID = worldMap[levelCurrent].getTileId(xAxis, yAxis, 0);

				if ("true".equals(worldMap[levelCurrent].getTileProperty(tileID, "blocked", "false")))
				{
					blocked[xAxis][yAxis] = true;   //wall contact array
				}
				if ("true".equals(worldMap[levelCurrent].getTileProperty(tileID, "die", "false")))
				{
					deadly[xAxis][yAxis] = true;    //deadly contact array
				}
				if ("true".equals(worldMap[levelCurrent].getTileProperty(tileID, "fish", "false")))
				{
					levelEnd[xAxis][yAxis] = true;  //level finished array
				}
				if ("true".equals(worldMap[levelCurrent].getTileProperty(tileID, "rotate", "false")))
				{
					storm[xAxis][yAxis] = true; //storm to rotate gravity
				}
			}
		}

		//sets gravity, animation and tux position to default
		tuxReset();
		levelTime = new Timer(1);    //timer to see how long you took for the level
	}

	////RENDER METHOD
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		if (reInit)
		{
			reInit = false;
			gc.reinit();    //makes sound not work in first level after clicking new game, once you played to level 2+
		}

		g.setBackground(new Color(126, 178, 222));

		bg.draw(0, 20);  //draw background

		worldMap[levelCurrent].render(0, 20); //draw the map at 0,0

		Menu.body.drawString(35, 10, "level " + (levelCurrent + 1));  //Level indicator
		Menu.body.drawString(350, 10, "Time: " + levelTime.getTime());  //game timer
		Menu.body.drawString(695, 10, "Menu"); //Menu Button

		//FOR TEXT PLACEMENT ONLY!!!
/*		g.drawString("Textmasse als Hilfe zum Platzieren von Text", 40, 70);

		g.drawString("Menu breite: " + Menu.body.getWidth("Menu"), 40, 90);
		g.drawString("Menu hoehe: " + Menu.body.getHeight("Menu"), 250, 90);
		g.drawRect(695, 10+2, Menu.body.getWidth("Menu"), Menu.body.getHeight("Menu"));*/

		//g.drawString("Tux X: " + (int) tuxX + "\nTux Y: " + (int) tuxY, 650, 50);   //tux position indicator

		tux.draw((int) tuxX, (int) tuxY + 20);   //draws tux at 79, 518 (bottom left)

		for (int xAxis = 0; xAxis < 20; xAxis++)    //draws animated snowstorms
		{
			for (int yAxis = 0; yAxis < 15; yAxis++)
			{
				if (storm[xAxis][yAxis])
				{
					snowStorm.draw(xAxis * size, yAxis * size + 20);
				}
			}
		}

		////level done event
		if (inputDelay.isTimeElapsed() && (collision(tuxX + 1, tuxY + 1, levelEnd)
				|| collision(tuxX + tuxWidth - 1, tuxY + 1, levelEnd)
				|| collision(tuxX + 1, tuxY + tuxHeight - 1, levelEnd)
				|| collision(tuxX + tuxWidth - 1, tuxY + tuxHeight - 1, levelEnd)))
		{
			highscore[levelCurrent] = levelTime.getTime();
			if (levelCurrent + 1 < levelMax)
			{
				levelCurrent++;    //loads new level
				win.play();        //sounds win play,
				try                //sleep (dirty...)
				{
					Thread.sleep(900);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				AL.destroy();
				gc.reinit();
			}
			inputDelay.reset();
		}

		////Storm rotation event
		if (gravityTimer.isTimeElapsed() && collision(tuxX + tuxWidth / 2, tuxY + tuxHeight / 2, storm))
		{
			rotateGravity();
			storms.play();
		}

		////Death event
		int collX = 11; //A QUICK AND DIRTY COLLISION FIX
		int collY = 21;
		if (inputDelay.isTimeElapsed() && (collision(tuxX + collX, tuxY + collY, deadly)
				|| collision(tuxX + tuxWidth - collX, tuxY + collY, deadly)
				|| collision(tuxX + collX, tuxY + tuxHeight - collY, deadly)
				|| collision(tuxX + tuxWidth - collX, tuxY + tuxHeight - collY, deadly)))
		{
			dead = true;
//			die.play(); //sounds sterben, sleep
//			try
//			{
//				Thread.sleep(500);
//			}
//			catch (InterruptedException e)
//			{
//				e.printStackTrace();
//			}
		}
		//death fade
		if (dead)
		{
			g.setColor(color);
			g.fillRect(0, 0, 800, 620);
			g.setColor(Color.white);
		}
	}

	////UPDATE METHOD
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		Input input = gc.getInput();    //gets keyboard input
		inputDelay.addTime(delta);   //updates timer
		levelTime.addTime(delta);
		gravityTimer.addTime(delta);

		float fallingSpeed = gravitySpeed + gravityAcc * delta; //same for falling, but with acceleration

		if (dead) // death fade
		{
			color.a -= delta * (1.0f / 2000);

			tuxReset(); //resets tux

			if (color.a < 0)
			{
				color.a = 1;
				dead = false;
			}
		}

		if (input != null)
		{
			float movingSpeed = moveSpeed * delta;    //makes movement speed dependent on refresh rate

			////move left, when gravity is bottom or top
			if ((input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_A)) && (gravity == 'b' || gravity == 't'))
			{
				setAnimation(input);        //changes the animation, so tux faces left
				moveTux(-movingSpeed, 0, delta);   //moves tux
			}
			////move right, when gravity is bottom or top
			if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)) && (gravity == 'b' || gravity == 't'))
			{
				setAnimation(input);
				moveTux(movingSpeed, 0, delta);
			}
			////move up, when gravity is rotated by +/- 90%
			if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_W)) && (gravity == 'l' || gravity == 'r'))
			{
				setAnimation(input);
				moveTux(0, -movingSpeed, delta);
			}
			////move down, when gravity is rotated by +/- 90%
			if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)) && (gravity == 'l' || gravity == 'r'))
			{
				setAnimation(input);
				moveTux(0, movingSpeed, delta);
			}

			////flip gravity, but only when touching the ground
			if ((input.isKeyDown(Input.KEY_SPACE) || input.isKeyDown(Input.KEY_X)) && gravitySpeed == 0 && inputDelay.isTimeElapsed())
			{
				flipGravity();  ///reverse gravity
				gravitation.play();
			}

			//reset tux
			if (input.isKeyDown(Input.KEY_R))
			{
				tuxReset(); //resets tux
			}
			////escape key hit for game menu
			else if (input.isKeyDown(Input.KEY_ESCAPE) || (((posX > 695 && posX < 764) && (posY > 579 && posY < 609)) && Mouse.isButtonDown(0)))
			{
				sbg.enterState(0);
				try
				{
					Thread.sleep(250);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}

		////Gravity bottom
		if (gravity == 'b' && !(collision(tuxX + 2, tuxY + tuxHeight + fallingSpeed - 1, blocked)
				|| collision(tuxX + tuxWidth - 2, tuxY + tuxHeight + fallingSpeed - 1, blocked)))
		{
			fall(delta);
		}
		////gravity top
		else if (gravity == 't' && !(collision(tuxX + 2, tuxY - fallingSpeed, blocked)
				|| collision(tuxX + tuxWidth - 2, tuxY - fallingSpeed, blocked)))
		{
			fall(delta);
		}
		////gravity left
		else if (gravity == 'l' && !(collision(tuxX - fallingSpeed, tuxY + 2, blocked)
				|| collision(tuxX - fallingSpeed, tuxY + tuxHeight - 2, blocked)))
		{
			fall(delta);
		}
		//gravity right
		else if (gravity == 'r' && !(collision(tuxX + tuxWidth + fallingSpeed, tuxY + 2, blocked)
				|| collision(tuxX + tuxWidth + fallingSpeed, tuxY + tuxHeight - 2, blocked)))
		{
			fall(delta);
		}
		else    //when not falling
		{
			gravitySpeed = 0;
		}
	}

	////check if collision happened with any object
	private boolean collision(float x, float y, boolean[][] z)
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

	////changes tux animation
	private void setAnimation(Input input)
	{
		switch (gravity)    //change walking animation
		{
			case 'b':
				if (input.isKeyDown(Input.KEY_LEFT) || (input.isKeyDown(Input.KEY_A)))
				{
					tux = bottomMovingLeft;
				}
				if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)))
				{
					tux = bottomMovingRight;
				}
				break;
			case 't':
				if (input.isKeyDown(Input.KEY_LEFT) || (input.isKeyDown(Input.KEY_A)))
				{
					tux = topMovingLeft;
				}
				if ((input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_D)))
				{
					tux = topMovingRight;
				}
				break;
			case 'l':
				if (input.isKeyDown(Input.KEY_UP) || (input.isKeyDown(Input.KEY_W)))
				{
					tux = leftMovingUp;
				}
				if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)))
				{
					tux = leftMovingDown;
				}
				break;
			case 'r':
				if (input.isKeyDown(Input.KEY_UP) || (input.isKeyDown(Input.KEY_W)))
				{
					tux = rightMovingUp;
				}
				if ((input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_S)))
				{
					tux = rightMovingDown;
				}
				break;
		}
	}

	////moves tux
	private void moveTux(float x, float y, int delta)
	{
		int offsetX = 0;    //offset for moving right (usually 23)
		int offsetY = 0;    //offset for moving down (usually 42)
		int offsetSize = 0; //offset for collision right. It equals block size (40)

		if (x > 0)  //enables offset for moving right
		{
			offsetX = tuxWidth;
			offsetSize = 39;
		}

		if (y > 0)  //enables offset for moving down
		{
			offsetY = tuxHeight;
		}

		//tux moves left or right
		if (!(collision(tuxX + offsetX + x, tuxY, blocked) || collision(tuxX + offsetX + x, tuxY + tuxHeight - 1, blocked)))
		{
			tux.update(delta);
			tuxX += x;
		}
		else
		{
			tux.update(delta);
			tuxX = tuxX - ((tuxX + offsetX) % size) + offsetSize;
		}

		//tux moves up or down
		if (!(collision(tuxX, tuxY + offsetY + y, blocked) || collision(tuxX + tuxWidth - 1, tuxY + offsetY + y, blocked)))
		{
			tux.update(delta);
			tuxY += y;
		}
	}

	////flips gravity
	private void flipGravity()
	{
		switch (gravity)
		{
			case 'b':
				gravity = 't';
				tux = topStanding;
				break;
			case 't':
				gravity = 'b';
				tux = bottomStanding;
				break;
			case 'l':
				gravity = 'r';
				tux = rightStanding;
				break;
			case 'r':
				gravity = 'l';
				tux = leftStanding;
				break;
		}
		inputDelay.reset();
	}

	////rotates gravity by 90 degree
	private void rotateGravity()
	{
		int tmpSize = tuxWidth;

		switch (gravity)
		{
			case 'b':
				tuxY += 20;
				gravity = 'l';
				tux = leftStanding;
				//noinspection SuspiciousNameCombination
				tuxWidth = tuxHeight; //rotates the image dimensions when gravity is rotated
				tuxHeight = tmpSize;
				break;
			case 't':
				tuxY -= 20;
				gravity = 'r';
				tux = rightStanding;
				//noinspection SuspiciousNameCombination
				tuxWidth = tuxHeight;
				tuxHeight = tmpSize;
				break;
			case 'l':
				tuxX -= 20;
				gravity = 't';
				tux = topStanding;
				//noinspection SuspiciousNameCombination
				tuxWidth = tuxHeight;
				tuxHeight = tmpSize;
				break;
			case 'r':
				tuxX += 20;
				gravity = 'b';
				tux = bottomStanding;
				//noinspection SuspiciousNameCombination
				tuxWidth = tuxHeight;
				tuxHeight = tmpSize;
				break;
		}
		gravityTimer.reset();
	}

	////handles falling
	private void fall(int delta)
	{
		////accelerate falling
		if (!dead)
		{
			gravitySpeed += gravityAcc * delta;
		}
		else
		{
			gravitySpeed = 0;
		}

		////limit falling speed
		if (gravitySpeed > gravitySpeedMax)
		{
			gravitySpeed = gravitySpeedMax;
		}

		switch (gravity)
		{
			case 'b':
				tux.update(delta);
				tuxY += gravitySpeed;
				break;
			case 't':
				tux.update(delta);
				tuxY -= gravitySpeed;
				break;
			case 'l':
				tux.update(delta);
				tuxX -= gravitySpeed;
				break;
			case 'r':
				tux.update(delta);
				tuxX += gravitySpeed;
				break;
		}
	}

	//resets tux
	private static void tuxReset()
	{
		tuxWidth = 23;       //tux is illuminati wide
		tuxHeight = 42;       //tux size has the answer to the world, the universe and all the rest.
		tuxX = 79;     //tux start coordinates (79 = start)
		tuxY = 518;    //518 is default
		tux = bottomStanding; //tux looks towards the player, when the game starts
		gravity = 'b';  //default gravity direktion
		gravitySpeed = 0f;  //tux current falling speed

		inputDelay = new Timer(300);    //timer to prevent some things from happening too fast
		gravityTimer = new Timer(300); //for proper gravity rotation
	}

	//for new level button in menu
	public static void newGame()
	{
		if (levelCurrent > 0)
		{
			reInit = true;
		}
		levelCurrent = 0;
		tuxReset();
	}

	//for highscore page
	public static String getHighscore(int level)
	{
		return highscore[level];
	}

	//for highscore page
	public static int getLevelMax()
	{
		return levelMax;
	}

	////get state ID
	@Override
	public int getID()
	{
		return 1;
	}
}