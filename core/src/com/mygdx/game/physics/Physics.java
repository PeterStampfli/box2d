package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Device;
import com.mygdx.game.utilities.TimeU;

/**
 * Setting up the physics world and debugCamera. Advancing physics.
 */

public class Physics {
    Device device;
    public World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera debugCamera;
    public BodyBuilder bodyBuilder;
    public FixtureBuilder fixtureBuilder;
    public JointBuilder jointBuilder;
    Pool<PhysicalSprite> physicalSpritePool;
    public PhysicalSpriteBuilder physicalSpriteBuilder;
    public Balance balance;
    float physicsTime;
    float graphicsTime;
    Array<Body> bodies;
    boolean bodiesNeedUpdate = true;
    static public float PIXELS_PER_METER = 30;       // default
    static final float TIME_STEP = 1 / 60f;
    final float MAX_TIME_INTERVAL = 0.25f;
    final int VELOCITY_ITERATIONS = 8;
    final int POSITION_ITERATIONS = 3;

    /**
     * Initialize box2D.
     *  @param device Device
     *
     */
    public Physics(Device device) {
        this.device=device;
        bodies = new Array<Body>();
        Box2D.init();
        bodyBuilder=new BodyBuilder(this);
        fixtureBuilder=new FixtureBuilder();
        jointBuilder=new JointBuilder(this);
        physicalSpritePool= Pools.get(PhysicalSprite.class);
        physicalSpriteBuilder=new PhysicalSpriteBuilder(device,this);
    }

    /**
     * Create a box2D world with gravity acceleration in pixels/sec².
     * Then the physics gives the same graphics results for different PIXELS_PER_METER values.
     *
     * @param gravityX float, gravitational acceleration in x-direction, pixels/sec²
     * @param gravityY float, gravitational acceleration in y-direction, pixels/sec²
     * @param doSleep boolean, true if non-moving bodies are not simulated (saves computer time,
     *                but resting bodies do not begin to move if gravity changes
     */
    public void createWorld(float gravityX, float gravityY, boolean doSleep) {
        if (world != null) {
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        } else {
            world = new World(new Vector2(gravityX, gravityY).scl(1f/PIXELS_PER_METER),
                                doSleep);
            device.disposer.add(world,"Physics.world");
        }
    }

    /**
     * create a balance object with given boost scale. registers it for resizing in device.
     *
     * @param scale float, scaling the gravity
     */
    public void createBalance(float scale){
        balance=new Balance(this,scale);
        device.addResizable(balance);
    }

    /**
     * create the debug renderer. comment out call of this method to suppress debug rendering.
     */
    public void createDebugRenderer(){
        debugRenderer = new Box2DDebugRenderer();
        device.disposer.add(debugRenderer,"Box2DDebugRenderer");
        debugCamera = new OrthographicCamera();
    }

    /**
     * If debugRenderer exists then do a debug rendering of result of last physics step.
     * Note that because of interpolation, the graphics positions lag slightly behind the physics positions.
     *
     * @param graphicsViewport Viewport, of the graphics world,
     *                         its camera data is scaled for setting the camera of the debugRenderer
     */
    public void debugRender(Viewport graphicsViewport) {
        if (debugRenderer != null) {
            Camera graphicsCamera = graphicsViewport.getCamera();
            debugCamera.position.set(graphicsCamera.position).scl(1f / PIXELS_PER_METER);
            debugCamera.viewportWidth = graphicsCamera.viewportWidth / PIXELS_PER_METER;
            debugCamera.viewportHeight = graphicsCamera.viewportHeight / PIXELS_PER_METER;
            debugCamera.update();
            debugRenderer.render(world, debugCamera.combined);
        }
    }

    // forces and torques

    /**
     * Get the reaction torque of a joint in graphics units: kg pixels² /sec² instead of Nm.
     *
     * @param joint Joint, to examine
     * @return float, reaction torque of a joint in kg pixels²/sec²
     */
    public float getReactionTorque(Joint joint) {
        return joint.getReactionTorque(1.0f / TIME_STEP) * PIXELS_PER_METER * PIXELS_PER_METER;
    }

