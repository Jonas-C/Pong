package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Stack;

public class ScreenManager {
    private static ScreenManager screenManager = null;

    private Stack<PongScreen> screenStack;

    private SpriteBatch batch;
    private BitmapFont font;
    private Skin skin;

    private ScreenManager(){
        screenStack = new Stack<>();
        batch = new SpriteBatch();
        font = new BitmapFont();
        skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    }

    public static ScreenManager getInstance(){
        if(screenManager == null){
            screenManager = new ScreenManager();
        }
        return screenManager;
    }

    public void push(PongScreen screen){
        screenStack.push(screen);
    }

    public void pop(){
        screenStack.pop().dispose();
    }

    public void set(PongScreen screen){
        pop();
        push(screen);
    }

    public void update(float delta){
        screenStack.peek().update(delta);
    }

    public void render(float delta){
        screenStack.peek().render(delta);
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    Skin getSkin() {
        return skin;
    }

}
