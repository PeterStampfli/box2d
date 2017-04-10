package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Created by peter on 3/22/17.
 * setting fixture parameters
 * attaching fixture to a body with given shape or collection of shapes and user data
 */
public class FixtureBuilder {
    public FixtureDef fixtureDef;

    /**
     * create fixturebuilder with scale=1 as default
     */
    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
        reset();
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
     * build and return a fixture, attached to given body with given shape and userData
     * dispose the shape later
     * @param body
     * @param shape
     * @param userData
     * @return
     */
    public Fixture build(Body body, Shape shape, Object userData){
        fixtureDef.shape=shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        return fixture;
    }

    /**
     * build and return a fixture, attached to given body with given shape, no userData
     * dispose the shape later
     * @param body
     * @param shape
     * @return
     */
    public Fixture build(Body body, Shape shape){
        return build(body, shape, null);
    }

    /**
     * build and attach to a body all shapes in a given box2DShapeCollection object, with userData
     * @param body
     * @param box2DShapeCollection
     * @param userData
     */
    public void build(Body body, box2DShapeCollection box2DShapeCollection, Object userData){
        for (Shape shape:box2DShapeCollection.shapes) {
            build(body,shape,userData);
        }
        for (box2DShapeCollection shapeCollection:box2DShapeCollection.shapeCollections) {
            build(body,shapeCollection,userData);
        }
    }

    /**
     * build and attach to a body all shapes in a given shapesBox2D object, no userData
     * @param body
     * @param shapesBox2D
     */
    public void build(Body body,box2DShapeCollection shapesBox2D){
        build(body,shapesBox2D,null);
    }
}
