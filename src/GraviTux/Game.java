package GraviTux;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

class Game extends StateBasedGame
{
	private static final String gamename = "GraviTux";
	private static final int menu = 0;
	private static final int play = 1;

	private Game()
	{
		super(Game.gamename);
		addState(new Menu());
		addState(new Play());
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		getState(menu).init(gc, this);
		getState(play).init(gc, this);
		gc.setTargetFrameRate(60);
		getContainer().setShowFPS(false);
		enterState(menu);
	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer appGc;
		appGc = new AppGameContainer(new Game());
		appGc.setDisplayMode(800, 600, false);
		appGc.start();
	}
}