package io.github.vasquezsandra;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class StaminaBar {
    private Texture[] staminaBar;
    private int playerStamina = 100;//Player starts with 100 stamina
    private int staminaIndex = 0;//Index position for the stamina texture bar

    public StaminaBar(){
        staminaBar = new Texture[]{
                new Texture("assets/StaminaBar/fullStamina.png"),
                new Texture("assets/StaminaBar/70Stamina.png"),
                new Texture("assets/StaminaBar/60Stamina.png"),
                new Texture("assets/StaminaBar/50Stamina.png"),
                new Texture("assets/StaminaBar/35Stamina.png"),
                new Texture("assets/StaminaBar/15Stamina.png"),
                new Texture("assets/StaminaBar/emptyStamina.png")
        };
    }

    /**
     * This method will return which index the array for stamina bar should be at
     * based on how much stamina a player has
     * @return Index for stamina bar
     */
    public int findStamina(){
        if(playerStamina == 100){
           return staminaIndex = 0;
        } else if (playerStamina >= 85){
           return staminaIndex = 1;
        } else if (playerStamina >= 75) {
           return staminaIndex = 2;
        } else if (playerStamina >=60 ){
           return staminaIndex = 3;
        }else if(playerStamina >= 40){
           return staminaIndex = 4;
        }else if (playerStamina >= 20){
            return staminaIndex = 5;
        } else{
           return staminaIndex = 6;
        }
    }
    //Will find what bar to return
    public Texture getStamina(){
        findStamina();
        return staminaBar[staminaIndex];
    }
    //Will retrieve player stamina
    public int getPlayerStamina(){
        return playerStamina;
    }
    //Subtracts from stamina
    public int takeStamina(int value){
        playerStamina -= value;
        return playerStamina;
    }
    //Sets a value for stamina
    public int setPlayerStamina(int value){
       return playerStamina = value;
    }

    public void render(SpriteBatch batch){
        batch.draw(getStamina(), 645, 10, 220, 220);
    }

}
