package com.mygdx.game.Pieces;

/**
 * Objects that have methods to set and read position and angle
 */

public interface Movable {

    public void setPositionAngle(float x,float y,float angle);

    public float getX();

    public float getY();

    public float getAngle();
}
