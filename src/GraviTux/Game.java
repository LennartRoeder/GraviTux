package GraviTux;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame
{
	private static final String gamename = "GraviTux";
	private static final int menu = 0;
	private static final int play = 1;
	private static final int credits = 2;
	private static final int highscores = 3;

	private Game()
	{
		super(Game.gamename);
		addState(new Menu());
		addState(new Play());
		addState(new Credits());
		addState(new Highscores());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		getState(menu).init(gc, this);
		getState(play).init(gc, this);
		getState(credits).init(gc, this);
		getState(highscores).init(gc, this);
		getContainer().setShowFPS(false);
//		enterState(highscores);   //set to menu when done!
	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer appGc;
		appGc = new AppGameContainer(new Game());
		appGc.setDisplayMode(800, 620, false);
		appGc.setTargetFrameRate(60);
		appGc.setVSync(true);
		appGc.start();
	}
}