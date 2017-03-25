package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
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
    float minWidth;
    float minHeight;

    World world;
    Box2DDebugRenderer debugRenderer;

    float physicsTime;
    float graphicsTime;
    Array<Body> bodies;

    final float TIME_STEP=1/60f;
    final float MAX_TIMEINTERVAL=0.25f;
    private float accumulator=0f;
    final int VELOCITY_ITERATIONS=8;
    final int POSITION_ITERATIONS=3;

    /**
     * create all builders, debug renderer only if debug==true
     * @param debug
     */
    public Physics(boolean debug){
        bodyBuilder=new BodyBuilder(this);
        fixtureBuilder=new FixtureBuilder();
        mouseJointBuilder=new MouseJointBuilder(this);
        distanceJointBuilder=new DistanceJointBuilder(this);
        bodies=new Array<Body>();
        if (debug) debugRenderer =new Box2DDebugRenderer();
    }


    public World createWorld(float gx,float gy,boolean maySleep){
        if (world!=null){
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        }
        Box2D.init();
        world=new World(new Vector2(gx,gy),maySleep);
        return world;
    }

    public Viewport createExtendViewport(float minWidth, float minHeight){
        this.minWidth=minWidth;
        this.minHeight=minHeight;
        OrthographicCamera camera=new OrthographicCamera();
        viewport=new ExtendViewport(minWidth,minHeight,camera);
        camera.setToOrtho(false,minWidth,minHeight);
        return viewport;
    }

    public Viewport createFitViewport(float minWidth, float minHeight){
        this.minWidth=minWidth;
        this.minHeight=minHeight;
        OrthographicCamera camera=new OrthographicCamera();
        viewport=new FitViewport(minWidth,minHeight,camera);
        camera.setToOrtho(false,minWidth,minHeight);
        return viewport;
    }

    public BodyBuilder dynamicBody(){
        return bodyBuilder.type(BodyDef.BodyType.DynamicBody);
    }

    public BodyBuilder staticBody(){
        return bodyBuilder.type(BodyDef.BodyType.StaticBody);
    }

    public BodyBuilder body(){
        return bodyBuilder;
    }

    public FixtureBuilder fixture(){
        return fixtureBuilder;
    }

    public MouseJointBuilder mouseJoint(){return mouseJointBuilder;}

    public DistanceJointBuilder distanceJoint(){return distanceJointBuilder;}

    public void debugRender(){
        if (debugRenderer !=null) debugRenderer.render(world,viewport.getCamera().combined);
    }

    public void step(float dTime){
        float frameTime=Math.min(dTime,MAX_TIMEINTERVAL);
        accumulator+=frameTime;
        while (accumulator>=TIME_STEP){
            // worldmanager???
            world.step(TIME_STEP,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
            accumulator-=TIME_STEP;
        }
    }

    public float getReactionTorque(Joint joint){
        return joint.getReactionTorque(1.0f/TIME_STEP);
    }

    public Vector2 getReactionForce(Joint joint){
        return joint.getReactionForce(1.0f/TIME_STEP);
    }



    public void start(){
        physicsTime= Basic.getTime();
    }

    public void step(){
        world.step(TIME_STEP,VELOCITY_ITERATIONS,POSITION_ITERATIONS);
        physicsTime+=TIME_STEP;
    }

    public void advance(){
        world.getBodies(bodies);
        graphicsTime=Basic.getTime();
        if (physicsTime<graphicsTime){   // we have to advance time with fixed timestep
            physicsTime=Math.max(physicsTime,graphicsTime-MAX_TIMEINTERVAL);  //prevent spiral of death
            if (physicsTime<graphicsTime-TIME_STEP){   // we need more than one time step
                while (physicsTime<graphicsTime-TIME_STEP){  // advance until we are close
                    step();
                }
                // set physics result
            }
            // physics time is less than TIME_STEP behind graphics time,
            // new physics result of sprites is set to this time
            step();
            //set physics result
            float progress=1-(physicsTime-graphicsTime)/TIME_STEP;  // 1 if physicsTime=graphicsTime, decreasing to zero
            // interpolate graphics

        }
    }

    public void draw(Batch batch){
        Object userData;
        world.getBodies(bodies);
        for (Body body:bodies){
            userData=body.getUserData();
            if (userData instanceof Drawable){
                ((Drawable)userData).draw(batch);
            }
        }
    }


    public void dispose(){
        world.dispose();
        if (debugRenderer !=null) debugRenderer.dispose();
        fixtureBuilder.disposeShape(false);
    }
}
