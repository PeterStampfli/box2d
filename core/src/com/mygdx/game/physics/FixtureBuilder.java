package com.mygdx.game.physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Shapes2D;
import com.mygdx.game.utilities.Basic;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 3/22/17.
 * creating sclaed fixture shapes without modifying vector2 or array parameter
 */
public class FixtureBuilder implements Disposable {
    public FixtureDef fixtureDef;
    public Body body;
    public float scale;
    private Shape internalShape;

    /**
     * create fixturebuilder with scale=1 as default
     */
    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
        scale=1;
        reset();
    }

    /**
     * create fixturebuilder with nontrivial scale for shapes
     * typically: scale=1f/pixels_per_meter
     * @param scale
     */
    public FixtureBuilder(float scale){
        this();
        this.scale=scale;
    }

    /**
     * sets the scale of the fixtureBuilder (eg. created by physics)
     * @param scale
     * @return
     */
    public FixtureBuilder setScale(float scale){
        this.scale=scale;
        return this;
    }

    /**
     * set the body for the next fixtures
     * @param body
     * @return
     */
    public FixtureBuilder setBody(Body body){
        this.body=body;
        return this;
    }

    /**
     * reset the fixtureDef with defaults
     * except the scale, shape and body
     * @return
     */
    public FixtureBuilder reset() {
        fixtureDef.density = 1;
        fixtureDef.filter.groupIndex = 0;
        fixtureDef.filter.maskBits = 1;
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.6f;
        return this;
    }

    /**
     * set the mass density (default=1)
     * @param density
     * @return
     */
    public FixtureBuilder density(float density) {
        fixtureDef.density = density;
        return this;
    }

    /**
     * set groupIndex (default=0)
     * @param index
     * @return
     */
    public FixtureBuilder groupIndex(short index) {
        fixtureDef.filter.groupIndex = index;
        return this;
    }

    /**
     * set the category bits (default=1)
     * @param bits
     * @return
     */
    public FixtureBuilder categoryBits(short bits) {
        fixtureDef.filter.categoryBits = bits;
        return this;
    }

    /**
     * set the mask bits (default=1)
     * @param bits
     * @return
     */
    public FixtureBuilder maskBits(short bits) {
        fixtureDef.filter.maskBits = bits;
        return this;
    }

    /**
     * set the friction for surface contact with other bodies (default=0.3 ?)
     * @param friction
     * @return
     */
    public FixtureBuilder friction(float friction) {
        fixtureDef.friction = friction;
        return this;
    }

    /**
     * set if fixture is sensor (default=false)
     * @param isSensor
     * @return
     */
    public FixtureBuilder isSensor(boolean isSensor) {
        fixtureDef.isSensor = isSensor;
        return this;
    }

    /**
     * set restition, bouncyness at collision (default=0.6 ?)
     * @param restitition
     * @return
     */
    public FixtureBuilder restitution(float restitition) {
        fixtureDef.restitution = restitition;
        return this;
    }

    /**
     * provide an external shape for the fixture
     * dispose this shape explicitly outside
     * No scaling
     * Disposes any shape created by the fixtureBuilder
     * @param shape
     * @return
     */
    public FixtureBuilder shape(Shape shape) {
        dispose();
        fixtureDef.shape = shape;
        return this;
    }

    /**
     * create a circle shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * with scaling
     * @param x
     * @param y
     * @param radius
     * @return
     */
    public FixtureBuilder circleShape(float x,float y, float radius) {
        dispose();
        CircleShape circle = new CircleShape();
        circle.setRadius(radius*scale);
        circle.setPosition(new Vector2(x*scale,y*scale));
        fixtureDef.shape = circle;
        internalShape=circle;
        return this;
    }

    /**
     * create a circle shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param position
     * @param radius
     * @return
     */
    public FixtureBuilder circleShape(Vector2 position, float radius) {
        circleShape(position.x,position.y, radius);
        return this;
    }

    /**
     * create a polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param vertices
     * @return
     */
    public FixtureBuilder polygonShape(float[] vertices) {
        dispose();
        int length=vertices.length;
        float[] scaledVertices=new float[length];
        for (int i=0;i<length;i++){
            scaledVertices[i]=scale*vertices[i];
        }
        PolygonShape polygon = new PolygonShape();
        polygon.set(scaledVertices);
        fixtureDef.shape=polygon;
        internalShape=polygon;
        return this;
    }

    /**
     * create a polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param vertices
     * @return
     */
    public FixtureBuilder polygonShape(Array<Vector2> vertices) {
        polygonShape(Basic.toFloats(vertices));
        return this;
    }

    /**
     * create a rectangular polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param width  full width
     * @param height  full height
     * @param centerX
     * @param centerY
     * @param angle
     * @return
     */
    public FixtureBuilder boxShape(float width, float height, float centerX,float centerY, float angle) {
        dispose();
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(0.5f * width*scale, 0.5f * height*scale, new Vector2(centerX*scale,centerY*scale), angle);
        fixtureDef.shape=polygon;
        internalShape=polygon;
        return this;
    }

    /**
     * create a rectangular polygon shape for the fixtureDef,axis aligned
     * this shape will be disposed when other shapes are used
     * @param width  full width
     * @param height  full height
     * @param center
     * @return
     */
    public FixtureBuilder boxShape(float width, float height, Vector2 center) {
        boxShape(width, height, center, 0f);
        return this;
    }

    /**
     * create a rectangular polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param width  full width
     * @param height  full height
     * @param center
     * @param angle
     * @return
     */
    public FixtureBuilder boxShape(float width, float height, Vector2 center, float angle) {
        boxShape(width, height, center.x, center.y, angle);
        return this;
    }

    /**
     * create a rectangular polygon shape for the fixtureDef, axis aligned
     * this shape will be disposed when other shapes are used
     * @param width  full width
     * @param height  full height
     * @param centerX
     * @param centerY
     * @return
     */
    public FixtureBuilder boxShape(float width, float height, float centerX, float centerY) {
        boxShape(width, height, centerX, centerY, 0f);
        return this;
    }

    /**
     * create the fixture, attach it to the body and set the user data of the fixture
     * @param body
     * @param userData  (can be null)
     * @return the fixture
     */
    public Fixture attach(Body body,Object userData) {
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        return fixture;
    }

    /**
     * create the fixture, attach it to the body, no user data
     * @param body
     * @return the fixture
     */
    public Fixture attach(Body body) {
        return attach(body,null);
    }

    /**
     * create the fixture, attach it to the previously set body, no user data
     * @return the fixture
     */
    public Fixture attach() {
        return attach(body,null);
    }

    /**
     * a circleShape based on a shape2D circle
     * @param circle
     * @return
     */
    public FixtureBuilder circleShape(Circle circle){
        circleShape(circle.x,circle.y,circle.radius);
        return this;
    }

    /**
     * a polygonShape based on a shape2D polygon
     * @param polygon
     * @return
     */
    public FixtureBuilder polygonShape(Polygon polygon){
        polygonShape(polygon.getVertices());
        return this;
    }

    /**
     * an axis aligned boxShape based on a shape2 rectangle
     * @param rectangle
     * @return
     */
    public FixtureBuilder boxShape(Rectangle rectangle){
        boxShape(rectangle.width,rectangle.height,rectangle.x+0.5f*rectangle.width,rectangle.y+0.5f*rectangle.height);
        return this;
    }

    /**
     * a boxShape as a line between points (x1,y1) and (x2,y2) of given thickness
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param thickness
     * @return
     */
    public FixtureBuilder boxShapeLine(float x1,float y1,float x2,float y2,float thickness){
        boxShape(Vector2.dst(x1,y1,x2,y2),thickness,0.5f*(x1+x2),0.5f*(y1+y2));
        return this;
    }

    /**
     * make a chain/line with many line fixtures, terminated and connected with circlefixtures
     * note that this is more for diagnostics, thickness parameter has to be first
     * @param thickness
     * @param coordinates
     */
    public void makeChain(float thickness, float... coordinates){
        float radius=0.5f*thickness;
        int lenght=coordinates.length-2;
        for (int i=0;i<lenght;i+=2){
            boxShapeLine(coordinates[i],coordinates[i+1],coordinates[i+2],coordinates[i+3],thickness);
            attach();
            circleShape(coordinates[i],coordinates[i+1],radius);
            attach();
        }
        circleShape(coordinates[lenght],coordinates[lenght+1],radius);
        attach();
    }

    /**
     * make a chain/line with many line fixtures, terminated and connected with circlefixtures
     * note that this is more for diagnostics, thickness parameter has to be first
     * @param chain
     */
    public void makeChain(Chain chain) {
        makeChain(chain.thickness, chain.coordinates.toArray());
    }

    /**
     * make a fixture collection from a Shape2D shape
     * including Shapes2D collections
     * @param shape
     */
    public void makeShape(Shape2D shape){
        if (shape instanceof Polygon){
            polygonShape((Polygon)shape);
            attach();
        }
        else if (shape instanceof Circle){
            circleShape((Circle) shape);
            attach();
        }
        else if (shape instanceof Rectangle){
            boxShape((Rectangle) shape);
            attach();
        }
        else if (shape instanceof Chain){
            Chain chain=(Chain)shape;
            if (chain.thickness>0.1f){
                makeChain(chain);

            }
        }
        else if (shape instanceof Shapes2D){
            Shapes2D shapes=(Shapes2D) shape;
            for (Shape2D subShape:shapes.shapes2D){
                makeShape(subShape);
            }
        }
        else {
            Gdx.app.log(" ******************** fixtureBuilder","unknown shape "+shape.getClass());
        }
    }

    @Override
    public void dispose(){
        if (internalShape!=null){
            L.og("dispose shape");
            internalShape.dispose();
            internalShape=null;
        }
        else {
            L.og("nix to dispose");
        }
    }
}
