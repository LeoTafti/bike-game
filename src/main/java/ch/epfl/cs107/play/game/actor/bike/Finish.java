package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
public class Finish extends Trigger {
	private String imagePath;
	private ImageGraphics graphics;
	
	private float radius;
	
	/**
	 * Creates a new Finish, the abstraction of a finish line
	 * @param game the ActorGame in which the Finish is
	 * @param fixed whether can move or not
	 * @param position absolute position of the Finish
	 * @param radius radius of the circle used to represent a Finish physical shape
	 * @param imagePath path of the image used for the Finish's graphical representation
	 */
	public Finish(ActorGame game, boolean fixed, Vector position, float radius, String imagePath) {
		super(game, fixed, position, Float.POSITIVE_INFINITY);
		
		if(imagePath == null) {
			throw new NullPointerException();
		}
		else {this.imagePath = imagePath;}
		
		if(radius <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			this.radius = radius;
			Shape circle = new Circle(radius);
			super.createParts(circle);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(super.isVisible() == false) {
			((BikeGame)super.getOwner()).setHasWon(true);
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphics = new ImageGraphics(imagePath, 2.f*radius, 2.f*radius, new Vector(0.5f, 0.5f));
		super.draw(canvas, graphics);
	}
}