package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.utilities.L;

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
     * In local coordinates. Zero is left bottom corner of the TextureRegion.
     * Scales from physics dimensions to graphics. Call after creating all fixtures.
     * Note that the local origin does not depend on translation and rotation.
     */
    public void setLocalOrigin(){
        Vector2 bodyCenter=body.getLocalCenter();
        L.og("local origin "+bodyCenter.toString());
        setLocalOrigin(bodyCenter.x*Physics.PIXELS_PER_METER,bodyCenter.y*Physics.PIXELS_PER_METER);
        L.og("originx "+getOriginX());
    }

    // placing and rotating the sprite: Do same for the body!

    /**
     * Set the origin of the body and its angle equal to the sprite's. Convert lengths.
     * Sets the body data used for interpolation.
     * Note that the local center rotates with the body.
     */
    public void setPositionAngleOfBody(){
        // set body origin and rotation
        body.setTransform(getX()/Physics.PIXELS_PER_METER,
                getY()/Physics.PIXELS_PER_METER,getAngle());

        L.og("trans x"+getX()/Physics.PIXELS_PER_METER);
        L.og("trans y"+getY()/Physics.PIXELS_PER_METER);

        L.og("body Worigin "+body.getWorldCenter().toString());
        L.og("body Lorigin "+body.getLocalCenter().toString());

        float lcX=body.getLocalCenter().x;
        float lcY=body.getLocalCenter().y;
        float ang=body.getAngle();
        L.og("rotatedX "+(MathUtils.cos(ang)*lcX-MathUtils.sin(ang)*lcY));


        previousBodyAngle=getAngle();
        newBodyAngle=getAngle();
        previousBodyWorldCenterX=getWorldOriginX();
        newBodyWorldCenterX=getWorldOriginX();
        previousBodyWorldCenterY=getWorldOriginY();
        newBodyWorldCenterY=getWorldOriginY();
    }

    /**
     * Set the Angle of the sprite and body using radians.
     *
     * @param angle float, in radians
     */
    @Override
    public void setAngle(float angle) {
        super.setAngle(angle);
        setPositionAngleOfBody();
    }

    /**
     * Set the Angle of the sprite and body using degrees.
     *
     * @param degrees float, in radians
     */
    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        setPositionAngleOfBody();
    }

    /**
     * Make that the sprite angle and angle of body is a multiple of 360/n degrees. Use 4 for a square lattice.
     *
     * @param n int, number of different sprite orientations
     */
    @Override
    public void quantizeAngle(int n) {
        super.quantizeAngle(n);
        setPositionAngleOfBody();
    }

    /**
     * Set the x-position of sprite and body
     *
     * @param x float, x-coordinate of the position
     */
    @Override
    public void setX(float x){
        superSetX(x);
        setPositionAngleOfBody();
    }

    /**
     * Set the y-position of sprite and body
     *
     * @param y float, y-coordinate of the position
     */
    @Override
    public void setY(float y){
        superSetY(y);
        setPositionAngleOfBody();
    }

    /**
     * Set the position of sprite and body
     *
     * @param x float, x-coordinate of the position
     * @param y float, y-coordinate of the position
     */
    @Override
    public void setPosition(float x,float y){
        super.setPosition(x,y);
        setPositionAngleOfBody();
    }

    /**
     * Set the x-coordinate of the sprite origin (center of rotation and scaling)
     * and the center of mass of the body.
     *
     * @param x  float, x-coordinate of the origin
     */
    @Override
    public void setWorldOriginX(float x) {
        super.setWorldOriginX(x);
        setPositionAngleOfBody();
    }

    /**
     * Set the y-coordinate of the sprite origin (center of rotation and scaling)
     * and the center of mass of the body.
     *
     * @param y  float, y-coordinate of the origin
     */
    @Override
    public void setWorldOriginY(float y) {
        super.setWorldOriginY(y);
        setPositionAngleOfBody();
    }

    /**
     * Set the origin (center of rotation and scaling) of the sprite
     * and the center of mass of the body.
     *
     * Implicitely also overrides the setWorldOrigin(Vector2 position) method.
     *
     * @param worldOriginPositionX float, x-coordinate of the origin
     * @param worldOriginPositionY float, y-coordinate of the origin
     */
    @Override
    public void setWorldOrigin(float worldOriginPositionX, float worldOriginPositionY) {
        super.setWorldOriginX(worldOriginPositionX);
        super.setWorldOriginY(worldOriginPositionY);
        setPositionAngleOfBody();
    }

    /**
     * Set the origin (center of rotation and scaling) and angle of the sprite
     * and the center of mass and angle of the body.
     *
     * @param worldOriginPositionX float, x-coordinate of the origin
     * @param worldOriginPositionY float, y-coordinate of the origin
     * @param angle float, angle in radians
     */
    public void setWorldOriginAngle(float worldOriginPositionX, float worldOriginPositionY,float angle) {
        super.setWorldOriginX(worldOriginPositionX);
        super.setWorldOriginY(worldOriginPositionY);
        super.setAngle(angle);
        setPositionAngleOfBody();
    }

    /**
     * Set the origin (center of rotation and scaling) and angle of the sprite
     * and the center of mass and angle of the body.
     *
     * @param worldOrigin vector2, position of the origin
     * @param angle float, angle in radians
     */
    public void setWorldOriginAngle(Vector2 worldOrigin,float angle) {
        super.setWorldOriginX(worldOrigin.x);
        super.setWorldOriginY(worldOrigin.y);
        super.setAngle(angle);
        setPositionAngleOfBody();
    }





    /**
     * Reads and stores position and angle of the body. Position is converted into pixel units.
     * Used for making the body move the sprite.
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
     * set angle and position of sprite from linear interpolation
     * between previous and new data of the body.
     *
     * progress=1 for graphics time equal to time of new physics data
     * progress=0 for graphics time equal to time of previous physics data
     *
     * @param progress float, progress between previous to new data, from 0 to 1
     */
    public void interpolateSpritePositionAngle(float progress){
        progress=1;
        L.og("setting");
        super.setWorldOriginX(MathUtils.lerp(previousBodyWorldCenterX, newBodyWorldCenterX,progress));
        super.setWorldOriginY(MathUtils.lerp(previousBodyWorldCenterY, newBodyWorldCenterY,progress));
        setAngle(MathUtils.lerpAngle(previousBodyAngle, newBodyAngle,progress));

        L.og("body pos "+body.getPosition().toString());
        L.og("Sprite pos x "+getX());
        L.og(getWidth());

    }

}
