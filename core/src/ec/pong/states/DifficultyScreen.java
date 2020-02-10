package ec.pong.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import ec.pong.PongGame;

public class DifficultyScreen implements PongScreen {

    private Stage stage;
    private ScreenManager screenManager;

    DifficultyScreen(){
        screenManager = ScreenManager.getInstance();
        Skin skin = screenManager.getSkin();
        stage = new Stage(new ExtendViewport(PongGame.V_WIDTH, PongGame.V_HEIGHT), screenManager.getBatch());
        Table table = new Table();
        table.top();
        table.setFillParent(true);

        Label title = new Label("Choose difficulty!", skin);
        table.add(title).expandX().padTop(50);
        table.row();

        TextButton easyButton = new TextButton("Easy", skin, "default");
        easyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.set(new SoloPlayScreen(100));
            }
        });
        table.add(easyButton).width(PongGame.V_WIDTH / 3).spaceTop(5).spaceBottom(5).expandX().padTop(50);
        table.row();

        TextButton mediumButton = new TextButton("Medium", skin, "default");
        mediumButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.set(new SoloPlayScreen(300));
            }
        });
        table.add(mediumButton).width(PongGame.V_WIDTH / 3).spaceTop(5).spaceBottom(5).expandX().padTop(50);
        table.row();

        TextButton hardButton = new TextButton("Hard", skin, "default");
        hardButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenManager.set(new SoloPlayScreen(500));
            }
        });
        table.add(hardButton).width(PongGame.V_WIDTH / 3).spaceTop(5).spaceBottom(5).expandX().padTop(50);
        Gdx.input.setInputProcessor(stage);

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
