package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;

public class TextBubble {
    private Texture cloud;
    private boolean intro = true;//Into is first set to true.
    private boolean showMessage = true;
    private BitmapFont font;
    private int level = 0;
    //The intro for the game
    private String[] introLines = {//Intro dialogue
            "Welcome to Farm Tycoon.\nYou must be the new helper!",
            "Lets start you off with a couple of seeds to take care of.",
            "Make sure you water them so they can grow!",
            "A plant is ready to collect after being watered 3 times.",
            "If you run out of stamina, go home and rest."
    };
    //Next level message
    private String[] levelUpMessage = {
    "Youre doing well with the plants! \nHere are two more plots to take care of!",
            "You make gardening look easy!\nLets see how you do with animals.."
    };
    private String waterDoneMessage = "Watering is completed!";
    private String staminaMessage = "You need more stamina!";
    private Texture messageCloud;
    private int currentLineIndex = 0;//Default index for the line of message


    TextBubble() {
        cloud = new Texture("textBuble.png");
        messageCloud = new Texture("textBuble.png");
        font = new BitmapFont(); //Default font
        font.setColor(Color.WHITE);//Text colot
        font.getData().setScale(2f);//Text size


    }
    //Sets into to false
    public void introDone() {
        intro = false;
    }
    //Sets show message to false
    public void messageDone(){
        showMessage = false;
    }
    //Checks to see if into is true or false
    public boolean isIntro() {
        return intro;
    }
    //If either plant is collected, the player levels up to level 1
    public int findLevel(Inventory inventory){
        if(inventory.getPlantCount("wheat") > 5 || inventory.getPlantCount("tomato")> 3)
            return level = 1;
        return 0;
    }
    //This will show the watering message when called
    public void showWateringMessage(SpriteBatch batch, float x, float y) {
        font.draw(batch, waterDoneMessage, x+90 , y+80 );
    }
    //This will print the stamina bar
    public void renderStamina(SpriteBatch batch){
        font.draw(batch, staminaMessage, 190, 50);
    }
    //This prints the inventory by getting the count of each plant.
    public void renderInventory(SpriteBatch batch, Inventory inventory) {
        // Get the counts for each plant type
        int tomatoCount = inventory.getPlantCount("tomato");
        int wheatCount = inventory.getPlantCount("wheat");
        int carrotCount = inventory.getPlantCount("carrot");
        int cucumberCount = inventory.getPlantCount("cucumber");

        // Formated string for plant inventory
        String formattedInventory = String.format(
                wheatCount+ "            "+carrotCount+"\n\n" +
                tomatoCount+        "            0\n\n"+
                      cucumberCount+  "             0\n"
        );

        //Print the inventory
        font.draw(batch, formattedInventory, 650, 400);
    }



    public void render(SpriteBatch batch, FarmPlot x, FarmPlot y) {
        //Mouse input
        int mouseX = Gdx.input.getX();
        int mouseY = Gdx.input.getY();
        //If the player is at the intro, message appears
        if (intro && level ==0) {
            batch.draw(cloud, 5, -100, 650, 350);//The texture for the message
            font.draw(batch, //The message
                    introLines[currentLineIndex], 90, 150, 460, Align.left, true); // wrap text
            //Next button range
            if ((mouseX > 465 && mouseX < 535) && (mouseY > 410 && mouseY < 445)) {
                if (Gdx.input.justTouched()) {//Follwing actions invoked when next button is clicked
                    currentLineIndex++;//Next line on the array appears.
                    if (currentLineIndex >= introLines.length) {//Last line
                        introDone();//end of the intro, can be set to false now.

                    }

                }
            }
        }
        if (level == 1 && showMessage == true) {
            batch.draw(cloud, 5, -100, 650, 350);
            font.draw(batch, levelUpMessage[0], 90, 150, 460, Align.left, true);
            if ((mouseX > 465 && mouseX < 535) && (mouseY > 410 && mouseY < 445)) {
                if (Gdx.input.justTouched()) {
                    messageDone();
                }
            }
        }
    }

    public void dispose() {
      cloud.dispose();
      font.dispose();
    }

}
