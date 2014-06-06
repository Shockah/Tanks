package pl.shockah.tanks;

import org.lwjgl.input.Keyboard;
import pl.shockah.glib.geom.Line;
import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.gl.Graphics;
import pl.shockah.glib.gl.color.Color;
import pl.shockah.glib.input.KInput;
import pl.shockah.glib.input.MInput;
import pl.shockah.glib.logic.actor.Renderable;

public class ActorTankPlayer extends ActorTank {
	protected void onUpdate() {
		double a, da;
		a = pos.direction(Game.getMousePos())-rotation;
		da = Vector2d.make(1,rotation2).deltaAngle(a);
		rotation2 += da*.05d;
		
		Vector2d keys = new Vector2d();
		if (KInput.down(Keyboard.KEY_W)) keys.y -= 1;
		if (KInput.down(Keyboard.KEY_S)) keys.y += 1;
		if (KInput.down(Keyboard.KEY_A)) keys.x -= 1;
		if (KInput.down(Keyboard.KEY_D)) keys.x += 1;
		
		if (keys.x != 0 || keys.y != 0) {
			a = keys.direction();
			da = Vector2d.make(1,rotation).deltaAngle(a);
			rotation += da*.1d;
			Vector2d move = Vector2d.make(4,rotation);
			
			pos.add(move);
			frame++;
			if (frame < 0) frame += frameMax;
			if (frame >= frameMax) frame -= frameMax;
			
			tires.add(new TiresGhost(pos.copyMe(),rotation));
		}
		
		if (blockFire > 0) blockFire--;
		if (blockFire == 0 && MInput.pressed(MInput.LEFT)) {
			new ActorBullet(Vector2d.make(24,rotation2+rotation)).create(pos.copyMe());
			blockFire = blockFireMax;
			Game.tremble += 50;
		}
		
		super.onUpdate();
	}
	
	protected void onCreateRenderables() {
		super.onCreateRenderables();
		
		new Renderable(this,0d-.5d){
			protected void onRender(Graphics g) {
				Vector2d v = Vector2d.make(pos.distance(Game.getMousePos()),rotation2+rotation);
				double da = Math.abs(v.Add(pos).deltaAngle(Game.getMousePos()));
				
				new Line(pos.x,pos.y,pos.x+v.x,pos.y+v.y).drawMulticolor(g,Color.Black.alpha(0f),Color.Black);
				g.setColor(Color.White.alpha((float)(.5f-(da/180d))));
				g.draw(Assets.sCrosshair,pos.x+v.x,pos.y+v.y);
				g.setColor(Color.White);
				g.draw(Assets.sCrosshair,Game.getMousePos());
			}
		}.create();
	}
}