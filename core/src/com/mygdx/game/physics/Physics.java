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
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.utilities.Basic;

/**
 * Created by peter on 3/18/17.
 */

// the basic viewport and camera are for the graphics
// the physics uses its own scaled units
//  relates the size in pixels to the size in meters: pixels per meter

//  the scaling is done here

public class Physics implements Disposable{

    static public float PIXELS_PER_METER=100;       // default


    World world;
    Box2DDebugRenderer debugRenderer;
    OrthographicCamera debugCamera;

    float physicsTime;
    float graphicsTime;
    Array<Body> bodies;
    boolean bodiesNeedUpdate=true;

    final float TIME_STEP=1/60f;
    final float MAX_TIMEINTERVAL=0.25f;
    private float accumulator=0f;
    final int VELOCITY_ITERATIONS=8;
    final int POSITION_ITERATIONS=3;

    /**
     * debug renderer only if debug==true
     * initialize box2D
     * @param debug
     */
    public Physics(boolean debug){
        bodies=new Array<Body>();
        if (debug) {
            debugRenderer=new Box2DDebugRenderer();
            debugCamera=new OrthographicCamera();
        }
        Box2D.init();
    }

    /**
     * create and return box2D world with given components of gravity and flag for bodies may sleep
     * create all builders
     * @param gravityX
     * @param gravityY
     * @param maySleep
     * @return
     */
    public World createWorld(float gravityX,float gravityY,boolean maySleep){
        if (world!=null){
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        }
        else {
            world = new World(new Vector2(gravityX, gravityY), maySleep);
        }
        return world;
    }

    /**
     * update the bodies array if needed
     */
    public void updateBodies(){
        if (bodiesNeedUpdate){
            world.getBodies(bodies);
            bodiesNeedUpdate=false;
        }
    }

    /**
     * if debug==true at physics creation then do a debug rendering of result of last physics step
     * note that for interpolation the graphics and physics positions are different for fast movement
     * @param graphicsViewport the viewport of the graphics world, its camera data is scaled to set the camera for the debugRenderer
     */
    public void debugRender(Viewport graphicsViewport){
        if (debugRenderer !=null) {
            Camera graphicsCamera=graphicsViewport.getCamera();
            debugCamera.position.set(graphicsCamera.position).scl(1f/PIXELS_PER_METER);
            debugCamera.viewportWidth=graphicsCamera.viewportWidth/PIXELS_PER_METER;
            debugCamera.viewportHeight=graphicsCamera.viewportHeight/PIXELS_PER_METER;
            debugCamera.update();
            debugRenderer.render(world,debugCamera.combined);
        }
    }

    /**
     * get the reaction torque of a joint, using the fixed physics time step
     * scaled to graphics units: kg pixels² /sec² instead of Nm
     * @param joint
     * @return
     */
    public float getReactionTorque(Joint joint){
        return joint.getReactionTorque(1.0f/TIME_STEP)*PIXELS_PER_METER*PIXELS_PER_METER;
    }

    /**
     * get the reaction force of a joint, using the fixed physics time step
     * scaled to graphics units, in kg pixels/sec² from N
     * @param joint
     * @return  reaction force as Vector2, will be overwritten at next call
     */
    public Vector2 getReactionForce(Joint joint){
        return joint.getReactionForce(1.0f/TIME_STEP).scl(PIXELS_PER_METER);
    }

    /**
     * start the physics. (sets the physics time equal to currrent time.)
     */
    public void start(){
        physicsTime= Basic.getTime();
    }

    /**
     * Make one time step. Increase physics time.
     * Override/Extend to remove/create bodies/sprites after the world step
     */
    public void step(){
        world.step(TIME_STEP,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        physicsTime+=TIME_STEP;
    }

    /**
     *  set physics data of all bodies userData objects implementing the Movable interface
     *  Updates the Array of bodies because some bodies may have been destroyed or created in the world step
     */
    public void setPhysicsData(){
        Object userData;
        updateBodies();
        for (Body body:bodies){
            userData=body.getUserData();
            if (userData instanceof BodyToSprite){
                ((BodyToSprite)userData).saveBodyPositionAngle();
            }
        }
    }

    /**
     * Interpolate the physics data of bodies userData objects that implement the Movable interface.
     * progress=1 gets new physics data, progress=0 gets previous physics data
     * @param progress
     */
    public void updateGraphicsData(float progress){
        Object userData;
        updateBodies();
        for (Body body:bodies){
            userData=body.getUserData();
            if (userData instanceof BodyToSprite){
                ((BodyToSprite)userData).updateSpritePositionAngle(progress);
            }
        }
    }

    /**
     * the graphics time is the time at call of this method.
     * advance the physics time past the graphics time.
     * Update physics data (eg. world center setPosition and setAngle) of userData objects.
     * Let userData objects ionterpolate physics data to get data at graphics time.
     */
    public void advance(){
        graphicsTime=Basic.getTime();
        if (physicsTime<graphicsTime){   // we have to advance time with fixed timestep
            physicsTime=Math.max(physicsTime,graphicsTime-MAX_TIMEINTERVAL);  //prevent spiral of death
            if (physicsTime<graphicsTime-TIME_STEP){   // we need more than one time step
                while (physicsTime<graphicsTime-TIME_STEP){  // advance until we are close
                    step();
                }
                setPhysicsData();
            }
            // physics time is less than TIME_STEP behind graphics time,
            // new physics result of sprites is set to this time
            step();
            setPhysicsData();
        }
        float progress=1-(physicsTime-graphicsTime)/TIME_STEP;  // 1 if physicsTime=graphicsTime, decreasing to zero
        updateGraphicsData(progress);
    }

    /**
     * do not forget to dispose the world and
     * maybe the debugRenderer and a masterShape left in the fixture builder
     */
    public void dispose(){
        world.dispose();
        if (debugRenderer !=null) debugRenderer.dispose();
    }
}
