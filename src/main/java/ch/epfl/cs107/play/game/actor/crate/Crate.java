package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Crate extends GameEntity implements Actor {
	private float width;
	private float height;
	private String imagePath;
	private ImageGraphics graphics;
	
	/**
	 * Creates a new Crate
	 * @param game the ActorGame in which the Crate is
	 * @param fixed whether it can move or not
	 * @param position absolute position of the Crate
	 * @param width on the x axis
	 * @param height on the y axis
	 * @param imagePath path of the image used for the Crate's graphical representation
	 */
	public Crate(ActorGame game, boolean fixed, Vector position, float width, float height, String imagePath) {
		super(game, fixed, position);
		
		if(width <= 0 || height <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			this.width = width;
			this.height = height;
		}
		
		if(imagePath == null) {
			throw new NullPointerException();
		}
		else {this.imagePath = imagePath;}
		
		PartBuilder partBuilder = super.getEntity().createPartBuilder();
        // Create a square polygon, and set the shape of the builder to this polygon
		Polygon polygon = new Polygon(
        		new Vector(0.f, 0.f),
        		new Vector(width, 0.f),
        		new Vector(width, height),
        		new Vector(0.f, height));
        
        partBuilder.setShape(polygon);
        partBuilder.build();
	}

	@Override
	public void draw(Canvas canvas) {
		graphics = new ImageGraphics(imagePath, width, height);
        graphics.setParent(super.getEntity());
        graphics.draw(canvas);
	}

	@Override
	public Transform getTransform() {
		return super.getEntity().getTransform();
	}

	@Override
	public Vector getVelocity() {
		return super.getEntity().getVelocity();
	}
	
	@Override
	public void destroy() {
		super.getOwner().deleteActor(this);
		super.destroy();
	}

}
