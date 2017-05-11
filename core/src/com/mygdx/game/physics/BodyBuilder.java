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
     * Creates and resets the bodyDef.
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
        setDynamicalBody();
        setAngle(0);
        setIsBullet(false);
        setFixedRotation(false);
        setGravityScale(1);
        setAllowSleep(true);
        setIsActive(true);
        setIsAwake(true);
        setAngularVelocity(0);
        setLinearVelocity(0,0);
        setAngularDamping(0.3f);
        setLinearDamping(0.3f);
        return this;
    }

    /**
     * Set the bodyDef to be the same as of another body.
     *
     * @param body Body, to copy the bodyDef data
     * @return this
     */
    public BodyBuilder sameAs(Body body) {
        setBodyType(body.getType());
        setAngle(body.getAngle());
        setIsBullet(body.isBullet());
        setFixedRotation(body.isFixedRotation());
        setGravityScale(body.getGravityScale());
        setAllowSleep(body.isSleepingAllowed());
        setIsActive(body.isActive());
        setIsAwake(body.isAwake());
        setAngularDamping(body.getAngularDamping());
        setLinearDamping(body.getLinearDamping());
        setAngularVelocity(body.getAngularVelocity());
        bodyDef.linearVelocity.set(body.getLinearVelocity());  // do not scale
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
    public BodyBuilder setKinematicBody() {
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
        bodyDef.position.set(positionX, positionY).scl(1f / Physics.PIXELS_PER_METER);
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
     * Set if angle of body is fixed.
     *
     * @param fixed boolean, true for no rotation
     * @return this BodyBuilder
     */
    public BodyBuilder setFixedRotation(boolean fixed){
        bodyDef.fixedRotation=fixed;
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
        bodyDef.linearVelocity.set(velocityX, velocityY).scl(1f / Physics.PIXELS_PER_METER);
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
     * Build a static body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildStaticBody(Shape2D shape){
        setStaticBody();
        return build(shape);
    }

    /**
     * Build a dynamical body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildDynamicalBody(Shape2D shape){
        setDynamicalBody();
        return build(shape);
    }

    /**
     * Build a kinematic body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildKinematicBody(Shape2D shape){
        setKinematicBody();
        return build(shape);
    }
}
