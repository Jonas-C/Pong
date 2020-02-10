package ec.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ec.pong.states.MenuScreen;
import ec.pong.states.ScreenManager;

public class PongGame extends Game {
//	public SpriteBatch batch;
//	public BitmapFont font;
//	public Skin skin;
	private ScreenManager screenManager;

	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 480;

	@Override
	public void create () {
//		batch = new SpriteBatch();
//		font = new BitmapFont();
//		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		screenManager = ScreenManager.getInstance();
		screenManager.push(new MenuScreen());
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		screenManager.update(Gdx.graphics.getDeltaTime());
		screenManager.render(Gdx.graphics.getDeltaTime());
	}

	@Override
	public void dispose () {
		screenManager.pop();
	}

	public static PongGame getInstance(){
		return (PongGame)Gdx.app.getApplicationListener();
	}
}
