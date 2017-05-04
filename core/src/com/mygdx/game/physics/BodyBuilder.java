package com.mygdx.game.physics;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;

/**
 * Build box2D bodies. Dimensions are in graphical units (unit is one pixel).
 */
public class BodyBuilder {

    public BodyDef bodyDef;
    private Physics physics;

    /**
     * Creates and resets the bodydef.
     *
     * @param physics Physics, We need physics and the world to create the body.
     */
    public BodyBuilder(Physics physics) {
        this.physics = physics;
        bodyDef = new BodyDef();
        reset();
    }

    /**
     * Reset the bodyDef to defaults.
     *
     * @return this, for chaining
     */
    public BodyBuilder reset() {
        bodyDef.type = BodyDef.BodyType.DynamicBody;
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
     * Set the bodyDef to be the same as of another body.
     *
     * @param body Body, to copy the bodyDef data
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
     * Set the body type.
     *
     * @param bodyType BodyDef.BodyType, dynamic, static or kinematic
     * @return this
     */
    public BodyBuilder setBodyType(BodyDef.BodyType bodyType) {
        bodyDef.type = bodyType;
        return this;
    }

    /**
     * Set to dynamical body type.
     *
     * @return this
     */
    public BodyBuilder setDynamicalBody() {
        return setBodyType(BodyDef.BodyType.DynamicBody);
    }

    /**
     * Set to static body type.
     *
     * @return this
     */
    public BodyBuilder setStaticBody() {
        return setBodyType(BodyDef.BodyType.StaticBody);
    }

    /**
     * Set to kinematic body type.
     *
     * @return this
     */
    public BodyBuilder setKinemazicBody() {
        return setBodyType(BodyDef.BodyType.KinematicBody);
    }

    /**
     * Set if body is active at start (default=true).
     *
     * @param isActive boolean, true if body should be active at start
     * @return
     */
    public BodyBuilder setIsActive(boolean isActive) {
        bodyDef.active = isActive;
        return this;
    }

    /**
     * Set if body is awake at start (default=true).
     *
     * @param isAwake boolean, true if body should be awake at start
     * @return
     */
    public BodyBuilder setIsAwake(boolean isAwake) {
        bodyDef.awake = isAwake;
        return this;
    }

    /**
     * Set if body may sleep (default=true).
     *
     * @param allowed boolean, true if body may sleep
     * @return
     */
    public BodyBuilder setAllowSleep(boolean allowed) {
        bodyDef.allowSleep = allowed;
        return this;
    }

    /**
     * Set if body is a bullet (default=false).
     *
     * @param isBullet boolean, true for a bullet
     * @return
     */
    public BodyBuilder setIsBullet(boolean isBullet) {
        bodyDef.bullet = isBullet;
        return this;
    }

    /**
     * Set body position (Given in pixel units).
     *
     * @param positionX float, x-component of the position in the graphics world.
     * @param positionY float, y-component of the position in the graphics world.
     * @return
     */
    public BodyBuilder setPosition(float positionX, float positionY) {
        bodyDef.position.set(positionX / Physics.PIXELS_PER_METER, positionY / Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set body position  (in pixel units).
     *
     * @param position
     * @return
     */
    public BodyBuilder setPosition(Vector2 position) {
        setPosition(position.x,position.y);
        return this;
    }

    /**
     * Set body angle, in radians.
     *
     * @param angle float, radians
     * @return
     */
    public BodyBuilder setAngle(float angle) {
        bodyDef.angle = angle;
        return this;
    }

    /**
     * Set the angular damping (default=0). Reduces the rotational velocity.
     * Can be larger than 1. Effect depends on time step for large values.
     *
     * @param damping float, angular damping, between 0 and 1 ?
     * @return
     */
    public BodyBuilder setAngularDamping(float damping) {
        bodyDef.angularDamping = damping;
        return this;
    }

    /**
     * Set the angular velocity in radians/sec (default=0).
     *
     * @param velocity
     * @return
     */
    public BodyBuilder setAngularVelocity(float velocity) {
        bodyDef.angularVelocity = velocity;
        return this;
    }

    /**
     * Set the linear damping (default=0). Reduces the linear velocity.
     * Can be larger than 1. Effect depends on time step for large values.
     *
     * @param damping float, linear damping, between 0 and 1 ?
     * @return
     */
    public BodyBuilder setLinearDamping(float damping) {
        bodyDef.linearDamping = damping;
        return this;
    }

    /**
     * Set initial velocity (default=0) in pixels/sec.
     *
     * @param velocityX
     * @param velocityY
     * @return
     */
    public BodyBuilder setLinearVelocity(float velocityX, float velocityY) {
        bodyDef.linearVelocity.set(velocityX / Physics.PIXELS_PER_METER, velocityY / Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set initial velocity (default=0) in pixels/sec.
     *
     * @param velocity
     * @return
     */
    public BodyBuilder setLinearVelocity(Vector2 velocity) {
        setLinearVelocity(velocity.x,velocity.y);
        return this;
    }

    /**
     * Set gravity scale (default=1, natural gravity). Multiplies world gravity acceleration.
     * For particles that float ?
     *
     * @param scale float, multiplier for gravity.
     * @return
     */
    public BodyBuilder setGravityScale(float scale) {
        bodyDef.gravityScale = scale;
        return this;
    }

    /**
     * Build the body without user data. Tells physics to update the bodies array.
     *
     * @return Body, a box2D body.
     */
    public Body build() {
        physics.bodiesNeedUpdate = true;
        return physics.world.createBody(bodyDef);
    }

    /**
     * Build the body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body build(Shape2D shape){
        Body body=build();
        physics.fixtureBuilder.build(body,shape);
        return body;
    }

    /**
     * Build the body and attach the supplied userData.
     *
     * @param userData Object, attach to the body.
     * @return Body, a box2D body.
     */
    public Body build(Object userData) {
        Body body = build();
        body.setUserData(userData);
        return body;
    }

    /**
     * Build the body without user data. Build fixtures defined by the Shape2D shape. Attach the userData
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @param userData Object, attach to the body.
     * @return Body, a box2D body.
     */
    public Body build(Shape2D shape,Object userData){
        Body body=build(shape);
        body.setUserData(userData);
        return body;
    }

}
