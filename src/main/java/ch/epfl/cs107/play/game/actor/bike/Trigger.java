package ch.epfl.cs107.play.game.actor.bike;

import ch.epfl.cs107.play.game.actor.Actor;
import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameEntity;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.math.Contact;
import ch.epfl.cs107.play.math.ContactListener;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Shape;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

public abstract class Trigger extends GameEntity implements Actor {
	
	private ContactListener listener;
	
	private boolean visible = true;
	
	private float elapsedTime;
	private final float timeBeforeAppearingAgain;
	
	/**
	 * Creates a new Trigger
	 * Triggers are GameEntities which react to being touched by the bike and disappear,
	 * but do not result in the cyclist death.
	 * @param game the ActorGame in which the Trigger is created
	 * @param fixed whether or not the Trigger can move
	 * @param position absolute position
	 * @param timeBeforeAppearingAgain how long the Trigger must be invisible before appearing again
	 */
	protected Trigger(ActorGame game, boolean fixed, Vector position, float timeBeforeAppearingAgain) {
		super(game, fixed, position);
		
		this.elapsedTime = 0;
		this.timeBeforeAppearingAgain = timeBeforeAppearingAgain;
		
		//Associate trigger with a ContactListener
		this.listener = new ContactListener() {

			@Override
			public void beginContact(Contact contact) {
				if(contact.getOther().getCollisionGroup() == Bike.BIKE_COLLISION_GROUP) {
					visible = false;
					elapsedTime = 0;
				}
			}

			@Override
			public void endContact(Contact contact) {}
		};
        super.getEntity().addContactListener(listener);
	}
	
	/**
	 * Gives the Trigger a physical representation
	 * @param shape the physical shape of the body.
	 */
	protected void createParts(Shape shape) {
		PartBuilder partBuilder = super.getEntity().createPartBuilder();
		
		partBuilder.setShape(shape);
		partBuilder.setGhost(true);
		partBuilder.build();
	}
	
	@Override
	public void update(float deltaTime) {
		elapsedTime += deltaTime;
		if(!visible && elapsedTime > timeBeforeAppearingAgain) {
			visible = true;
		}
	}
	
	/**
	 * @return visible value
	 */
	protected boolean isVisible() {
		return visible;
	}
	
	/**
	 * Draws Trigger on the canvas
	 * @param canvas the canvas on which to draw
	 * @param graphics graphical representation of the Trigger
	 */
	protected void draw(Canvas canvas, ImageGraphics graphics) {
		//draws shape only if visible
		if(visible) {
			graphics.setParent(getEntity());
			graphics.draw(canvas);
		}
	}
	/**
	 * Draws Trigger on the canvas
	 * @param canvas the canvas on which to draw
	 * @param graphics graphical representation of the Trigger
	 */
	protected void draw(Canvas canvas, ShapeGraphics graphics) {
		if(visible) {
			graphics.setParent(getEntity());
			graphics.draw(canvas);
		}
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
