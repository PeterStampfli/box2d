package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Pool;

/**
 * Interpolates body data to graphics time and dimensions, sets body position and angle
 */

/**
 * Set the angle of the body equal to the sprite's. The bodies center of mass should
 * be at the sprites "origin" for rotation. Convert lengths.
 * Sets the body data used for interpolation.
 * Note that the local center (of mass) rotates with the body around it's "position".
 * Attention: For static bodies worldCenter==position, localCenter==0.
 *
 * body.getLocalCenter
 */

public class BodyGraphicsInterface implements BodyFollower,Pool.Poolable {

    public Body body;
    float progress;
    private float previousBodyAngle, newBodyAngle;
    private float previousBodyPositionX, newBodyPositionX;   // using pixel units
    private float previousBodyPositionY, newBodyPositionY;

    /**
     * set the body to use, for
     *
     * @param body
     */
    public void setBody(Body body) {
        this.body = body;
    }

    /**
     * reset for putting back into the pool
     */
    public void reset(){
        body=null;
    }


    /**
     * Set the angle of the body equal to those of the graphics. The bodies center of mass should
     * be at the sprites "origin" for rotation. Convert lengths.
     * Sets the body data used for interpolation.
     * Note that the local center (of mass) rotates with the body around it's "position".
     * Attention: For static bodies worldCenter==position, localCenter==0.
     */
    public void writePositionAngleOfBody(float x,float y,float angle){
        body.setTransform(x/Physics.PIXELS_PER_METER,
                y/Physics.PIXELS_PER_METER,angle);
        newBodyPositionX=x;
        newBodyPositionY=y;
        newBodyAngle=angle;
        progress=1;
    }

    /**
     * Reads and stores position and angle of the body. Position is converted into pixel units.
     * Used for making the body move the sprite.
     */
    public void readPositionAngleOfBody(){
        previousBodyAngle = newBodyAngle;
        previousBodyPositionX = newBodyPositionX;
        previousBodyPositionY = newBodyPositionY;
        newBodyAngle =body.getAngle();
        Vector2 bodyPosition=body.getPosition().scl(Physics.PIXELS_PER_METER);        // that's always the same object
        newBodyPositionX =bodyPosition.x;
        newBodyPositionY =bodyPosition.y;
    }

    /**
     * Set the interpolation parameter, may be overwritten
     * @param progress float, between 0 (use previous) and 1 (use new data)
     */
    public void interpolatePositionAngleOfBody(float progress){
        this.progress=progress;
    }

    /**
     * get interpolated x-coordinate of position for graphics
     * @return
     */
    public float getGraphicsPositionX(){
        return MathUtils.lerp(previousBodyPositionX, newBodyPositionX,progress);
    }

    /**
     * get interpolated y-coordinate of position for graphics
     * @return
     */
    public float getGraphicsPositionY(){
        return MathUtils.lerp(previousBodyPositionY, newBodyPositionY,progress);
    }

    /**
     * get interpolated angle for graphics
     * @return
     */
    public float getGraphicsAngle(){
        return MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress);
    }

}
