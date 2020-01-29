package ch.epfl.cs107.play.game.actor;

public interface GameWithLevels {
	/**
	 * @param message, the message shown on screen when the level is loaded
	 * 
	 */
	public void loadLevel(String message);
	public void nextLevel();
	public void resetLevel();
}
