package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

/**
 * Created by peter on 3/22/17.
 */
public class MouseJointBuilder {
    private Physics physics;
    private MouseJointDef mouseJointDef;

    public MouseJointBuilder(Physics physics) {
        this.physics = physics;
        mouseJointDef = new MouseJointDef();
    }

    public MouseJointBuilder reset() {
        mouseJointDef.collideConnected = true;
        mouseJointDef.dampingRatio = 0.7f;
        mouseJointDef.frequencyHz = 5;
        mouseJointDef.maxForce = 10;
        return this;
    }

    public MouseJointBuilder setDummyBody(Body body) {
        mouseJointDef.bodyA = body;
        return this;
    }

    public MouseJointBuilder setBody(Body body) {
        mouseJointDef.bodyB = body;
        return this;
    }

    public MouseJointBuilder setCollideConnected(Boolean b) {
        mouseJointDef.collideConnected = b;
        return this;
    }

    public MouseJointBuilder setDampingRatio(float d) {
        mouseJointDef.dampingRatio = d;
        return this;
    }

    public MouseJointBuilder setFrequencyHz(float d) {
        mouseJointDef.frequencyHz = d;
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

    public MouseJoint build(Object userData) {
        MouseJoint mouseJoint = (MouseJoint) physics.world.createJoint(mouseJointDef);
        mouseJoint.setUserData(userData);
        return mouseJoint;
    }

    public MouseJoint build() {
        return build(null);
    }
}