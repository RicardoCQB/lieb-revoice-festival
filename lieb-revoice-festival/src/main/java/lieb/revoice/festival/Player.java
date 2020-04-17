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
@MovementInfo(velocity = 70)
@CollisionInfo(collisionBoxWidth = 8, collisionBoxHeight = 16, collision = true)
public class Player extends Creature implements IUpdateable {

	public static final int MAX_ADDITIONAL_JUMPS = 2;
	
	private static Player instance;
	
	private final Jump jump;
	
	private int consecutiveJumps;
	
	private Player() {
		super("Freddie Mercury");
		
		// setup the player's abilities
	    this.jump = new Jump(this);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}