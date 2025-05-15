package io.github.vasquezsandra;

import com.badlogic.gdx.graphics.Texture;

public class PlantTextures {
//Creates the stages for Tomatoes, as well as its duration times
    public static Plant createTomatoPlant() {
        Texture[] stages = {
                new Texture("assets/DirtBeds/tomSeedHov.png"),
                new Texture("assets/Plants/tomWater1.png"),
                new Texture("assets/Plants/tomWater2.png"),
                new Texture("assets/Plants/tomReady.png")
        };
        float[] durations = {1f, 1f, 1f, 1f};//Array of duration for each watering phase
        return new Plant(stages, durations, "Tomato");//Returns the texture stages and duration to Plant class
    }

    public static Plant createWheatPlant() {
        Texture[] stages = {
                new Texture("assets/DirtBeds/cornSeedHov.png"),
                new Texture("assets/Plants/cornWater1.png"),
                new Texture("assets/Plants/cornWater2.png"),
                new Texture("assets/Plants/cornReady.png")
        };
        float[] durations = {1f, 1f, 1f, 1f};
        return new Plant(stages, durations, "Wheat");
    }


    public static Plant createCarrotPlant() {
        Texture[] stages = {
                new Texture("assets/Plants/carrotSeedling.png"),
                new Texture("assets/Plants/carrotPhase1.png"),
                new Texture("assets/Plants/carrotPhase2.png"),
                new Texture("assets/Plants/carrotPhase3.png")
        };
        float[] durations = {1f, 1f, 2f, 2f};
        return new Plant(stages, durations, "Carrot");
    }
    public static Plant createCucumberPlant() {
        Texture[] stages = {
                new Texture("assets/Plants/cucumberSeedlings.png"),
                new Texture("assets/Plants/cuccumberPhase1.png"),
                new Texture("assets/Plants/cucumberPhase2.png"),
                new Texture("assets/Plants/cucumberPhase3.png")
        };
        float[] durations = {1f, 1f, 2f, 2f};
        return new Plant(stages, durations, "Cucumber");
    }
}
