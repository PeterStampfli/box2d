package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by peter on 3/24/17.
 */

public class Box2DSprite extends Sprite {

    float angleBefore,angleAfter;
    float positionXBefore,positionXAfter;   // position of the worldCenter of the body = worldOrigin of sprite
    float positionYBefore,positionYAfter;

    /**
     * creates a box2DSprite based on a Texture with same size
     * @param texture
     */
    public Box2DSprite(Texture texture){
        super(texture);
    }

    /**
     * Creates a box2DSprite based on a specific TextureRegion,
     * the new sprite's region parameters are a copy
     *
     * @param textureRegion
     */
    public Box2DSprite(TextureRegion textureRegion){
        super(textureRegion);
    }

    /**
     * set the origin (center of rotation and scaling) equal to center of mass of the body
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Uses world dimensions. (Not pixel numbers)
     * @param body
     */
    public void setLocalOrigin(Body body){
        Vector2 center=body.getLocalCenter();
        setOrigin(center.x,center.y);
    }

    /**
     * reduce the size of the sprite to world dimensions.
     * Factor is given number of pixels per meter
     * @param pixelPerMeter
     */
    public void adjustSizeToPixelScale(float pixelPerMeter){
        setSize(getWidth()/pixelPerMeter,getHeight()/pixelPerMeter);
    }

    /**
     * set angle of sprite
     * @param angle in radians
     */
    public void setAngle(float angle){
        setRotation(angle * MathUtils.radiansToDegrees);
    }

    /**
     * set the position of the sprite such that the origin (center of rotation)
     * lies at given position of center of mass
     * @param massCenterPositionX
     * @param massCenterPositionY
     */
    public void setWorldOrigin(float massCenterPositionX,float massCenterPositionY){
        setPosition(massCenterPositionX-getOriginX(),massCenterPositionY-getOriginY());
    }

    /**
     * set the position of the sprite such that the origin (center of rotation)
     * lies at given position of center of mass
     * @param massCenterPosition
     */
    public void setWorldOrigin(Vector2 massCenterPosition){
        setWorldOrigin(massCenterPosition.x,massCenterPosition.y);
    }

    /**
     * set position and angle of the sprite to agree with the box2D body
     * @param body
     */
    public void setPositionAngle(Body body){
        setAngle(body.getAngle());
        setWorldOrigin(body.getWorldCenter());
    }

    /**
     * set position and angle from body at last physics update before graphics time
     * @param body
     */
    public void setPositionAngleBefore(Body body){
        angleBefore=body.getAngle();
        Vector2 worldCenter=body.getWorldCenter();
        positionXBefore=worldCenter.x;
        positionYBefore=worldCenter.y;
    }

    /**
     * set position and angle from body at next physics update after graphics time
     * @param body
     */
    public void setPositionAngleAfter(Body body){
        angleBefore=body.getAngle();
        Vector2 worldCenter=body.getWorldCenter();
        positionXAfter=worldCenter.x;
        positionYAfter=worldCenter.y;
    }

    /**
     * set position and angle before graphics time equal to earlier data after previous graphics time
     */
    public void positionAngleBeforeEqualAfter(){
        angleBefore=angleAfter;
        positionXBefore=positionXAfter;
        positionYBefore=positionYAfter;
    }

    /**
     * set angle and position of sprite from linear interpolation
     * progress=1 for graphics time equals next physics time after
     * progress=0 for graphics time equals last physics time before graphics time
     * constant TIME_STEP between physics times, thus progress=(graphics time-time before)/TIME_STEP
     * @param progress
     */
    public void setInterpolatedPositionAngle(float progress){
        setPosition(MathUtils.lerp(positionXBefore,positionXAfter,progress),
                    MathUtils.lerp(positionYBefore,positionYAfter,progress));
        setAngle(MathUtils.lerpAngle(angleBefore,angleAfter,progress));
    }
}
