package io.github.vasquezsandra;

import com.badlogic.gdx.graphics.Texture;

public class Plant {
    private String plantName;
    private Texture[] growthStages;//Multiple stages of growth
    private int currentStage = 0;//Deafult stage of plant
    private float[] stageDurations;//Time until its ready to be watered


    public Plant(Texture[] growthStages, float[] stageDurations, String plantName) {
        this.growthStages = growthStages;
        this.stageDurations = stageDurations;
        this.plantName = plantName;
    }

    /**
     * When this method is called by water click, the current stage of the plant
     * increases by one.
     */
    public void water() {
        if (currentStage < stageDurations.length - 1) {
            currentStage++;
        }
    }

    /**
     * This represents the current texture of a plant, based of the growth stage it is at
     * @return The current plant texture
     */
    public Texture getCurrentTexture() {
        return growthStages[currentStage];
    }
    //Gets the current stage it is at
    public float getCurrentStageDuration() {
        return stageDurations[currentStage];
    }
    public String getPlantName() {
        return plantName;
    }
    //Boolean is true if the plant is equal to its stages
    public boolean isFullyGrown() {
        return currentStage == growthStages.length - 1;
    }
    //Resets its stages
    public void reset() {
        currentStage = 0;
    }

}
