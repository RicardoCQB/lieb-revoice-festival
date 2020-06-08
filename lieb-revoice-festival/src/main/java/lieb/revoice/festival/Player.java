package lieb.revoice.festival;

import java.awt.geom.Rectangle2D;

import com.fazecast.jSerialComm.SerialPort;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.EntityListener;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.entities.Trigger;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.sound.Sound;

@EntityInfo(width = 18, height = 18)
@MovementInfo(velocity = 220)
@CollisionInfo(collisionBoxWidth = 50, collisionBoxHeight = 140, collision = true)
public class Player extends Creature implements IUpdateable {

	public static final int MAX_ADDITIONAL_JUMPS = 1;

	private static Player instance;

	private final Jump jump;

	private int consecutiveJumps;

	private static SerialPort comPort;

	private char currentAcStatus;
	
	public static Sound PLAYER_JUMP = Resources.sounds().get("jump2.wav");

	private Player() {
		super("frankmars");

		// setup the player's abilities
		this.jump = new Jump(this);

		SerialPort.getCommPorts();
		comPort = SerialPort.getCommPort("COM3");
		comPort.openPort();

		currentAcStatus = '0';
	}

	public static Player instance() {
		if (instance == null) {
			instance = new Player();
		}

		return instance;
	}

	@Override
	public void update() {
		// reset the number of consecutive jumps when touching the ground
		if (this.isTouchingGround()) {
			this.consecutiveJumps = 0;
		}
		
		try {
			if (comPort.bytesAvailable() == 0) {

			} else {
				byte[] readBuffer = new byte[comPort.bytesAvailable()];
				int numRead = comPort.readBytes(readBuffer, readBuffer.length);
				currentAcStatus = (char) readBuffer[numRead - 1];
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (currentAcStatus == '1') {
			this.jump();
			currentAcStatus = '0';
		}		
	}

	@Override
	protected IMovementController createMovementController() {
		// setup movement controller
		return new PlatformingMovementController<>(this);
	}

	private boolean isTouchingGround() {
		// It collides with the ground (a) of with any (b) static box.
		Rectangle2D groundCheck = new Rectangle2D.Double(this.getCollisionBox().getX(), this.getCollisionBox().getY(),
				this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 1);

		if (groundCheck.getMaxY() > Game.physics().getBounds().getMaxX()) {
			return true;
		}

		return Game.physics().collides(groundCheck, Collision.STATIC);
	}

	/**
	 * Checks whether this instance can currently jump and then performs the
	 * <code>Jump</code> ability.
	 * <p>
	 * <i>Note that the name of this methods needs to be equal to
	 * {@link PlatformingMovementController#JUMP}} in order for the controller to be
	 * able to use this method. <br>
	 * Another option is to explicitly specify the <code>Action.name()</code>
	 * attribute on the annotation.</i>
	 * </p>
	 */
	@Action(description = "This performs the jump ability for the player's entity.")
	public void jump() {
		if (this.consecutiveJumps >= MAX_ADDITIONAL_JUMPS || !this.jump.canCast()) {
			return;
		}
		Game.audio().playSound((PLAYER_JUMP));
		this.jump.cast();
		this.consecutiveJumps++;
	}

}