package pl.shockah.tanks;

import pl.shockah.glib.geom.vector.Vector2d;
import pl.shockah.glib.logic.Entities;

public class EntityTankEnemy extends EntityTank {
	protected Vector2d aiPos = new Vector2d();
	
	protected void onCreate() {
		aiPos.set(pos);
	}
	
	protected void onUpdate() {
		if (pos.distance(aiPos) <= 50) aiPos.set(Main.rand.nextDouble()*Game.mapSize.x,Main.rand.nextDouble()*Game.mapSize.y);
		
		double a, da;
		a = pos.direction(Entities.getType(EntityTankPlayer.class).get(0).pos)-rotation;
		da = Vector2d.make(1,rotation2).deltaAngle(a);
		rotation2 += da*.05d;
		
		Vector2d keys = Vector2d.make(1,pos.direction(aiPos));
		
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
		if (blockFire == 0 && Main.rand.nextInt(200) == 0) {
			new EntityBullet(Vector2d.make(24,rotation2+rotation)).create(pos.copyMe());
			blockFire = blockFireMax;
			Game.tremble += 10;
		}
		
		super.onUpdate();
	}
}