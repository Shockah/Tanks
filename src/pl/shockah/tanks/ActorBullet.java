package pl.shockah.tanks;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.logic.actor.ActorRenderable;

public class ActorBullet extends ActorRenderable {
	public Vector2d vel = new Vector2d();
	
	public ActorBullet(Vector2d vel) {
		super(-5);
		this.vel.set(vel);
	}
	
	protected void onUpdate() {
		pos.add(vel);
		if (pos.x < -32 || pos.y < -32 || pos.x > Game.mapSize.x+32 || pos.y > Game.mapSize.y+32) destroy();
	}
	
	protected void onRender(Graphics g) {
		g.setColor(Color.White);
		Assets.sBullet.rotation.angle = vel.direction()-135;
		g.draw(Assets.sBullet,pos);
	}
}