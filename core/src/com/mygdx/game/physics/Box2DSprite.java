package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 3/24/17.
 */

public class Box2DSprite extends Sprite implements Drawable{

    float previousPhysicsAngle, newPhysicsAngle;
    float previousPhysicsWorldCenterX, newPhysicsWorldCenterX;   // position of the worldCenter of the body = worldOrigin of sprite
    float previousPhysicsWorldCenterY, newPhysicsWorldCenterY;

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
     * without interpolation: set position and angle of the sprite to agree with the box2D body
     * @param body
     */
    public void setPositionAngle(Body body){
        setAngle(body.getAngle());
        setWorldOrigin(body.getWorldCenter());
    }

    /**
     * if new Physics position and angle have data of result of the previous physics step
     * and a new time step has been done then this method results in:
     * -previous position and angle will have result of previous time step
     * -new position and angle will contain result of the new (last) time step
     *
     * @param body
     */
    public void setPhysicsResult(Body body){
        previousPhysicsAngle = newPhysicsAngle;
        previousPhysicsWorldCenterX = newPhysicsWorldCenterX;
        previousPhysicsWorldCenterY = newPhysicsWorldCenterY;
        newPhysicsAngle =body.getAngle();
        Vector2 worldCenter=body.getWorldCenter();
        newPhysicsWorldCenterX =worldCenter.x;
        newPhysicsWorldCenterY =worldCenter.y;
        L.og(newPhysicsWorldCenterX);
    }

    /**
     * angle and position of sprite from linear interpolation between previous and new physics data
     *
     * progress=1 for graphics time equal to time of new physics data
     * progress=0 for graphics time equal to time of previous physics data
     * use constant TIME_STEP between physics times, thus progress=(graphics time-time previous physics step)/TIME_STEP
     * if (time of new physics step-time of previous physics step)=TIME_STEP
     * @param progress
     */
    public void interpolateGraphics(float progress){
        setWorldOrigin(MathUtils.lerp(previousPhysicsWorldCenterX, newPhysicsWorldCenterX,progress),
                       MathUtils.lerp(previousPhysicsWorldCenterY, newPhysicsWorldCenterY,progress));
        setAngle(MathUtils.lerpAngle(previousPhysicsAngle, newPhysicsAngle,progress));
    }

    /**
     * set origin, physics position and angle of sprite according to body
     * @param body
     */
    public void initializePhysics(Body body){
        setLocalOrigin(body);
        setPhysicsResult(body);
        // repeat to set both previous and new data
        setPhysicsResult(body);
    }
}
