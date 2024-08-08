package de.keeyzar.earthragequit.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.keeyzar.earthragequit.ERQGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 700;
		new LwjglApplication(new ERQGame(null), config);
//		new LwjglApplication(new Textwriting(), config);
//		new LwjglApplication(new test(), config);
	}
}
