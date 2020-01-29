package ch.epfl.cs107.play.game.actor;

import java.util.ArrayList;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.Positionable;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;

/**
 * Represents a game in which multiple actors evolve in a physical world
 */
public abstract class ActorGame implements Game {
	
	private Window window;
	private FileSystem fileSystem;
	private World world;
	private ArrayList<Actor> actors = new ArrayList<Actor>();
		
	private Vector viewCenter;
	private Vector viewTarget;
	private Positionable viewCandidate;
	private static final float VIEW_TARGET_VELOCITY_COMPENSATION = 0.2f;
	private static final float VIEW_INTERPOLATION_RATIO_PER_SECOND = 0.1f;
	private static final float VIEW_SCALE = 10.0f;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		this.window = window;
		this.fileSystem = fileSystem;
		
		world = new World();
		world.setGravity(new Vector(0.0f, -9.81f));
		
		viewCenter = Vector.ZERO;
		viewTarget = Vector.ZERO;
		return true;
	}

	@Override
	public void update(float deltaTime) {
		//Simulate physics
		world.update(deltaTime);

		//Update each actor
		for(int i = 0; i < actors.size(); i++) {
			actors.get(i).update(deltaTime);
		}
		
		//Position camera
		
		//Update expected viewport center
		if (viewCandidate != null) {
			viewTarget =
					viewCandidate.getPosition().add(viewCandidate.getVelocity().mul(VIEW_TARGET_VELOCITY_COMPENSATION));
		}
		//Interpolate with previous location
		float ratio = (float)Math.pow(VIEW_INTERPOLATION_RATIO_PER_SECOND, deltaTime);
		viewCenter = viewCenter.mixed(viewTarget, 1.0f - ratio);
		//Compute new viewport
		Transform viewTransform = Transform.I.scaled(VIEW_SCALE).translated(viewCenter);
		window.setRelativeTransform(viewTransform);
		
		//Draw each actor
		for(Actor actor : actors) {
			actor.draw(window);
		}
	}

	@Override
	public void end() {
		// Do nothing here
	}
	
	//Better way than defining method ActorGame.getWorld
	public Entity buildEntity(boolean fixed, Vector position) {
		EntityBuilder entityBuilder = world.createEntityBuilder();
		entityBuilder.setFixed(fixed);
        entityBuilder.setPosition(position);
		return entityBuilder.build();
	}
	
	/**
	 * @return a wheelConstraintBuilder
	 */
	public WheelConstraintBuilder getWheelConstraintBuilder() {
		return world.createWheelConstraintBuilder();
	}
	
	/**
	 * @return a revoluteConstraintBuilder
	 */
	public RevoluteConstraintBuilder getRevoluteConstraintBuilder() {
		return world.createRevoluteConstraintBuilder();
	}
	
	//Getters
	/**
	 * @return the keyboard
	 */
	public Keyboard getKeyboard(){
		return window.getKeyboard();
	}
	
	/**
	 * @return the canvas
	 */
	public Canvas getCanvas() {
		return window;
	}

	//Setters
	/**
	 * Sets camera viewCandidate
	 * @param viewCandidate the candidate the camera should follow
	 */
	public void setViewCandidate(Positionable viewCandidate) {
		this.viewCandidate = viewCandidate;
	}
	
	/**
	 * Adds an actor to the list of actors
	 * @param actor the actor to add
	 */
	public void addActor(Actor actor) {
		actors.add(actor);
	}
	
	/**
	 * removes an actor from the list of actors
	 * @param actor the actor to remove
	 */
	public void deleteActor(Actor actor) {
		actors.remove(actor);
	}
	
	/**
	 * Cleans everything
	 */
	public void destroyAllActors() {
		while(actors.size() > 0) {
			actors.get(0).destroy();
		}
	}
}
