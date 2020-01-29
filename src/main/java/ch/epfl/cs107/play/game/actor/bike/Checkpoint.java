package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Checkpoint extends Trigger {
	private String imagePath;
	private ImageGraphics graphics;
	
	private float radius;
	
	/**
	 * Creates a new Checkpoint, point at which the cyclist returns when he died
	 * @param game the ActorGame in which the Checkpoint is
	 * @param fixed whether can move or not
	 * @param position absolute position of the Checkpoint
	 * @param radius radius of the circle used to represent a Checkpoint's physical shape
	 * @param imagePath path of the image used for the Checkpoint's graphical representation
	 */
	public Checkpoint(ActorGame game, boolean fixed, Vector position, float radius, String imagePath) {
		super(game, fixed, position, Float.POSITIVE_INFINITY);
		
		if(imagePath == null) {
			throw new NullPointerException();
		}
		else {this.imagePath = imagePath;}
		
		if(radius <= 0) {
			throw new IllegalArgumentException();
		}
		else {this.radius = radius;}
		
		Shape circle = new Circle(radius);
		super.createParts(circle);
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(isVisible() == false) {
			((BikeGame)getOwner()).setInitialBikePosition(this.getEntity().getPosition().x, this.getEntity().getPosition().y);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphics = new ImageGraphics(imagePath, 2.f*radius, 2.f*radius, new Vector(0.5f, 0.5f));
		super.draw(canvas, graphics);
	}

}
