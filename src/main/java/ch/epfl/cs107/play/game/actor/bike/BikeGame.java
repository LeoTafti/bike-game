package ch.epfl.cs107.play.game.actor.bike;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.game.actor.GameWithLevels;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.actor.bike.levels.Level;
import ch.epfl.cs107.play.game.actor.bike.levels.LevelFlat;
import ch.epfl.cs107.play.game.actor.bike.levels.LevelHilly;
import ch.epfl.cs107.play.game.actor.bike.levels.LevelRoller;
import ch.epfl.cs107.play.game.actor.bike.levels.LevelStunt;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Transform;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class BikeGame extends ActorGame implements GameWithLevels {
	private Vector initialBikePosition = new Vector(0.f, 0.f);
	private boolean hasWon = false;
	private boolean hasDied = false;
	private TextGraphics message;
	
	private List<Level> levels;
	private static int CURRENT_LEVEL = 1;
	private final int LAST_LEVEL = 3;
	
	private float elapsedTimeSinceFinish = 0;
	private final float WAIT_FOR = 3.f;
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		
		this.levels = createLevelList();
		loadLevel("Have fun :)");
		
		message = new TextGraphics("", 0.1f, Color.RED, Color.WHITE, 0.01f, true, false, new Vector(0.5f, 0.5f), 1.0f, 100.0f);
		message.setParent(getCanvas());
		message.setRelativeTransform(Transform.I.translated(0.0f, -1.0f));
		
		return true;
	}
	
	@Override
	public void update(float deltaTime) {
		super.update(deltaTime);
		if(hasWon) {
			if(BikeGame.CURRENT_LEVEL < LAST_LEVEL) {
//				if(this.getKeyboard().get(KeyEvent.VK_ENTER).isPressed()) {
//					nextLevel();
//				}
				
				//The above code snippet waits for the user to press "enter" to go to the next level
				//but here, automatically goes to the next level after a few seconds (waitFor value)
				elapsedTimeSinceFinish += deltaTime;
				if(elapsedTimeSinceFinish > WAIT_FOR) {
					nextLevel();
				}
			}
			else {
				message.setText("The End !");
				message.draw(getCanvas());
			}
		}
		if(hasDied) {
			if(this.getKeyboard().get(KeyEvent.VK_R).isPressed()) {
				resetLevel();
			}
			message.setText("'R' to retry");
			message.draw(getCanvas());
		}
	}

	/**
	 * Sets hasWon value
	 * @param bool hasWon value
	 */
	protected void setHasWon(boolean bool) {
		hasWon = bool;
	}
	
	/**
	 * @return hasWon value
	 */
	protected boolean getHasWon() {
		return hasWon;
	}
	
	/**
	 * Sets hasDied value
	 * @param bool hasDied value
	 */
	protected void setHasDied(boolean bool) {
		hasDied = bool;
	}

	/**
	 * Sets initialBikePosition value
	 * @param x abscissa
	 * @param y ordinate
	 */
	protected void setInitialBikePosition(float x, float y) {
		initialBikePosition = new Vector(x, y);
	}
	
	/**
	 * @return initialBikePosition value
	 */
	public Vector getInitialBikePosition() {
		return initialBikePosition;
	}

	/**
	 * creates a list of all the playable levels
	 * @return lis$t of levels
	 */
	protected List<Level> createLevelList(){
		return Arrays.asList(
//				new LevelFlat(this),
				new LevelHilly(this),
				new LevelRoller(this),
				new LevelStunt(this)
				);
	}
	
	@Override
	public void loadLevel(String message) {
		super.destroyAllActors();
		
		Level currentLevel = levels.get(BikeGame.CURRENT_LEVEL - 1);
		currentLevel.setMessageString(message);
		currentLevel.createAllActors();
		super.addActor(currentLevel);
	}

	@Override
	public void nextLevel() {
		hasWon = false;
		elapsedTimeSinceFinish = 0.f;
		BikeGame.CURRENT_LEVEL += 1;
		this.initialBikePosition = new Vector(0.f, 0.f);
		loadLevel("Next level");
	}
	@Override
	public void resetLevel() {
		hasWon = false;
		hasDied = false;
		loadLevel("Try again");
	}
}
