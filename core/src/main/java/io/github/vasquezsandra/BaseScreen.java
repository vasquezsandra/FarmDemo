package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class BaseScreen implements Screen {
    private final FarmTycoonGame game;
    private final SpriteBatch batch;//Batches of textures
    private final Stage stage;//New stage for the game objects

    private final Texture background;//Game backgrond
    private final TextBubble wordBubble;//game text tab, for the intro
    private final Player player;
    private final BedMenu bedMenu;//Tab menu for plant beds, when they are touched
    private final FarmPlot tomPlot;//plant bed 1
    private final FarmPlot wheatPlot;//Plant bed 2
    private final Plant tomatoPlant;
    private final Plant wheatPlant;
    private final FarmPlot carrotPlot;//Carrot plant bed
    private final Plant carrotPlant;
    private final FarmPlot cucumberPlot;//Cucumber plant bed
    private final Plant cucumberPlant;
    private StaminaBar stamina;
    private UITab uiTab;
    private Inventory inventory;


    /**
     * The base screen and the textures and objects that will appear on the users screen
     * @param game FarmTycoon
     */
    public BaseScreen(FarmTycoonGame game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        this.inventory = game.inventory;  //Inventory for game
        this.stamina = game.stamina; //Game stamina bar
        this.player = game.player; //Player
        this.wordBubble = game.textBubble;
        background = new Texture("BaseScreen.png");
        bedMenu = new BedMenu(inventory, stamina); // Pass the shared stamina bar
        uiTab = new UITab(game, stamina);
        //Multiple textures for each plant
        tomatoPlant = PlantTextures.createTomatoPlant();
        wheatPlant = PlantTextures.createWheatPlant();
        carrotPlant = PlantTextures.createCarrotPlant();
        carrotPlot = new FarmPlot(560, 240, "assets/DirtBeds/dirtPlot.png", "assets/DirtBeds/dirtPlot.png", bedMenu, wordBubble, carrotPlant);
        cucumberPlant = PlantTextures.createCucumberPlant();
        cucumberPlot = new FarmPlot(560, 50, "assets/DirtBeds/dirtPlot.png", "assets/DirtBeds/dirtPlot.png", bedMenu, wordBubble, cucumberPlant);
        tomPlot = new FarmPlot(10, 240, "assets/DirtBeds/tomSeed.png", "assets/DirtBeds/tomSeedHov.png", bedMenu, wordBubble, tomatoPlant);
        wheatPlot = new FarmPlot(10, 50, "assets/DirtBeds/cornSeed.png", "assets/DirtBeds/cornSeedHov.png", bedMenu, wordBubble, wheatPlant);
    }

    /**
     * Renders the base screen and its objects.
     * @param delta
     */
    @Override
    public void render(float delta) {
        player.update(delta);//Updates the player
        bedMenu.update(delta);//Updates bed menu
        batch.begin();//Begins rendering batches in this screen
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());//Presents the background
        stamina.render(batch);//Renders staminas render method
        tomPlot.render(batch);
        wheatPlot.render(batch);
        wordBubble.findLevel(inventory); //Text message for the next level
        //If level is one, new plots are rendered to screen
        if (wordBubble.findLevel(inventory) == 1) {
            carrotPlot.render(batch);
            cucumberPlot.render(batch);
        }
        uiTab.render(batch);
        //Renders the inventory text to inventory tab
        if (uiTab.isInventory()) {
            wordBubble.renderInventory(batch,inventory); // wordBubble draws the inventory count when inventory is open
        }
        tomPlot.update(delta);
        wheatPlot.update(delta);
        if (wordBubble.findLevel(inventory) == 1) {
            cucumberPlot.update(delta);
            carrotPlot.update(delta);

        }
        player.render(batch, delta, wordBubble);
        bedMenu.render(batch, player);
        wordBubble.render(batch, tomPlot, wheatPlot );

        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        player.dispose();
        bedMenu.dispose();
        tomPlot.dispose();
        wheatPlot.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
