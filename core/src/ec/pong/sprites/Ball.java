package ec.pong.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import ec.pong.PongGame;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ball implements Observable {

    private static final int VELOCITY = 300;
    private final float COLLISION_IGNORE_PERIOD = 0.20f;
    //Ball can get stuck at specific angles. By adding a small grace period this can be avoided.

    final float initPosX;
    final float initPosY;
    private float timeSincePaddleCollision;
    private int additionalSpeed;

    List<Observer> observers;

    private Sprite ballSprite;
    private Random rand;
    Vector2 pos;
    Vector2 velocity;
    Rectangle bounds;
    private Array<Paddle> paddles;

    public Ball(float x, float y, Sprite sprite, Array<Paddle> paddles){
        pos = new Vector2(x, y);
        this.paddles = paddles;
        timeSincePaddleCollision = 0;
        initPosX = x;
        initPosY = y;
        additionalSpeed = 0;
        this.ballSprite = sprite;
        observers = new ArrayList<>();
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

    private void paddleBounce(Rectangle bounds){
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
            notifyObservers();
        }
    }

    private void wallBounce(){
        velocity.y = -(velocity.y);
    }

    private boolean isColliding(Rectangle paddleBounds){
        return bounds.overlaps(paddleBounds);
    }

    private void checkWallCollision(){
        if(pos.y < 0 || pos.y + ballSprite.getRegionHeight() > PongGame.V_HEIGHT){
            pos.y = pos.y < 0 ? 0 : PongGame.V_HEIGHT - ballSprite.getRegionHeight();
            wallBounce();
        }
    }

    public Vector2 getVelocity(){
        return velocity;
    }

    public void update(float delta){
        pos.add(velocity.x * delta, velocity.y  * delta);
        bounds.setPosition(pos);
        timeSincePaddleCollision += delta;
        checkCollision();
    }

    private void checkCollision(){
        checkPaddleCollision();
        checkWallCollision();
    }

    private void checkPaddleCollision(){
        if(paddles == null) return;
        for(Paddle paddle : paddles){
            if(isColliding(paddle.bounds)){
                paddleBounce(paddle.bounds);
            }
        }
    }

    public void resetBall(boolean playerWon){
        additionalSpeed = 0;
        pos.set(initPosX, initPosY);
        velocity.set(playerWon ? -(VELOCITY) : VELOCITY, (rand.nextInt(15) +1) * (rand.nextFloat() > 0.5f ? 15 : -15));
        bounds.setPosition(pos);
    }

    @Override
    public void register(Observer obj) {
        observers.add(obj);
    }

    @Override
    public void unregister(Observer obj) {
        observers.remove(obj);
    }

    @Override
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.receiveNotification(this);
        }
    }
}