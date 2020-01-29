package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.game.actor.general.Wheel;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Polyline;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;

public class Bike extends GameEntity implements Actor {
	private Wheel leftWheel;
	private Wheel rightWheel;
	private Wheel motorWheel;
	
	private final float MAX_WHEEL_SPEED = 20.0f;
	private Direction direction; //Enum type, RIGHT or LEFT, direction in which the bike/cyclist looks and moves
	private boolean hit = false;
	private ShapeGraphics graphics;
	
	public static final int BIKE_COLLISION_GROUP = 1;
	
	/**
	 * Creates a new Bike, made up by a cyclist and his bike (two wheels, each attached to the the cyclist's entity)
	 * @param game the ActorGame in which the Bike progresses
	 * @param position absolute position of the bike
	 * @param wheelRadius radius of both wheels
	 * @param wheelImagePath path of the image used for the wheels' graphical representation
	 */
	public Bike(ActorGame game, Vector position, float wheelRadius, String wheelImagePath) {
		super(game, false, position);
		Vector leftWheelPosition = new Vector(position.getX() -1.f, position.getY());
		Vector rightWheelPosition = new Vector(position.getX() + 1.f, position.getY());
		
		if(wheelImagePath == null) {
			throw new NullPointerException();
		}
		else if(wheelRadius <= 0) {
			throw new IllegalArgumentException();
		}
		else {
			leftWheel = new Wheel(game, false, leftWheelPosition, wheelRadius, wheelImagePath);
			rightWheel = new Wheel(game, false, rightWheelPosition, wheelRadius, wheelImagePath);
		}
		
		this.direction = Direction.RIGHT;
		motorWheel = leftWheel;
		
		leftWheel.attach(super.getEntity(), new Vector(-1.f, 0.f), new Vector(-0.5f, -1.f));
		rightWheel.attach(super.getEntity(), new Vector(1.f, 0.f), new Vector(0.5f, -1.f));
		
		
		PartBuilder partBuilder = super.getEntity().createPartBuilder();
		Polygon polygon = new Polygon(
				0.f, 0.5f,
				0.5f, 1.f,
				0.f, 2.f,
				-0.5f, 1.f);
		
		partBuilder.setShape(polygon);
		partBuilder.setGhost(true);
		partBuilder.setCollisionGroup(BIKE_COLLISION_GROUP);
		partBuilder.build();
		
		//Add a contact listener, to know whether or not the bike crashed
		ContactListener listener = new ContactListener() {
			@Override
			public void beginContact(Contact contact) {
				if(contact.getOther().isGhost()) {
					return;
				}
				if(leftWheel.entityEquals(contact.getOther().getEntity())
						|| rightWheel.entityEquals(contact.getOther().getEntity())) {
					return;
				}
				hit = true;
			}
			
			@Override
			public void endContact(Contact contact) {
			}
		};
		
		this.getEntity().addContactListener(listener);
	}
	
	@Override
	public void update(float deltaTime) {
		if(!hit) {
			//By default, de-activate wheel motor
			leftWheel.relax();
			rightWheel.relax();
			
			Keyboard keyboard = super.getOwner().getKeyboard();
			//Change cyclist direction
			if(keyboard.get(KeyEvent.VK_SPACE).isPressed()) {
				switch(this.direction) {
				case RIGHT:
					this.direction = Direction.LEFT;
					motorWheel = rightWheel;
					break;
				case LEFT:
					this.direction = Direction.RIGHT;
					motorWheel = leftWheel;
					break;
				default:
					//Because we're using an enum type, this should never occur
					break;
				}
			}
			//Block both wheels
			if(keyboard.get(KeyEvent.VK_DOWN).isDown()) {
				leftWheel.power(0.f);
				rightWheel.power(0.f);
			}
			//Make bike roll
			else if(keyboard.get(KeyEvent.VK_UP).isDown()) {
				switch(this.direction) {
				case RIGHT:
					if(leftWheel.getSpeed() > - MAX_WHEEL_SPEED) {
						leftWheel.power(-MAX_WHEEL_SPEED);
					}
					break;
				case LEFT:
					if(rightWheel.getSpeed() < MAX_WHEEL_SPEED) {
						rightWheel.power(MAX_WHEEL_SPEED);
					}
				}
			}
			
			//Tilt right
			if(keyboard.get(KeyEvent.VK_RIGHT).isDown()) {
				super.getEntity().applyAngularForce(-30.f);
			}
			//Tilt left
			if(keyboard.get(KeyEvent.VK_LEFT).isDown()) {
				super.getEntity().applyAngularForce(30.f);
			}
			//Boost
			if(keyboard.get(KeyEvent.VK_SHIFT).isDown()) {
				switch(this.direction) {
				case RIGHT:
					leftWheel.power(-1.5f*MAX_WHEEL_SPEED);
					break;
				case LEFT:
					rightWheel.power(1.5f*MAX_WHEEL_SPEED);
				}
			}
//			//Jump
//			if(keyboard.get(KeyEvent.VK_SHIFT).isPressed()) {
//				super.getEntity().applyImpulse(new Vector(0.f, 5.f), null);
//			}
			
		}
		//else ~ if hit
		else {
			((BikeGame)this.getOwner()).setHasDied(true);
			this.destroy();
		}
	}
	
