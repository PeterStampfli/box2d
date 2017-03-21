package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.utils.Array;
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
    FixtureBuilder fixtureBuilder;
    MouseJointBuilder mouseJointBuilder;
    DistanceJointBuilder distanceJointBuilder;


    static float METERS_PER_PIXEL;
    Viewport viewport;
    float minWidth;
    float minHeight;

    World world;
    Box2DDebugRenderer debugRenderer;

    final float TIME_STEP=1/60f;
    private float accumulator=0f;
    final int VELOCITY_ITERATIONS=8;
    final int POSITION_ITERATIONS=3;

    public class BodyBuilder{

        public BodyDef bodyDef;
        private Object userData;

        public BodyBuilder(){
            bodyDef=new BodyDef();
            reset();
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
            userData=null;
            return this;
        }

        public BodyBuilder sameAs(Body body){
            bodyDef.type=body.getType();
            bodyDef.angle=body.getAngle();
            bodyDef.bullet=body.isBullet();
            bodyDef.fixedRotation=body.isFixedRotation();
            bodyDef.gravityScale=body.getGravityScale();
            bodyDef.allowSleep=body.isSleepingAllowed();
            bodyDef.active=body.isActive();
            bodyDef.awake=body.isAwake();
            bodyDef.angularVelocity=body.getAngularVelocity();
            bodyDef.linearVelocity.set(body.getLinearVelocity());
            bodyDef.angularDamping=body.getAngularDamping();
            bodyDef.linearDamping=body.getLinearDamping();
            userData=body.getUserData();
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

        public BodyBuilder allowSleep(boolean b){
            bodyDef.allowSleep=b;
            return this;
        }


        public BodyBuilder bullet(boolean b){
            bodyDef.bullet=b;
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

        public BodyBuilder userData(Object data){
            userData=data;
            return this;
        }

        public Body build(){
            Body body= world.createBody(bodyDef);
            body.setUserData(userData);
            return body;
        }

    }

    public class FixtureBuilder{
        public FixtureDef fixtureDef;
        private  boolean needToDisposeShape;
        private Object userData;

        public FixtureBuilder(){
            fixtureDef=new FixtureDef();
            reset();
        }

        private void disposeShape(boolean disposeNext){
            if (needToDisposeShape){
                fixtureDef.shape.dispose();
                needToDisposeShape=disposeNext;
            }
        }

        public FixtureBuilder reset(){
            disposeShape(false);
            fixtureDef.density=1;
            fixtureDef.filter.groupIndex=0;
            fixtureDef.filter.maskBits=1;
            fixtureDef.filter.categoryBits=1;
            fixtureDef.friction=0.3f;
            fixtureDef.isSensor=false;
            fixtureDef.restitution=0.6f;
            userData=null;
            return this;
        }

        public FixtureBuilder density(float d){
            fixtureDef.density=d;
            return this;
        }

        public FixtureBuilder filterGroupIndex(short d){
            fixtureDef.filter.groupIndex=d;
            return this;
        }

        public FixtureBuilder filterCategoryBits(short d){
            fixtureDef.filter.categoryBits=d;
            return this;
        }

        public FixtureBuilder filterMaskBits(short d){
            fixtureDef.filter.maskBits=d;
            return this;
        }

        public FixtureBuilder friction(float d){
            fixtureDef.friction=d;
            return this;
        }

        public FixtureBuilder isSensor(boolean b){
            fixtureDef.isSensor=b;
            return this;
        }

        public FixtureBuilder restitution(float d){
            fixtureDef.restitution=d;
            return this;
        }

        public FixtureBuilder shape(Shape shape){
            disposeShape(false);
            fixtureDef.shape=shape;
            return this;
        }

        public FixtureBuilder circleShape(Vector2 position, float radius){
            disposeShape(true);
            CircleShape circle=new CircleShape();
            circle.setRadius(radius);
            if (position!=null) circle.setPosition(position);
            shape(circle);
            return this;
        }

        public FixtureBuilder circleShape(float radius){
            circleShape(null,radius);
            return this;
        }

        public FixtureBuilder circleShape(float x,float y,float radius){
            circleShape(new Vector2(x,y),radius);
            return this;
        }

        public FixtureBuilder polygonShape(float[] vertices){
            disposeShape(true);
            PolygonShape polygon=new PolygonShape();
            polygon.set(vertices);
            shape(polygon);
            return this;
        }

        public FixtureBuilder polygonShape(Array<Vector2> vertices){
            polygonShape(Vector2Array.toFloats(vertices));
            return this;
        }

        public FixtureBuilder boxShape(float width,float height){
            disposeShape(true);
            PolygonShape polygon=new PolygonShape();
            polygon.setAsBox(0.5f*width,0.5f*height);
            shape(polygon);
            return this;
        }

        public FixtureBuilder boxShape(float width,float height,Vector2 center,float angle){
            disposeShape(true);
            PolygonShape polygon=new PolygonShape();
            polygon.setAsBox(0.5f*width,0.5f*height,center,angle);
            shape(polygon);
            return this;
        }

        public FixtureBuilder boxShape(float width,float height,Vector2 center){
            boxShape(width, height, center,0f);
            return this;
        }

        public FixtureBuilder boxShape(float width,float height,float centerX,float centerY,float angle){
            boxShape(width, height, new Vector2(centerX,centerY),angle);
            return this;
        }

        public FixtureBuilder boxShape(float width,float height,float centerX,float centerY){
            boxShape(width, height, new Vector2(centerX,centerY),0f);
            return this;
        }

        public FixtureBuilder boxShape(float width,float height,float angle){
            boxShape(width, height, new Vector2(),angle);
            return this;
        }

        public FixtureBuilder userData(Object data){
            userData=data;
            return this;
        }

        public Fixture attachTo(Body body){
            Fixture fixture=body.createFixture(fixtureDef);
            fixture.setUserData(userData);
            return  fixture;
        }
    }

    public class MouseJointBuilder{
        private MouseJointDef mouseJointDef;
        private Object userData;

        public MouseJointBuilder(){
            mouseJointDef=new MouseJointDef();
        }

        public MouseJointBuilder reset(){
            mouseJointDef.collideConnected=true;
            mouseJointDef.dampingRatio=0.7f;
            mouseJointDef.frequencyHz=5;
            mouseJointDef.maxForce=10;
            userData=null;
            return this;
        }

        public MouseJointBuilder dummyBody(Body body){
            mouseJointDef.bodyA=body;
            return this;
        }

        public MouseJointBuilder body(Body body){
            mouseJointDef.bodyB=body;
            return this;
        }

        public MouseJointBuilder collideConnected(Boolean b){
            mouseJointDef.collideConnected=b;
            return this;
        }

        public MouseJointBuilder dampingRatio(float d){
            mouseJointDef.dampingRatio=d;
            return this;
        }

        public MouseJointBuilder frequencyHz(float d){
            mouseJointDef.frequencyHz=d;
            return this;
        }

        public MouseJointBuilder maxForce(float d){
            mouseJointDef.maxForce=d;
            return this;
        }

        public MouseJointBuilder target(Vector2 p){
            mouseJointDef.target.set(p);
            return this;
        }

        public MouseJointBuilder target(float x,float y){
            mouseJointDef.target.set(x, y);
            return this;
        }

        public MouseJointBuilder userData(Object data){
            userData=data;
            return this;
        }

        public MouseJoint build(){
            MouseJoint mouseJoint= (MouseJoint) world.createJoint(mouseJointDef);
            mouseJoint.setUserData(userData);
            return mouseJoint;
        }
    }

    public class DistanceJointBuilder {
        private DistanceJointDef distanceJointDef;
        private Object userData;

        public DistanceJointBuilder() {
            distanceJointDef = new DistanceJointDef();
            reset();
        }

        public DistanceJointBuilder reset(){
            distanceJointDef.dampingRatio=0;
            distanceJointDef.frequencyHz=0;
            distanceJointDef.length=1;
            distanceJointDef.collideConnected=true;
            distanceJointDef.localAnchorA.setZero();
            distanceJointDef.localAnchorB.setZero();
            return this;
        }

        public DistanceJointBuilder userData(Object data){
            userData=data;
            return this;
        }

        public DistanceJointBuilder dampingRatio(float d){
            distanceJointDef.dampingRatio=d;
            return this;
        }

        public DistanceJointBuilder frequencyHz(float d){
            distanceJointDef.frequencyHz=d;
            return this;
        }

        public DistanceJointBuilder collideConnected(boolean c){
            distanceJointDef.collideConnected=c;
            return this;
        }

        public DistanceJointBuilder length(float d){
            distanceJointDef.length=d;
            return this;
        }

        public DistanceJointBuilder bodyA(Body body){
            distanceJointDef.bodyA=body;
            return this;
        }

        public DistanceJointBuilder bodyB(Body body){
            distanceJointDef.bodyB=body;
            return this;
        }

        public DistanceJointBuilder localAnchorA(Vector2 p){
            distanceJointDef.localAnchorA.set(p);
            return this;
        }

        public DistanceJointBuilder localAnchorA(float x,float y){
            distanceJointDef.localAnchorA.set(x, y);
            return this;
        }

        public DistanceJointBuilder localAnchorB(Vector2 p){
            distanceJointDef.localAnchorB.set(p);
            return this;
        }

        public DistanceJointBuilder localAnchorB(float x,float y){
            distanceJointDef.localAnchorB.set(x, y);
            return this;
        }

        public DistanceJointBuilder length(){
            Vector2 anchorDistance=new Vector2(distanceJointDef.bodyA.getPosition())
                                                .add(distanceJointDef.localAnchorA)
                                                .sub(distanceJointDef.bodyB.getPosition())
                                                .sub(distanceJointDef.localAnchorB);
            length(anchorDistance.len());
            return this;
        }


        public DistanceJoint build(){
            DistanceJoint distanceJoint= (DistanceJoint) world.createJoint(distanceJointDef);
            distanceJoint.setUserData(userData);
            return distanceJoint;
        }
    }

    public Physics(float metersPerPixel,boolean debug){
        bodyBuilder=new BodyBuilder();
        fixtureBuilder=new FixtureBuilder();
        mouseJointBuilder=new MouseJointBuilder();
        distanceJointBuilder=new DistanceJointBuilder();
        METERS_PER_PIXEL=metersPerPixel;
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
        float frameTime=Math.min(dTime,0.25f);
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

    public void dispose(){
        world.dispose();
        if (debugRenderer !=null) debugRenderer.dispose();
        fixtureBuilder.disposeShape(false);
    }
}
