package lieb.revoice.festival;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;

import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.screens.GameScreen;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.sound.Sound;

public class MenuScreen extends GameScreen {
	public static final String SCREEN_NAME = "MENU";
	public static Sound MENU_MUSIC = Resources.sounds().get("menuMusic.wav");
	private static ImageComponent playButton;

	public MenuScreen() {
		super(SCREEN_NAME);
	}

	public static void init() {
		Game.audio().playMusic((MENU_MUSIC));
		playButton = new ImageComponent(0,0, 20, 20);
	}
}
