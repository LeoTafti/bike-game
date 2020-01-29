package ch.epfl.cs107.play.game.actor.bike.levels;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.general.Seesaw;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class LevelFlat extends Level {

	/**
	 * Creates a new Level
	 * @param game the game in which the level is created
	 */
	public LevelFlat(ActorGame game) {
		super(game);
	}
	
	@Override
	public void createAllActors() {
		ActorGame game = super.getOwner();
		
		Polyline shape = new Polyline(
				-1000.f, -500.f,
				-1000.f, 0.f,
				4.5f, 0.f,
				5.f, 0.2f,
				5.5f, 0.f,
				1000.f, 0.f,
				1000.f, -500.f);
		
		game.addActor(new Terrain(game, shape));
		
		Bike bike = new Bike(game, ((BikeGame)getOwner()).getInitialBikePosition(), 0.5f, "wheel.2.png");
		game.addActor(bike);
		game.setViewCandidate(bike);
		game.addActor(new Seesaw(game, new Vector(5.f, 0.5f), 5.f, 0.1f));
		game.addActor(new Checkpoint(game, true, new Vector(10.f, 0.6f), 0.5f, "flag.yellow.png"));
		game.addActor(new Checkpoint(game, true, new Vector(17.5f, 0.6f), 0.5f, "flag.yellow.png"));
		game.addActor(new Checkpoint(game, true, new Vector(25.f, 0.6f), 0.5f, "flag.yellow.png"));
		game.addActor(new Finish(game, true, new Vector(30.f, 0.6f), 0.5f, "flag.red.png"));
	}
}
