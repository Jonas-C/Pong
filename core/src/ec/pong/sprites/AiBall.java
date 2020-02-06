package ec.pong.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class AiBall extends Ball{

    private boolean stopped;

    public AiBall(float x, float y, Sprite sprite) {
        super(x, y, sprite);
        getBallSprite().setColor(Color.BLACK);
        stopped = false;
    }

    public boolean checkStopCondition(float paddlePos){
        return this.pos.x >= paddlePos;
    }

    public void stop(){
        stopped = true;
        velocity.set(0, 0);
    }

    public void start(Ball ball){
        stopped = false;
        velocity.set(ball.velocity.x * 2, ball.velocity.y * 2);
        pos.set(ball.getPos());
    }

    public boolean isStopped(){
        return stopped;
    }

    @Override
    public boolean isColliding(Rectangle paddleBounds){
        return bounds.overlaps(paddleBounds);
    }

    public void resetBall(Vector2 playBallVelocity){
        pos.set(initPosX, initPosY);
        velocity.set(playBallVelocity.x * 2, playBallVelocity.y * 2);
        bounds.setPosition(pos);
        stopped = false;
    }
}
