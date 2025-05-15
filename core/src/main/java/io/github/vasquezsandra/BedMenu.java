package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BedMenu {
    private Texture menu, exTab, selectWater, selectCollect;
    private boolean isOpen = false;
    private FarmPlot selectedPlot;
    private boolean canWater = true;
    private Inventory inventory;
    private boolean needsStamina = false;
    private StaminaBar stamina;
    private float waterCooldown = 0f;
    private TextBubble message;
    private float staminaMessageTimer = 0f;
    private final float STAMINA_MESSAGE_DURATION = 2f; // 2 seconds

    /**
     * Objects for the bed menu
     * @param inventory Game inventory
     * @param stamina Game stamina
     */
    public BedMenu(Inventory inventory, StaminaBar stamina) {
    //Textures for the plant bed tab.
        menu = new Texture("assets/DirtBeds/dirtMenu.png");
        exTab = new Texture("assets/DirtBeds/exTab.png");
        selectWater = new Texture("assets/DirtBeds/selectWater.png");
        selectCollect = new Texture("assets/DirtBeds/selectCollect.png");
        this.inventory = inventory;
        this.stamina = stamina;
        message = new TextBubble();
    }

    /**
     * The bed menu is open method. Returns isOpen is true for selected plot.
     * @param plot The selected plot.
     */
    public void open(FarmPlot plot) {
        isOpen = true;
        selectedPlot = plot;
    }
    //When this method is invoked, boolean is false, plant bed tab is closed.
    public void close() {
        isOpen = false;
    }

    /**
     * This updates the bed menu
     * @param delta
     */
    public void update(float delta) {
        //If can water is not true
        if (!canWater) {
            waterCooldown += delta;// Water cooldown is added for next timer
            //Get the selected plot's watering duration
            if (selectedPlot != null && selectedPlot.getPlant() != null) {
                float currentStageDuration = selectedPlot.getPlant().getCurrentStageDuration();
               //When the cooldown is greater than the plants duration, can water is true.
                if (waterCooldown >= currentStageDuration) {
                    canWater = true;
                    waterCooldown = 0f;
                }
            }
        }
        //If player needs stamina, show message for certain time.
         if (needsStamina) {
            staminaMessageTimer -= delta;
            //When timer is 0, set needs stamina to false, message goes away.
            if (staminaMessageTimer <= 0f) {
                needsStamina = false;
            }
        }
    }

    /**
     * This method will return the amount of crops a plant adds to inventory after collected
     * @param plantName The plants name.
     * @return The crop amount
     */
    private int getAmountToAdd(String plantName) {
        switch (plantName.toLowerCase()) {
            case "tomato":
                return 4;
            case "wheat":
                return 6;
            case "carrot":
                return 5;
            case "cucumber":
                return 3;
            default:
                return 0;
        }
    }

    /**
     * Renders the bed menu
     * @param batch
     * @param player
     */
    public void render(SpriteBatch batch, Player player) {
       //Only render when it is open
        if (!isOpen) return;
        //When it is open, draw the bed menu
        batch.draw(menu, 200, 60, 500, 400);//Where the sprite appears on screen
        if (needsStamina) {
            message.renderStamina(batch);
        }

        //If the players mouse is on the x, the button will highlight.
        //If player clicks the x, the bed menu closes
        if (Gdx.input.getX() > 520 && Gdx.input.getX() < 545 && Gdx.input.getY() > 110 && Gdx.input.getY() < 300) {
            batch.draw(exTab, 200, 60, 500, 400);
            if (Gdx.input.justTouched())
                close();//isOpen is set to false
        }

        // User is able to click the water button if the timer cooldown is at 0.
        //Clicking the button will invoke the startWatering animation.
        if (Gdx.input.getX() > 343 && Gdx.input.getX() < 433) {
            if (Gdx.input.getY() > 160 && Gdx.input.getY() < 200 && canWater && selectedPlot != null) {
                batch.draw(selectWater, 200, 60, 500, 400);
                if (Gdx.input.isTouched() && canWater) {
                    canWater = false;//Resets boolean
                    player.startWatering(selectedPlot.getPlant());//Begin start watering animation for selected plot
                    selectedPlot.resetWaterState();
                    close(); //isOpen is set to false
                }
            } else if (Gdx.input.getY() > 230 && Gdx.input.getY() < 270 && selectedPlot != null) { // Collect button
                Plant plant = selectedPlot.getPlant();//Collect action for the selected plant
                //Only allow interaction from the collect button if it is fully grown
                if (plant.isFullyGrown()) {
                    batch.draw(selectCollect, 200, 60, 500, 400);
                    //When the button is clicked
                    if (Gdx.input.justTouched()) {
                        //Appear if the player clicks collect, but does not have enough stamina
                        if (plant != null && stamina.getPlayerStamina() < 10) {
                            needsStamina = true;
                            staminaMessageTimer = STAMINA_MESSAGE_DURATION; //Starts the 2 seconds timer
                            close();//Closes bed menu
                        }
                        //If the player has stamina, the collect action is done
                        if (plant != null && stamina.getPlayerStamina() >= 10) {
                            // You could add a counter to track collected plants here
                            player.startCollection(plant);//Sprite action
                            String plantName = plant.getPlantName();//Gets the name of plant
                            int amountToAdd = getAmountToAdd(plantName);  //Gets the amount to add for the plany
                            inventory.collectPlant(plantName, amountToAdd);//Then adds that ammount to the inventory
                            stamina.takeStamina(15);//removes 15 stamina for each time player collects a plant
                            close();//Closes bed menu
                        }

                    }
                }
            }
        }
    }
    public void dispose() {
        menu.dispose();
        exTab.dispose();
        selectWater.dispose();
        selectCollect.dispose();
    }
}