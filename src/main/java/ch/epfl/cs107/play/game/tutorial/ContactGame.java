package ch.epfl.cs107.play.game.tutorial;

import java.awt.Color;
import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.ShapeGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.BasicContactListener;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ContactGame implements Game {
	private Window window;
	private World world;
	
	private BasicContactListener contactListener;
	
	private Entity block;
	private float blockWidth = 10.f;
	private float blockHeight = 1.f;
	private String blockImage = "stone.broken.4.png";
	
	private ImageGraphics blockGraphics;
	
	private Entity ball;
	private float ballRadius = 0.5f;
	private ShapeGraphics ballGraphics;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        
        world = new World();
        
        world.setGravity(new Vector(0.0f, -9.81f));
		
        EntityBuilder entityBuilder = world.createEntityBuilder();
        PartBuilder partBuilder = null;
        
        
        //First create the block entity
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(-5.f, -1.f));
        block = entityBuilder.build();
        //Then give the block a shape
        partBuilder = block.createPartBuilder();
        Polygon rectangleBlock = new Polygon(
        		new Vector(0.f, 0.f),
        		new Vector(blockWidth, 0.f),
        		new Vector(blockWidth, blockHeight),
        		new Vector(0.f, blockHeight));
        partBuilder.setShape(rectangleBlock);
        partBuilder.build();
        //Finally, give the block a graphical representation
        blockGraphics = new ImageGraphics(blockImage, blockWidth, blockHeight);
        blockGraphics.setParent(block);
        
        
        //Repeat the process for the ball
		entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(0.f, 2.0f));
        ball = entityBuilder.build();
        
        partBuilder = ball.createPartBuilder();
        Circle circle = new Circle(ballRadius);
        partBuilder.setShape(circle);
        partBuilder.build();
        
        ballGraphics = new ShapeGraphics(circle, Color.BLUE, Color.BLUE, .1f, 1, 0);
        ballGraphics.setParent(ball);
        
        //Associate ball with a contact listener
        contactListener = new BasicContactListener();
        ball.addContactListener(contactListener);
		
		//Successfully initiated
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		//Simulate physics
		world.update(deltaTime);
		
		//Position camera
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		//Render the scene
		blockGraphics.draw(window);
		
		//contactListener is associated to ball
		//contactListener.getEntities() returns the list of entities in collision with ball
		int numberOfCollisions = contactListener.getEntities().size();
		if(numberOfCollisions > 0) {
			ballGraphics.setFillColor(Color.RED);
		}
		ballGraphics.draw(window);
	}
	
	@Override
	public void end() {
		//do nothing here
	}
}
