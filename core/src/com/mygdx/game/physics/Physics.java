package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;

/**
 * Setting up the physics world and debugCamera. Advancing physics.
 */

public class Physics implements Disposable {
    public World world;
    public BodyBuilder bodyBuilder;
    public FixtureBuilder fixtureBuilder;
    Pool<PhysicalSprite> physicalSpritePool;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera debugCamera;
    float physicsTime;
    float graphicsTime;
    Array<Body> bodies;
    boolean bodiesNeedUpdate = true;
    private float accumulator = 0f;
    static public float PIXELS_PER_METER = 100;       // default
    final float TIME_STEP = 1 / 60f;
    final float MAX_TIMEINTERVAL = 0.25f;
    final int VELOCITY_ITERATIONS = 8;
    final int POSITION_ITERATIONS = 3;

    /**
     * Initialize box2D and debugRenderer.
     *
     * @param debug boolean, true for creating a debugRenderer
     */
    public Physics(boolean debug) {
        bodies = new Array<Body>();
        if (debug) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = new OrthographicCamera();
        }
        Box2D.init();
        bodyBuilder=new BodyBuilder(this);
        fixtureBuilder=new FixtureBuilder();
        physicalSpritePool= Pools.get(PhysicalSprite.class);
    }

    /**
     * Create and return a box2D world with gravity acceleration in pixels/sec².
     * Then the physics gives the same graphics results for different PIXELS_PER_METER values.
     *
     * @param gravityX float, gravitational acceleration in x-direction, pixels/sec²
     * @param gravityY
     * @param maySleep
     * @return World, a box2D world
     */
    public World createWorld(float gravityX, float gravityY, boolean maySleep) {
        if (world != null) {
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        } else {
            world = new World(new Vector2(gravityX, gravityY), maySleep);
        }
        return world;
    }

    /**
     * If debug==true at physics creation then do a debug rendering of result of last physics step.
     * Note that because of interpolation, the graphics positions lag slightly behind the physics positions.
     *
     * @param graphicsViewport Viewport, of the graphics world, its camera data is scaled to set the camera for the debugRenderer
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

    /**
     * Start the physics. Sets the physics time equal to current time.
     */
    public void start() {
        physicsTime = Basic.getTime();
    }

    /**
     * Make one time step. Increase physics time by the fixed time step.
     * Override this to remove or create bodies after a world step.
     */
    public void step() {
        world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS);
        physicsTime += TIME_STEP;
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
     * Set the positions and angles of all PhysicalSprite userData objects.
     * Updates the Array of bodies if some bodies may have been destroyed or created in the world step.
     */
    public void setPhysicsData() {
        Object userData;
        updateBodies();
        for (Body body : bodies) {
            userData = body.getUserData();
            if (userData instanceof PhysicalSprite) {
                ((PhysicalSprite) userData).readPositionAngleOfBody();
            }
        }
    }

    /**
     * Let the PhysicalSprite userData objects interpolate positions and angles to give the data at graphics time.
     * progress=1 gets the new physics data, progress=0 gets the previous physics data.
     *
     * @param progress float, interpolation parameter
     */
    public void updateGraphicsData(float progress) {
        Object userData;
        updateBodies();
        for (Body body : bodies) {
            userData = body.getUserData();
            if (userData instanceof PhysicalSprite) {
                ((PhysicalSprite) userData).interpolateSpritePositionAngle(progress);
            }
        }
    }

    /**
     * Advance physics with fixed time steps.
     * The graphics time is the real world time at the call of this method.
     * Thus advance the physics time past the graphics time.
     * Uses interpolation for the positions and angles of sprites at graphics time.
     */
    public void advance() {
        graphicsTime = Basic.getTime();
        if (physicsTime < graphicsTime) {   // we have to advance time with fixed time step
            physicsTime = Math.max(physicsTime, graphicsTime - MAX_TIMEINTERVAL);  //prevent spiral of death
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
        float progress = 1 - (physicsTime - graphicsTime) / TIME_STEP;  // 1 if physicsTime=graphicsTime, decreasing to zero
        updateGraphicsData(progress);
    }

    /**
     * Dispose the world and the debugRenderer.
     */
    public void dispose() {
        world.dispose();
        if (debugRenderer != null) debugRenderer.dispose();
    }
}
