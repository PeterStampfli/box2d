package com.mygdx.game.physics;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.FrictionJoint;
import com.badlogic.gdx.physics.box2d.joints.FrictionJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.physics.box2d.joints.PulleyJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;

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
    float maxTorque;
    float motorSpeed;
    float maxLength;
    private Vector2 localAxisA=new Vector2();
    private float lowerTranslation;
    private float upperTranslation;
    private float maxForce;
    private Vector2 groundAnchorA=new Vector2();
    private Vector2 groundAnchorB=new Vector2();
    private float lengthA;
    private float lengthB;
    private float ratio;



    private DistanceJointDef distanceJointDef;
    private MouseJointDef mouseJointDef;
    private RevoluteJointDef revoluteJointDef;
    private RopeJointDef ropeJointDef;
    private PrismaticJointDef prismaticJointDef;
    private FrictionJointDef frictionJointDef;
    private PulleyJointDef pulleyJointDef;

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
        setFrequencyHz(0);
        setCollideConnected(false);
        setLocalAnchorAIsLocalCenter();
        setLocalAnchorBIsLocalCenter();
        setMaxAcceleration(5000);
        setEnableLimit(false);
        setEnableMotor(false);
        setLowerAngle(-MathUtils.PI);
        setUpperAngle(MathUtils.PI);
        setReferenceAngle(0);
        setMaxTorque(10000);
        setMaxForce(10000);
        setRotatingMotorSpeed(0);
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
     * Set the maximum length of the (rope) joint.
     *
     * @param d float, the maximum length
     * @return this Jointbuilder
     */
    public JointBuilder setMaxLength(float d) {
        maxLength = d;
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
     * Set the oscillation frequency of the joint, must be less than 0.5/TIME_STEP.
     *
     * @param f float, oscillation frequency, set 0 for rigid joint (no softness)
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
     * set reference value for revolution angle at initial positions and angles of bodies.
     * Default is zero. But the bodies might then be in a rotated state. Relevant for
     * getting current joint angle and limits.
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
     * Set the maximum torque of a joint in graphics units: kg pixels² /sec² instead of Nm.
     * (MaxMotorTorque)
     *
     * @param torque float,limit
     * @return
     */
    public JointBuilder setMaxTorque(float torque) {
        maxTorque=torque/(Physics.PIXELS_PER_METER*Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * set motor speed in radians per second for revolute joint. Positive for counter-clockwise.
     *
     * @param speed
     * @return
     */
    public JointBuilder setRotatingMotorSpeed(float speed){
        motorSpeed=speed;
        return this;
    }

    /**
     * set motor speed in pixels per second for prismatic joint.
     *
     * @param speed
     * @return
     */
    public JointBuilder setLinearMotorSpeed(float speed){
        motorSpeed=speed/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * set lower position of prismatic joint in pixels.
     *
     * @param limit
     * @return
     */
    public JointBuilder setLowerTranslation(float limit){
        lowerTranslation=limit/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * set upper position of prismatic joint in pixels.
     *
     * @param limit
     * @return
     */
    public JointBuilder setUpperTranslation(float limit){
        upperTranslation=limit/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * sets the local axis at body A of a prismatic joint.
     * (Units are irrelevant, will be normalized)
     *
     * @param x
     * @param y
     * @return
     */
    public JointBuilder setLocalAxisA(float x,float y){
        localAxisA.set(x, y).nor();
        return this;
    }

    /**
     * sets the local axis at body A of a prismatic joint. (will be normalized)
     *
     * @param v
     * @return
     */
    public JointBuilder setLocalAxisA(Vector2 v){
        setLocalAxisA(v.x,v.y);
        return this;
    }

    /**
     * set the maximum force of a joint (in kg pixels/sec² instead of N)
     *  (MotorForce for a prismatic joint)
     * @param force
     * @return
     */
    public JointBuilder setMaxForce(float force){
        maxForce=force/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * Set a ground anchor for connection to body A in pixel units.
     *
     * @param x
     * @param y
     * @return
     */
    public JointBuilder setGroundAnchorA(float x,float y){
        groundAnchorA.set(x, y).scl(1f/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set a ground anchor for connection to body A in pixel units.
     *
     * @param position
     * @return
     */
    public JointBuilder setGroundAnchorA(Vector2 position){
        return setGroundAnchorA(position.x,position.y);
    }

    /**
     * Set a ground anchor for connection to body B in pixel units.
     *
     * @param x
     * @param y
     * @return
     */
    public JointBuilder setGroundAnchorB(float x,float y){
        groundAnchorB.set(x, y).scl(1f/Physics.PIXELS_PER_METER);
        return this;
    }

    /**
     * Set a ground anchor for connection to body B in pixel units.
     *
     * @param position
     * @return
     */
    public JointBuilder setGroundAnchorB(Vector2 position){
        return setGroundAnchorB(position.x,position.y);
    }

    /**
     * set reference length for connection to body A in pixel units.
     * @param length
     * @return
     */
    public JointBuilder setLengthA(float length){
        lengthA=length/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * set reference length for connection to body B in pixel units.
     * @param length
     * @return
     */
    public JointBuilder setLengthB(float length){
        lengthB=length/Physics.PIXELS_PER_METER;
        return this;
    }

    /**
     * Set the ratio of a pulley joint
     *
     * @param x
     * @return
     */
    public JointBuilder setRatio(float x){
        ratio=x;
        return this;
    }

    /**
     * Set bodies of jointDef and set local anchor positions if they are local centers.
     * Can't set local anchors because they are not fields of JointDef.
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
     * build a pulley joint between two bodies
     *
     * @param bodyA
     * @param bodyB
     * @return
     */
    public PulleyJoint buildPulleyJoint(Body bodyA, Body bodyB) {
        if (pulleyJointDef == null) {
            pulleyJointDef = new PulleyJointDef();
        }
        setBodiesAndAnchors(pulleyJointDef, bodyA, bodyB);
        pulleyJointDef.localAnchorA.set(localAnchorA);
        pulleyJointDef.localAnchorB.set(localAnchorB);
        pulleyJointDef.groundAnchorA.set(groundAnchorA);
        pulleyJointDef.groundAnchorB.set(groundAnchorB);
        pulleyJointDef.lengthA=lengthA;
        pulleyJointDef.lengthB=lengthB;
        pulleyJointDef.ratio=ratio;
        return (PulleyJoint) physics.world.createJoint(pulleyJointDef);
    }

    /**
     * build a pulley joint between two physical sprites
     *
     * @param spriteA
     * @param spriteB
     * @return
     */
    public PulleyJoint buildPulleyJoint(PhysicalSprite spriteA,PhysicalSprite spriteB) {
        return buildPulleyJoint(spriteA.body,spriteB.body);
    }

    /**
     * build a friction joint between two bodies
     *
     * @param bodyA
     * @param bodyB
     * @return
     */
    public FrictionJoint buildFrictionJoint(Body bodyA, Body bodyB) {
        if (frictionJointDef == null) {
            frictionJointDef = new FrictionJointDef();
        }
        setBodiesAndAnchors(frictionJointDef, bodyA, bodyB);
        frictionJointDef.localAnchorA.set(localAnchorA);
        frictionJointDef.localAnchorB.set(localAnchorB);
        frictionJointDef.maxForce=maxForce;
        frictionJointDef.maxTorque=maxTorque;
        return (FrictionJoint) physics.world.createJoint(prismaticJointDef);
    }

    /**
     * build a friction joint between two physical sprites
     *
     * @param spriteA
     * @param spriteB
     * @return
     */
    public FrictionJoint buildFrictionJoint(PhysicalSprite spriteA,PhysicalSprite spriteB) {
        return buildFrictionJoint(spriteA.body,spriteB.body);
    }

    /**
     * build a prismatic joint between two bodies
     *
     * @param bodyA
     * @param bodyB
     * @return
     */
    public PrismaticJoint buildPrismaticJoint(Body bodyA, Body bodyB){
        if (prismaticJointDef==null){
            prismaticJointDef=new PrismaticJointDef();
        }
        setBodiesAndAnchors(prismaticJointDef,bodyA,bodyB);
        prismaticJointDef.localAnchorA.set(localAnchorA);
        prismaticJointDef.localAnchorB.set(localAnchorB);
        prismaticJointDef.enableLimit=enableLimit;
        prismaticJointDef.enableMotor=enableMotor;
        prismaticJointDef.localAxisA.set(localAxisA);
        prismaticJointDef.referenceAngle=referenceAngle;
        prismaticJointDef.lowerTranslation=lowerTranslation;
        prismaticJointDef.upperTranslation=upperTranslation;
        prismaticJointDef.maxMotorForce=maxForce;
        prismaticJointDef.motorSpeed=motorSpeed;
        return (PrismaticJoint) physics.world.createJoint(prismaticJointDef);
    }

    /**
     * build a prismatic joint between two physical sprites
     *
     * @param spriteA
     * @param spriteB
     * @return
     */
    public PrismaticJoint buildPrismaticJoint(PhysicalSprite spriteA, PhysicalSprite spriteB){
        return buildPrismaticJoint(spriteA.body,spriteB.body);
    }

    /**
     * Build a rope joint between two bodies. Use it to keep a chain from breaking. Attach user data later if needed.
     *
     * @param bodyA Body
     * @param bodyB Body
     * @return RopeJoint
     */
    public RopeJoint buildRopeJoint(Body bodyA,Body bodyB){
        if (ropeJointDef==null){
            ropeJointDef=new RopeJointDef();
        }
        setBodiesAndAnchors(ropeJointDef,bodyA,bodyB);
        ropeJointDef.localAnchorA.set(localAnchorA);
        ropeJointDef.localAnchorB.set(localAnchorB);
        ropeJointDef.maxLength=maxLength;
        return (RopeJoint) physics.world.createJoint(ropeJointDef);
    }

    /**
     * Build a rope joint between two physical bodies. Use it to keep a chain from breaking. Attach user data later if needed.
     *
     * @param spriteA Body
     * @param spriteB Body
     * @return RopeJoint
     */
    public RopeJoint buildRopeJoint(PhysicalSprite spriteA, PhysicalSprite spriteB){
        return buildRopeJoint(spriteA.body,spriteB.body);
    }

    /**
     * Build a revolute joint between two bodies. Attach user data later if needed.
     *
     * @param bodyA Body
     * @param bodyB Body
     * @return RevoluteJoint
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
        revoluteJointDef.maxMotorTorque=maxTorque;
        revoluteJointDef.motorSpeed=motorSpeed;
        return (RevoluteJoint) physics.world.createJoint(revoluteJointDef);
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
        return  (DistanceJoint) physics.world.createJoint(distanceJointDef);
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
            dummyBody=physics.bodyBuilder.reset().setPosition(10000,10000).buildStaticBody(null);
            physics.bodyBuilder.reset();
        }
        mouseJointDef.bodyA=dummyBody;
        mouseJointDef.bodyB=body;
        mouseJointDef.maxForce=body.getMass()*maxAcceleration;
        mouseJointDef.target.set(target).scl(1f/Physics.PIXELS_PER_METER);
        mouseJointDef.dampingRatio=dampingRatio;
        mouseJointDef.frequencyHz=frequencyHz;
        return  (MouseJoint) physics.world.createJoint(mouseJointDef);
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