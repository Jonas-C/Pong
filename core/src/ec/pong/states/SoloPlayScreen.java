package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import ec.pong.PongGame;
import ec.pong.sprites.AiBall;
import ec.pong.sprites.AiPaddle;
import ec.pong.sprites.Ball;
import ec.pong.sprites.Paddle;
import ec.pong.sprites.PlayerPaddle;
import ec.pong.scenes.Hud;

public class SoloPlayScreen implements Screen {

    private PongGame game;
    private Viewport viewport;
    private Hud hud;
    private TextureAtlas textures;
    private Paddle playerPaddle;
    private AiPaddle enemyPaddle;
    private Ball ball;
    private AiBall aiBall;
    private boolean playerWon;
    private OrthographicCamera cam;
    private float difficulty;
    private Sprite screenBarSprite;

    SoloPlayScreen(PongGame game, float enemyMovementSpeed){
        this.game = game;
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
        playerPaddle = new PlayerPaddle(50, (PongGame.V_HEIGHT/ 2) - paddleSprite.getRegionHeight() / 2, paddleSprite, viewport);
        enemyPaddle = new AiPaddle(PongGame.V_WIDTH - paddleSprite.getRegionWidth() - 50, (PongGame.V_HEIGHT/ 2) - paddleSprite.getRegionHeight() / 2, paddleSprite, enemyMovementSpeed);
        ball = new Ball((PongGame.V_WIDTH / 2) - (ballSprite.getRegionWidth()/2) , (PongGame.V_HEIGHT/ 2) - (ballSprite.getRegionHeight() / 2), ballSprite);
        aiBall = new AiBall((PongGame.V_WIDTH / 2) - (ballSprite.getRegionWidth()/2) , (PongGame.V_HEIGHT/ 2) - (ballSprite.getRegionHeight() / 2), ballSprite);
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
        game.batch.draw(playerPaddle.getPaddleSprite(), playerPaddle.getPos().x, playerPaddle.getPos().y);
        game.batch.draw(enemyPaddle.getPaddleSprite(), enemyPaddle.getPos().x, enemyPaddle.getPos().y);
        game.batch.draw(ball.getBallSprite(), ball.getPos().x, ball.getPos().y);
//        game.batch.draw(aiBall.getBallSprite(), aiBall.getPos().x, aiBall.getPos().y);
        update(delta);
        game.batch.end();
    }

    private void update(float delta){
        ball.update(delta);
        aiBall.update(delta);
        enemyPaddle.update(delta);
        playerPaddle.update(delta);
        checkCollision();
        checkWinCondition();
        checkAiBall();
    }

    private void checkAiBall(){
        if( !aiBall.isStopped() && aiBall.checkStopCondition(enemyPaddle.getPos().x)){
            aiBall.stop();
            enemyPaddle.setDesiredYPos(aiBall.getPos());
        }
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
            game.setScreen(new GameWonScreen(game, playerWon, difficulty));
        }
        resetMap();
    }

    private void checkCollision(){
        if(ball.isColliding(playerPaddle.getBounds())){
            ball.paddleBounce(playerPaddle.getBounds());
            aiBall.start(ball);
        }
        else if(ball.isColliding(enemyPaddle.getBounds())){
            ball.paddleBounce(enemyPaddle.getBounds());
        }
    }

    private void resetMap(){
        ball.resetBall(playerWon);
        aiBall.resetBall(ball.getVelocity());
        playerPaddle.resetPaddle();
        enemyPaddle.resetPaddle();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        cam.position.set(cam.viewportWidth/2, cam.viewportHeight/2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(playerPaddle);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textures.dispose();
        hud.dispose();
    }
}