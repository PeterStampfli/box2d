package com.mygdx.game.physics;

/**
 * Created by peter on 3/23/17.
 *
 * for objects of the graphics world that can be positioned to follow box2D body
 */

public interface Positionable {

    /**
     * set the center of gravity = center of rotation and scaling
     * for Sprite it is Origin (use world coordinates to set origin????)
     * for Body it is localCenter
     * @param x
     * @param y
     */
    public void setLocalCenter(float x,float y);

    /**
     * set position (of the sprite)
     * @param x
     * @param y
     */
    public void setPosition(float x,float y);

    /**
     * set the angle of the sprite around "origin". Angle is in radians.
     * @param angle in radians
     */
    public void setAngle(float angle);
}
