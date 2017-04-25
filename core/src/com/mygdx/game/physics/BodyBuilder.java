package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 3/22/17.
 *
 * input data is in grphical world units (virtual pixels)
 */
public class BodyBuilder {

    private Physics physics;
    public BodyDef bodyDef;

    /**
     * creates and resets the bodydef
     * @param physics we need the world to create the body
     */
    public BodyBuilder(Physics physics) {
        this.physics = physics;
        bodyDef = new BodyDef();
        reset();
    }

    /**
     * reset the BodyDef data to defaults, userData=null
     * dynamic body
     * @return this
     */
    public BodyBuilder reset() {
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        bodyDef.angle = 0;
        bodyDef.bullet = false;
        bodyDef.fixedRotation = false;
        bodyDef.gravityScale = 1;
        bodyDef.allowSleep = true;
        bodyDef.active = true;
        bodyDef.awake = true;
        bodyDef.angularVelocity = 0;
        bodyDef.linearVelocity.setZero();
        bodyDef.angularDamping = 0;
        bodyDef.linearDamping = 0;
        return this;
    }

    /**
     * set the bodyDef data to same as a body, including userData
     * @param body
     * @return this
     */
    public BodyBuilder sameAs(Body body) {
        bodyDef.type = body.getType();
        bodyDef.angle = body.getAngle();
        bodyDef.bullet = body.isBullet();
        bodyDef.fixedRotation = body.isFixedRotation();
        bodyDef.gravityScale = body.getGravityScale();
        bodyDef.allowSleep = body.isSleepingAllowed();
        bodyDef.active = body.isActive();
        bodyDef.awake = body.isAwake();
        bodyDef.angularVelocity = body.getAngularVelocity();
        bodyDef.linearVelocity.set(body.getLinearVelocity());
        bodyDef.angularDamping = body.getAngularDamping();
        bodyDef.linearDamping = body.getLinearDamping();
        return this;
    }


    /**
     * set the body type
     * @param bodyType
     * @return this
     */
    public BodyBuilder setBodyType(BodyDef.BodyType bodyType) {
        bodyDef.type = bodyType;
        return this;
    }

    /**
     * set if body starts isActive (default=true)
     * @param isActive
     * @return
     */
    public BodyBuilder setIsActive(boolean isActive) {
        bodyDef.active = isActive;
        return this;
    }

    /**
     * set if body starts isAwake (default=true)
     * @param isAwake
     * @return
     */
    public BodyBuilder setIsAwake(boolean isAwake) {
        bodyDef.awake = isAwake;
        return this;
    }

    /**
     * set if body may sleep (default=true)
     * @param allowed
     * @return
     */
    public BodyBuilder setAllowSleep(boolean allowed) {
        bodyDef.allowSleep = allowed;
        return this;
    }

    /**
     * set if body is bullet (default=false)
     * @param isBullet
     * @return
     */
    public BodyBuilder setIsBullet(boolean isBullet) {
        bodyDef.bullet = isBullet;
        return this;
    }

    /**
     * set body setPosition
     * as scaled from graphics setPosition
     * @param positionX
     * @param positionY
     * @return
     */
    public BodyBuilder setPosition(float positionX, float positionY) {
        bodyDef.position.set(positionX/Physics.PIXELS_PER_METER, positionY/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * set body setPosition (graphics world units)
     * @param position
     * @return
     */
    public BodyBuilder setPosition(Vector2 position) {
        bodyDef.position.set(position);
        return this;
    }

    /**
     * set body angle
     * @param angle
     * @return
     */
    public BodyBuilder setAngle(float angle) {
        bodyDef.angle = angle;
        return this;
    }

    /**
     * set angular damping. (default=0), reduces rotational velocity
     * can be larger than 1. Effect depends on time step for large values.
     * @param damping
     * @return
     */
    public BodyBuilder setAngularDamping(float damping) {
        bodyDef.angularDamping = damping;
        return this;
    }

    /**
     * set angular velocity (radians/sec) (default=0)
     * @param velocity
     * @return
     */
    public BodyBuilder setAngularVelocity(float velocity) {
        bodyDef.angularVelocity = velocity;
        return this;
    }

    /**
     * set linear damping (default=0), reduces linear velocity
     * can be larger than 1. Effect depends on time step for large values.
     * @param damping
     * @return
     */
    public BodyBuilder setLinearDamping(float damping) {
        bodyDef.linearDamping = damping;
        return this;
    }

    /**
     * set initial velocity (default=0)
     * scaled from graphics world units
     * @param velocityX
     * @param velocityY
     * @return
     */
    public BodyBuilder setLinearVelocity(float velocityX, float velocityY) {
        bodyDef.linearVelocity.set(velocityX/Physics.PIXELS_PER_METER, velocityY/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * set initial velocity (default=0)
     * @param velocity
     * @return
     */
    public BodyBuilder setLinearVelocity(Vector2 velocity) {
        bodyDef.linearVelocity.set(velocity);
        return this;
    }

    /**
     * set gravity scale (default=1, natural gravity)
     * @param scale
     * @return
     */
    public BodyBuilder setGravityScale(float scale) {
        bodyDef.gravityScale = scale;
        return this;
    }

    /**
     * build the body without user data
     * set that bodies list needs update
     * @return
     */
    public Body build(){
        physics.bodiesNeedUpdate=true;
        return physics.world.createBody(bodyDef);
    }

    /**
     * build the body from BodyDef and attach the supplied userData
     * mark, that the bodies array has to be updated
     * @param userData
     * @return
     */
    public Body build(Object userData) {
        Body body = build();
        body.setUserData(userData);
        return body;
    }

    /**
     * build a body with a sprite (created extra with its image ...)
     * creates a bodyToSprite object for the new body and given sprite and sets it as user data
     * get bodyToSprite from body.userData if needed (why?)
     * @param sprite
     * @return
     */
    public Body build(ExtensibleSprite sprite){
        Body body=build();
        body.setUserData(new BodyToSprite(body,sprite));
        return body;
    }
}
