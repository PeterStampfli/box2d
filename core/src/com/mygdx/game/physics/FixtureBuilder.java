package com.mygdx.game.physics;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.mygdx.game.Images.Shape2DCollection;

/**
 * Attach fixtures to a body. Set data and shape of the fixture.
 */

public class FixtureBuilder {
    public FixtureDef fixtureDef;

    /**
     * Create a fixtureBuilder with default fixtureDef data.
     */
    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
        reset();
    }

    /**
     * Reset the fixtureDef with default data.
     *
     * @return this, for chaining
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
     * Set the mass density (default=1kg/m²).
     *
     * @param density float, density in kg/m² (?)
     * @return this, for chaining
     */
    public FixtureBuilder setDensity(float density) {
        fixtureDef.density = density;
        return this;
    }

    /**
     * Set group index (default=0) for collisions.
     *
     * @param index int, group index
     * @return this, for chaining
     */
    public FixtureBuilder setGroupIndex(short index) {
        fixtureDef.filter.groupIndex = index;
        return this;
    }

    /**
     * Set the category bits (default=1) for collisions.
     *
     * @param bits short, 16 bits for filtering collisions.
     * @return this, for chaining
     */
    public FixtureBuilder setCategoryBits(short bits) {
        fixtureDef.filter.categoryBits = bits;
        return this;
    }

    /**
     * Set the mask bits (default=1) for collisions.
     *
     * @param bits short, 16 bits for filtering collisions.
     * @return this, for chaining
     */
    public FixtureBuilder setMaskBits(short bits) {
        fixtureDef.filter.maskBits = bits;
        return this;
    }

    /**
     * Set the friction for surface contact with other bodies.
     *
     * @param friction float, between 0 and 1
     * @return this, for chaining
     */
    public FixtureBuilder setFriction(float friction) {
        fixtureDef.friction = friction;
        return this;
    }

    /**
     * Set if fixture is a sensor (default=false).
     *
     * @param isSensor boolean, true if fixture is a sensor
     * @return this, for chaining
     */
    public FixtureBuilder setIsSensor(boolean isSensor) {
        fixtureDef.isSensor = isSensor;
        return this;
    }

    /**
     * Set restitution, bouncy-nes at collision (default=0.6).
     * 0 for inelastic collision. 1 for elastic collision.
     *
     * @param restitution float, fraction of elastic collision
     * @return this, for chaining
     */
    public FixtureBuilder setRestitution(float restitution) {
        fixtureDef.restitution = restitution;
        return this;
    }

    /**
     * Build and return one fixture, attached to given body with a Shape2D Shape and userData.
     *
     * @param body Body, to attach the fixture
     * @param shape2D Shape2D, of the fixture
     * @param userData Object
     * @return Fixture, the fixture that has been attached
     */
    public Fixture buildOne(Body body, Shape2D shape2D, Object userData){
        Shape shape = Box2DShape.ofShape2D(shape2D);
        fixtureDef.shape=shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        shape.dispose();
        return fixture;
    }

    /**
     * Build and return one fixture, attached to given body with a box2D Shape. No userData.
     * Dispose the Shape later.
     *
     * @param body Body, gets the fixture
     * @param shape2D Shape2D, of the fixture
     * @return Fixture, the fixture that has been attached
     */
    public Fixture buildOne(Body body, Shape2D shape2D){
        return buildOne(body, shape2D, null);
    }

    /**
     * Build and attach one or more fixtures with the shapes from shape2D objects, including shape2DCollections,
     * to a body. Set the userData object of the fixtures. The generated box2D Shapes are disposed automatically.
     *
     * @param body Body, gets the fixture
     * @param shape2D Shape2D, shape for the fixture, can be a Shape2DCollection
     * @param userData Object
     */
    public void build(Body body, Shape2D shape2D,Object userData){
        if (shape2D instanceof Shape2DCollection){
            Shape2DCollection shape2DCollection=(Shape2DCollection) shape2D;
            for (Shape2D subShape2D:shape2DCollection.items){
                build(body,subShape2D,userData);
            }
        }
        else {
            buildOne(body, shape2D, userData);
        }
    }

    /**
     * Build and attach one or more fixtures with the shapes from shape2D objects, including shape2DCollections,
     * to a body. Set the userData object of the fixtures. The generated box2D Shapes are disposed automatically.
     *
     * @param body Body, gets the fixture
     * @param shape2D Shape2D, shape for the fixture, can be a Shape2DCollection
     */
    public void build(Body body, Shape2D shape2D){
        build(body, shape2D,null);
    }

    /**
     * Destroy all fixtures of a body.
     *
     * @param body Body, remove fixtures
     */
    public void destroyFixtures(Body body){
        for(int i=body.getFixtureList().size;i>0;i--){
            body.destroyFixture(body.getFixtureList().first());
        }
    }
}
