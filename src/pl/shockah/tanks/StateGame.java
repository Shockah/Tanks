package pl.shockah.tanks;

import pl.shockah.Util;
import pl.shockah.glib.AssetLoader;
import pl.shockah.glib.geom.Rectangle;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.geom.vector.Vector2i;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.actor.Actors;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.state.State;
import pl.shockah.glib.state.View;

public class StateGame extends State {
	private static Vector2i view = new Vector2i(800,600);
	
	protected void onCreate() {
		AssetLoader al = Assets.createAssetLoader();
		while (!al.finished()) {
			al.update();
			Util.sleep(1);
		}
		Assets.setupAssets();
		
		Game.reset();
		new ActorRenderable(100000){
			protected void onRender(Graphics g) {
				Vector2i size = State.get().displaySize();
				
				g.translate(Actors.type(ActorTankPlayer.class).get(0).pos.Sub(size.Div(2)).negate());
				Game.tremblev.set(Vector2d.make(Game.tremble,Main.rand.nextDouble()*360d));
				g.translate(Game.tremblev);
				
				g.setColor(Color.White);
				for (int y = -size.y; y < size.y+Assets.sBackground.textureHeight(); y += Assets.sBackground.textureHeight())
				for (int x = -size.x; x < size.x+Assets.sBackground.textureHeight(); x += Assets.sBackground.textureHeight()) {
					g.draw(Assets.sBackground,x,y);
				}
			}
		}.create();
		new ActorRenderable(-100000){
			protected void onRender(Graphics g) {
				Vector2i size = State.get().displaySize();
				
				Color c1 = Color.Black, c2 = Color.TransparentBlack;
				new Rectangle(0,0,Game.mapSize.x,32).drawMulticolor(g,true,c1,c1,c2,c2);
				new Rectangle(0,0,32,Game.mapSize.y).drawMulticolor(g,true,c1,c2,c1,c2);
				new Rectangle(0,Game.mapSize.y-32,Game.mapSize.x,32).drawMulticolor(g,true,c2,c2,c1,c1);
				new Rectangle(Game.mapSize.x-32,0,32,Game.mapSize.y).drawMulticolor(g,true,c2,c1,c2,c1);
				
				g.setColor(Color.Black);
				Vector2d size2 = new Vector2d(Game.mapSize.x,Game.mapSize.y);
				g.draw(new Rectangle(-size.x,-size.y,size.x,size.y*2+size2.y));
				g.draw(new Rectangle(size2.x,-size.y,size.x,size.y*2+size2.y));
				g.draw(new Rectangle(0,-size.y,size2.x,size.y));
				g.draw(new Rectangle(0,size2.y,size2.x,size.y));
				
				g.translate(Game.tremblev.Negate());
				Game.tremble -= Game.tremble*.2d;
				g.translate(Actors.type(ActorTankPlayer.class).get(0).pos.Sub(size.Div(2)));
			}
		}.create();
		
		Vector2i size = displaySize();
		new ActorTankPlayer().create(size.Div(2));
		for (int i = 0; i < 3; i++) new ActorTankEnemy().create(Main.rand.nextDouble()*size.x,Main.rand.nextDouble()*size.y);
	}
	
	protected void onSetup() {
		setViews();
	}
	protected void onDisplayChange(Vector2i size, boolean fullscreen) {
		view.set(size);
		setViews();
	}
	protected void setViews() {
		views.clear();
		views.add(new View(view));
	}
}