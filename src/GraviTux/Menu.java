package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("ALL")
class Menu extends BasicGameState
{
	private Image bg;
	public static UnicodeFont header, menu, body;

	public Menu()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		//Header Font (used for the word GraviTux in menu)
		header = new UnicodeFont("res/GraviTux/fonts/BRLNSDB.TTF", 135, false, false);
		header.addAsciiGlyphs();
		header.getEffects().add(new ColorEffect());  // Create a default white color effect
		header.loadGlyphs();

		//Menu Font (used for menu points)
		menu = new UnicodeFont("res/GraviTux/fonts/BRLNSR.TTF", 55, false, false);  //destination, bold, italic
		menu.addAsciiGlyphs();
		menu.getEffects().add(new ColorEffect());  // Create a default white color effect
		menu.loadGlyphs();

		//Body Font (used for credits and ingame text)
		body = new UnicodeFont("res/GraviTux/fonts/BRLNSR.TTF", 30, false, false);  //destination, bold, italic
		body.addAsciiGlyphs();
		body.getEffects().add(new ColorEffect());  // Create a default white color effect
		body.loadGlyphs();

		bg = new Image("GraviTux/menu/BG_v4.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//Color for the top 20px background.
		g.setColor(new Color(126, 178, 222));
		g.fillRect(0, 0, 800, 21);
		g.setColor(Color.white);

		bg.draw(0, 20);

		header.drawString(130, 70, "GraviTux");
		menu.drawString(210, 210, "new game");
		menu.drawString(210, 270, "continue");
		menu.drawString(210, 330, "highscore");
		menu.drawString(210, 390, "credits");
		menu.drawString(210, 450, "exit");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		//new game button
		if (Mouse.isButtonDown(0) && (posX > 210 && posX < 490) && (posY > 328 && posY < 430))
		{
			Play.newGame();
			sbg.enterState(1);
		}

		//continue
		if (Mouse.isButtonDown(0) && (posX > 210 && posX < 490) && (posY > 308 && posY < 370))
		{
			sbg.enterState(1);
		}

		//highscores
		if (Mouse.isButtonDown(0) && (posX > 210 && posX < 490) && (posY > 148 && posY < 310))
		{
			sbg.enterState(3);
		}

		//credits
		if (Mouse.isButtonDown(0) && (posX > 210 && posX < 490) && (posY > 188 && posY < 250))
		{
			sbg.enterState(2);
		}

		//exit game
		if (Mouse.isButtonDown(0) && (posX > 210 && posX < 490) && (posY > 128 && posY < 190))
		{
			System.exit(0);
		}
	}

	@Override
	public int getID()
	{
		return 0;
	}
}