package ch.epfl.cs107.play.game.actor;

import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.Vector;

/**
 * Represents an Entity in an ActorGame
 */
public abstract class GameEntity {
	private Entity entity;
	private ActorGame actorGame;
	
	/**
	 * Creates a new GameEntity, an abstraction of an Entity evolving inside of an ActorGame
	 * @param game the ActorGame in which the GameEntity is
	 * @param fixed whether it can move or not
	 * @param position absolute position
	 */
	protected GameEntity(ActorGame game, boolean fixed, Vector position) {
		if(game == null) {
			throw new NullPointerException();
		}
		else{this.actorGame = game;}
		
		if(position == null) {
			throw new NullPointerException();
		}
		else {this.entity = actorGame.buildEntity(fixed, position);}
	}
	public GameEntity(ActorGame game, boolean fixed) {
		this(game, fixed, new Vector(0.f, 0.f));
	}
	
	//Getters
	/**
	 * @return the entity
	 */
	protected Entity getEntity() {
		return entity;
	}
	
	/**
	 * @return the actorGame
	 */
	protected ActorGame getOwner() {
		return actorGame;
	}
	
	//Allows getEntity to stay protected, cf. Project guidelines 5.2.4, question 6
	/**
	 * Checks if this.entity is the same as the entity in argument
	 * @param entity the entity the check against
	 * @return true if same, false otherwise
	 */
	public boolean entityEquals(Entity entity) {
		if (this.getEntity() == entity) {
			return true;
		}
		return false;
	}
	
	/**
	 * Destroys entity
	 */
	public void destroy() {
		entity.destroy();
	}
}
