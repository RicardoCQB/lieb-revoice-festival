package lieb.revoice.festival;

import java.awt.geom.Rectangle2D;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.IUpdateable;
import de.gurkenlabs.litiengine.entities.Action;
import de.gurkenlabs.litiengine.entities.CollisionInfo;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityInfo;
import de.gurkenlabs.litiengine.entities.MovementInfo;
import de.gurkenlabs.litiengine.input.PlatformingMovementController;
import de.gurkenlabs.litiengine.physics.Collision;
import de.gurkenlabs.litiengine.physics.IMovementController;

@EntityInfo(width = 18, height = 18)
@MovementInfo(velocity = 100)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 16, collision = true)
public class Player extends Creature implements IUpdateable {

	public static final int MAX_ADDITIONAL_JUMPS = 2;

	private static Player instance;

	private final Jump jump;

	private int consecutiveJumps;

	private Player() {
		super("frankmars");

		// setup movement controller
	    this.addController(new PlatformingMovementController<>(this));
		// setup the player's abilities
		this.jump = new Jump(this);
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
	}

	private boolean isTouchingGround() {
		//It collides with the ground (a) of with any (b) static box.
		Rectangle2D groundCheck = new Rectangle2D.Double(
				this.getCollisionBox().getX(), this.getCollisionBox().getY(), 
				this.getCollisionBoxWidth(), this.getCollisionBoxHeight() + 1);
		
		if(groundCheck.getMaxY() > Game.physics().getBounds().getMaxX()) {
			return true;
		}

		return Game.physics().collides(groundCheck, Collision.STATIC);
	}


	}