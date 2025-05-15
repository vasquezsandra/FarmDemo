package io.github.vasquezsandra;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class HomeScreen implements Screen {
    private final FarmTycoonGame game;
    private SpriteBatch batch;
    private Texture homeBackground;
    private UITab uiTab;
    private Inventory inventory;
    private final Player player;
    private final TextBubble wordBubble;
    private StaminaBar stamina;

    /**
     * The home screen, with different objects
     * @param game
     * @param stamina
     */
    public HomeScreen(FarmTycoonGame game, StaminaBar stamina) {
        this.game = game;
        batch = new SpriteBatch();
        homeBackground = new Texture("assets/HomeScreen.png");
        this.inventory = game.inventory;//Passes the same inventory
        player = game.player;//Passes the same player
        uiTab = new UITab(game, stamina);
        this.wordBubble = game.textBubble;
        this.stamina = stamina;//Passes the same stamina bar

    }

    @Override
    /**
     * Renders the home screen and its objects to the player
     */
    public void render(float delta) {
        batch.begin();
        //Home screen background
        batch.draw(homeBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player.update(delta);//Updates the player
        stamina.render(batch);
        //Still renders the UI tab
        uiTab.render(batch);
        if (uiTab.isInventory()) {
            wordBubble.renderInventory(batch,inventory); // wordBubble draws the inventory count when inventory is open
        }
        player.render(batch, delta, wordBubble);
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // adjust because libGDX Y is flipped
        //This is the beds location, if it is touched, the player goes to sleep
        if(mouseX < 300 && mouseX > 50 && mouseY >300 && mouseY < 360 ){
            if(Gdx.input.justTouched()) {
                player.showEmoji();//The method is invoked
            }
        }
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void show() {}
    @Override public void hide() {}
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        batch.dispose();
        homeBackground.dispose();
    }
}
