package pl.shockah.tanks;

import java.util.Random;
import pl.shockah.glib.Gamelib;
import pl.shockah.glib.logic.standard.GameStandard;

public class Main {
	public static final Random rand = new Random();
	
	public static void main(String[] args) {
		Gamelib.start(new GameStandard(),new StateGame(),"Tanks");
	}
}