package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 3/24/17.
 * Connecting sprite with body
 */

public class Box2DSprite extends TouchableSprite {

    private float previousBodyAngle, newBodyAngle;
    private float previousBodyWorldCenterX, newBodyWorldCenterX;   // position of the worldCenter of the body = worldOrigin of sprite
    private float previousBodyWorldCenterY, newBodyWorldCenterY;
    public Body body;


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
        setLocalOrigin(body.getLocalCenter());
    }

    /**
     * without interpolation: set position and angle of the sprite to agree with the box2D body
     * @param body
     */
    public void setSpritePositionAngle(Body body){
        setAngle(body.getAngle());
        setWorldOrigin(body.getWorldCenter());
    }

    /**
     * if new Physics position and angle had data of result of the previous physics step
     * and a new time step has been done then this method results in:
     * -previous position and angle will have result of previous time step
     * -new position and angle will contain result of the new (last) time step
     *
     * @param body
     */
    public void saveBodyPositionAngle(Body body){
        previousBodyAngle = newBodyAngle;
        previousBodyWorldCenterX = newBodyWorldCenterX;
        previousBodyWorldCenterY = newBodyWorldCenterY;
        newBodyAngle =body.getAngle();
        Vector2 worldCenter=body.getWorldCenter();
        newBodyWorldCenterX =worldCenter.x;
        newBodyWorldCenterY =worldCenter.y;
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
    public void updateSpritePositionAngle(float progress){
        setWorldOrigin(MathUtils.lerp(previousBodyWorldCenterX, newBodyWorldCenterX,progress),
                       MathUtils.lerp(previousBodyWorldCenterY, newBodyWorldCenterY,progress));
        setAngle(MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress));
    }

    /**
     * set origin, physics position and angle of sprite according to body
     * @param body
     */
    public void initializeSprite(Body body){
        setLocalOrigin(body);
        saveBodyPositionAngle(body);
        // repeat to set both previous and new data
        saveBodyPositionAngle(body);
    }

    /**
     * test if its body contains given position
     * check only fixtures that are not sensors
     * @param positionX
     * @param positionY
     * @return
     */
    public boolean contains(float positionX, float positionY){
        if (body!=null) {
            Array<Fixture> fixtures = body.getFixtureList();
            for (Fixture fixture : fixtures) {
                if (!fixture.isSensor()&&fixture.testPoint(positionX, positionY)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * test if its body contains given position
     * @param position
     * @return
     */
    public boolean contains (Vector2 position){
        return  contains(position.x,position.y);
    }

}
