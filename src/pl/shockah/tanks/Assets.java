package pl.shockah.tanks;

import pl.shockah.glib.AssetLoader;
import pl.shockah.glib.gl.tex.EResizeFilter;
import pl.shockah.glib.gl.tex.Image;
import pl.shockah.glib.gl.tex.SpriteSheet;

public class Assets {
	@Image.Loadable(path="sBackground.png") public static Image sBackground;
	@Image.Loadable(path="sCrosshair.png") public static Image sCrosshair;
	@Image.Loadable(path="sTankBase.png") public static Image sTankBase;
	@Image.Loadable(path="sTankTurret.png") public static Image sTankTurret;
	@Image.Loadable(path="sBullet.png") public static Image sBullet;
	@SpriteSheet.Loadable(path="ssTankTires.png",framesX=8) public static SpriteSheet ssTankTires;
	
	public static AssetLoader createAssetLoader() {
		return new AssetLoader();
	}
	public static void setupAssets() {
		sCrosshair.center();
		sTankBase.center();
		sTankTurret.center();
		sBullet.center();
		for (int i = 0; i < ssTankTires.getCount(); i++) ssTankTires.getImage(i).center();
		
		sTankBase.getTexture().setResizeFilter(EResizeFilter.Nearest);
		sTankTurret.getTexture().setResizeFilter(EResizeFilter.Nearest);
		ssTankTires.getTexture().setResizeFilter(EResizeFilter.Nearest);
		
		sTankBase.scale.scale(2);
		sTankTurret.scale.scale(2);
		sBullet.scale.scale(.5d);
		for (int i = 0; i < ssTankTires.getCount(); i++) ssTankTires.getImage(i).scale.scale(2);
	}
}