    /**
     * Get the reaction force of a joint scaled to graphics units: kg pixels/sec².
     *
     * @param joint Joint, to examine
     * @return reaction force as Vector2, will be overwritten at next call
     */
    public Vector2 getReactionForce(Joint joint) {
        return joint.getReactionForce(1.0f / TIME_STEP).scl(PIXELS_PER_METER);
    }

    // instead of extending the body class: special methods

    /**
     * Get the bodies local center of mass in graphics units.
     *
     * Usually we first create the sprite with its shapes. Then the body and its fixtures. This
     * determines the center of mass for the body.
     * Thus we set the origin (center of rotation and scaling) of the sprite equal to center of mass of the body.
     * In local coordinates. Zero is left bottom corner of the TextureRegion.
     * Scales from physics dimensions to graphics. Call after creating all fixtures.
     * Note that the local origin does not depend on translation and rotation.
     *
     * @param body can be any bodyType
     * @return Vector2, for the center of mass, BEWARE: always the same instance
     */
    static public Vector2 getLocalCenter(Body body){
        BodyDef.BodyType bodyType=body.getType();
        if (bodyType!= BodyDef.BodyType.DynamicBody){
            body.setType(BodyDef.BodyType.DynamicBody);
        }
        Vector2 bodyCenter=body.getLocalCenter().scl(PIXELS_PER_METER);
        if (bodyType!= BodyDef.BodyType.DynamicBody){
            body.setType(bodyType);
        }
        return bodyCenter;
    }

    /**
     * Get the position of the body in graphics units.
     *
     * @param body Body
     * @return Vector2, for center of mass, always the same instance
     */
    static public Vector2 getPosition(Body body){
        return body.getPosition().scl(Physics.PIXELS_PER_METER);        // that's always the same object
    }

    /**
     * Get the center of mass of the body. World position in graphics units.
     * Reading its position and using the rotated local origin of the sprite.
     *
     * @param body Body
     * @param originX float, x-coordinate of the local origin of the sprite in pixels
     * @param originY float, y-coordinate of the local origin of the sprite in pixels
     * @return Vector2, for center of mass, always the same instance
     */
    static public Vector2 getCenterOfMass(Body body,float originX, float originY){
        float angle=body.getAngle();
        float sinAngle= MathUtils.sin(angle);
        float cosAngle=MathUtils.cos(angle);
        Vector2 bodyPosition=getPosition(body);        // that's always the same object
        bodyPosition.x+=cosAngle*originX-sinAngle*originY;
        bodyPosition.y+=sinAngle*originX+cosAngle*originY;
        return bodyPosition;
    }

    /**
     * Set the position of center of mass of the body and its angle. Pixel units.
     * Position of the body results from its center of mass and rotated sprites origin.
     *
     * @param body Body
     * @param centerX float, x-coordinate of center of mass
     * @param centerY float, y-coordinate of center of mass
     * @param angle float
     * @param originX float, x-coordinate of the local origin of the sprite in pixels
     * @param originY float, y-coordinate of the local origin of the sprite in pixels
     */
    static public void setCenterOfMassAngle(Body body,float centerX,float centerY,float angle,
                                            float originX,float originY){
        float sinAngle=MathUtils.sin(angle);
        float cosAngle=MathUtils.cos(angle);
        body.setTransform((centerX-cosAngle*originX+sinAngle*originY)/PIXELS_PER_METER,
                (centerY-sinAngle*originX-cosAngle*originY)/PIXELS_PER_METER,
                angle);
    }

    /**
     * Set the position of body and angle. Position results from center of mass and rotated sprites origin.
     *
     * @param body Body
     * @param center Vector2
     * @param angle float
     * @param originX float, x-coordinate of the local origin of the sprite in pixels
     * @param originY float, y-coordinate of the local origin of the sprite in pixels
     */
    static public void setCenterOfMassAngle(Body body,Vector2 center,float angle,
                                            float originX,float originY){
        setCenterOfMassAngle(body,center.x,center.y,angle,originX,originY);
    }

