package lieb.revoice.festival;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.entities.Spawnpoint;
import de.gurkenlabs.litiengine.environment.CreatureMapObjectLoader;
import de.gurkenlabs.litiengine.graphics.Camera;
import de.gurkenlabs.litiengine.graphics.PositionLockCamera;

public class GameLogic {

	private GameLogic() {

	}

	public static void init() {

		// we'll use a camera in our game that is locked to the location of the player
		Camera camera = new PositionLockCamera(Player.instance());
		camera.setClampToMap(true);
		Game.world().setCamera(camera);

		// set a basic gravity for all levels.
		Game.world().setGravity(300);
		Game.graphics().setBaseRenderScale(0.901f);
		// add default game logic for when a level is loaded
		Game.world().addLoadedListener(e -> {

			// spawn the player instance on the spawn point with the name "enter"
			Spawnpoint enter = e.getSpawnpoint("enter");
			if (enter != null) {
				enter.spawn(Player.instance());				
			}
		});
		
		AccelerometerJump AJ = new AccelerometerJump();
		
		Game.inputLoop().attach(AJ);
	}
}
