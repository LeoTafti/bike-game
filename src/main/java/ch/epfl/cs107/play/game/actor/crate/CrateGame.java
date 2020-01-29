package ch.epfl.cs107.play.game.actor.crate;

import ch.epfl.cs107.play.game.actor.ActorGame;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Window;

public class CrateGame extends ActorGame {
	private String crateImagePath = "box.4.png";
	
	@Override
	public boolean begin(Window window, FileSystem fileSystem) {
		super.begin(window, fileSystem);
		Crate crate1 = new Crate(this, false, new Vector(0.0f, 5.0f), 1.f, 1.f, crateImagePath);
		Crate crate2 = new Crate(this, false, new Vector(0.2f, 7.0f), 1.f, 1.f, crateImagePath);
		Crate crate3 = new Crate(this, false, new Vector(2.0f, 6.0f), 1.f, 1.f, crateImagePath);
		super.addActor(crate1);
		super.addActor(crate2);
		super.addActor(crate3);
		return true;
	}
}
