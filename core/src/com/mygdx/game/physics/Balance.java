package com.mygdx.game.physics;

import com.mygdx.game.utilities.Accelerometer;

/**
 * Using the accelerometer to set gravity
 */

public class Balance extends Accelerometer {
    private Physics physics;
    public float scale;

    /**
     * Create an Balance object to set the gravity from accelerometer readings
     *
     * @param physics Physics
     * @param scale float, magnification scale for gravity
     */
    public Balance(Physics physics,float scale){
        super();
        this.physics=physics;
        setScale(scale);
    }

    /**
     * Create an Balance object to set the gravity from accelerometer readings. scale=1
     *
     * @param physics Physics
     */
    public Balance(Physics physics){
        this(physics,1f);
    }

    /**
     * set the scale for conversion from readings to gravity
     *
     * @param scale float, the scale
     */
    public void setScale(float scale) {
        this.scale = scale;
    }

    /**
     * update the world gravity. Accelerometer_reading times scale gives pixels/sec²
     */
    public void update(){
        physics.world.setGravity(read().scl(scale/Physics.PIXELS_PER_METER));
    }
}
