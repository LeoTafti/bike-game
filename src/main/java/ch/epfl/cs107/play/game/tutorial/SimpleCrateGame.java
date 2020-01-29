package ch.epfl.cs107.play.game.tutorial;

import ch.epfl.cs107.play.game.Game;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Entity;
import ch.epfl.cs107.play.math.EntityBuilder;
import ch.epfl.cs107.play.math.PartBuilder;
import ch.epfl.cs107.play.math.Polygon;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.math.World;
import ch.epfl.cs107.play.window.Window;

public class SimpleCrateGame implements Game {
	// Store context
    private Window window;
    
    // We need our physics engine
    private World world;
	
	private Entity block;
	private Entity crate;
	
	private ImageGraphics blockGraphics;
	private ImageGraphics crateGraphics;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		// Store context
        this.window = window;
        
        world = new World();
        
        world.setGravity(new Vector(0.0f, -9.81f));
        
        EntityBuilder entityBuilder = world.createEntityBuilder();
        entityBuilder.setFixed(true);
        entityBuilder.setPosition(new Vector(1.0f, 0.5f));
        block = entityBuilder.build();

        // At this point, your body is in the world, but it has no geometry
        // You need to use another builder to add shapes
        PartBuilder partBuilder = block.createPartBuilder();
        // Create a square polygon, and set the shape of the builder to this polygon
        Polygon polygon = new Polygon(
        		new Vector(0.0f, 0.0f),
        		new Vector(1.0f, 0.0f),
        		new Vector(1.0f, 1.0f),
        		new Vector(0.0f, 1.0f));
        
        partBuilder.setShape(polygon);
        // Finally, do not forget the following line.
        partBuilder.build();
        // Note : we do not need to keep a reference on partBuilder
        
        entityBuilder.setFixed(false);
        entityBuilder.setPosition(new Vector(0.2f, 4.0f));
        crate = entityBuilder.build();
        
        partBuilder = crate.createPartBuilder();
        // We can re-use "polygon" defined above here, because crate and block have the same shape
        
        //test, as suggested in the project guidelines
//        polygon = new Polygon(
//        		new Vector(0.0f, -1.0f),
//        		new Vector(2.0f, -1.0f),
//        		new Vector(2.0f, 1.0f),
//        		new Vector(0.0f, 1.0f));
        partBuilder.setShape(polygon);
        partBuilder.build();
        
        
        
        blockGraphics = new ImageGraphics("stone.broken.4.png", 1, 1);
        blockGraphics.setParent(block);
        
        crateGraphics = new ImageGraphics("box.4.png", 1, 1);
        crateGraphics.setParent(crate);
        
        //successfully initiated
        return true;
	}
	
	@Override
    public void update(float deltaTime) {
	    	// Game logic comes here
    		// Nothing to do, yet
    	
	    	// Simulate physics
	    	// Our body is fixed, though, nothing will move
    		world.update(deltaTime);
    	
    		// we must place the camera where we want
    		// We will look at the origin (identity) and increase the view size a bit
    		window.setRelativeTransform(Transform.I.scaled(10.0f));

    		
	    	// We can render our scene now,
	    	blockGraphics.draw(window);
	    crateGraphics.draw(window);
      
        // The actual rendering will be done now, by the program loop
    }
	
	@Override
	public void end() {
		//Nothing to do here
	}
}
