package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by peter on 3/26/17.
 */

public interface Positionable {

    public void setPhysicsData(Body body);

    public void updateGraphicsData(float progress);
}
