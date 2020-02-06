package ec.pong;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import ec.pong.states.MenuScreen;

public class PongGame extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Skin skin;

	public static final int V_WIDTH = 800;
	public static final int V_HEIGHT = 480;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
	}
}
