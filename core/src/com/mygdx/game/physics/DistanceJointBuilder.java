package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

/**
 * Created by peter on 3/22/17.
 */
public class DistanceJointBuilder {
    private Physics physics;
    private DistanceJointDef distanceJointDef;

    /**
     * creates and resets the distanceJointDef
     * @param physics
     */
    public DistanceJointBuilder(Physics physics) {
        this.physics = physics;
        distanceJointDef = new DistanceJointDef();
        reset();
    }

    /**
     * reset the distanceJointDef to default values
     *  (a stiff joint)
     * @return
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

    public DistanceJointBuilder setDampingRatio(float d) {
        distanceJointDef.dampingRatio = d;
        return this;
    }

    public DistanceJointBuilder setFrequencyHz(float d) {
        distanceJointDef.frequencyHz = d;
        return this;
    }

    public DistanceJointBuilder setCollideConnected(boolean c) {
        distanceJointDef.collideConnected = c;
        return this;
    }

    public DistanceJointBuilder setBodyA(Body body) {
        distanceJointDef.bodyA = body;
        return this;
    }

    public DistanceJointBuilder setBodyB(Body body) {
        distanceJointDef.bodyB = body;
        return this;
    }

    public DistanceJointBuilder setLocalAnchorA(Vector2 p) {
        distanceJointDef.localAnchorA.set(p);
        return this;
    }

    public DistanceJointBuilder setLocalAnchorA(float x, float y) {
        distanceJointDef.localAnchorA.set(x, y);
        return this;
    }

    public DistanceJointBuilder setLocalAnchorAIsLocalCenter() {
        distanceJointDef.localAnchorA.set(distanceJointDef.bodyA.getLocalCenter());
        return this;
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
        distanceJointDef.length = d;
        return this;
    }

    public DistanceJointBuilder setLength() {
        Vector2 anchorDistance = new Vector2(distanceJointDef.bodyA.getPosition())
                .add(distanceJointDef.localAnchorA)
                .sub(distanceJointDef.bodyB.getPosition())
                .sub(distanceJointDef.localAnchorB);
        setLength(anchorDistance.len());
        return this;
    }


    public DistanceJoint build(Object userData) {
        DistanceJoint distanceJoint = (DistanceJoint) physics.world.createJoint(distanceJointDef);
        distanceJoint.setUserData(userData);
        return distanceJoint;
    }

    public DistanceJoint build() {
        return build(null);
    }
}
