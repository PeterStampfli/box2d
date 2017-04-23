package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteContains;

/**
 * Created by peter on 4/23/17.
 * make that a sprite moves with a Box2D body
 * use the fixtures of the body for sprite.contains
 * stores references to bodies and sprites
 * in method calls transfer only varying data, if possible
 */

public class BodyToSprite implements SpriteContains{
    private float previousBodyAngle, newBodyAngle;
    private float previousBodyWorldCenterX, newBodyWorldCenterX;   // position of the worldCenter of the body = worldOrigin of sprite
    private float previousBodyWorldCenterY, newBodyWorldCenterY;
    public ExtensibleSprite sprite;
    public Body body;

    /**
     * initialization: set body and sprite references, set LOcal origin of sprite and its contains method
     * set position and angle of sprite
     * @param body
     * @param sprite
     */
    public BodyToSprite(Body body,ExtensibleSprite sprite){
        this.body=body;
        this.sprite=sprite;
        body.setUserData(this);
        sprite.setContains(this);
        setLocalOrigin();
        setSpritePositionAngle();
        saveBodyPositionAngle();
    }


    /**
     * set the origin (center of rotation and scaling) equal to center of mass of the body
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Scales from physics dimensions to graphics
     */
    public void setLocalOrigin(){
        Vector2 bodyCenter=body.getLocalCenter();
        sprite.setLocalOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
    }

    /**
     * without interpolation: set position and angle of the sprite to agree with the box2D body
     * scale from physics position to graphics
     */
    public void setSpritePositionAngle(){
        sprite.setAngle(body.getAngle());
        Vector2 bodyCenter=body.getLocalCenter();
        sprite.setWorldOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
    }

    /**
     * if new Physics position and angle had data of result of the previous physics step
     * and a new time step has been done then this method results in:
     * -previous position and angle will have result of previous time step
     * -new position and angle will contain result of the new (last) time step
     *
     * scale from physics dimensions to graphics
     */
    public void saveBodyPositionAngle(){
        previousBodyAngle = newBodyAngle;
        previousBodyWorldCenterX = newBodyWorldCenterX;
        previousBodyWorldCenterY = newBodyWorldCenterY;
        newBodyAngle =body.getAngle();
        Vector2 bodyCenter=body.getWorldCenter();
        newBodyWorldCenterX =bodyCenter.x*Physics.PIXELS_PER_METER;
        newBodyWorldCenterY =bodyCenter.y*Physics.PIXELS_PER_METER;
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
        sprite.setWorldOrigin(MathUtils.lerp(previousBodyWorldCenterX, newBodyWorldCenterX,progress),
                MathUtils.lerp(previousBodyWorldCenterY, newBodyWorldCenterY,progress));
        sprite.setAngle(MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress));
    }

    /**
     * contains if one of its body fixtures contains the given position
     * check only fixtures that are not sensors
     * does not check the masterTextureRegion region,
     * thus use only shapes/fixtures that do not go outside the masterTextureRegion
     * (anyway need to do like this because only the fixtures are responsible for collision
     *
     * scale from graphics position to physics
     * @param positionX
     * @param positionY
     * @return
     */
    public boolean contains(ExtensibleSprite sprite, float positionX, float positionY){
        positionX/=Physics.PIXELS_PER_METER;
        positionY/=Physics.PIXELS_PER_METER;
        Array<Fixture> fixtures = body.getFixtureList();
        for (Fixture fixture : fixtures) {
            // if any fixture is not a sensor and it contains the point, then the body contains the point
            if (!fixture.isSensor()&&fixture.testPoint(positionX, positionY)) {
                return true;
            }
        }
        return false;
    }
}
