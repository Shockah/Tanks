package pl.shockah.tanks;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.input.MInput;
import pl.shockah.glib.logic.Entities;
import pl.shockah.glib.state.State;

public class Game {
	public static Vector2d mapSize = new Vector2d(), tremblev = new Vector2d();
	public static double tremble = 0d;
	
	public static void reset() {
		reset(new Vector2d(1024,1024));
	}
	public static void reset(Vector2d mapSize) {
		Game.mapSize.set(mapSize);
		tremblev.set(0,0);
		tremble = 0d;
	}
	
	public static Vector2d getMousePos() {
		Vector2i size = State.get().getDisplaySize();
		return MInput.getPos().toDouble().add(Entities.getType(EntityTankPlayer.class).get(0).pos.Sub(size.Div(2)));
	}
}