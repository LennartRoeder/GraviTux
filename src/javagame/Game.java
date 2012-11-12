package javagame;

/**
 * Created with IntelliJ IDEA.
 * User: lennart
 * Date: 11/12/12
 * Time: 7:39 PM
 * To change this template use File | Settings | File Templates.
 */


 //http://www.tnbforum.com/viewtopic.php?f=119&t=24196

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame
{
	public static final String gamename= "Ham Blaster!";
	public static final int menu = 0;
	public static final int play = 1;

	public Game(String gamename)
	{
		super(gamename);
		this.addState(new Menu(menu));
		this.addState(new Menu(play));
	}

	@Override
	public void initStatesList(GameContainer gameContainer) throws SlickException
	{
		//To change body of implemented methods use File | Settings | File Templates.
	}

	public static void main(String[] args)
	{
		System.out.println("Hello World!");
	}
}
