package lieb.revoice.festival;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.graphics.ImageRenderer;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.sound.Sound;
import de.gurkenlabs.litiengine.util.Imaging;

public class MenuScreen extends Screen implements IUpdateable {
	public static final String SCREEN_NAME = "MENU";
	private static final BufferedImage background = Imaging.scale(Resources.images().get("startmenu.png"), Game.window().getWidth(), Game.window().getHeight());
	public static Sound MENU_MUSIC = Resources.sounds().get("menuMusic.wav");
	private Menu mainMenu;
	private String levelName;

	public MenuScreen() {
		super(SCREEN_NAME);
		this.levelName = "forest";
	}

	@Override
	public void prepare() {
		super.prepare();
		Game.loop().attach(this);
		Game.window().getRenderComponent().setBackground(Color.BLACK);
		Game.graphics().setBaseRenderScale(6f * Game.window().getResolutionScale());
		Game.audio().playMusic((MENU_MUSIC));

		this.mainMenu.setForwardMouseEvents(false);

		this.mainMenu.getCellComponents().get(0).setHovered(true);
	}

	@Override
	protected void initializeComponents() {
		super.initializeComponents();		
		final double centerX = Game.window().getResolution().getWidth() / 2.0;
		final double centerY = Game.window().getResolution().getHeight() * 1 / 2;
		final double buttonWidth = 450;

		this.mainMenu = new Menu(centerX - buttonWidth / 2, centerY * 1, buttonWidth, centerY / 2, "Play Forest","Play Tents","Play Stages", "Exit");

		Input.keyboard().onKeyReleased(event -> {
			if (this.isSuspended()) {
				return;
			}

			if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_W) {
				this.mainMenu.setCurrentSelection(Math.max(0, this.mainMenu.getCurrentSelection() - 1));
				for (ImageComponent comp : this.mainMenu.getCellComponents()) {
					comp.setHovered(false);
				}
				this.mainMenu.getCellComponents().get(this.mainMenu.getCurrentSelection()).setHovered(true);
			}

			if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) {
				this.mainMenu.setCurrentSelection(Math.min(1, this.mainMenu.getCurrentSelection() + 1));
				for (ImageComponent comp : this.mainMenu.getCellComponents()) {
					comp.setHovered(false);
				}
				this.mainMenu.getCellComponents().get(this.mainMenu.getCurrentSelection()).setHovered(true);
			}

			if (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == KeyEvent.VK_SPACE) {
				switch (this.mainMenu.getCurrentSelection()) {
				case 0:
					this.levelName = "forest";
					this.startGame();
					break;
				case 1:
					this.levelName = "tents";
					this.startGame();
					break;
				case 2:
					this.levelName = "stages";
					this.startGame();
					break;
				case 3:				
					System.exit(0);
					break;
				}
			}
		});

		this.getComponents().add(this.mainMenu);
	}

	@Override
	public void render(final Graphics2D g) {		
		ImageRenderer.render(g, background, 0, 0);
		if (Game.world().environment() != null) {
			Game.world().environment().render(g);
		}

		super.render(g);
	}

	@Override
	public void update() {
	}
	
	@Override
	  public void suspend() {
	    super.suspend();
	    Game.loop().detach(this);
	    Game.audio().stopMusic();
	  }
	
	private void startGame() {
		this.mainMenu.setEnabled(false);		
		
		Game.loop().perform(1500, () -> {
			Game.window().getRenderComponent().fadeIn(1500);
			Game.screens().add(new IngameScreen(levelName));
			Game.screens().display("INGAME");
		});
	}
	
	private void showInfo() {
		this.mainMenu.setEnabled(false);		
		
		Game.loop().perform(1500, () -> {
			Game.window().getRenderComponent().fadeIn(1500);
			Game.screens().add(new IngameScreen(levelName));
			Game.screens().display("INGAME");
		});
	}

}
