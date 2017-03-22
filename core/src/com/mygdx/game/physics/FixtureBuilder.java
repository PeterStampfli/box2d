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
    private Object userData;

    public FixtureBuilder() {
        fixtureDef = new FixtureDef();
        reset();
    }

    public void disposeShape(boolean disposeNext) {
        if (needToDisposeShape) {
            fixtureDef.shape.dispose();
            needToDisposeShape = disposeNext;
        }
    }

    public FixtureBuilder reset() {
        disposeShape(false);
        fixtureDef.density = 1;
        fixtureDef.filter.groupIndex = 0;
        fixtureDef.filter.maskBits = 1;
        fixtureDef.filter.categoryBits = 1;
        fixtureDef.friction = 0.3f;
        fixtureDef.isSensor = false;
        fixtureDef.restitution = 0.6f;
        userData = null;
        return this;
    }

    public FixtureBuilder density(float d) {
        fixtureDef.density = d;
        return this;
    }

    public FixtureBuilder filterGroupIndex(short d) {
        fixtureDef.filter.groupIndex = d;
        return this;
    }

    public FixtureBuilder filterCategoryBits(short d) {
        fixtureDef.filter.categoryBits = d;
        return this;
    }

    public FixtureBuilder filterMaskBits(short d) {
        fixtureDef.filter.maskBits = d;
        return this;
    }

    public FixtureBuilder friction(float d) {
        fixtureDef.friction = d;
        return this;
    }

    public FixtureBuilder isSensor(boolean b) {
        fixtureDef.isSensor = b;
        return this;
    }

    public FixtureBuilder restitution(float d) {
        fixtureDef.restitution = d;
        return this;
    }

    public FixtureBuilder shape(Shape shape) {
        disposeShape(false);
        fixtureDef.shape = shape;
        return this;
    }

    public FixtureBuilder circleShape(Vector2 position, float radius) {
        disposeShape(true);
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        if (position != null) circle.setPosition(position);
        shape(circle);
        return this;
    }

    public FixtureBuilder circleShape(float radius) {
        circleShape(null, radius);
        return this;
    }

    public FixtureBuilder circleShape(float x, float y, float radius) {
        circleShape(new Vector2(x, y), radius);
        return this;
    }

    public FixtureBuilder polygonShape(float[] vertices) {
        disposeShape(true);
        PolygonShape polygon = new PolygonShape();
        polygon.set(vertices);
        shape(polygon);
        return this;
    }

    public FixtureBuilder polygonShape(Array<Vector2> vertices) {
        polygonShape(com.mygdx.game.utilities.Basic.toFloats(vertices));
        return this;
    }

    public FixtureBuilder boxShape(float width, float height) {
        disposeShape(true);
        PolygonShape polygon = new PolygonShape();
        polygon.setAsBox(0.5f * width, 0.5f * height);
        shape(polygon);
        return this;
    }

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

    public FixtureBuilder userData(Object data) {
        userData = data;
        return this;
    }

    public Fixture attachTo(Body body) {
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData);
        return fixture;
    }
}
