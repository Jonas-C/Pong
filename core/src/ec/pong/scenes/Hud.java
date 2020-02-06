package ec.pong.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ec.pong.PongGame;

public class Hud {

    public Stage stage;

    private Integer playerScore;
    private Integer enemyScore;

    private Label playerScoreLabel;
    private Label enemyScoreLabel;



    public Hud(SpriteBatch batch, BitmapFont font){
        playerScore = 0;
        enemyScore = 0;
        stage = new Stage(new ExtendViewport(PongGame.V_WIDTH, PongGame.V_HEIGHT), batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
        playerScoreLabel = new Label(playerScore.toString(), labelStyle);
        enemyScoreLabel = new Label(enemyScore.toString(), labelStyle);

        table.add(playerScoreLabel).expandX().padTop(10).right().padRight(50);
        table.add(enemyScoreLabel).expandX().padTop(10).left().padLeft(50);

        stage.addActor(table);
    }

    public void updateScore(boolean playerPoint){
        if(playerPoint){
            playerScore++;
            playerScoreLabel.setText(playerScore.toString());
        } else {
            enemyScore++;
            enemyScoreLabel.setText(enemyScore.toString());
        }
    }

    public boolean matchWon(){
        return playerScore == 21 || enemyScore == 21;
    }

    public void dispose(){
        stage.dispose();
    }
}
