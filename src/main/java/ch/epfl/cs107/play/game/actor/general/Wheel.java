package ch.epfl.cs107.play.game.actor.general;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.WheelConstraint;
import ch.epfl.cs107.play.math.WheelConstraintBuilder;
import ch.epfl.cs107.play.window.Canvas;

public class Wheel extends GameEntity implements Actor {
	private float radius;
	private String imagePath;
	private ImageGraphics graphics;
	public WheelConstraint wheelConstraint;
	
	/**
	 * Creates a new Wheel
	 * @param game the ActorGame in which the Wheel is
	 * @param fixed whether it can move or not
	 * @param position absolute position
	 * @param radius radius of the wheel
	 * @param imagePath path of the image used for the graphical representation
	 */
	public Wheel(ActorGame game, boolean fixed, Vector position, float radius, String imagePath) {
		super(game, fixed, position);
		
		if(imagePath == null) {
			throw new NullPointerException();
		}
		else {this.imagePath = imagePath;}
		
		if(radius <= 0) {
			throw new IllegalArgumentException();
		}
		else {this.radius = radius;}
		
		PartBuilder partBuilder = super.getEntity().createPartBuilder();
		
		Circle circle = new Circle(radius);
		
		partBuilder.setShape(circle);
		partBuilder.setFriction(5);
		partBuilder.build();
		
	}
	@Override
	public void draw(Canvas canvas) {
		graphics = new ImageGraphics(imagePath, 2.f*radius, 2.f*radius, new Vector(0.5f, 0.5f));
		graphics.setParent(super.getEntity());
		graphics.draw(canvas);
	}
	
	/**
	 * Builds a constraint between the wheel and a vehicle
	 * @param vehicle the vehicle to which we want to "attach" the wheel
	 * @param anchor the location of the anchor
	 * @param axis the location of the axis
	 */
	public void attach(Entity vehicle, Vector anchor, Vector axis) {
		WheelConstraintBuilder constraintBuilder = super.getOwner().getWheelConstraintBuilder();
		constraintBuilder.setFirstEntity(vehicle);
		constraintBuilder.setFirstAnchor(anchor);
		constraintBuilder.setSecondEntity(super.getEntity());
		constraintBuilder.setSecondAnchor(Vector.ZERO);
		constraintBuilder.setAxis(axis);
		constraintBuilder.setFrequency(3.0f);
		constraintBuilder.setDamping(0.5f);
		constraintBuilder.setMotorMaxTorque(10.0f);
		wheelConstraint = constraintBuilder.build();
	}
	/**
	 * Removes the constraint between the wheel and the vehicle
	 */
	public void detach() {
		wheelConstraint.destroy();
	}
	
	/**
	 * Make the wheel turn
	 * @param speed the speed at which the wheel should go
	 */
	public void power(float speed) {
		wheelConstraint.setMotorEnabled(true);
		wheelConstraint.setMotorSpeed(speed);
	}
	/**
	 * Stop powering the wheel
	 */
	public void relax() {
		wheelConstraint.setMotorEnabled(false);
	}
	
	/**
	 * @return absolute angular velocity
	 */
	public float getSpeed() {
		float vehicleAngularVelocity = wheelConstraint.getFirstBody().getAngularVelocity();
		float wheelAngularVelocity = wheelConstraint.getSecondBody().getAngularVelocity();
		return wheelAngularVelocity - vehicleAngularVelocity;
	}
	
	/**
	 * @return the angular position of the wheel
	 */
	public float getMotorWheelAngularPosition() {
		return wheelConstraint.getFirstBody().getAngularPosition();
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
		this.detach();
		super.getOwner().deleteActor(this);
		super.destroy();
	}
}
