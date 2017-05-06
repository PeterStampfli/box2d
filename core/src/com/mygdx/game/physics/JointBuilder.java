package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;

/**
 * A builder for all kinds of joints.
 */

public class JointBuilder {
    private Physics physics;
    private Body bodyA;
    private Body bodyB;
    public Body dummyBody;
    private Vector2 localAnchorA=new Vector2();
    boolean localAnchorAIsLocalCenter;
    private Vector2 localAnchorB=new Vector2();
    boolean localAnchorBIsLocalCenter;
    private Vector2 target=new Vector2();
    private float length;
    boolean adjustLength;
    float dampingRatio;
    float frequencyHz;
    boolean collideConnected;
    float maxAcceleration;


    private DistanceJointDef distanceJointDef;
    private MouseJointDef mouseJointDef;

    /**
     * Create a joint builder with access to physics.
     * Creates a dummy body for the mouse joint. Resets the bodyBuilder.
     *
     * @param physics Physics with a world, that creates the joints
     */
    public JointBuilder(Physics physics){
        this.physics=physics;
        dummyBody=physics.bodyBuilder.reset().setStaticBody().setPosition(100,100).build();
        physics.bodyBuilder.reset();
        reset();
    }

    /**
     * Reset to default values.
     * Makes it a stiff joint.
     *
     * @return this, for chaining
     */
    public JointBuilder reset() {
        setAdjustLength();
        setDampingRatio(0);
        setFrequencyHz(10);
        setCollideConnected(false);
        setLocalAnchorAIsLocalCenter();
        setLocalAnchorBIsLocalCenter();
        setMaxAcceleration(5000);
        return this;
    }

    /**
     * Set one of the connected bodies.
     *
     * @param body Body, will be attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyA(Body body) {
        bodyA = body;
        return this;
    }

    /**
     * Set one of the connected bodies.
     *
     * @param physicalSprite PhysicalSprite, its Body will be attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyA(PhysicalSprite physicalSprite) {
        bodyA = physicalSprite.body;
        return this;
    }

    /**
     * Set one of the connected bodies to be the dummy body. For the mouse joint.
     * (A static body without fixture, far away).
     *
     * @return this, for chaining
     */
    public JointBuilder setBodyAIsDummy() {
        bodyA = dummyBody;
        return this;
    }

    /**
     * Set the other connected body.
     *
     * @param body Body, will be attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyB(Body body) {
        bodyB = body;
        return this;
    }

    /**
     * Set the other of the connected bodies.
     *
     * @param physicalSprite PhysicalSprite, its Body will be attached to the joint
     * @return this, for chaining
     */
    public JointBuilder setBodyB(PhysicalSprite physicalSprite) {
        bodyB = physicalSprite.body;
        return this;
    }

    /**
     * Set the LOCAL anchor point on body A.
     * Relative to local "origin" at getPosition(), corresponds to the lower left corner of the sprite image,
     * not the center of mass and for rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param x float, x-coordinate in pixels for the anchor
     * @param y float, x-coordinate in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorA(float x, float y) {
        localAnchorAIsLocalCenter =false;
        localAnchorA.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the LOCAL anchor point on body A.
     *Relative to local "origin" at getPosition(), corresponds to the lower left corner of the sprite image,
     * not the center of mass and for rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param position Vector2, position in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorA(Vector2 position) {
        return setLocalAnchorA(position.x,position.y);
    }

    /**
     * Set that the local anchor of body A at its center.
     *
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorAIsLocalCenter() {
        localAnchorAIsLocalCenter =true;
        return this;
    }

    /**
     * Set the LOCAL anchor point on body B.
     * Relative to local "origin" at getPosition(), corresponds to the lower left corner of the sprite image,
     * not the center of mass and for rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param x float, x-coordinate in pixels for the anchor
     * @param y float, x-coordinate in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorB(float x, float y) {
        localAnchorBIsLocalCenter =false;
        localAnchorB.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the LOCAL anchor point on body B.
     * Relative to local "origin" at getPosition(), corresponds to the lower left corner of the sprite image,
     * not the center of mass and for rotation angle=0.
     * Uses pixels (graphics) as unit for length.
     *
     * @param position Vector2, position in pixels for the anchor
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorB(Vector2 position) {
        return setLocalAnchorB(position.x,position.y);
    }

    /**
     * Set that the local anchor of body B at its center.
     *
     * @return this, for chaining
     */
    public JointBuilder setLocalAnchorBIsLocalCenter() {
        localAnchorBIsLocalCenter =true;
        return this;
    }

