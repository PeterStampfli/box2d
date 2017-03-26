package com.mygdx.game.physics;

import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by peter on 3/26/17.
 */

// defines objects that can update their physics data from reading a box2D body
// and that can interpolate the physics data to get data at graphics time (time of rendering)

public interface Positionable {

    public void setPhysicsData(Body body);

    public void updateGraphicsData(float progress);
}
