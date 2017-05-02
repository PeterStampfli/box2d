package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * A sprite with a body, using physics for motion
 */

public class PhysicalSprite extends ExtensibleSprite {
    Physics physics;
    Body body;
    private float previousBodyAngle, newBodyAngle;
    private float previousBodyWorldCenterX, newBodyWorldCenterX;   // using pixel units
    private float previousBodyWorldCenterY, newBodyWorldCenterY;

    /**
     * Reset the sprite and put it back in the pool. Free the body !
     */
    @Override
    public void reset(){
        super.reset();
        if (body!=null) {
            physics.world.destroyBody(body);
            body=null;
            physics.bodiesNeedUpdate = true;
        }
    }

    /**
     * Put the sprite back to the pool. The pool for physical sprites. Calls reset in the process.
     */
    @Override
    public void free() {
        physics.physicalSpritePool.free(this);
    }

    /**
     * Usually we first create the sprite with its shapes. Then the body and its fixtures. This
     * determines the center of mass for the body.
     * Thus we set the origin (center of rotation and scaling) of the sprite equal to center of mass of the body.
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Scales from physics dimensions to graphics
     */
    public void setLocalOrigin(){
        Vector2 bodyCenter=body.getLocalCenter();
        setLocalOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
    }

    /**
     * Set the origin of the body and its angle equal to the sprite's. Convert lengths.
     * Sets the body data used here for interpolation.
     */
    public void setPositionAngleOfBody(){
        // set body origin and rotation
        body.setTransform(getWorldOriginX()/Physics.PIXELS_PER_METER,
                          getWorldOriginY()/Physics.PIXELS_PER_METER,getAngle());
        previousBodyAngle=getAngle();
        newBodyAngle=getAngle();
        previousBodyWorldCenterX=getWorldOriginX();
        newBodyWorldCenterX=getWorldOriginX();
        previousBodyWorldCenterY=getWorldOriginY();
        newBodyWorldCenterY=getWorldOriginY();
    }

    /**
     * Reads and stores position and angle of the body. Position is converted into pixel units.
     */
    public void readPositionAngleOfBody(){
        previousBodyAngle = newBodyAngle;
        previousBodyWorldCenterX = newBodyWorldCenterX;
        previousBodyWorldCenterY = newBodyWorldCenterY;
        newBodyAngle =body.getAngle();
        Vector2 bodyCenter=body.getWorldCenter();
        newBodyWorldCenterX =bodyCenter.x*Physics.PIXELS_PER_METER;
        newBodyWorldCenterY =bodyCenter.y*Physics.PIXELS_PER_METER;
    }

    /**
     * set angle and position of sprite from linear interpolation between previous and new physics data
     *
     * progress=1 for graphics time equal to time of new physics data
     * progress=0 for graphics time equal to time of previous physics data
     *
     * @param progress float, progress between previous to new data, from 0 to 1
     */
    public void interpolateSpritePositionAngle(float progress){
        setWorldOriginX(MathUtils.lerp(previousBodyWorldCenterX, newBodyWorldCenterX,progress));
        setWorldOriginY(MathUtils.lerp(previousBodyWorldCenterY, newBodyWorldCenterY,progress));
        setAngle(MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress));
    }

}
