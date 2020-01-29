package ch.epfl.cs107.play.game.actor.bike.levels;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Seesaw;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class LevelHilly extends Level {

	/**
	 * Creates a new Level
	 * @param game the game in which the level is created
	 */
	public LevelHilly(ActorGame game) {
		super(game);
	}
	
	@Override
	public void createAllActors() {
		ActorGame game = super.getOwner();
		
		Polyline terrainShape = new Polyline(
				-40.0f, -100.0f,
				-40.f, 100.f,
				-20.f, 100.f,
				-20.f, 0.f,
				5.0f, 0.0f,
				8.0f, 1.0f,
				13.0f, 1.0f,
				20.0f, 3.f,
				21.0f, 3.f,
				30.0f, 0.0f,
				40.0f, -5.0f,
				47.8f, -5.f,
				48.f, -4.8f,
				48.2f, -5.f,
				55.0f, -5.0f,
				60.0f, -4.0f,
				70.0f, 0.0f,
				200.f, -200.f,
				200.f, 200.f,
				220.f, 200.f,
				220.f, -100.f);
		
		Polyline slippyTerrainShape = new Polyline(
				13.0f, 1.01f,
				20.0f, 3.01f,
				21.0f, 3.01f,
				30.0f, 0.01f,
				40.0f, -4.99f);
		
		game.addActor(new Terrain(game, terrainShape));
		game.addActor(new Terrain(game, slippyTerrainShape, 0.f, Color.CYAN));
		
		Bike bike = new Bike(game, ((BikeGame)getOwner()).getInitialBikePosition(), 0.5f, "wheel.2.png");
		game.addActor(bike);
		game.setViewCandidate(bike);
		
		//3 regular crates
		game.addActor(new Crate(game, false, new Vector(5.0f, 5.0f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(6.2f, 7.0f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(7.0f, 5.5f), 1.f, 1.f, "box.4.png"));
		
		//Seesaw
		game.addActor(new Seesaw(game, new Vector(48.f, -4.f), 5.f, 0.1f));
		game.addActor(new Crate(game, false, new Vector(46.f, -3.5f), 0.1f, 0.1f, "box.4.png"));
		
		//Finish line
		game.addActor(new Finish(game, true, new Vector(71.f, 1.f), 0.7f, "flag.red.png"));
	}

}
