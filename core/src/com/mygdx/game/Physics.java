package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by peter on 3/18/17.
 */

// we want to use the same units for physics and graphics
// we use a viewport for both
// METERS_PER_PIXEL is the sprite scale

public class Physics implements Disposable{

    static float METERS_PER_PIXEL;
    Viewport viewport;
    float minWidth;
    float minHeight;

    World world;
    Box2DDebugRenderer debugRenderer;

    BodyDef bodyDef;
    FixtureDef fixtureDef;

    final float TIME_STEP=1/60f;
    private float accumulator=0f;

    public Physics(float metersPerPixel,boolean debug){
        METERS_PER_PIXEL=metersPerPixel;
        if (debug) debugRenderer =new Box2DDebugRenderer();
    }

    public World createWorld(float gx,float gy,boolean maySleep){
        if (world!=null){
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        }
        Box2D.init();
        world=new World(new Vector2(gx,gy),maySleep);
        bodyDef=new BodyDef();
        fixtureDef=new FixtureDef();
        fixtureDef.density=1;                         // important: finite density  !!!
        fixtureDef.friction=0.3f;
        fixtureDef.restitution=0.5f;
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

    public Body createBody(float x,float y,float angle,BodyDef.BodyType bodyType){
        bodyDef.position.set(x, y);
        bodyDef.type=bodyType;
        bodyDef.angle=angle;
        return world.createBody(bodyDef);
    }

    public FixtureDef getFixtureDef(){
        return fixtureDef;
    }

    public Body createDynamicBody(float x,float y){
        return createBody(x,y,0, BodyDef.BodyType.DynamicBody);
    }

    public Body createDynamicBody(float x,float y,float angle){
        return createBody(x,y,angle, BodyDef.BodyType.DynamicBody);
    }

    public Body createStaticBody(float x,float y){
        return createBody(x,y,0, BodyDef.BodyType.StaticBody);
    }

    public Body createStaticBody(float x,float y,float angle){
        return createBody(x,y,angle, BodyDef.BodyType.StaticBody);
    }

    public void debugRender(){
        if (debugRenderer !=null) debugRenderer.render(world,viewport.getCamera().combined);
    }

    public void step(float dTime){
        float frameTime=Math.min(dTime,0.25f);
        accumulator+=frameTime;
        while (accumulator>=TIME_STEP){
            // worldmanager???
            world.step(TIME_STEP,6,2);
            accumulator-=TIME_STEP;
        }

    }



    public void dispose(){
        world.dispose();
        if (debugRenderer !=null) debugRenderer.dispose();
    }
}
