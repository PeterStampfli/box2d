package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

/**
 * Created by peter on 3/22/17.
 */
public class MouseJointBuilder {
    private World world;
    private MouseJointDef mouseJointDef;

    public MouseJointBuilder(World world) {
        this.world = world;
        mouseJointDef = new MouseJointDef();
    }

    public MouseJointBuilder reset() {
        mouseJointDef.collideConnected = true;
        mouseJointDef.dampingRatio = 0.7f;
        mouseJointDef.frequencyHz = 5;
        mouseJointDef.maxForce = 10;
        return this;
    }

    public MouseJointBuilder dummyBody(Body body) {
        mouseJointDef.bodyA = body;
        return this;
    }

    public MouseJointBuilder body(Body body) {
        mouseJointDef.bodyB = body;
        return this;
    }

    public MouseJointBuilder collideConnected(Boolean b) {
        mouseJointDef.collideConnected = b;
        return this;
    }

    public MouseJointBuilder dampingRatio(float d) {
        mouseJointDef.dampingRatio = d;
        return this;
    }

    public MouseJointBuilder frequencyHz(float d) {
        mouseJointDef.frequencyHz = d;
        return this;
    }

    public MouseJointBuilder maxForce(float d) {
        mouseJointDef.maxForce = d;
        return this;
    }

    public MouseJointBuilder target(Vector2 p) {
        mouseJointDef.target.set(p);
        return this;
    }

    public MouseJointBuilder target(float x, float y) {
        mouseJointDef.target.set(x, y);
        return this;
    }

    public MouseJoint build(Object userData) {
        MouseJoint mouseJoint = (MouseJoint) world.createJoint(mouseJointDef);
        mouseJoint.setUserData(userData);
        return mouseJoint;
    }

    public MouseJoint build() {
        return build(null);
    }
}