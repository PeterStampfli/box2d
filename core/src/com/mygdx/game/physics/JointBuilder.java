package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

/**
 * A builder for all kinds of joints.
 */

public class JointBuilder {
    private Physics physics;
    public Body dummyBody;
    private Vector2 localAnchorA=new Vector2();
    boolean localAnchorAIsLocalCenter;
    private Vector2 localAnchorB=new Vector2();
    boolean localAnchorBIsLocalCenter;
    private float length;
    boolean adjustLength;
    float dampingRatio;
    float frequencyHz;
    boolean collideConnected;
    float maxAcceleration;
    boolean enableLimit;
    boolean enableMotor;
    float lowerAngle;
    float referenceAngle;
    float upperAngle;
    float maxMotorTorque;
    float motorSpeed;


    private DistanceJointDef distanceJointDef;
    private MouseJointDef mouseJointDef;
    private RevoluteJointDef revoluteJointDef;

    /**
     * Create a joint builder with access to physics.
     * Creates a dummy body for the mouse joint. Resets the bodyBuilder.
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
    public JointBuilder reset() {
        setAdjustLength();
        setDampingRatio(0);
        setFrequencyHz(10);
        setCollideConnected(false);
        setLocalAnchorAIsLocalCenter();
        setLocalAnchorBIsLocalCenter();
        setMaxAcceleration(5000);
        setEnableLimit(false);
        setEnableMotor(false);
        setLowerAngle(-MathUtils.PI);
        setUpperAngle(MathUtils.PI);
        setReferenceAngle(0);
        setMaxMotorTorque(10000);
        setMotorSpeed(0);
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
        localAnchorA.set(x,y).scl(1f/Physics.PIXELS_PER_METER);
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
        localAnchorB.set(x,y).scl(1f/Physics.PIXELS_PER_METER);
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
     * @param bodyA Body, one of the two bodies
     * @param bodyB Body, the other of the two bodies
     * @return float, estimate for the length
     */
    public float determineLength(Body bodyA,Body bodyB) {
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
     * Set that there are joint limits.
     *
     * @param enabled boolean, true to enable the limits
     * @return
     */
    public JointBuilder setEnableLimit(boolean enabled) {
        enableLimit = enabled;
        return this;
    }

    /**
     * Set that there joint has motor.
     *
     * @param enabled boolean, true to enable the motor
     * @return
     */
    public JointBuilder setEnableMotor(boolean enabled) {
        enableMotor = enabled;
        return this;
    }

    /**
     * set reference for revolution angle
     *
     * @param angle
     * @return
     */
    public JointBuilder setReferenceAngle(float angle){
        referenceAngle=angle;
        return this;
    }

    /**
     * set upper limit for revolution angle
     *
     * @param angle
     * @return
     */
    public JointBuilder setUpperAngle(float angle){
        upperAngle=angle;
        return this;
    }

    /**
     * set lower limit for revolution angle
     *
     * @param angle
     * @return
     */
    public JointBuilder setLowerAngle(float angle){
        lowerAngle=angle;
        return this;
    }

    /**
     * Set the maximum motor torque of a joint in graphics units: kg pixels² /sec² instead of Nm.
     *
     * @param torque float,limit
     * @return
     */
    public JointBuilder setMaxMotorTorque(float torque) {
        maxMotorTorque=torque/(Physics.PIXELS_PER_METER*Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * set motor speed in radians per second. Positive for counter-clockwise.
     *
     * @param speed
     * @return
     */
    public JointBuilder setMotorSpeed(float speed){
        motorSpeed=speed;
        return this;
    }

    /**
     * Set bodies of jointDef and local anchor positions of they are local centers.
     * Set collideConnected.
     *
     * @param jointDef
     * @param bodyA
     * @param bodyB
     */
    private void setBodiesAndAnchors(JointDef jointDef, Body bodyA, Body bodyB){
        jointDef.bodyA=bodyA;
        jointDef.bodyB=bodyB;
        jointDef.collideConnected=collideConnected;
        if (localAnchorAIsLocalCenter){               // adjust local anchor if it is the body center
            localAnchorA.set(bodyA.getLocalCenter()); // now we know the body
        }
        if (localAnchorBIsLocalCenter){
            localAnchorB.set(bodyB.getLocalCenter());
        }
    }

    /**
     * Build a revolute joint between two bodies. Attach user data later if needed.
     *
     * @param bodyA Body
     * @param bodyB Body
     * @return DistanceJoint
     */
    public RevoluteJoint buildRevoluteJoint(Body bodyA, Body bodyB){
        if (revoluteJointDef==null){
            revoluteJointDef=new RevoluteJointDef();
        }
        setBodiesAndAnchors(revoluteJointDef,bodyA,bodyB);
        revoluteJointDef.collideConnected=false;
        revoluteJointDef.localAnchorA.set(localAnchorA);
        revoluteJointDef.localAnchorB.set(localAnchorB);
        revoluteJointDef.enableLimit=enableLimit;
        revoluteJointDef.enableMotor=enableMotor;
        revoluteJointDef.lowerAngle=lowerAngle;
        revoluteJointDef.referenceAngle=referenceAngle;
        revoluteJointDef.upperAngle=upperAngle;
        revoluteJointDef.maxMotorTorque=maxMotorTorque;
        revoluteJointDef.motorSpeed=motorSpeed;
        RevoluteJoint revoluteJoint=(RevoluteJoint) physics.world.createJoint(revoluteJointDef);
        return revoluteJoint;
    }

    /**
     * Build a revolute joint between two physical sprites. Attach user data later if needed.
     *
     * @param spriteA
     * @param spriteB
     * @return
     */
    public RevoluteJoint buildRevoluteJoint(PhysicalSprite spriteA,PhysicalSprite spriteB) {
        return buildRevoluteJoint(spriteA.body, spriteB.body);
    }

    /**
     * Build a distance joint between two bodies. Attach user data later if needed.
     *
     * @param bodyA Body
     * @param bodyB Body
     * @return DistanceJoint
     */
    public DistanceJoint buildDistanceJoint(Body bodyA,Body bodyB){
        if (distanceJointDef==null){
            distanceJointDef=new DistanceJointDef();
        }
        setBodiesAndAnchors(distanceJointDef,bodyA,bodyB);
        distanceJointDef.localAnchorA.set(localAnchorA);
        distanceJointDef.localAnchorB.set(localAnchorB);
        if (adjustLength){
            distanceJointDef.length= determineLength(bodyA,bodyB);
        }
        else {
            distanceJointDef.length=length;
        }
        distanceJointDef.dampingRatio=dampingRatio;
        distanceJointDef.frequencyHz=frequencyHz;
        DistanceJoint distanceJoint = (DistanceJoint) physics.world.createJoint(distanceJointDef);
        return distanceJoint;
    }

    /**
     * Build a distance joint between two physical sprites. Attach user data later if needed.
     *
     * @param spriteA PhysicalSprite
     * @param spriteB PhysicalSprite
     * @return DistanceJoint
     */
    public DistanceJoint buildDistanceJoint(PhysicalSprite spriteA,PhysicalSprite spriteB){
        return buildDistanceJoint(spriteA.body,spriteB.body);
    }

    /**
     * Build a mouseJoint based on the mouseJointDef.
     * Creates a reasonable dummy body. Change if no good.
     * Attach user data later if needed.
     *
     * @param body Body, the body to move.
     * @param target Vector2, world point, where the mouseJoint is attached to the body, in pixels
     * @return MouseJoint
     */
    public MouseJoint buildMouseJoint(Body body,Vector2 target) {
        if (mouseJointDef==null){
            mouseJointDef=new MouseJointDef();
            dummyBody=physics.bodyBuilder.reset().setPosition(10000,00).buildStaticBody(null);
            physics.bodyBuilder.reset();
        }
        mouseJointDef.bodyA=dummyBody;
        mouseJointDef.bodyB=body;
        mouseJointDef.maxForce=body.getMass()*maxAcceleration;
        mouseJointDef.target.set(target).scl(1f/Physics.PIXELS_PER_METER);
        mouseJointDef.dampingRatio=dampingRatio;
        mouseJointDef.frequencyHz=frequencyHz;
        MouseJoint mouseJoint = (MouseJoint) physics.world.createJoint(mouseJointDef);
        return mouseJoint;
    }

    /**
     * Build a mouseJoint based on the mouseJointDef.
     * Creates a reasonable dummy body. Change if no good.
     * Attach user data later if needed.
     *
     * @param sprite PhysicalSprite, the sprite to move.
     * @param target Vector2, world point, where the mouseJoint is attached to the body, in pixels
     * @return MouseJoint
     */
    public MouseJoint buildMouseJoint(PhysicalSprite sprite,Vector2 target) {
        return buildMouseJoint(sprite.body,target);
    }
}