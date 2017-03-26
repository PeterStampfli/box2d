package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.utils.Array;

/**
 * Created by peter on 3/22/17.
 */
public class FixtureBuilder {
    public FixtureDef fixtureDef;
    private boolean needToDisposeShape;

    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
        reset();
    }

    /**
     * dispose the shape only if it has been created by the fixture builder
     * external shapes have to be disposed extra
     * @param disposeNext
     */
    public void disposeShape(boolean disposeNext) {
        if (needToDisposeShape) {
            fixtureDef.shape.dispose();
            needToDisposeShape = disposeNext;
        }
    }

    /**
     * reset the fixtureDef with defaults
     * @return
     */
    public FixtureBuilder reset() {
        disposeShape(false);
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
     * dispose this shape explicitely outside
     * Disposes any shape created by the fixtureBuilder
     * @param shape
     * @return
     */
    public FixtureBuilder shape(Shape shape) {
        disposeShape(false);
        fixtureDef.shape = shape;
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
        disposeShape(true);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        if (position != null) circle.setPosition(position);
        shape(circle);
        return this;
    }

    /**
     * create a circle shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param radius
     * @return
     */
    public FixtureBuilder circleShape(float radius) {
        circleShape(null, radius);
        return this;
    }

    /**
     * create a circle shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param positionX
     * @param positionY
     * @param radius
     * @return
     */
    public FixtureBuilder circleShape(float positionX, float positionY, float radius) {
        circleShape(new Vector2(positionX, positionY), radius);
        return this;
    }

    /**
     * create a polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param vertices
     * @return
     */
    public FixtureBuilder polygonShape(float[] vertices) {
        disposeShape(true);
        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);
        shape(polygon);
        return this;
    }

    /**
     * create a polygon shape for the fixtureDef
     * this shape will be disposed when other shapes are used
     * @param vertices
     * @return
     */
    public FixtureBuilder polygonShape(Array<Vector2> vertices) {
        polygonShape(com.mygdx.game.utilities.Basic.toFloats(vertices));
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
        disposeShape(true);
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(0.5f * width, 0.5f * height, center, angle);
        shape(polygon);
        return this;
    }

    public FixtureBuilder boxShape(float width, float height, Vector2 center) {
        boxShape(width, height, center, 0f);
        return this;
    }

    public FixtureBuilder boxShape(float width, float height, float centerX, float centerY, float angle) {
        boxShape(width, height, new Vector2(centerX, centerY), angle);
        return this;
    }

    public FixtureBuilder boxShape(float width, float height, float centerX, float centerY) {
        boxShape(width, height, new Vector2(centerX, centerY), 0f);
        return this;
    }

    public FixtureBuilder boxShape(float width, float height, float angle) {
        boxShape(width, height, new Vector2(), angle);
        return this;
    }

    /**
     * create a rectangular polygon shape for the fixtureDef
     * centered and axis aligned
     * this shape will be disposed when other shapes are used
     * @param width the full width
     * @param height the full height
     * @return
     */
    public FixtureBuilder boxShape(float width, float height) {
        boxShape(width, height,0,0,0);
        return this;
    }

    /**
     * create the fixture, attach it to the body and set the user data of the fixture
     * @param body
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
        Fixture fixture = body.createFixture(fixtureDef);
        return attach(body,null);
    }
}
