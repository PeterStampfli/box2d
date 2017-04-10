package com.mygdx.game.physics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Pieces.TouchableSprite;

/**
 * Created by peter on 3/24/17.
 * Connecting sprite with body
 * scaling body data to match graphics
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
     * set origin of the sprite, position and angle of sprite according to body
     * set the user data of the body to this sprite
     * @param body
     */
    public void setBody(Body body){
        this.body=body;
        body.setUserData(this);
        setLocalOrigin();
        saveBodyPositionAngle();
        // repeat to set both previous and new data
        saveBodyPositionAngle();
        updateSpritePositionAngle(1f);
    }

    /**
     * set the origin (center of rotation and scaling) equal to center of mass of the body
     * In local coordinates. Zero is left bottom corner of the Textureregion.
     * Scales from physics dimensions to graphics
     */
    public void setLocalOrigin(){
        Vector2 bodyCenter=body.getLocalCenter();
        setOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
    }

    /**
     * without interpolation: set position and angle of the sprite to agree with the box2D body
     * scale from physics position to graphics
     */
    public void setSpritePositionAngle(){
        setAngle(body.getAngle());
        Vector2 bodyCenter=body.getLocalCenter();
        setWorldOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
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
        setWorldOrigin(MathUtils.lerp(previousBodyWorldCenterX, newBodyWorldCenterX,progress),
                       MathUtils.lerp(previousBodyWorldCenterY, newBodyWorldCenterY,progress));
        setAngle(MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress));
    }

    /**
     * test if its body fixtures contains given position
     * check only fixtures that are not sensors
     * does not check the image region,
     * thus use only shapes/fixtures that do not go outside the image
     *
     * scale from graphics position to physics
     * @param positionX
     * @param positionY
     * @return
     */
    public boolean bodyContains(float positionX, float positionY){
        positionX/=Physics.PIXELS_PER_METER;
        positionY/=Physics.PIXELS_PER_METER;
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
     * @param x
     * @param y
     * @return
     */
    @Override
    public boolean contains (float x,float y){
        return  bodyContains(x, y);
    }

}
