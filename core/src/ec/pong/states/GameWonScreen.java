package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ec.pong.PongGame;

public class GameWonScreen extends InputAdapter implements Screen {

    private Stage stage;

    GameWonScreen(final PongGame game, boolean playerWon, final float difficulty){
        stage = new Stage(new ExtendViewport(PongGame.V_WIDTH, PongGame.V_HEIGHT), game.batch);
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(this);

        Label title = new Label(playerWon ? "YOU WON!" : "YOU LOST", game.skin);
        table.add(title).expandX().padTop(50);
        table.row();

        TextButton retryButton = new TextButton("Try again!", game.skin, "default");
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new SoloPlayScreen(game, difficulty));
            }
        });
        table.add(retryButton).width(PongGame.V_WIDTH/3).space(5,0,5,0).expandX().padTop(50);
        table.row();

        TextButton mainMenuButton = new TextButton("Main Menu", game.skin, "default");
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                game.setScreen(new MenuScreen(game));
            }
        });
        table.add(mainMenuButton).width(PongGame.V_WIDTH/3).space(5,0,5,0).expandX().padTop(50);
        stage.addActor(table);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.getCamera().update();
        stage.getBatch().setProjectionMatrix(stage.getCamera().combined);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        stage.getCamera().position.set(stage.getCamera().viewportWidth/2, stage.getCamera().viewportHeight/2, 0);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
