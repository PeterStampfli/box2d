package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Created by peter on 3/22/17.
 */
public class BodyBuilder {

    private Physics physics;
    public BodyDef bodyDef;
    private Object userData;

    /**
     * creates and resets the bodydef
     * @param physics we need access to physics.world to create the body
     */
    public BodyBuilder(Physics physics) {
        this.physics = physics;
        bodyDef = new BodyDef();
        reset();
    }

    /**
     * define the body type
     * @param bodyType
     * @return this
     */
    public BodyBuilder type(BodyDef.BodyType bodyType) {
        bodyDef.type = bodyType;
        return this;
    }

    /**
     * reset the BodyDef data to defaults, userData=null
     * @return this
     */
    public BodyBuilder reset() {
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
        userData = null;
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
        userData = body.getUserData();
        return this;
    }

    /**
     * set if body starts isActive (default=true)
     * @param isActive
     * @return
     */
    public BodyBuilder active(boolean isActive) {
        bodyDef.active = isActive;
        return this;
    }

    /**
     * set if body starts isAwake (default=true)
     * @param isAwake
     * @return
     */
    public BodyBuilder awake(boolean isAwake) {
        bodyDef.awake = isAwake;
        return this;
    }

    /**
     * set if body may sleep (default=true)
     * @param allowed
     * @return
     */
    public BodyBuilder allowSleep(boolean allowed) {
        bodyDef.allowSleep = allowed;
        return this;
    }

    /**
     * set if body is bullet (default=false)
     * @param isBullet
     * @return
     */
    public BodyBuilder bullet(boolean isBullet) {
        bodyDef.bullet = isBullet;
        return this;
    }

    /**
     * set body position
     * @param positionX
     * @param positionY
     * @return
     */
    public BodyBuilder position(float positionX, float positionY) {
        bodyDef.position.set(positionX, positionY);
        return this;
    }

    /**
     * set body position
     * @param position
     * @return
     */
    public BodyBuilder position(Vector2 position) {
        bodyDef.position.set(position);
        return this;
    }

    /**
     * set body angle
     * @param angle
     * @return
     */
    public BodyBuilder angle(float angle) {
        bodyDef.angle = angle;
        return this;
    }

    /**
     * set angular damping. (default=0), reduces rotational velocity
     * can be larger than 1. Effect depends on time step for large values.
     * @param damping
     * @return
     */
    public BodyBuilder angularDamping(float damping) {
        bodyDef.angularDamping = damping;
        return this;
    }

    /**
     * set angular velocity (radians/sec) (default=0)
     * @param velocity
     * @return
     */
    public BodyBuilder angularVelocity(float velocity) {
        bodyDef.angularVelocity = velocity;
        return this;
    }

    /**
     * set linear damping (default=0), reduces linear velocity
     * can be larger than 1. Effect depends on time step for large values.
     * @param damping
     * @return
     */
    public BodyBuilder linearDamping(float damping) {
        bodyDef.linearDamping = damping;
        return this;
    }

    /**
     * set initial velocity (default=0)
     * @param velocityX
     * @param velocityY
     * @return
     */
    public BodyBuilder linearVelocity(float velocityX, float velocityY) {
        bodyDef.linearVelocity.set(velocityX, velocityY);
        return this;
    }

    /**
     * set initial velocity (default=0)
     * @param velocity
     * @return
     */
    public BodyBuilder linearVelocity(Vector2 velocity) {
        bodyDef.linearVelocity.set(velocity);
        return this;
    }

    /**
     * set gravity scale (default=1, natural gravity)
     * @param scale
     * @return
     */
    public BodyBuilder gravityScale(float scale) {
        bodyDef.gravityScale = scale;
        return this;
    }

    /**
     * set user data
     * @param data
     * @return
     */
    public BodyBuilder userData(Object data) {
        userData = data;
        return this;
    }

    /**
     * build the body from BodyDef and attach the userData
     * @return
     */
    public Body build() {
        Body body = physics.world.createBody(bodyDef);
        body.setUserData(userData);
        return body;
    }

}