    /**
     * Set the world target point in pixels.
     *
     * @param x float, x-coordinate in pixels
     * @param y float, y-coordinate in pixels
     * @return
     */
    public JointBuilder setTarget(float x, float y) {
        target.set(x/Physics.PIXELS_PER_METER, y/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set the world target point in pixels.
     *
     * @param p Vector2, position in pixels
     * @return
     */
    public JointBuilder setTarget(Vector2 p) {
        return setTarget(p.x,p.y);
    }

    /**
     * Set the maximum acceleration for mouse joints in pixels/sec².
     * (maxForce=max acceleration* mass)
     *
     * @param a float, limit for acceleration
     * @return this, JointBuilder
     */
    public JointBuilder setMaxAcceleration(float a){
        maxAcceleration=a/Physics.PIXELS_PER_METER;
        return this;
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
     * Determine length between anchor points on the bodies using transformation to world points.
     *
     * @return float, estimate for the length
     */
    public float determineLength() {
        return bodyA.getWorldPoint(localAnchorA).sub(bodyB.getWorldPoint(localAnchorB)).len();
    }

    /**
     * Set dimensionless damping of the joint. 0 is no damping. 1 is critical damping (no overshoot).
     *
     * @param d float, damping
     * @return this, for chaining
     */
    public JointBuilder setDampingRatio(float d) {
        dampingRatio = d;
        return this;
    }

    /**
     * Set the oscillation frequency of the joint, < 0.5/TIME_STEP.
     *
     * @param f float, oscillation frequency, 0 for rigid joint (?)
     * @return this, for chaining
     */
    public JointBuilder setFrequencyHz(float f) {
        frequencyHz = f;
        return this;
    }

    /**
     * Set that the bodies connected by the joint can collide or not.
     *
     * @param enabled boolean, true to enable the collision
     * @return
     */
    public JointBuilder setCollideConnected(boolean enabled) {
        collideConnected = enabled;
        return this;
    }

    /**
     * Set basic joint def data for all joints.
     *
     * @param jointDef JointDef subclass
     */
    private void setBasicJointDef(JointDef jointDef){
        jointDef.bodyA=bodyA;
        jointDef.bodyB=bodyB;
        jointDef.collideConnected=collideConnected;
    }

    /**
     * Build a distance joint with user data.
     *
     * @param userData
     * @return DistanceJoint
     */
    public DistanceJoint buildDistanceJoint(Object userData){
        if (distanceJointDef==null){
            distanceJointDef=new DistanceJointDef();
        }
        setBasicJointDef(distanceJointDef);
        if (localAnchorAIsLocalCenter){               // adjust local anchor if it is the body center
            localAnchorA.set(bodyA.getLocalCenter()); // now we know the body
        }
        if (localAnchorBIsLocalCenter){
            localAnchorB.set(bodyB.getLocalCenter());
        }
        distanceJointDef.localAnchorA.set(localAnchorA);
        distanceJointDef.localAnchorB.set(localAnchorB);
        if (adjustLength){
            distanceJointDef.length= determineLength();
        }
        else {
            distanceJointDef.length=length;
        }
        distanceJointDef.dampingRatio=dampingRatio;
        distanceJointDef.frequencyHz=frequencyHz;
        DistanceJoint distanceJoint = (DistanceJoint) physics.world.createJoint(distanceJointDef);
        distanceJoint.setUserData(userData);
        return distanceJoint;
    }

    /**
     * Build a distance joint without data.
     *
     * @return DistanceJoint
     */

    public DistanceJoint buildDistanceJoint(){
        return buildDistanceJoint(null);
    }

    /**
     * Build a mousejoint based on the mouseJountDef and attach user data.
     *
     * @param userData Object
     * @return MouseJoint
     */
    public MouseJoint buildMouseJoint(Object userData) {
        if (mouseJointDef==null){
            mouseJointDef=new MouseJointDef();
        }
        setBasicJointDef(mouseJointDef);
        mouseJointDef.dampingRatio=dampingRatio;
        mouseJointDef.frequencyHz=frequencyHz;
        mouseJointDef.maxForce=bodyB.getMass()*maxAcceleration;
        mouseJointDef.target.set(target);
        MouseJoint mouseJoint = (MouseJoint) physics.world.createJoint(mouseJointDef);
        mouseJoint.setUserData(userData);
        return mouseJoint;
    }

    /**
     * Build a mousejoint based on the mouseJountDef. No user data.
     *
     * @return MouseJoint
     */
    public MouseJoint buildMouseJoint() {
        return buildMouseJoint(null);
    }
}