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
    private Object userData;

    public DistanceJointBuilder(Physics physics) {
        this.physics = physics;
        distanceJointDef = new DistanceJointDef();
        reset();
    }

    public DistanceJointBuilder reset() {
        distanceJointDef.dampingRatio = 0;
        distanceJointDef.frequencyHz = 0;
        distanceJointDef.length = 1;
        distanceJointDef.collideConnected = true;
        distanceJointDef.localAnchorA.setZero();
        distanceJointDef.localAnchorB.setZero();
        return this;
    }

    public DistanceJointBuilder userData(Object data) {
        userData = data;
        return this;
    }

    public DistanceJointBuilder dampingRatio(float d) {
        distanceJointDef.dampingRatio = d;
        return this;
    }

    public DistanceJointBuilder frequencyHz(float d) {
        distanceJointDef.frequencyHz = d;
        return this;
    }

    public DistanceJointBuilder collideConnected(boolean c) {
        distanceJointDef.collideConnected = c;
        return this;
    }

    public DistanceJointBuilder length(float d) {
        distanceJointDef.length = d;
        return this;
    }

    public DistanceJointBuilder bodyA(Body body) {
        distanceJointDef.bodyA = body;
        return this;
    }

    public DistanceJointBuilder bodyB(Body body) {
        distanceJointDef.bodyB = body;
        return this;
    }

    public DistanceJointBuilder localAnchorA(Vector2 p) {
        distanceJointDef.localAnchorA.set(p);
        return this;
    }

    public DistanceJointBuilder localAnchorA(float x, float y) {
        distanceJointDef.localAnchorA.set(x, y);
        return this;
    }

    public DistanceJointBuilder localAnchorB(Vector2 p) {
        distanceJointDef.localAnchorB.set(p);
        return this;
    }

    public DistanceJointBuilder localAnchorB(float x, float y) {
        distanceJointDef.localAnchorB.set(x, y);
        return this;
    }

    public DistanceJointBuilder length() {
        Vector2 anchorDistance = new Vector2(distanceJointDef.bodyA.getPosition())
                .add(distanceJointDef.localAnchorA)
                .sub(distanceJointDef.bodyB.getPosition())
                .sub(distanceJointDef.localAnchorB);
        length(anchorDistance.len());
        return this;
    }


    public DistanceJoint build() {
        DistanceJoint distanceJoint = (DistanceJoint) physics.world.createJoint(distanceJointDef);
        distanceJoint.setUserData(userData);
        return distanceJoint;
    }
}
