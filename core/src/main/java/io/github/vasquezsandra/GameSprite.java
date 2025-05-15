package io.github.vasquezsandra;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameSprite {
    private Texture walkSheet;
    private Animation<TextureRegion>[] animations;//Collection of walking actions
    private TextureRegion[] idleFrames;
    private Texture actionSheet;
    private Animation<TextureRegion>[] actionAnimation;//Collection of other actions
    private float stateTime;
    private int currentAnimation;

    public static final int CHAR_W_PIXEL = 190;
    public static final int CHAR_H_PIXEL = 200;
    public static final float WALK_SPEED = 0.1f;
    public static final int CHAR_WIDTH = CHAR_W_PIXEL * 6;
    public static final int CHAR_HEIGHT = CHAR_H_PIXEL * 4;

    public GameSprite(String spriteSheetPath) {
        walkSheet = new Texture(spriteSheetPath);//New texture object, this is the walk sprite sheet
        actionSheet = new Texture("assets/GameSprites/spriteActions.png");//sprite action sheet
        //Array which will store the rows & columns of the sprite sheets.
        TextureRegion[][] walkSpriteSheet = TextureRegion.split(walkSheet, CHAR_W_PIXEL, CHAR_H_PIXEL);
        TextureRegion[][] actionSpriteSheet = TextureRegion.split(actionSheet, 48, 48);
        //Creates the amount of ways to split into the array
        animations = new Animation[4];
        //Assign sprites to array index
        animations[0] = createAnimation(walkSpriteSheet[0]); // down
        animations[1] = createAnimation(walkSpriteSheet[1]); // up
        animations[2] = createAnimation(walkSpriteSheet[2]); // left
        animations[3] = createAnimation(walkSpriteSheet[3]); // right
        //Creates the amount of ways to split
        actionAnimation = new Animation[12];
        actionAnimation[2] = createAnimation(actionSpriteSheet[2]);//This is the collecting action
        actionAnimation[10] = createAnimation(actionSpriteSheet[10]); //Watering action

        //Idle action, when sprite gets no input
        idleFrames = new TextureRegion[]{
                walkSpriteSheet[0][0], // Down
                walkSpriteSheet[1][0], // Up
                walkSpriteSheet[2][0], // Left
                walkSpriteSheet[3][0]  // Right
        };
        stateTime = 0f;
        currentAnimation = 0; //Default
    }
    //Creates the animation for sheet
    private Animation<TextureRegion> createAnimation(TextureRegion[] spriteRow) {
        return new Animation<>(WALK_SPEED, spriteRow);
    }
    //This method will return frame
    public TextureRegion getCurrentFrame(boolean moving, float delta) {
        if (moving) {
            stateTime += delta;
            //When player is moving, that current frame is returned
            return animations[currentAnimation].getKeyFrame(stateTime, true);
        } else {
            return idleFrames[currentAnimation];
        }
    }
//This method will return the current frame from the action animation.
    public TextureRegion getActionFrame(int index, float delta) {
        stateTime += delta;
        return actionAnimation[index].getKeyFrame(stateTime, false);
    }
//Sets the current animation to the specified index
    public void setAnimation(int animationIndex) {
        if (animationIndex >= 0 && animationIndex < animations.length) {
            if (currentAnimation != animationIndex) {
                resetStateTime();
            }
            currentAnimation = animationIndex;
        }
    }
    //Sets the state time to 0
    public void resetStateTime() {
        stateTime = 0f;
    }
    //Returns state time
    public float getStateTime() {
        return stateTime;
    }

    public void dispose() {
        walkSheet.dispose();
        actionSheet.dispose();
    }
}
