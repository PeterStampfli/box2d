package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.Iterator;

/**
 * make that all dynamical bodies have the an acceleration depending on their center of mass position
 */

abstract public class Forces {
    private Physics physics;
    private Vector2 position=new Vector2();
    private Vector2 acceleration=new Vector2();


    /**
     * create a forceField object using given physics
     *
     * @param physics Physics
     */
    public Forces(Physics physics){
        this.physics=physics;
    }

    /**
     * calculates the acceleration in pixel/sec² as a function of center of body (pixel lengths)
     *
     * @param acceleration Vector2 acceleration to calculate
     * @param position Vector2 position in pixels (graphics units)
     * @return
     */
    abstract public void calculateAcceleration(Vector2 acceleration, Vector2 position);


    /**
     * update body forces in m/sec² from calculation of acceleration
     * converts units, takes into account inversion of y-axis for acceleration ???
     */
    public void update(){
        Body body;
        float inverseScale=1f/Physics.PIXELS_PER_METER;
        Iterator<Body> bodies=physics.bodies.iterator();
        while (bodies.hasNext()){
            body=bodies.next();
            position.set(body.getWorldCenter()).scl(Physics.PIXELS_PER_METER);
            calculateAcceleration(acceleration,position);
            acceleration.scl(inverseScale);
            body.applyForceToCenter(acceleration,true);
        }
    }
}
