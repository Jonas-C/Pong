package ec.pong.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PlayerPaddle extends Paddle {

    private boolean touchingPaddle;
    private Vector2 touchPos;
    private float prevTouchY;
    private Viewport viewport;
    public PlayerPaddle(float x, float y, Sprite sprite, Viewport viewport) {
        super(x, y, sprite);
        touchingPaddle = false;
        touchPos = new Vector2();
        Gdx.input.setInputProcessor(this);
        this.viewport = viewport;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchPos.set(screenX, screenY);
        viewport.unproject(touchPos);
        if(bounds.contains(touchPos)){
            touchingPaddle = true;
            prevTouchY = touchPos.y;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        touchingPaddle = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if(touchingPaddle){
            touchPos.set(screenX, screenY);
            viewport.unproject(touchPos);
            updatePosition(touchPos.y - prevTouchY);
            prevTouchY = touchPos.y;
        }
        return false;
    }

}
