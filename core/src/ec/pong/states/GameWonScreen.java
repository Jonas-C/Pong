package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ec.pong.PongGame;

public class GameWonScreen extends InputAdapter implements PongScreen {

    private Stage stage;
    private ScreenManager screenManager;

    GameWonScreen(boolean playerWon, final float difficulty){
        screenManager = ScreenManager.getInstance();
        stage = new Stage(new ExtendViewport(PongGame.V_WIDTH, PongGame.V_HEIGHT), screenManager.getBatch());
        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(this);

        Label title = new Label(playerWon ? "YOU WON!" : "YOU LOST", screenManager.getSkin());
        table.add(title).expandX().padTop(50);
        table.row();

        TextButton retryButton = new TextButton("Try again!", screenManager.getSkin(), "default");
        retryButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                screenManager.set(new SoloPlayScreen(difficulty));
            }
        });
        table.add(retryButton).width(PongGame.V_WIDTH/3).space(5,0,5,0).expandX().padTop(50);
        table.row();

        TextButton mainMenuButton = new TextButton("Main Menu", screenManager.getSkin(), "default");
        mainMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dispose();
                screenManager.set(new MenuScreen());
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

    @Override
    public void update(float delta) {

    }
}
