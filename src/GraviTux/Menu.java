package GraviTux;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

@SuppressWarnings("ALL")
class Menu extends BasicGameState
{
	private Image bg, tux;
	public static UnicodeFont header, menu, body;

	public Menu()
	{
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		//Header Font (used for the word GraviTux in menu)
		header = new UnicodeFont("res/GraviTux/fonts/BRLNSDB.TTF", 90, false, false);
		header.addAsciiGlyphs();
		header.getEffects().add(new ColorEffect());  // Create a default white color effect
		header.loadGlyphs();

		//Menu Font (used for menu points)
		menu = new UnicodeFont("res/GraviTux/fonts/BRLNSR.TTF", 40, false, false);  //destination, bold, italic
		menu.addAsciiGlyphs();
		menu.getEffects().add(new ColorEffect());  // Create a default white color effect
		menu.loadGlyphs();

		//Body Font (used for credits and ingame text)
		body = new UnicodeFont("res/GraviTux/fonts/BRLNSR.TTF", 30, false, false);  //destination, bold, italic
		body.addAsciiGlyphs();
		body.getEffects().add(new ColorEffect());  // Create a default white color effect
		body.loadGlyphs();

		bg = new Image("GraviTux/menu/BG_v6.png");
		tux = new Image("GraviTux/menu/tuxMenu_30x30.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		bg.draw(0, 0);

		header.drawString(210, 70, "GraviTux");
		menu.drawString(310, 210, "new game");
		menu.drawString(310, 270, "continue");
		menu.drawString(310, 330, "highscore");
		menu.drawString(310, 390, "credits");
		menu.drawString(310, 450, "exit");

		//new game button
		if ((posX > 309 && posX < 483) && (posY > 361 && posY < 403))
		{
			tux.draw(269, 215);

			if (Mouse.isButtonDown(0))
			{
				Play.newGame();
				sbg.enterState(1);
			}
		}
		//continue
		if ((posX > 309 && posX < 450) && (posY > 301 && posY < 338))
		{
			tux.draw(269, 275);

			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
		//highscores
		if ((posX > 309 && posX < 461) && (posY > 241 && posY < 283))
		{
			tux.draw(269, 335);

			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(3);
			}
		}
		//credits
		if ((posX > 309 && posX < 414) && (posY > 181 && posY < 218))
		{
			tux.draw(269, 395);

			if (Mouse.isButtonDown(0))
			{
				sbg.enterState(2);
			}
		}
		//exit game
		if ((posX > 309 && posX < 367) && (posY > 121 && posY < 158))
		{
			tux.draw(269, 455);

			if (Mouse.isButtonDown(0))
			{
				System.exit(0);
			}
		}

		//FOR TEXT PLACEMENT ONLY!!!
/*		g.drawString("Textmasse als Hilfe zum Platzieren von Text", 10, 10);

		g.drawString("new Game breite: " + menu.getWidth("new game"), 10, 30);
		g.drawString("new Game hoehe: " + menu.getHeight("new game"), 250, 30);
		g.drawRect(210, 210+7, menu.getWidth("new game"), 51);

		g.drawString("continue breite: " + menu.getWidth("continue"), 10, 45);
		g.drawString("continue hoehe: " + menu.getHeight("continue"), 250, 45);
		g.drawRect(210, 270+7, menu.getWidth("continue"), 51);

		g.drawString("highscore breite: " + menu.getWidth("highscore"), 10, 60);
		g.drawString("highscore hoehe: " + menu.getHeight("highscore"), 250, 60);
		g.drawRect(210, 330+7, menu.getWidth("highscore"), 51);

		g.drawString("credits breite: " + menu.getWidth("credits"), 10, 75);
		g.drawString("credits hoehe: " + menu.getHeight("credits"), 250, 75);
		g.drawRect(210, 390+7, menu.getWidth("credits"), 51);

		g.drawString("exit breite: " + menu.getWidth("exit"), 10, 90);
		g.drawString("exit hoehe: " + menu.getHeight("exit"), 250, 90);
		g.drawRect(210, 450+7, menu.getWidth("exit"), 51);*/
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
	}

	@Override
	public int getID()
	{
		return 0;
	}
}