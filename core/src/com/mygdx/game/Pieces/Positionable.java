package com.mygdx.game.Pieces;

/**
 * Objects that have methods to set and read position and angle
 */

public interface Positionable {

    void setPositionAngle(float x, float y, float angle);

    float getX();

    float getY();

    float getAngle();
}
