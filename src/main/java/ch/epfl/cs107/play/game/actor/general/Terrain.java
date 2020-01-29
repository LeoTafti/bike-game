package ch.epfl.cs107.play.game.actor.general;

import java.awt.Color;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public class Terrain extends GameEntity implements Actor {
	private Polyline polyline;
	private ShapeGraphics graphics;
	private Color outlineColor;
	
	/**
	 * Creates new terrain ("ground")
	 * @param game the AtorGame in which the Terrain is
	 * @param shape the shape of the terrain
	 * @param friction coefficient
	 * @param outlineColor Color of the outline
	 */
	public Terrain(ActorGame game, Polyline shape, float friction, Color outlineColor) {
		super(game, true);
		
		if(outlineColor == null) {
			throw new NullPointerException();
		}
		else {this.outlineColor = outlineColor;}
		
		PartBuilder partBuilder = super.getEntity().createPartBuilder();
		polyline = shape;
		partBuilder.setShape(polyline);
		partBuilder.setFriction(friction);
		partBuilder.build();
	}
	
	/**
	 * Creates new Terrain, with default friction coefficient of 1 and outlineColor red
	 * @param game the AtorGame in which the Terrain is
	 * @param shape the shape of the terrain
	 */
	public Terrain(ActorGame game, Polyline shape) {
		this(game, shape, 1.f, Color.RED);
	}
	
	@Override
	public void draw(Canvas canvas) {
		graphics = new ShapeGraphics(polyline, Color.WHITE, outlineColor, 0.1f, 1.f, 0.f);
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
