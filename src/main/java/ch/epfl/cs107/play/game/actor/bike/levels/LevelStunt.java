package ch.epfl.cs107.play.game.actor.bike.levels;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.bike.Bike;
import ch.epfl.cs107.play.game.actor.bike.BikeGame;
import ch.epfl.cs107.play.game.actor.bike.Checkpoint;
import ch.epfl.cs107.play.game.actor.bike.Finish;
import ch.epfl.cs107.play.game.actor.crate.Crate;
import ch.epfl.cs107.play.game.actor.general.Seesaw;
import ch.epfl.cs107.play.game.actor.general.Terrain;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Vector;

public class LevelStunt extends Level {

	/**
	 * Creates a new Level
	 * @param game the game in which the level is created
	 */
	public LevelStunt(ActorGame game) {
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
				5.f, 0.f,
				10.f, 1.f,
				10.f, 0.f,
				30.f, 0.f,
				37.f, 4.f,
				37.f, 0.f,
				44.5f, 0.f,
				44.5f, 5.f,
				43.f, 5.f,
				43.f, 6.f,
				47.f, 6.f,
				47.f, 5.f,
				45.5f, 5.f,
				45.5f, 0.f,
				85.5f, 0.f,
				85.5f, 0.2f,
				85.7f, 0.f,
				98.f, 0.f,
				98.f, -1.f,
				100.f, -1.f,
				100.f, 0.f,
				103.f, 0.f,
				103.f, -1.f,
				105.f, -1.f,
				105.f, 0.f,
				150.f, 0.f,
				150.f, 100.f,
				170.f, 100.f,
				170.f, -100.f);

		game.addActor(new Terrain(game, terrainShape));
		
		Polyline slippyTerrainShape = new Polyline(
				43.f, 6.01f,
				47.f, 6.01f);
		
		game.addActor(new Terrain(game, slippyTerrainShape, 0.f, Color.CYAN));
		
		Bike bike = new Bike(game, ((BikeGame)getOwner()).getInitialBikePosition(), 0.5f, "wheel.2.png");
		game.addActor(bike);
		game.setViewCandidate(bike);
		
		//First obstacles group
		game.addActor(new Crate(game, true, new Vector(10.f, 0.f), 3.f, 2.f, "wood.1.png"));
		game.addActor(new Crate(game, true, new Vector(15.f, 0.f), 3.f, 3.f, "wood.1.png"));
		game.addActor(new Crate(game, true, new Vector(23.f, 0.f), 3.f, 3.f, "wood.1.png"));
		game.addActor(new Crate(game, false, new Vector(17.5f, 4.f), 6.f, 0.2f, "wood.1.png"));
		
		
		
		//Pyramid of boxes obstacle
		game.addActor(new Crate(game, false, new Vector(53.0f, 0.0f), 1.f, 1.f, "box.4.png"));
		
		game.addActor(new Crate(game, false, new Vector(54.0f, 0.0f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(54.0f, 1.0f), 1.f, 1.f, "box.4.png"));

		game.addActor(new Crate(game, false, new Vector(55.0f, 0.0f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(55.0f, 1.0f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(55.0f, 2.0f), 1.f, 1.f, "box.4.png"));
		
		//Checkpoint 1
		game.addActor(new Checkpoint(game, true, new Vector(62.f, 0.6f), 0.7f, "flag.yellow.png"));
		
		//Big pyramid of boxes
		//(hard one, but definitely doable :D)
		game.addActor(new Crate(game, true, new Vector(68.f, 0.f), 2.f, 2.f, "wood.1.png"));
		
		game.addActor(new Crate(game, true, new Vector(71.5f, 0.f), 2.f, 2.f, "wood.1.png"));
		game.addActor(new Crate(game, true, new Vector(71.5f, 2.f), 2.f, 2.f, "wood.1.png"));
		
		game.addActor(new Crate(game, true, new Vector(75.f, 0.f), 2.f, 2.f, "wood.1.png"));
		game.addActor(new Crate(game, true, new Vector(75.f, 2.f), 2.f, 2.f, "wood.1.png"));
		game.addActor(new Crate(game, true, new Vector(75.f, 4.f), 2.f, 2.f, "wood.1.png"));
		
		game.addActor(new Crate(game, false, new Vector(76.5f, 6.2f), 10.f, 0.2f, "wood.1.png"));
		
		//Checkpoint 2
		game.addActor(new Checkpoint(game, true, new Vector(92.f, 0.6f), 0.7f, "flag.yellow.png"));
		
		//Killing seesaws
		game.addActor(new Seesaw(game, new Vector(100.f, 3.f), 4.8f, 0.2f));
		game.addActor(new Crate(game, false, new Vector(99.f, 5.f), 1.f, 1.f, "box.4.png"));
		
		game.addActor(new Seesaw(game, new Vector(105.f, 3.f), 4.8f, 0.2f));
		game.addActor(new Crate(game, false, new Vector(104.f, 5.f), 1.f, 1.f, "box.4.png"));
		
		
		//Finish line
		game.addActor(new Finish(game, true, new Vector(110.f, 0.6f), 0.7f, "flag.red.png"));
		
		
	}

}
