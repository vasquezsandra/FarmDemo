package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UITab {
    private FarmTycoonGame game;
    private Texture[] tabs;
    private int currentTab = 0;
    private boolean isInventory = false;
    private Texture[]inventoryScreens;
    private StaminaBar stamina;


    public UITab(FarmTycoonGame game, StaminaBar stamina) {
        this.game = game;
        //This is the tabs for UI panel
        tabs = new Texture[] {
                new Texture("assets/UITab/UITAB.png"), //Default tab
                new Texture("assets/UITab/inventoryUI.png"), //Inventory hover
                new Texture("assets/UITab/homeUI.png"), //Home hover
                new Texture("assets/UITab/farmUI.png")//Star button hover
        };
        //This is the inventory tab screen
        inventoryScreens = new Texture[]{
                new Texture("assets/UITab/inventoryTab.png"),//Default
                new Texture("assets/UITab/inventoryExit.png")//Exit is hovered
        };
        this.stamina = stamina;
    }

    /**
     * This method will find the texture that needs to return back to the users sreen
     * @return UI texture
     */
    public int findTab() {
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

        //If mouse is hovered over inventory button
        if (mouseX > 730 && mouseX < 770 && mouseY >24 && mouseY<74) {
            currentTab = 1; //Inventory button interaction
            //If button is clicked, inventory is opened
            if(Gdx.input.justTouched()){
                openInventory();
            }
            //If mouse is hovered over home button
        } else if(mouseX > 784 && mouseX < 827 && mouseY >24 && mouseY<74){
            currentTab=3;
            //And if that button is clicked, we go home
            if(Gdx.input.justTouched()){
                game.setScreen(game.baseScreen);
            }

        }else if(mouseX >677 && mouseX <717 && mouseY >24 && mouseY<74){
            currentTab = 2;//Star button interaction
            //If star button is clicked, we return to farm (base screen)
            if(Gdx.input.justTouched()){
                game.setScreen(game.homeScreen);
            }
        } //The default UI Tab
        else {
            currentTab = 0; // Default
        }

        return currentTab;
    }

    /**
     * This method will get the current tab.
     * @return The array index for tabs.
     */
    public Texture getTab() {
        findTab(); // Updates which tab texture to show
        return tabs[currentTab]; //Returns index for Texture array.
    }
    public boolean openInventory(){
        System.out.println("Inventory opened");
        return isInventory = true;
    }
    public boolean closeInventory(){
        return isInventory = false;
    }
    public boolean isInventory() {
        return isInventory;
    }

    public void render(SpriteBatch batch) {
        batch.draw(getTab(), 650, -50, 200, 200);//Draws the panel by which one is interacted with
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        //If inventory is opened, draw it to the screen
        if(isInventory){
            batch.draw(inventoryScreens[0], 550, 200, 300, 300);
            //If the player clicks on the x, close inventory
            if(mouseX >768 && mouseX < 812 && mouseY >425 && mouseY < 490){
                batch.draw(inventoryScreens[1], 550, 200, 300, 300);
                if(Gdx.input.justTouched()){
                    closeInventory();
                }
            }


        }
    }
}