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

    BodyBuilder bodyBuilder;

    static float METERS_PER_PIXEL;
    Viewport viewport;
    float minWidth;
    float minHeight;

    World world;
    Box2DDebugRenderer debugRenderer;

    FixtureDef fixtureDef;

    final float TIME_STEP=1/60f;
    private float accumulator=0f;

    public class BodyBuilder{

        private BodyDef bodyDef;

        public BodyBuilder(){
            bodyDef=new BodyDef();
        }

        public BodyBuilder type(BodyDef.BodyType bodyType){
            bodyDef.type=bodyType;
            return this;
        }

        public BodyBuilder reset(){
            bodyDef.angle=0;
            bodyDef.bullet=false;
            bodyDef.fixedRotation=false;
            bodyDef.gravityScale=1;
            bodyDef.allowSleep=true;
            bodyDef.active=true;
            bodyDef.awake=true;
            bodyDef.angularVelocity=0;
            bodyDef.linearVelocity.setZero();
            bodyDef.angularDamping=0;
            bodyDef.linearDamping=0;
            return this;
        }

        public BodyBuilder position(float x,float y){
            bodyDef.position.set(x, y);
            return this;
        }

        public BodyBuilder position(Vector2 p){
            bodyDef.position.set(p);
            return this;
        }

        public BodyBuilder angle(float a){
            bodyDef.angle=a;
            return this;
        }

        public BodyBuilder angularDamping(float a){
            bodyDef.angularDamping=a;
            return this;
        }

        public BodyBuilder angularVelocity(float a){
            bodyDef.angularVelocity=a;
            return this;
        }

        public BodyBuilder linearDamping(float d){
            bodyDef.linearDamping=d;
            return this;
        }

        public BodyBuilder linearVelocity(float x,float y){
            bodyDef.linearVelocity.set(x, y);
            return this;
        }

        public BodyBuilder linearVelocity(Vector2 v){
            bodyDef.linearVelocity.set(v);
            return this;
        }

        public BodyBuilder gravityScale(float s){
            bodyDef.gravityScale=s;
            return this;
        }

        public BodyBuilder active(boolean b){
            bodyDef.active=b;
            return this;
        }

        public BodyBuilder awake(boolean b){
            bodyDef.awake=b;
            return this;
        }

        public BodyBuilder bullet(boolean b){
            bodyDef.bullet=b;
            return this;
        }

        public BodyBuilder allowSleep(boolean b){
            bodyDef.allowSleep=b;
            return this;
        }

        public Body build(){
            return world.createBody(bodyDef);
        }

    }

    public Physics(float metersPerPixel,boolean debug){
        bodyBuilder=new BodyBuilder().reset();
        METERS_PER_PIXEL=metersPerPixel;
        if (debug) debugRenderer =new Box2DDebugRenderer();
    }

    public World createWorld(float gx,float gy,boolean maySleep){
        if (world!=null){
            Gdx.app.log("***** Physics", "World already exists!!!!!!!!!!!!!");
        }
        Box2D.init();
        world=new World(new Vector2(gx,gy),maySleep);
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

    public FixtureDef getFixtureDef(){
        return fixtureDef;
    }

    public BodyBuilder dynamicBody(){
        return bodyBuilder.type(BodyDef.BodyType.DynamicBody);
    }

    public BodyBuilder staticBody(){
        return bodyBuilder.type(BodyDef.BodyType.StaticBody);
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