    /**
     * set the velocity (of a kinematic body). Given in pixels/sec
     *
     * @param body Body
     * @param vx float, x-component of velocity
     * @param vy float, y-component of velocity
     */
    static public void setVelocity(Body body,float vx,float vy){
        body.setLinearVelocity(vx/PIXELS_PER_METER,vy/PIXELS_PER_METER);
    }

    /**
     * set the velocity (of a kinematic body). Given in pixels/sec
     *
     * @param body Body
     * @param v Vector2, velocity
     */
    static public void setVelocity(Body body,Vector2 v){
        body.setLinearVelocity(v.x/PIXELS_PER_METER,v.y/PIXELS_PER_METER);
    }

    // doing the physics ....

    /**
     * Start the physics. Sets the physics time equal to current time.
     */
    public void start() {
        physicsTime = TimeU.getTime();
    }

    /**
     * Update the bodies array if bodies have been created or destroyed.
     */
    public void updateBodies() {
        if (bodiesNeedUpdate) {
            world.getBodies(bodies);
            bodiesNeedUpdate = false;
        }
    }

    /**
     * Call body followers to prepare a time step.
     * Make one time step. Increase physics time by the fixed time step.
     * Override this to remove or create bodies after a world step.
     */
    public void step() {
        Object userData;
        updateBodies();
        for (Body body : bodies) {
            userData = body.getUserData();
            if (userData instanceof BodyFollower) {
                ((BodyFollower) userData).prepareTimeStep();
            }
        }
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        physicsTime += TIME_STEP;
    }

    /**
     * Set the positions and angles of all PhysicalSprite userData objects that have dynamic bodies.
     * Updates the Array of bodies if some bodies may have been destroyed or created in the world step.
     */
    public void setPhysicsData() {
        Object userData;
        updateBodies();
        for (Body body : bodies) {
            userData = body.getUserData();
            if ((body.getType()!= BodyDef.BodyType.StaticBody)&&(userData instanceof BodyFollower)) {
                ((BodyFollower) userData).readPositionAngleOfBody();
            }
        }
    }

    /**
     * Let the PhysicalSprite userData objects interpolate positions and angles to give the data at graphics time.
     * progress=1 gets the new physics data, progress=0 gets the previous physics data.
     * For static body use last position as dynamic body and fix progress=1.
     *
     * @param progress float, interpolation parameter
     */
    public void updateGraphicsData(float progress) {
        Object userData;
        updateBodies();
        for (Body body : bodies) {
            userData = body.getUserData();
            if ((body.getType()!= BodyDef.BodyType.StaticBody)&&(userData instanceof BodyFollower)) {
                    ((BodyFollower) userData).interpolatePositionAngleOfBody(progress);
            }
        }
    }

    /**
     * Advance physics with fixed time steps.
     * The graphics time is the real world time at the call of this method.
     * Thus advance the physics time past the graphics time.
     * Uses interpolation for the positions and angles of sprites at graphics time.
     * Clears forces explicitly.
     */
    public void advance() {
        world.setAutoClearForces(false);
        graphicsTime = TimeU.getTime();
        if (physicsTime < graphicsTime) {   // we have to advance time with fixed time step
            physicsTime = Math.max(physicsTime, graphicsTime - MAX_TIME_INTERVAL);  //prevent spiral of death
            if (physicsTime < graphicsTime - TIME_STEP) {   // we need more than one time step
                while (physicsTime < graphicsTime - TIME_STEP) {  // advance until we are close
                    step();
                }
                setPhysicsData();
            }
            // physics time is less than TIME_STEP behind graphics time,
            // new physics result of sprites is set to this time
            step();
            setPhysicsData();
        }
        world.clearForces();
        float progress = 1 - (physicsTime - graphicsTime) / TIME_STEP;  // 1 if physicsTime=graphicsTime, decreasing to zero
        updateGraphicsData(progress);
    }
}
