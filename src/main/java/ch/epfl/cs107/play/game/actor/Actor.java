package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Positionable;

/*
 * Represents an actor in the game logic
 */
public interface Actor extends Graphics, Positionable {
	/**
	 * Simulates a single time step.
	 * @param deltaTime elapsed time since last update, in seconds, non-negative
	 */
	public default void update(float deltaTime) {
		//By default, actors have nothing to update
	}
	public default void destroy() {
		//By default, actors have nothing to destroy
	}
}
