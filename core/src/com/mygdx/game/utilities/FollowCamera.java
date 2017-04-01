package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * a camera that follows a game subject
 * for debugging you can move it with the keyboard-keypad arrows, no zoom
 */

public class FollowCamera extends OrthographicCamera{

    public float gameWorldWidth,gameWorldHeight;    // game world left lower corner at (0,0), do not show outside
    public boolean debugAllowed=false;
    public boolean isFollowing=true;
    private final float POSITION_CHANGE=0.02f;

    /**
     * sets if debuging is allowed. returns object for chaining
     * @param allowed
     * @return
     */
    public FollowCamera setDebugAllowed(boolean allowed){
        debugAllowed=allowed;
        return  this;
    }

    /**
     * set width and height of the game world, lower left corner is at (0,0)
     * @param width
     * @param height
     * @return
     */
    public FollowCamera setGameWorldSize(float width, float height){
        gameWorldWidth=width;
        gameWorldHeight=height;
        return this;
    }

    /**
     * follow the position, use enter key to go in debug mode if debugging allowed (default)
     * in debugging move the camera with the arrow keys, leave debugging with right shift key
     * @param x
     * @param y
     */
    public void follow(float x,float y){
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
            isFollowing=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            isFollowing=false;
        }
        if (isFollowing||!debugAllowed) {
            this.position.x=x;
            this.position.y=y;
        }
        else
        {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)){
                this.position.y+=POSITION_CHANGE*this.viewportHeight;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
                this.position.y-=POSITION_CHANGE*this.viewportHeight;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                this.position.x+=POSITION_CHANGE*this.viewportWidth;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
                this.position.x-=POSITION_CHANGE*this.viewportWidth;
            }
        }
        float half=0.5f*this.viewportWidth;
        this.position.x=MathUtils.clamp(this.position.x,half,gameWorldWidth-half);
        half=0.5f*this.viewportHeight;
        this.position.y=MathUtils.clamp(this.position.y,half,gameWorldHeight-half);
    }

    /**
     * follow the position, use enter key to go in debug mode if debugging allowed (default)
     * in debugging move the camera with the arrow keys, leave debugging with right shift key
     * @param position
     */
    public void follow(Vector2 position){
        follow(position.x,position.y);
    }

    /**
     * follow the position, use enter key to go in debug mode if debugging allowed (default)
     * in debugging move the camera with the arrow keys, leave debugging with right shift key
     * @param sprite
     */
    public void follow(Sprite sprite){
        follow(sprite.getX(),sprite.getY());
    }
}
