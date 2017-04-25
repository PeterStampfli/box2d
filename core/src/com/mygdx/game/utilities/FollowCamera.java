package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * An orthographic camera that follows a game subject.
 * For debugging you can move it with the keyboard arrows, no zoom.
 */

public class FollowCamera extends OrthographicCamera {

    private final float POSITION_CHANGE = 0.02f;
    public float gameWorldWidth, gameWorldHeight;    // game world left lower corner at (0,0), do not show outside
    public boolean debugAllowed = false;
    public boolean isFollowing = true;

    /**
     * Sets if moving the camera with the keyboard is allowed.
     *
     * @param allowed boolean, true to allow debugging
     * @return this, for chaining.
     */
    public FollowCamera setDebugAllowed(boolean allowed) {
        debugAllowed = allowed;
        return this;
    }

    /**
     * Set width and height of the game world, lower left corner is at (0,0). The camera shows only
     * the region x=0...width and y=0...height.
     *
     * @param width
     * @param height
     * @return this, for chaining
     */
    public FollowCamera setGameWorldSize(float width, float height) {
        gameWorldWidth = width;
        gameWorldHeight = height;
        return this;
    }

    /**
     * Follow the position of a game object.
     * If debugging is allowed, then you can use enter key to go in debug mode. The camera won't
     * follow the object. Move the camera with the keyboard arrow keys. Leave debugging with the right shift key.
     *
     * @param x float, x-coordinate of the game object
     * @param y float, y-coordinate of the game object
     */
    public void follow(float x, float y) {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)) {
            isFollowing = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            isFollowing = false;
        }
        if (isFollowing || !debugAllowed) {
            this.position.x = x;
            this.position.y = y;
        } else {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                this.position.y += POSITION_CHANGE * this.viewportHeight;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                this.position.y -= POSITION_CHANGE * this.viewportHeight;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                this.position.x += POSITION_CHANGE * this.viewportWidth;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                this.position.x -= POSITION_CHANGE * this.viewportWidth;
            }
        }
        // limit the camera position to keep its view inside the game world
        float half = 0.5f * this.viewportWidth;
        this.position.x = MathUtils.clamp(this.position.x, half, gameWorldWidth - half);
        half = 0.5f * this.viewportHeight;
        this.position.y = MathUtils.clamp(this.position.y, half, gameWorldHeight - half);
    }

    /**
     * Follow the position of a game object.
     * If debugging is allowed, then you can use enter key to go in debug mode. The camera won't
     * follow the object. Move the camera with the keyboard arrow keys. Leave debugging with the right shift key.
     *
     * @param position Vector2 position of the game object
     */
    public void follow(Vector2 position) {
        follow(position.x, position.y);
    }

    /**
     * Follow the position of a game object.
     * If debugging is allowed, then you can use enter key to go in debug mode. The camera won't
     * follow the object. Move the camera with the keyboard arrow keys. Leave debugging with the right shift key.
     *
     * @param sprite, follow this sprite (or subclass)
     */
    public void follow(Sprite sprite) {
        follow(sprite.getX(), sprite.getY());
    }
}
