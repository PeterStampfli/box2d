package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

/**
 * Build distance joints.
 */
public class DistanceJointBuilder {
    private Physics physics;
    private DistanceJointDef distanceJointDef;
    private boolean adjustLength=false;

    /**
     * Creates and resets the distanceJointDef data.
     *
     * @param physics Physics, with the world.
     */
    public DistanceJointBuilder(Physics physics) {
        this.physics = physics;
        distanceJointDef = new DistanceJointDef();
        reset();
    }

    /**
     * Reset the distanceJointDef to default values.
     * Makes it a stiff joint.
     *
     * @return this, for chaining
     */
    public DistanceJointBuilder reset() {
        distanceJointDef.dampingRatio = 0;
        distanceJointDef.frequencyHz = 0;
        distanceJointDef.length = 1;
        distanceJointDef.collideConnected = true;
        distanceJointDef.localAnchorA.setZero();
        distanceJointDef.localAnchorB.setZero();
        return this;
    }

    /**
     * Set dimensionless damping of the joint. 0 is no damping. 1 is critical damping (no overshoot).
     *
     * @param d float, damping
     * @return this, for chaining
     */
    public DistanceJointBuilder setDampingRatio(float d) {
        distanceJointDef.dampingRatio = d;
        return this;
    }

    /**
     * Set the oscillation frequency of the joint, < 0.5/TIME_STEP.
     *
     * @param frequencyHz float, oscillation frequency, 0 for rigid joint (?)
     * @return this, for chaining
     */
    public DistanceJointBuilder setFrequencyHz(float frequencyHz) {
        distanceJointDef.frequencyHz = frequencyHz;
        return this;
    }

    /**
     * Set that the bodies connected by the joint can collide or not.
     *
     * @param enabled boolean, true to enable the collision
     * @return
     */
    public DistanceJointBuilder setCollideConnected(boolean enabled) {
        distanceJointDef.collideConnected = enabled;
        return this;
    }

    /**
     * Set one of the connected bodies.
     *
     * @param body Body, attached to the joint
     * @return this, for chaining
     */
    public DistanceJointBuilder setBodyA(Body body) {
        distanceJointDef.bodyA = body;
        return this;
    }

    /**
     * Set the other connected body.
     *
     * @param body Body, attached to the joint
     * @return this, for chaining
     */
    public DistanceJointBuilder setBodyB(Body body) {
        distanceJointDef.bodyB = body;
        return this;
    }

    /**
     * Set the LOCAL anchor point on body A. Relative to local origin and rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param x float, x-coordinate in pixels for the anchor
     * @param y float, x-coordinate in pixels for the anchor
     * @return this, for chaining
     */
    public DistanceJointBuilder setLocalAnchorA(float x, float y) {
        distanceJointDef.localAnchorA.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the LOCAL anchor point on body A. Relative to local origin and rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param position Vector2, position in pixels for the anchor
     * @return this, for chaining
     */
    public DistanceJointBuilder setLocalAnchorA(Vector2 position) {
        return setLocalAnchorA(position.x,position.y);
    }

    /**
     * Set that the local anchor of body A is its center.
     *
     * @return this, for chaining
     */
    public DistanceJointBuilder setLocalAnchorAIsLocalCenter() {
        return setLocalAnchorA(0,0);
    }

    public DistanceJointBuilder setLocalAnchorB(Vector2 p) {
        distanceJointDef.localAnchorB.set(p);
        return this;
    }

    public DistanceJointBuilder setLocalAnchorB(float x, float y) {
        distanceJointDef.localAnchorB.set(x, y);
        return this;
    }

    public DistanceJointBuilder setLocalAnchorBIsLocalCenter() {
        distanceJointDef.localAnchorB.set(distanceJointDef.bodyB.getLocalCenter());
        return this;
    }

    public DistanceJointBuilder setLength(float d) {
        adjustLength=false;
        distanceJointDef.length = d;
        return this;
    }

    public DistanceJointBuilder setAdjustLength() {
        adjustLength=true;
        return this;
    }

    public void adjustTheLength() {
        Vector2 anchorDistance = new Vector2(distanceJointDef.bodyA.getPosition())
                .add(distanceJointDef.localAnchorA)
                .sub(distanceJointDef.bodyB.getPosition())
                .sub(distanceJointDef.localAnchorB);
        setLength(anchorDistance.len());
    }


    public DistanceJoint build(Object userData) {
        if (adjustLength){
            adjustTheLength();
        }
        DistanceJoint distanceJoint = (DistanceJoint) physics.world.createJoint(distanceJointDef);
        distanceJoint.setUserData(userData);
        return distanceJoint;
    }

    public DistanceJoint build() {
        return build(null);
    }
}
