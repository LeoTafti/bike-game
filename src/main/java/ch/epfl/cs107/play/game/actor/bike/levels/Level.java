package ch.epfl.cs107.play.game.actor.bike.levels;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.math.Node;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Level extends Node implements Actor {
	private ActorGame actorGame;
	private float elapsedTime;
	private final float timeToWait;
	
	private TextGraphics message;
	
	/**
	 * Creates a new Level
	 * @param game the game in which the level is created
	 */
	protected Level(ActorGame game) {
		if(game == null) {
			throw new NullPointerException();
		}
		else {this.actorGame = game;}
		
		elapsedTime = 0;
		timeToWait = 2.f;
		message = new TextGraphics("", 0.1f, Color.RED, Color.WHITE, 0.01f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(actorGame.getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
	}
	
	/**
	 * @param messageString the message shown on screen when level is loaded
	 */
	public void setMessageString(String messageString) {
		message.setText(messageString);
	}
	
	/**
	 * @return actorGame, the game in which the level
	 */
	protected ActorGame getOwner() {
		return actorGame;
	}
	
	/**
	 * creates all the  actors of the level.
	 * has to be overridden by subclasses, therefore abstract
	 */
	public abstract void createAllActors();
	
	@Override
	public void update(float deltaTime) {
		elapsedTime += deltaTime;
		if(elapsedTime < timeToWait) {
			message.setAlpha((timeToWait-elapsedTime)/timeToWait); //value between 1 (for elapsedTime == 0) and 0 (for elapsedTime == timeToWait)
			message.draw(actorGame.getCanvas());
		}
		else {
			//Message vanished, we do not need to keep the level in the list of actors
			this.destroy();
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		
	}
	
	@Override
	public void destroy() {
		actorGame.deleteActor(this);
	}
}
