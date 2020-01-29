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

public class LevelRoller extends Level {
	
	/**
	 * Creates a new Level
	 * @param game the game in which the level is created
	 */
	public LevelRoller(ActorGame game) {
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
				3.f, 0.f,
				4.6f, 0.3f,
				6.f, 1.f,
				20.f, 15.f,
				23.f, 15.f,
				25.f, 10.5f,
				28.f, 6.5f,
				31.f, 4.f,
				33.f, 3.3f,
				35.f, 2.9f,
				36.5f, 2.8f,
				38.f, 3.f,
				39.5f, 3.4f,
				41.f, 3.8f,
				41.f, 0.5f,
				42.f, -2.5f,				
				43.8f, -4.6f,
				45.9f, -5.5f,
				48.f, -5.9f,
				49.9f, -5.7f,
				52.f, -5f,
				57.1f, -5f,
				57.1f, -4.f,
				60.f, -4.f,
				60.f, -15.f,
				61.f, -10.f,
				62.f, -15.f,
				63.f, -10.f,
				64.f, -15.f,
				65.f, -10.f,
				66.f, -15.f,
				67.f, -10.f,
				68.f, -15.f,
				69.f, -10.f,
				70.f, -15.f,
				71.f, -10.f,
				72.f, -15.f,
				73.f, -10.f,
				74.f, -15.f,
				75.f, -10.f,
				76.f, -15.f,
				77.f, -10.f,
				78.f, -15.f,
				79.f, -10.f,
				80.f, -15.f,
				81.f, -10.f,
				82.f, -15.f,
				83.f, -10.f,
				84.f, -4.f,
				94.f, -4.f,
				94.f, -5.f,
				90.f, -5.f,
				87.f, -7.f,
				86.f, -10.f,
				87.f, -13.f,
				90.f, -15.f,
				93.f, -15.f,
				96.f, -13.f,
				99.f, -13.f,
				100.f, -12.f,
				101.f, -13.f,
				102.f, -12.f,
				103.f, -13.f,
				104.f, -12.f,
				105.f, -13.f,
				106.f, -12.f,
				107.f, -13.f,
				108.f, -12.f,
				109.f, -13.f,
				110.f, -12.f,
				150.f, -12.f,
				150.f, 0.f,
				150.f, 100.f,
				170.f, 100.f,
				170.f, -100.f);

		game.addActor(new Terrain(game, terrainShape));
		
		Polyline slippyTerrainShape1 = new Polyline(
				17.f, 12.01f,
				20.f, 15.01f);
		game.addActor(new Terrain(game, slippyTerrainShape1, 0.f, Color.CYAN));
		
		Polyline slippyTerrainShape2 = new Polyline(
				83.f, -10.01f,
				84.f, -4.01f);
		game.addActor(new Terrain(game, slippyTerrainShape2, 0.f, Color.CYAN));
		
		Polyline looping = new Polyline(
				44.1f, 4.1f,
				45.f, 4.3f,
				46.f, 5.f,
				46.6f, 5.8f,
				47.f, 7.f,
				46.8f, 8.f,
				46.2f, 9.2f, 
				45.4f, 9.7f,
				44.4f, 10.f,
				43.2f, 9.7f,
				42.3f, 9.f,
				41.8f, 8.1f,
				41.6f, 7.f);
		
		game.addActor(new Terrain(game, looping)); 
		
		Polyline slippyTerrainOblique = new Polyline(
				94.f, -8.f,
				120.f, 0.f);
		
		game.addActor(new Terrain(game, slippyTerrainOblique, 0.f, Color.CYAN));
		
		Polyline slippyShortcut = new Polyline(
				127.f, 2.f,
				113.f, -12.f);
		
		game.addActor(new Terrain(game, slippyShortcut, 0.f, Color.CYAN));
				

		Bike bike = new Bike(game, ((BikeGame)getOwner()).getInitialBikePosition(), 0.5f, "wheel.2.png");
		game.addActor(bike);
		game.setViewCandidate(bike);


		//Checkpoint 1
		game.addActor(new Checkpoint(game, true, new Vector(42.3f, 2.f), 0.7f, "flag.yellow.png"));
		
		
		//Three small crates
		game.addActor(new Crate(game, false, new Vector(54.0f, -4.f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(55.0f, -4.f), 1.f, 1.f, "box.4.png"));
		game.addActor(new Crate(game, false, new Vector(56.0f, -4.f), 1.f, 1.f, "box.4.png"));

		
		//Dangerous Seesaws
		game.addActor(new Seesaw(game, new Vector(67.f, -5.f), 8.f, 0.2f));
		game.addActor(new Seesaw(game, new Vector(77.f, -5.f), 8.f, 0.2f));

		//Checkpoint 2
		game.addActor(new Checkpoint(game, true, new Vector(89.f, -3.4f), 0.7f, "flag.yellow.png"));
		
		//Finish line
		game.addActor(new Finish(game, true, new Vector(112.f, -11.4f), 0.7f, "flag.red.png"));

	}

}
