package ch.epfl.cs107.play.game.tutorial;

import java.awt.event.KeyEvent;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Circle;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.RevoluteConstraintBuilder;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class ScaleGame implements Game {
	private Window window;
	private World world;
	
	private Entity block;
	private float blockWidth = 10.f;
	private float blockHeight = 1.f;
	private String blockImage = "stone.broken.4.png";
	private ImageGraphics blockGraphics;
	
	private Entity plank;
	private float plankWidth = 5.f;
	private float plankHeight = 0.2f;
	private String plankImage = "wood.3.png";
	private ImageGraphics plankGraphics;
	
	private Entity ball;
	private float ballRadius = 0.5f;
	private String ballImage = "explosive.11.png";
	private ImageGraphics ballGraphics;
	
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
        Polygon squareBlock = new Polygon(
        		new Vector(0.f, 0.f),
        		new Vector(blockWidth, 0.f),
        		new Vector(blockWidth, blockHeight),
        		new Vector(0.f, blockHeight));
        partBuilder.setShape(squareBlock);
        partBuilder.build();
        //Finally, give the block a graphical representation
        blockGraphics = new ImageGraphics(blockImage, blockWidth, blockHeight);
        blockGraphics.setParent(block);
        
        //Repeat the process for plank
        entityBuilder.setFixed(false); //Not sure about this line, should it be fixed or not ?
        entityBuilder.setPosition(new Vector(-2.5f, 0.8f));
        plank = entityBuilder.build();
        partBuilder = plank.createPartBuilder();
        Polygon rectanglePlank = new Polygon(
        		new Vector(0.f, 0.f),
        		new Vector(plankWidth, 0.f),
        		new Vector(plankWidth, plankHeight),
        		new Vector(0.f, plankHeight));
        partBuilder.setShape(rectanglePlank);
        partBuilder.build();
        plankGraphics = new ImageGraphics(plankImage, plankWidth, plankHeight);
        plankGraphics.setParent(plank);
        
        //And repeat the process for the ball
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(0.5f, 4.f));
        ball =  entityBuilder.build();
        partBuilder = ball.createPartBuilder();
        Circle circleBall = new Circle(ballRadius);
        partBuilder.setShape(circleBall);
        //The following line makes the "game" (a lot more) playable, otherwise the ball slides
        //down the plank even if we try our hardest to make it go up
        partBuilder.setFriction(5);
        partBuilder.build();
        ballGraphics = new ImageGraphics(ballImage, 2.f*ballRadius, 2.f*ballRadius, new Vector(0.5f, 0.5f));
        ballGraphics.setParent(ball);
        
        RevoluteConstraintBuilder revoluteConstraintBuilder = world.createRevoluteConstraintBuilder();
        revoluteConstraintBuilder.setFirstEntity(block);
        revoluteConstraintBuilder.setFirstAnchor(new Vector(blockWidth/2, (blockHeight*7)/4));
        revoluteConstraintBuilder.setSecondEntity(plank);
        revoluteConstraintBuilder.setSecondAnchor(new Vector(plankWidth/2, plankHeight/2));
        revoluteConstraintBuilder.setInternalCollision(true);
        revoluteConstraintBuilder.build();
  
		//Successfully initiated
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		//Game logic : being able to control the ball
		if (window.getKeyboard().get(KeyEvent.VK_LEFT).isDown()) {
			ball.applyAngularForce(10.f);
		}
		else if(window.getKeyboard().get(KeyEvent.VK_RIGHT).isDown()) {
			ball.applyAngularForce(-10.f);
		}
		
		//Simulate physics
		world.update(deltaTime);
		
		//Position camera
		window.setRelativeTransform(Transform.I.scaled(10.0f));
		
		//Render the scene
		blockGraphics.draw(window);
		plankGraphics.draw(window);
		ballGraphics.draw(window);
	}
	
	@Override
	public void end() {
		//Nothing to do here
	}
}
