package com.mygdx.game.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * make that all dynamical bodies have the same acceleration depending on their center of mass position
 * calculation of acceleration is done in pixel units (graphical units) in method to overwrite
 */

abstract public class ForceField {
    private Physics physics;



    public ForceField(Physics physics){
        this.physics=physics;
    }


    abstract public Vector2 acceleration(Vector2 position);


    public void update(){

        //get bodies from Physics     Array<Body> bodies;
        // use body.getWorldCenter(),body.getMass(),body.applyForceToCenter(Vector2 force,boolean wake)
// transform to pixel data

    }


}
