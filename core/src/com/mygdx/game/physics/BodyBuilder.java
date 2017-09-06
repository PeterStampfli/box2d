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
     * Set if body is active at start (default=true).
     *
     * @param isActive boolean, true if body should be active at start
     * @return this, for chaining
     */
    public BodyBuilder setIsActive(boolean isActive) {
        bodyDef.active = isActive;
        return this;
    }

    /**
     * Set if body is awake at start (default=true).
     *
     * @param isAwake boolean, true if body should be awake at start
     * @return this, for chaining
     */
    public BodyBuilder setIsAwake(boolean isAwake) {
        bodyDef.awake = isAwake;
        return this;
    }

    /**
     * Set if body may sleep (default=true).
     *
     * @param allowed boolean, true if body may sleep
     * @return this, for chaining
     */
    public BodyBuilder setAllowSleep(boolean allowed) {
        bodyDef.allowSleep = allowed;
        return this;
    }

    /**
     * Set if body is a bullet (default=false).
     *
     * @param isBullet boolean, true for a bullet
     * @return this, for chaining
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
     * @return this, for chaining
     */
    public BodyBuilder setPosition(float positionX, float positionY) {
        bodyDef.position.set(positionX, positionY).scl(1f / Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set body position  (in pixel units).
     *
     * @param position Vector2
     * @return this, for chaining
     */
    public BodyBuilder setPosition(Vector2 position) {
        setPosition(position.x,position.y);
        return this;
    }

    /**
     * Set body angle, in radians.
     *
     * @param angle float, radians
     * @return this, for chaining
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
     * @return this, for chaining
     */
    public BodyBuilder setAngularDamping(float damping) {
        bodyDef.angularDamping = damping;
        return this;
    }

    /**
     * Set if angle of body is fixed.
     *
     * @param fixed boolean, true for no rotation
     * @return this, for chaining this BodyBuilder
     */
    public BodyBuilder setFixedRotation(boolean fixed){
        bodyDef.fixedRotation=fixed;
        return this;
    }

    /**
     * Set the angular velocity in radians/sec (default=0).
     *
     * @param velocity float
     * @return this, for chaining
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
     * @return this, for chaining
     */
    public BodyBuilder setLinearDamping(float damping) {
        bodyDef.linearDamping = damping;
        return this;
    }

    /**
     * Set initial velocity (default=0) in pixels/sec.
     *
     * @param velocityX float, x-component of velocity
     * @param velocityY float, x-component of velocity
     * @return this, for chaining
     */
    public BodyBuilder setLinearVelocity(float velocityX, float velocityY) {
        bodyDef.linearVelocity.set(velocityX, velocityY).scl(1f / Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set initial velocity (default=0) in pixels/sec.
     *
     * @param velocity Vector2, velocity
     * @return this, for chaining
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
     * @return this, for chaining
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
     * Fixtures are not sensors.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    private Body build(Shape2D shape){
        Body body=build();
        if (shape!=null) {
            physics.fixtureBuilder.setIsSensor(false).build(body,shape);
        }
        return body;
    }

    /**
     * Build a static body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildStaticBody(Shape2D shape){
        bodyDef.type= BodyDef.BodyType.StaticBody;
        return build(shape);
    }

    /**
     * Build a dynamical body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildDynamicalBody(Shape2D shape){
        bodyDef.type= BodyDef.BodyType.DynamicBody;
        return build(shape);
    }

    /**
     * Build a kinematic body without user data. Build fixtures defined by the Shape2D shape.
     *
     * @param shape Shape2D, shape or shape collection to attach to the body
     * @return Body, a box2D body.
     */
    public Body buildKinematicBody(Shape2D shape){
        bodyDef.type= BodyDef.BodyType.KinematicBody;
        return build(shape);
    }
}
