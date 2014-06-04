package pl.shockah.tanks;

import java.util.ArrayList;
import java.util.List;
import pl.shockah.Math2;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.logic.actor.ActorRenderable;
import pl.shockah.glib.logic.actor.Renderable;

public class ActorTank extends ActorRenderable {
	public Color color = Color.fromHSB(Main.rand.nextFloat(),.5f+Main.rand.nextFloat()*.5f,1f);
	public double rotation = 0, rotation2 = 0;
	protected final int frameLength = 4, frameMax = Assets.ssTankTires.count()*frameLength, blockFireMax = 60;
	protected int frame = 0, blockFire = 0;
	
	protected List<TiresGhost> tires = new ArrayList<>();
	
	protected void onUpdate() {
		pos.x = Math2.limit(pos.x,0,Game.mapSize.x);
		pos.y = Math2.limit(pos.y,0,Game.mapSize.y);
	}
	
	protected void onRegister() {
		new Renderable(this,0d-.1d){
			protected void onRender(Graphics g) {
				Image imgTires = Assets.ssTankTires.image(0);
				for (TiresGhost tg : tires) {
					g.setColor(Color.Maroon.alpha(1f/3f*tg.alpha));
					imgTires.rotation.angle = tg.rotation;
					g.draw(imgTires,tg.pos);
				}
				for (int i = 0; i < tires.size(); i++) {
					TiresGhost tg = tires.get(i);
					tg.alpha -= 3f/255f;
					if (tg.alpha <= 0) tires.remove(i--);
				}
			}
		}.create();
		new Renderable(this,0d-.2d){
			protected void onRender(Graphics g) {
				g.setColor(Color.White);
				Image imgTires = Assets.ssTankTires.image(frame/frameLength);
				imgTires.rotation.angle = rotation;
				g.draw(imgTires,pos);
			}
		}.create();
		new Renderable(this,0d-.3d){
			protected void onRender(Graphics g) {
				g.setColor(color);
				Assets.sTankBase.rotation.angle = rotation;
				g.draw(Assets.sTankBase,pos);
			}
		}.create();
		new Renderable(this,0d-.4d){
			protected void onRender(Graphics g) {
				g.setColor(color);
				Assets.sTankTurret.rotation.angle = rotation2+rotation;
				g.draw(Assets.sTankTurret,pos);
			}
		}.create();
	}
	
	protected static class TiresGhost {
		protected final Vector2d pos;
		protected final double rotation;
		protected float alpha = 1f;
		
		public TiresGhost(Vector2d pos, double rotation) {
			this.pos = pos;
			this.rotation = rotation;
		}
	}
}