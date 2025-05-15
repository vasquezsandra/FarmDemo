package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FarmPlot {
    private float x, y, width, height;
    private Texture normalTexture, hoverTexture, plots;
    private Plant plant;
    private BedMenu bedMenu;
    private TextBubble intro;
    private Texture readyToWaterIcon;
    private float waterTimer = 0f;
    private boolean readyToWater = false;

    //Farmplot objects
    public FarmPlot(float x, float y, String normalPath, String hoverPath, BedMenu bedMenu, TextBubble intro, Plant plant) {
        this.x = x;
        this.y = y;
        this.width = 300;
        this.height = 300;
        this.normalTexture = new Texture(normalPath);
        this.hoverTexture = new Texture(hoverPath);
        this.bedMenu = bedMenu;
        this.intro = intro;
        this.plant = plant;
        plots = new Texture("assets/DirtBeds/dirtPlot.png");//empty plant bed for the intro
        readyToWaterIcon = new Texture("assets/readyToWater.png");
    }

    public float getX(){
        return x;
    }
    public float getY(){
        return y;
    }
    //Returns readyToWater boolean
    public boolean isReadyToWater() {
        return readyToWater;
    }
    //Updates FarmPlot
    public void update(float delta) {
        //If plant is done watering, this adds to the cool down for the next watering
        if (plant != null && !plant.isFullyGrown() && !readyToWater) {
            waterTimer += delta;//
        //When the cooldown is equal to the plants duration, it is ready to be watered
            if (waterTimer >= plant.getCurrentStageDuration()) {
                readyToWater = true;
                waterTimer = 0f;
            }
        }
    }


    public void render(SpriteBatch batch) {
        //To get the coordinate inputs neater
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();//This makes y coordinate bigger going up.
        //When mouse is around these coordinates, plot beds should hover.
        boolean isHovering = mouseX >= x + 40 && mouseX <= x + 270
                && mouseY >= y + 70 && mouseY <= y + 230;

        batch.draw(plots, x, y, width, height);
        //I didnt want the player to be able to open the bed menu before the intro, so i set this boolean.
        if(!intro.isIntro()) {//When player is done with the intro, they can open bed menu.
            if (isHovering) {
                batch.draw(hoverTexture, x, y, width, height);
                if (isHovering && Gdx.input.isTouched()) {//only when a player hovers over and clicks on plant bed, does the menu open.
                    bedMenu.open(this);//opens the menu
                }
            } else {
                batch.draw(normalTexture, x, y, width, height);
            }
            if (plant != null) {
                batch.draw(plant.getCurrentTexture(), x , y , 300, 300);
            }
            if (!intro.isIntro() && isReadyToWater()) {
                //This is the water icon, when the plant needs to be watered
                batch.draw(readyToWaterIcon, x + 200, y + 160, 100, 100);
            }
        }
    }
    //When this method is invoked, the plant to done watering and the timer is reset
    public void resetWaterState() {
        readyToWater = false;
        waterTimer = 0f;

    }
    //This gets the current plant
    public Plant getPlant() {
        return plant;
    }

    public void dispose() {
        normalTexture.dispose();
        hoverTexture.dispose();
    }
}