	/**
	 * //Body parts locations, in local coordinates for drawing purposes
	 */
	
	//For a few upper body "joints", there are two methods, one is the regular one
	//the other (with suffix Victory) is the position when the cyclist has his arms up in the air
	
	private Vector getHeadLocation() {
		Vector location = new Vector(0.0f, 1.75f);
		return possibleXSymmetry(location);
	}
	
	private Vector getShoulderLocation() {
		Vector location = new Vector(-0.2f, 1.5f);
		return possibleXSymmetry(location);
	}
	private Vector getShoulderLocationVictory() {
		Vector location = new Vector(-0.35f, 1.6f);
		return possibleXSymmetry(location);
	}
	
	private Vector getHandLocation() {
		Vector location = new Vector(0.5f, 1.f);
		return possibleXSymmetry(location);
	}
	private Vector getHandLocationVictory() {
		Vector location = new Vector(0.1f, 2.4f);
		return possibleXSymmetry(location);
	}
	
	private Vector getElbowLocationVictory() {
		Vector location = new Vector(-0.1f, 1.85f);
		return possibleXSymmetry(location);
	}
	
	private Vector getHipsLocation() {
		Vector location = new Vector(-0.5f, 0.9f);
		return possibleXSymmetry(location);
	}
	private Vector getLeftKneeLocation() {
		float alpha = motorWheel.getMotorWheelAngularPosition()/2;
		
		Vector location = new Vector(-((float)Math.sin(alpha)*0.15f), (float)Math.sin(alpha)*0.2f+0.5f);
		//note that we need the X symmetry when cyclist's direction is RIGHT, therefore we call
		//possibleXSymmetry with already the X symmetry of location,
		//so that we obtain the effect wanted
		return possibleXSymmetry(new Vector(-location.x, location.y));
	}
	private Vector getRightKneeLocation() {
		float alpha = motorWheel.getMotorWheelAngularPosition()/2;
		
		Vector location = new Vector(((float)Math.sin(alpha)*0.15f), -(float)Math.sin(alpha)*0.2f+0.5f);
		//as above
		return possibleXSymmetry(new Vector(-location.x, location.y));
	}
	private Vector getLeftFootLocation() {
		float alpha = motorWheel.getMotorWheelAngularPosition()/2;
		
		Vector location = new Vector((float)Math.cos(-alpha)*0.2f, -(float)Math.sin(-alpha)*0.2f);
		return location;
	}
	private Vector getRightFootLocation() {
		float alpha = motorWheel.getMotorWheelAngularPosition()/2;
		
		Vector location = new Vector(-(float)Math.cos(-alpha)*0.2f, (float)Math.sin(-alpha)*0.2f);
		return location;
	}
	
	//Returns X symmetry of vector if cyclist's direction is LEFT
	private Vector possibleXSymmetry(Vector vector) {
		if(direction == Direction.RIGHT) {return vector;}
		else {return new Vector(-vector.x, vector.y);}
	}
	
	@Override
	public void draw(Canvas canvas) {
		Circle head = new Circle(0.2f, getHeadLocation());
		
		Polyline arm;
		Polyline body;
		if(((BikeGame)super.getOwner()).getHasWon() == false) {
			arm = new Polyline(
					getShoulderLocation(),
					getHandLocation());
			body = new Polyline(
					getShoulderLocation(),
					getHipsLocation());
		}
		else {
			arm = new Polyline(
					getShoulderLocationVictory(),
					getElbowLocationVictory(),
					getHandLocationVictory());
			body = new Polyline(
					getShoulderLocationVictory(),
					getHipsLocation());
		}
		Polyline leftThigh = new Polyline(
				getHipsLocation(),
				getLeftKneeLocation());
		Polyline rightThigh = new Polyline(
				getHipsLocation(),
				getRightKneeLocation());
		Polyline leftShin = new Polyline(
				getLeftKneeLocation(),
				getLeftFootLocation());
		Polyline rightShin = new Polyline(
				getRightKneeLocation(),
				getRightFootLocation());

		Shape[] bodyParts = {head, arm, body, leftThigh, rightThigh, leftShin, rightShin};
		for(Shape bodyPart : bodyParts) {
			graphics = new ShapeGraphics(bodyPart, Color.RED, Color.RED, 0.1f, 1.f, 1.f);
			graphics.setParent(super.getEntity());
			graphics.draw(canvas);
		}
		
		leftWheel.draw(canvas);
		rightWheel.draw(canvas);
		
//		//TODO : remove
//		//Temporary : draws the hitbox
//		graphics = new ShapeGraphics(new Polygon(
//				0.f, 0.5f,
//				0.5f, 1.f,
//				0.f, 2.f,
//				-0.5f, 1.f), Color.ORANGE, null, 0.f, 0.5f, 1.f);
//		graphics.setParent(super.getEntity());
//		graphics.draw(canvas);
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
		leftWheel.destroy();
		rightWheel.destroy();
		super.destroy();
	}

}
