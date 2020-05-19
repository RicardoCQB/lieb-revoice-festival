package lieb.revoice.festival;

import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.resources.Resources;
import de.gurkenlabs.litiengine.environment.tilemap.IMap;

public class Program {

	public static void main(String[] args) {
		
		// Set information about the game
	    Game.info().setName("Revoice Festival");
	    Game.info().setSubTitle("Find your voice");
	    Game.info().setVersion("v1.0");
	    Game.info().setWebsite("https://github.com/RicardoCQB/lieb-revoice-festival");
	    Game.info().setDescription("2D Platformer made with LITIengine,"
	    		+ " also the jumps are controled with real life movements using an acelerometer.");

	    // Initiates the game infrastructure.
	    Game.init(args);	    
	    
	    // Load data from the utiLITI game file
	    Resources.load("game.litidata");
	    
	    // Set the icon for the game
	    Game.window().setIcon(Resources.images().get("icon.png"));
	    Game.screens().add(new MenuScreen());	   
	    
	    Game.graphics().setBaseRenderScale(4.001f);
	    
	    Game.start(); 
	    
	}

}
