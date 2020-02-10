package ec.pong.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class AiBall extends Ball implements Observer{

    private float aiPaddleXPos;

    public AiBall(float x, float y, Sprite sprite, float aiPaddleXPos) {
        super(x, y, sprite, null);
        this.aiPaddleXPos = aiPaddleXPos;
        getBallSprite().setColor(Color.BLACK);
    }

    private boolean checkStopCondition(){
        return this.pos.x >= aiPaddleXPos;
    }

    private void stop(){
        velocity.set(0, 0);
        notifyObservers();
    }

    private void start(Ball ball){
        velocity.set(ball.velocity.x * 2, ball.velocity.y * 2);
        pos.set(ball.getPos());
    }

    public void resetBall(Vector2 playBallVelocity){
        pos.set(initPosX, initPosY);
        velocity.set(playBallVelocity.x * 2, playBallVelocity.y * 2);
        bounds.setPosition(pos);
    }

    @Override
    public void update(float delta){
        super.update(delta);
        if(checkStopCondition()){
            stop();
        }
    }

    @Override
    public void notifyObservers(){
        for(Observer observer : observers){
            observer.receiveNotification(this.pos);
        }
    }

    @Override
    public void receiveNotification(Object obj) {
        start((Ball)obj);
    }
}
