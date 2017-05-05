package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

/**
 * Build a mouse joint. Box2D has a joint pool. Use world.destroyJoint(joint)
 */
public class MouseJointBuilder {
    private Physics physics;
    public Body dummyBody;
    private MouseJointDef mouseJointDef;

    /**
     * Create a mouse joint builder. Needs the physics.world.
     * Creates a dummy body. Resets the bodyBuilder.
     * @param physics
     */
    public MouseJointBuilder(Physics physics) {
        this.physics = physics;
        mouseJointDef = new MouseJointDef();
        dummyBody=physics.bodyBuilder.reset().setStaticBody().setPosition(100,100).build();
        physics.bodyBuilder.reset();
        reset();
    }

    /**
     * Set the parameters to standard values. Sets the dummy body.
     *
     * @return this MouseJointBuilder
     */
    public MouseJointBuilder reset() {
        setDummyBody(dummyBody);
        setCollideConnected(true);
        setDampingRatio(0.7f);
        setFrequencyHz(10);
        setMaxForce(10);
        return this;
    }

    /**
     * Set the second dummy body.
     *
     * @param body Body, some body. It is not affected.
     * @return this MouseJointBuilder
     */
    public MouseJointBuilder setDummyBody(Body body) {
        mouseJointDef.bodyA = body;
        return this;
    }

    /**
     * Set the target body that the joint moves.
     *
     * @param body Body
     * @return this MouseJointBuilder
     */
    public MouseJointBuilder setBody(Body body) {
        mouseJointDef.bodyB = body;
        return this;
    }

    /**
     * Set if dummy body and target do collide.
     *
     * @param b boolean, true if dummy and target collide.
     * @return this MouseJointBuilder
     */
    public MouseJointBuilder setCollideConnected(Boolean b) {
        mouseJointDef.collideConnected = b;
        return this;
    }

    /**
     * Set damping of the joint. 0 for no damping. 1 for critical damping
     * (no overshoot). Higher values possible.
     *
     * @param d float, damping
     * @return this MouseJointBuilder
     */
    public MouseJointBuilder setDampingRatio(float d) {
        mouseJointDef.dampingRatio = d;
        return this;
    }

    /**
     * Set the oscillation frequency of the joint, < 0.5/TIME_STEP.
     *
     * @param frequencyHz float, oscillation frequency, 0 for rigid joint (?)
     * @return this, for chaining
     */
    public MouseJointBuilder setFrequencyHz(float frequencyHz) {
        mouseJointDef.frequencyHz = frequencyHz;
        return this;
    }

    public MouseJointBuilder setMaxForce(float d) {
        mouseJointDef.maxForce = d;
        return this;
    }

    public MouseJointBuilder setTarget(Vector2 p) {
        mouseJointDef.target.set(p);
        return this;
    }

    public MouseJointBuilder setTarget(float x, float y) {
        mouseJointDef.target.set(x, y);
        return this;
    }

    /**
     * Build a mousejoint based on the mouseJountDef and attach user data.
     *
     * @param userData Object
     * @return MouseJoint
     */
    public MouseJoint build(Object userData) {
        MouseJoint mouseJoint = (MouseJoint) physics.world.createJoint(mouseJointDef);
        mouseJoint.setUserData(userData);
        return mouseJoint;
    }

    /**
     * Build a mousejoint based on the mouseJountDef. No user data.
     *
     * @return MouseJoint
     */
    public MouseJoint build() {
        return build(null);
    }
}