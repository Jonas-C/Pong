package ec.pong.sprites;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ec.pong.PongGame;

public abstract class Paddle extends InputAdapter {

    private Sprite paddleSprite;
    Rectangle bounds;
    Vector2 pos;

    private final float initPosX;
    private final float initPosY;

    Paddle(float x, float y, Sprite sprite){
        paddleSprite = sprite;
        pos = new Vector2(x, y);
        bounds = new Rectangle(x, y, paddleSprite.getRegionWidth(), paddleSprite.getRegionHeight());
        paddleSprite.setColor(Color.BROWN);
        initPosX = x;
        initPosY = y;
    }

    public void update(float delta){
        bounds.setPosition(pos);
    }

    void updatePosition(float y){
        float newPosY = pos.y + y;
        if(newPosY > 0 && newPosY < PongGame.V_HEIGHT - paddleSprite.getRegionHeight()){
            pos.add(0, y);
        } else if(newPosY < 0 || newPosY > PongGame.V_HEIGHT - paddleSprite.getRegionHeight()){
            pos.set(pos.x, newPosY < 0 ? 0 : PongGame.V_HEIGHT - paddleSprite.getRegionHeight());
        }
    }

    public void resetPaddle(){
        pos.set(initPosX, initPosY);
        bounds.setPosition(pos);
    }

    public Vector2 getPos(){
        return pos;
    }

    public Rectangle getBounds(){
        return bounds;
    }

    public Sprite getPaddleSprite(){
        return paddleSprite;
    }


}
