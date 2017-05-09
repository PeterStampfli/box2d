package com.mygdx.game.physics;

/**
 * Read  and interpolate position and angle of body, convert to graphics data and
 * provide data for sprite.
 */

public class BodyInterpolator {


    private float previousBodyAngle, newBodyAngle;
    private float previousBodyWorldCenterX, newBodyWorldCenterX;   // using pixel units
    private float previousBodyWorldCenterY, newBodyWorldCenterY;

    private float progress;
}
