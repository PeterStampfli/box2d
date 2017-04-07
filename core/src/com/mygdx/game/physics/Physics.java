package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
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

// we want to use the same units for physics and graphics
// we use the same viewport for both
// a special scale is needed for drawing sprites, textures and textureregions:
//  relates the size in pixels to the size in meters: pixels per meter

public class Physics implements Disposable{

    BodyBuilder bodyBuilder;
    FixtureBuilder fixtureBuilder;
    MouseJointBuilder mouseJointBuilder;
    DistanceJointBuilder distanceJointBuilder;

    Viewport viewport;

    World world;
    Box2DDebugRenderer debugRenderer;

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
     * @param viewport
     * @param debug
     */
    public Physics(Viewport viewport,boolean debug){
        bodies=new Array<Body>();
        this.viewport=viewport;
        if (debug) {
            debugRenderer =new Box2DDebugRenderer();
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
            bodyBuilder=new BodyBuilder(this);
            fixtureBuilder=new FixtureBuilder();
            mouseJointBuilder=new MouseJointBuilder(this);
            distanceJointBuilder=new DistanceJointBuilder(this);
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
     * set the body type to dynamic body and return the bodybuilder
     * @return
     */
    public BodyBuilder dynamicBody(){
        return bodyBuilder.type(BodyDef.BodyType.DynamicBody);
    }

    /**
     *set the body type to static body and return the bodybuilder
     * @return
     */
    public BodyBuilder staticBody(){
        return bodyBuilder.type(BodyDef.BodyType.StaticBody);
    }

    /**
     * get the bodyBuilder
     * @return
     */
    public BodyBuilder body(){
        return bodyBuilder;
    }

    /**
     * get the fixture builder
     * @return
     */
    public FixtureBuilder fixture(){
        return fixtureBuilder;
    }

    /**
     * get the mouse joint builder
     * @return
     */
    public MouseJointBuilder mouseJoint(){return mouseJointBuilder;}

    /**
     * get the distance joint builder
     * @return
     */
    public DistanceJointBuilder distanceJoint(){return distanceJointBuilder;}

    /**
     * if debug==true at physics creation then do a debug rendering of result of last physics step
     * note that for interpolation the graphics and physics positions are different
     */
    public void debugRender(){
        if (debugRenderer !=null) {
            debugRenderer.render(world,viewport.getCamera().combined);
        }
    }

    /**
     * get the reaction torque of a joint, using the fixed physics time step
     * @param joint
     * @return
     */
    public float getReactionTorque(Joint joint){
        return joint.getReactionTorque(1.0f/TIME_STEP);
    }

    /**
     * get the reaction force of a joint, using the fixed physics time step
     * @param joint
     * @return
     */
    public Vector2 getReactionForce(Joint joint){
        return joint.getReactionForce(1.0f/TIME_STEP);
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
            if (userData instanceof com.mygdx.game.Pieces.Box2DSprite){
                ((com.mygdx.game.Pieces.Box2DSprite)userData).saveBodyPositionAngle(body);
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
            if (userData instanceof com.mygdx.game.Pieces.Box2DSprite){
                ((com.mygdx.game.Pieces.Box2DSprite)userData).updateSpritePositionAngle(progress);
            }
        }
    }

    /**
     * the graphics time is the time at call of this method.
     * advance the physics time past the graphics time.
     * Update physics data (eg. world center position and angle) of userData objects.
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
     * maybe the debugRenderer and a shape left in the fixture builder
     */
    public void dispose(){
        world.dispose();
        if (debugRenderer !=null) debugRenderer.dispose();
        fixtureBuilder.dispose();
    }
}
