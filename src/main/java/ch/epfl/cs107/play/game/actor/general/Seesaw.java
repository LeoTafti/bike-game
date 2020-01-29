package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Seesaw extends GameEntity implements Actor {
	private float plankWidth;
	private float plankHeight;

	private ShapeGraphics graphics;
	
	private Polygon polygon;
	
	/**
	 * @param game the ActorGame in which the Seesaw is
	 * @param position absolute position of the Seesaw pivot
	 * @param plankWidth on the x axis
	 * @param plankHeight on the y axis
	 */
	public Seesaw(ActorGame game, Vector position, float plankWidth, float plankHeight) {
		super(game, false, position);
		
		if(plankWidth <= 0 || plankHeight <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			this.plankWidth = plankWidth;
			this.plankHeight = plankHeight;
		}

		PartBuilder partBuilder = super.getEntity().createPartBuilder();
		polygon = new Polygon(
        		new Vector(-plankWidth/2, 0.f),
        		new Vector(plankWidth/2, 0.f),
        		new Vector(plankWidth/2, plankHeight),
        		new Vector(-plankWidth/2, plankHeight));
		
        partBuilder.setShape(polygon);
//        partBuilder.setFriction(0.5f);
        partBuilder.build();
        
        RevoluteConstraintBuilder revoluteConstraintBuilder = super.getOwner().getRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(super.getOwner().buildEntity(true, position));
        revoluteConstraintBuilder.setFirstAnchor(new Vector(0.f, 0.f));
        revoluteConstraintBuilder.setSecondEntity(super.getEntity());
        revoluteConstraintBuilder.setSecondAnchor(new Vector(0.f, 0.f));
        revoluteConstraintBuilder.setInternalCollision(false);
        revoluteConstraintBuilder.build();
	}

	@Override
	public void draw(Canvas canvas) {
		graphics = new ShapeGraphics(polygon, Color.WHITE, Color.RED, 0.05f, 1.f, 0.f);
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
