package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * A builder for all kinds of joints.
 */

public class JointBuilder {
    private Physics physics;
    private Body bodyA;
    private Body bodyB;
    private Vector2 localAnchorA;
    private Vector2 localAnchorB;
    private float length;
    boolean adjustLength;
    float dampingRatio;
    float frequencyHz;
    boolean setCollideConnected;



    /**
     * Create a joint builder with access to physics.
     *
     * @param physics Physics with a world, that creates the joints
     */
    public JointBuilder(Physics physics){
        this.physics=physics;
        reset();
    }


    /**
     * Reset to default values.
     * Makes it a stiff joint.
     *
     * @return this, for chaining
     */
    public DistanceJointBuilder reset() {
        setAdjustLength();

        distanceJointDef.dampingRatio = 0;
        distanceJointDef.frequencyHz = 0;
        distanceJointDef.collideConnected = true;
        distanceJointDef.localAnchorA.setZero();
        distanceJointDef.localAnchorB.setZero();
        return this;
    }

    /**
     * Set one of the connected bodies.
     *
     * @param body Body, attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyA(Body body) {
        bodyA = body;
        return this;
    }

    /**
     * Set the other connected body.
     *
     * @param body Body, attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyB(Body body) {
        bodyB = body;
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
    public JointBuilder setLocalAnchorA(float x, float y) {
        localAnchorA.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the LOCAL anchor point on body A. Relative to local origin and rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param position Vector2, position in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorA(Vector2 position) {
        return setLocalAnchorA(position.x,position.y);
    }

    /**
     * Set that the local anchor of body A is its center.
     *
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorAIsLocalCenter() {
        return setLocalAnchorA(0,0);
    }

    /**
     * Set the LOCAL anchor point on body B. Relative to local origin and rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param x float, x-coordinate in pixels for the anchor
     * @param y float, x-coordinate in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorB(float x, float y) {
        localAnchorB.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the LOCAL anchor point on body B. Relative to local origin and rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param position Vector2, position in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorB(Vector2 position) {
        return setLocalAnchorB(position.x,position.y);
    }

    /**
     * Set that the local anchor of body B is its center.
     *
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorBIsLocalCenter() {
        return setLocalAnchorB(0,0);
    }

    /**
     * Set the length of the joint.
     *
     * @param d float, the length
     * @return this Jointbuilder
     */
    public JointBuilder setLength(float d) {
        adjustLength=false;
        length = d;
        return this;
    }

    /**
     * Set that the joint length will be determined after joint creation using getLength/setLength.
     *
     * @return
     */
    public JointBuilder setAdjustLength() {
        adjustLength=true;
        return this;
    }

    /**
     * Estimate Length of joint from distance between body positions.
     *
     * @return float, estimate for the length
     */
    public float estimateLength() {
        return bodyA.getPosition().dst(bodyB.getPosition());

    }

}
