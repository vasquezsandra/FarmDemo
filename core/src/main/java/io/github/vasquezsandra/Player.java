package io.github.vasquezsandra;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {
    private GameSprite playerSprite;

    public static final float SPEED = 120;//walking sprite speed
    private float x, y;//sprite coordinates

    private enum State { IDLE, MOVING, WATERING, COLLECT, SLEEP}//Diffent states of sprites.
    private State currentState = State.IDLE;//default state
    private float wateringMessageTimer = 0f;
    private boolean showWateringMessage = false;
    private Plant plantToWater = null; // holds reference to the plant being watered
    private Plant plantToCollect = null;
    private boolean showEmoji = false;
    private Texture emojiSheet;
    private TextureRegion selectedEmoji;
    private float emojiTimer = 0f; //Sleep timer
    private StaminaBar stamina;


    public Player(StaminaBar stamina) {
        playerSprite = new GameSprite("assets/GameSprites/walkSheet.png");
        x = 400;//x coordinate for deafult sprite
        y = 100;//y coordinate for default sprite
        emojiSheet = new Texture("assets/GameSprites/Emoji_Spritesheet_Free.png");
        TextureRegion[][] emojiRegions = TextureRegion.split(emojiSheet, 32, 32);
        selectedEmoji = emojiRegions[6][2]; //Sleep emoji
        this.stamina = stamina;
    }

    public void update(float delta) {
        if (currentState == State.WATERING){
            return;//Skips input while doing an action
        }
        else if(currentState ==State.COLLECT){
            return;
        }
        if (currentState == State.SLEEP) {
            emojiTimer -= delta;
            if (emojiTimer <= 0f) {
                hideEmoji(); //Hide the sleep emoji
                stamina.setPlayerStamina(100);
                currentState = State.IDLE; //Return to idle after sleeping
            }
            return; //Don't move when sleeping
        }
        boolean moving = false;
       //Walking animation sprite.
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {//Left animation
            x -= SPEED * delta;
            playerSprite.setAnimation(2);//The array index from the walking sprite sheet
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {//Right animation
            x += SPEED * delta;
            playerSprite.setAnimation(3);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {//Up animation
            y += SPEED * delta;
            playerSprite.setAnimation(1);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {//Down animation
            y -= SPEED * delta;
            playerSprite.setAnimation(0);
            moving = true;
        }
        //If the player is idle, reset statetime
        if (!moving) {
            playerSprite.resetStateTime();
            currentState = State.IDLE; //Changes current state to idle
        } else {
            currentState = State.MOVING;
        }
        //This has the watering message appear until the timer is off.
        if (showWateringMessage) {
            wateringMessageTimer -= delta;
            if (wateringMessageTimer <= 0f) {
                showWateringMessage = false;
            }
        }

    }
    //This will show the sleep emoji
    public void showEmoji() {
        showEmoji = true;//Boolean set to true
        emojiTimer = 5f;//Will be shown for 5 seconds
        currentState = State.SLEEP;//Changes the player state

    }
    //This will hide the sleep emoji
    public void hideEmoji() {
        showEmoji = false;//Set to false, player not sleeping
    }

    //This method will render differnt sprite action.
    public void render(SpriteBatch batch, float delta,TextBubble message) {
        boolean isMoving = currentState == State.MOVING;
        switch (currentState) {
            case COLLECT: //Players current state is collect
                batch.draw(
                        playerSprite.getActionFrame(2, delta),
                        x, y,
                        GameSprite.CHAR_W_PIXEL + 20, GameSprite.CHAR_H_PIXEL
                );//Collect sprite animation
                //After 2 seconds, the player goes back to being idle
                if (playerSprite.getStateTime() > 1.5f) {
                    currentState = State.IDLE;
                    plantToCollect.reset();//This will reset the plans to stage 1
                }
                break;
            //This is watering action
            case WATERING:
                batch.draw(
                        playerSprite.getActionFrame(10, delta),//frame for watering sprite
                        x + 5, y,//Adjusting sprite watering action to coordinate with default
                        GameSprite.CHAR_W_PIXEL + 20, GameSprite.CHAR_H_PIXEL//resizing to match deafult sprite
                );//Watering sprite action
                //After 1.5 seconds, the player is back to being idle and watering message appears
                if (playerSprite.getStateTime() > 1.5f) {
                    currentState = State.IDLE;
                    playerSprite.resetStateTime();
                    showWateringMessage = true;
                    wateringMessageTimer = 1.5f;
                }
                if (plantToWater != null && playerSprite.getStateTime() == 0) {
                    plantToWater.water(); //Invokes the plant watering stages
                    plantToWater = null; //Resets the plant
                }

                break;
            case SLEEP://Current state is sleep
                batch.draw(
                        selectedEmoji,
                        252,
                        320,
                        48, 48
                );//The sleeping emoji will be placed above the bed

                break;

            //This is the default action
            default://Default
                batch.draw(
                        playerSprite.getCurrentFrame(isMoving, delta),
                        x, y,
                        GameSprite.CHAR_W_PIXEL, GameSprite.CHAR_H_PIXEL
                );
                break;
        }
        if (showWateringMessage) {
            message.showWateringMessage(batch, x, y + 80);
        }

    }
    //When this method is invoked, currentstate =watering.
    public void startWatering(Plant plant) {
        currentState = State.WATERING;
        playerSprite.resetStateTime();
        plantToWater = plant;
    }
    //This method sets current state to collect
    public void startCollection(Plant plant) {
        currentState = State.COLLECT;
        plantToCollect = plant;
    }

    public void dispose() {
        playerSprite.dispose();
    }
}
