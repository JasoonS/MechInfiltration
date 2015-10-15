package jason.smythe.uct.csc2003.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import jason.smythe.uct.csc2003.MechInfultration;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Mech Infiltration! Good luck my frined, the mechanical world depends on you.";
		config.vSyncEnabled = true;
		config.width = 1250;
		config.height = 700;
		new LwjglApplication(new MechInfultration(), config);
	}
}