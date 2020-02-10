package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ec.pong.PongGame;
import ec.pong.sprites.AiBall;
import ec.pong.sprites.AiPaddle;
import ec.pong.sprites.Ball;
import ec.pong.sprites.Observer;
import ec.pong.sprites.Paddle;
import ec.pong.sprites.PlayerPaddle;
import ec.pong.scenes.Hud;

public class SoloPlayScreen implements Screen {

    private PongGame game;
    private Viewport viewport;
    private Hud hud;
    private TextureAtlas textures;
    private Ball ball;
    private AiBall aiBall;
    private boolean playerWon;
    private OrthographicCamera cam;
    private float difficulty;
    private Sprite screenBarSprite;
    private Array<Paddle> paddles;
    private Paddle enemyPaddle;

    SoloPlayScreen(float enemyMovementSpeed){
        this.game = PongGame.getInstance();
        difficulty = enemyMovementSpeed;
        cam = new OrthographicCamera();
        viewport = new ExtendViewport(PongGame.V_WIDTH, PongGame.V_HEIGHT, cam);
        viewport.apply();
        textures = new TextureAtlas("pong.atlas");
        Sprite paddleSprite = textures.createSprite("paddle");
        Sprite ballSprite = textures.createSprite("ball");
        screenBarSprite = textures.createSprite("screenBar");
        playerWon = false;
        hud = new Hud(game.batch, game.font);
        paddles = new Array<>();
        Paddle playerPaddle = new PlayerPaddle(50, (PongGame.V_HEIGHT/ 2) - paddleSprite.getRegionHeight() / 2, paddleSprite, viewport);
        enemyPaddle = new AiPaddle(PongGame.V_WIDTH - paddleSprite.getRegionWidth() - 50, (PongGame.V_HEIGHT/ 2) - paddleSprite.getRegionHeight() / 2, paddleSprite, enemyMovementSpeed);
        paddles.add(playerPaddle, enemyPaddle);
        ball = new Ball((PongGame.V_WIDTH / 2) - (ballSprite.getRegionWidth()/2) , (PongGame.V_HEIGHT/ 2) - (ballSprite.getRegionHeight() / 2), ballSprite, paddles);
        aiBall = new AiBall((PongGame.V_WIDTH / 2) - (ballSprite.getRegionWidth()/2) ,
                (PongGame.V_HEIGHT/ 2) - (ballSprite.getRegionHeight() / 2), ballSprite, enemyPaddle.getPos().x);
        ball.register(aiBall);
        aiBall.register((Observer)enemyPaddle);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        cam.update();
        Gdx.gl.glClearColor(0,0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(cam.combined);
        hud.stage.draw();
        game.batch.begin();
        game.batch.draw(screenBarSprite, PongGame.V_WIDTH / 2, 0);
        for(Paddle paddle : paddles){
            game.batch.draw(paddle.getPaddleSprite(), paddle.getPos().x, paddle.getPos().y);
        }
        game.batch.draw(ball.getBallSprite(), ball.getPos().x, ball.getPos().y);
        update(delta);
        game.batch.end();
    }

    private void update(float delta){
        ball.update(delta);
        aiBall.update(delta);
        for(Paddle paddle : paddles){
            paddle.update(delta);
        }
        checkWinCondition();
    }

    private void checkWinCondition(){
        if(ball.getPos().x >= PongGame.V_WIDTH || ball.getPos().x <= 0){
            playerWon = ball.getPos().x > 0;
            roundWon(playerWon);
        }
    }

    private void roundWon(boolean playerWon){
        hud.updateScore(playerWon);
        if(hud.matchWon()){
            dispose();
            game.setScreen(new GameWonScreen(playerWon, difficulty));
        }
        resetMap();
    }

    private void resetMap(){
        ball.resetBall(playerWon);
        aiBall.resetBall(ball.getVelocity());
        for(Paddle paddle : paddles){
            paddle.resetPaddle();
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        textures.dispose();
        hud.dispose();
        ball.unregister(aiBall);
        aiBall.unregister((Observer)enemyPaddle);
    }
}