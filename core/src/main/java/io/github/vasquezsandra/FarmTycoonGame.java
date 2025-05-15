package io.github.vasquezsandra;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;



/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FarmTycoonGame extends Game {
    public SpriteBatch batch;
    private Screen currentScreen;

    public Inventory inventory;
    public HomeScreen homeScreen;
    public BaseScreen baseScreen;
    public StaminaBar stamina;
    public Player player;
    public TextBubble textBubble;


    @Override
    public void create() {
        batch = new SpriteBatch();
        stamina = new StaminaBar();
        inventory = new Inventory();
        player = new Player(stamina);
        textBubble = new TextBubble();
        baseScreen = new BaseScreen(this);
        homeScreen = new HomeScreen(this, stamina);
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void resize(int width, int height) {
        if (currentScreen != null) {
            currentScreen.resize(width, height);
        }
    }

    @Override
    public void pause() {
        if (currentScreen != null) {
            currentScreen.pause();
        }
    }

    @Override
    public void resume() {
        if (currentScreen != null) {
            currentScreen.resume();
        }
    }

    @Override
    public void dispose() {
        if (currentScreen != null) {
            currentScreen.dispose();
        }
    }
}
