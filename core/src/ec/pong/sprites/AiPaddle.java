package ec.pong.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class AiPaddle extends Paddle{
    private Vector2 desiredPos;
    private float centerPoint;
    /*
    Currently set to the following:
    Easy: 100
    Medium: 300
    Hard: 500
     */
    private final float paddleSpeed;

    public AiPaddle(float x, float y, Sprite sprite, float paddleSpeed) {
        super(x, y, sprite);
        this.paddleSpeed = paddleSpeed;
        desiredPos = new Vector2(x, y);
        centerPoint = getPaddleSprite().getRegionHeight() / 2;
    }

    public void setDesiredYPos(Vector2 desiredPos){
        this.desiredPos.set(desiredPos.x, (int)desiredPos.y + (desiredPos.y > pos.y ? centerPoint : -(centerPoint)));
    }

    @Override
    public void update(float dt){
        if(!bounds.contains(desiredPos)){
            float newPosY = desiredPos.y <= pos.y ? dt * -(paddleSpeed) : dt * paddleSpeed;
            updatePosition(newPosY);
            bounds.setPosition(pos);
        }
    }

    @Override
    public void resetPaddle(){
        super.resetPaddle();
        desiredPos.set(pos.x, pos.y);
    }

}
