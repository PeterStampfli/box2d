package com.mygdx.game.physics;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Images.Shape2DCollection;

/**
 * Created by peter on 3/22/17.
 * setting fixture parameters
 * attaching fixture to a body with given masterShape or collection of shapes and user data
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
     * except the scale, masterShape and body
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
     * build and return a fixture, attached to given body with given masterShape and userData
     * dispose the masterShape later
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
     * build and return a fixture, attached to given body with given masterShape, no userData
     * you have to dispose the masterShape later
     * @param body
     * @param shape
     * @return
     */
    public Fixture build(Body body, Shape shape){
        return build(body, shape, null);
    }

    /**
     * build and attach shapes from shape2D objects, including shape2DCollections,
     * to given body and userData object
     * the generated masterShape is disposed automatically
     * @param body
     * @param shape2D
     * @param userData
     * @return
     */
    public Fixture build(Body body, Shape2D shape2D,Object userData){
        Fixture fixture=null;
        if (shape2D instanceof Shape2DCollection){
            Shape2DCollection shape2DCollection=(Shape2DCollection) shape2D;
            for (Shape2D subShape2D:shape2DCollection.shapes2D){
                fixture=build(body,subShape2D,userData);
            }
        }
        else {
            Shape shape = Box2DShape.ofShape2D(shape2D);
            fixture = build(body, shape, userData);
            shape.dispose();
        }
        return fixture;
    }

    /**
     * build and attach shapes from shape2D objects, including shape2DCollections,
     * to given body
     * the generated masterShape is disposed automatically
     * no user data
     * @param body
     * @param shape2D
     * @return
     */
    public Fixture build(Body body, Shape2D shape2D){
        return build(body, shape2D,null);
    }

}
