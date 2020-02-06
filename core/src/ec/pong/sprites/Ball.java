package ec.pong.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ec.pong.PongGame;

import java.util.Random;

public class Ball {

    private static final int VELOCITY = 300;
    private final float COLLISION_IGNORE_PERIOD = 0.20f;
    //Ball can get stuck at specific angles. By adding a small grace period this can be avoided.

    final float initPosX;
    final float initPosY;
    private float timeSincePaddleCollision;
    private int additionalSpeed;

    private Sprite ballSprite;
    private Random rand;
    Vector2 pos;
    Vector2 velocity;
    Rectangle bounds;

    public Ball(float x, float y, Sprite sprite){
        pos = new Vector2(x, y);
        timeSincePaddleCollision = 0;
        initPosX = x;
        initPosY = y;
        additionalSpeed = 0;
        this.ballSprite = sprite;
        rand = new Random();
        velocity = new Vector2(-(VELOCITY), (rand.nextInt(15) +1) * 15);
        bounds = new Rectangle(x, y, ballSprite.getRegionWidth(), ballSprite.getRegionHeight());
    }

    public Sprite getBallSprite(){
        return ballSprite;
    }

    public Vector2 getPos(){
        return pos;
    }

    public void paddleBounce(Rectangle bounds){
        if(timeSincePaddleCollision > COLLISION_IGNORE_PERIOD){
            additionalSpeed += 5;
            boolean positiveVelocity = velocity.x > 0;
            float relativeIntersect =  (pos.y + this.bounds.getHeight()/2) - (bounds.y + (bounds.getHeight()/2));
            float normalIntersect = (relativeIntersect/(bounds.getHeight()/2));
            velocity.setAngle(normalIntersect * 75); //75 = degrees
            velocity.add(additionalSpeed, additionalSpeed);
            if(positiveVelocity){
                velocity.x = -(velocity.x); //for enemy paddle
            }
            timeSincePaddleCollision = 0;
        }
    }

    private void wallBounce(){
        velocity.y = -(velocity.y);
    }

    public boolean isColliding(Rectangle paddleBounds){
        return bounds.overlaps(paddleBounds);
    }

    private boolean isCollidingWall(){
        if(pos.y < 0){
            pos.y = 0;
            return true;
        } else if(pos.y + ballSprite.getRegionHeight() > PongGame.V_HEIGHT){
            pos.y = PongGame.V_HEIGHT - ballSprite.getRegionHeight();
            return true;
        } else {
            return false;
        }
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void update(float delta){
        pos.add(velocity.x * delta, velocity.y  * delta);
        bounds.setPosition(pos);
        timeSincePaddleCollision += delta;
        if(isCollidingWall()){
            wallBounce();
        }
    }

    public void resetBall(boolean playerWon){
        additionalSpeed = 0;
        pos.set(initPosX, initPosY);
        velocity.set(playerWon ? -(VELOCITY) : VELOCITY, (rand.nextInt(15) +1) * (rand.nextFloat() > 0.5f ? 15 : -15));
        bounds.setPosition(pos);
    }
}