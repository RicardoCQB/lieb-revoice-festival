package lieb.revoice.festival;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.gui.ImageComponent;
import de.gurkenlabs.litiengine.gui.Menu;
import de.gurkenlabs.litiengine.gui.screens.Screen;
import de.gurkenlabs.litiengine.input.Input;
import de.gurkenlabs.litiengine.sound.Sound;

public class MenuScreen extends Screen implements IUpdateable {
	public static final String SCREEN_NAME = "MENU";
	public static Sound MENU_MUSIC = Resources.sounds().get("menuMusic.wav");
	private Menu mainMenu;

	public MenuScreen() {
		super(SCREEN_NAME);
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

		this.mainMenu = new Menu(centerX - buttonWidth / 2, centerY * 1.3, buttonWidth, centerY / 2, "Play", "Exit");

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
				// Game.audio().playSound("select.wav");
			}

			if (event.getKeyCode() == KeyEvent.VK_DOWN || event.getKeyCode() == KeyEvent.VK_S) {
				this.mainMenu.setCurrentSelection(Math.min(1, this.mainMenu.getCurrentSelection() + 1));
				for (ImageComponent comp : this.mainMenu.getCellComponents()) {
					comp.setHovered(false);
				}
				this.mainMenu.getCellComponents().get(this.mainMenu.getCurrentSelection()).setHovered(true);
				// Game.audio().playSound("select.wav");
			}

			if (event.getKeyCode() == KeyEvent.VK_ENTER || event.getKeyCode() == KeyEvent.VK_SPACE) {
				// Game.audio().playSound("confirm.wav");
				switch (this.mainMenu.getCurrentSelection()) {
				case 0:
					this.startGame();
					break;
				case 1:
					System.exit(0);
					break;
				}

			}
		});

		this.getComponents().add(this.mainMenu);
	}

	@Override
	public void render(final Graphics2D g) {
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
			Game.screens().add(new IngameScreen("tents"));
			Game.screens().display("INGAME");
		});
	}

}
