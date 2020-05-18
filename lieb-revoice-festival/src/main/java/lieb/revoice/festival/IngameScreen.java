package lieb.revoice.festival;

import java.awt.Color;
import java.awt.Graphics2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

public class IngameScreen extends GameScreen {

	public static final String SCREEN_NAME = "INGAME";
	// public static Sound MENU_MUSIC = Resources.sounds().get("menuMusic.wav");
	
	public static String mapName;

	public IngameScreen(String mapName) {
		super(SCREEN_NAME);
		IngameScreen.mapName = mapName;
	}

	@Override
	public void prepare() {
		super.prepare();	
		Game.loop().perform(1500, () -> {	      
	        Game.window().getRenderComponent().fadeIn(1500);
	        GameLogic.init();
	        Game.world().loadEnvironment(mapName);
	        
	        //Game.audio().playMusic(Resources.sounds().get("pumpkinville.ogg"));
	      });
	}

}
