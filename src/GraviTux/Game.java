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

	private Game(String gamename)
	{
		super(Game.gamename);
		this.addState(new Menu(menu));
		this.addState(new Play());
	}

	public void initStatesList(GameContainer gc) throws SlickException
	{
		this.getState(menu).init(gc, this);
		this.getState(play).init(gc, this);
		this.enterState(play);
	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer appgc;
		appgc = new AppGameContainer(new Game(gamename));
		appgc.setDisplayMode(800, 600, false);
		appgc.setTargetFrameRate(60);
		appgc.start();
	}
}