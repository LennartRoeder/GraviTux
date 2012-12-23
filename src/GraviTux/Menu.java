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
		g.setBackground(new Color(142, 150, 217));

		bg.draw(0, 20);

		header.drawString(130, 70, "GraviTux");
		menu.drawString(210, 210, "new game");
		menu.drawString(210, 270, "continue");
		menu.drawString(210, 330, "highscore");
		menu.drawString(210, 390, "credits");
		menu.drawString(210, 450, "exit");

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
		int posX = Mouse.getX();
		int posY = Mouse.getY();

		//new game button
		if (Mouse.isButtonDown(0) && (posX > 209 && posX < 451) && (posY > 351 && posY < 404))
		{
			Play.newGame();
			sbg.enterState(1);
		}

		//continue
		if (Mouse.isButtonDown(0) && (posX > 209 && posX < 406) && (posY > 291 && posY < 344))
		{
			sbg.enterState(1);
		}

		//highscores
		if (Mouse.isButtonDown(0) && (posX > 209 && posX < 419) && (posY > 231 && posY < 284))
		{
			sbg.enterState(3);
		}

		//credits
		if (Mouse.isButtonDown(0) && (posX > 209 && posX < 355) && (posY > 171 && posY < 224))
		{
			sbg.enterState(2);
		}

		//exit game
		if (Mouse.isButtonDown(0) && (posX > 209 && posX < 291) && (posY > 111 && posY < 164))
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