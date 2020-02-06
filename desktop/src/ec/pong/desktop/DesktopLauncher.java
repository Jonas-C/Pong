package ec.pong.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import ec.pong.PongGame;

public class DesktopLauncher {

	public static final int V_HEIGHT = 960;
	public static final int V_WIDTH = 1600;
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = V_HEIGHT;
		config.width = V_WIDTH;
		new LwjglApplication(new PongGame(), config);
	}
}
