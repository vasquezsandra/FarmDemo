package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {
    private FarmTycoonGame game;
    private Stage stage;//Stage to hold the objects
    private Texture background;//Texture png for backgrounf
    private SpriteBatch batch;//Batch of sprites for better rendering
    private Music backgroundMusic;
    private Texture exitButton;//Texture for exit button
    private Texture playButton;//Texture for play button
    private Texture activePlayButton;
    private Texture activeExitButton;

    /**
     * This method will present the main menu buttons and the hovering effects.
     * @param game The FarmTycoon game
     */
    public MenuScreen(FarmTycoonGame game) {
        this.game = game;
        this.batch = new SpriteBatch();

        //Main menu background and buttons with the hovering texture
        background = GameTextures.getTexture("assets/background.png");
        playButton = GameTextures.getTexture("play.png");
        activePlayButton = GameTextures.getTexture("hoverPlay.png");
        exitButton = GameTextures.getTexture("exit.png");
        activeExitButton = GameTextures.getTexture("exitHover.png");

        //Music, music volume
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/jazz.mp3"));//Binary file path
        backgroundMusic.setLooping(true);//Music is continuous
        backgroundMusic.setVolume(0.7f);//Music volume
        backgroundMusic.play();//Plays

        stage = new Stage(new ScreenViewport());//Objects will appear on the screen
        Gdx.input.setInputProcessor(stage);//Sets the stage up for input


    }

    @Override
    public void show() {

    }

    @Override
    /**
     * This render methods updates the screen, this will present the features to the users screen.
     * The user will be presented textures that can be interacted with.
     */
    public void render(float delta) {

        game.batch.begin();//Begins rendering the batches
        // Draw the background to fit the screen
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        //batch.end();

       // game.batch.begin();
        //Draws the playButton and sets it to x, y coordinates
        game.batch.draw(playButton,90, 10, 300, 300);

        //Exit buttons for the menu screen
        if((Gdx.input.getX()< 475 || Gdx.input.getX() > 610) ||(Gdx.input.getY() < 310 || Gdx.input.getY() > 385) ){
            game.batch.draw(exitButton, 400, 10, 300, 300);//inactive exit button
        }else{//This is the button update, when it is hovered over
            game.batch.draw(activeExitButton, 400, 10, 300, 300);
            if(Gdx.input.isTouched()){
                Gdx.app.exit();//games closes when selected
            }
        }

        //Play buttons
        if((Gdx.input.getX() < 160||Gdx.input.getX() >300)||(Gdx.input.getY() < 310 || Gdx.input.getY() > 385)){
            game.batch.draw(playButton, 90, 10, 300, 300);
        }else{
            game.batch.draw(activePlayButton, 90, 10, 300,300);
            if(Gdx.input.isTouched()){//Hovered play button, following actions are invoked, when clicked.
                backgroundMusic.setVolume(0.4f);//Music lowers

                //If the play button is touched, the next screen is set.
                game.setScreen(new BaseScreen(game));
            }
        }

        game.batch.end();
        stage.act(delta);//Updates the objects
        stage.draw();
    }

    @Override
    /**
     * This method will resize the screen
     */
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {




    }
}

