package lieb.revoice.festival;

import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

public class IngameScreen extends GameScreen implements IUpdateable{

	
	public static final String SCREEN_NAME = "INGAME";
	//public static Sound MENU_MUSIC = Resources.sounds().get("menuMusic.wav");
	
	
	public IngameScreen() {
		super(SCREEN_NAME);
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void render(final Graphics2D g) {
		if (Game.world().environment() != null) {
			Game.world().environment().render(g);
		}

		super.render(g);
	}
	
}
