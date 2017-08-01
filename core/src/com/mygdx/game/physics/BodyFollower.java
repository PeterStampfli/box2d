package com.mygdx.game.physics;

/**
 * For all sprite like objects that follow a box2D body
 */

public interface BodyFollower {

    /**
     * the follower might adjust something before the time step
     */
    void prepareTimeStep();

    /**
     * The follower reads new body data and updates previous data.
     */
    void readPositionAngleOfBody();

    /**
     * The follower interpolates between new and previous data
     *
     * @param progress float, between 0 (use previous) and 1 (use new data)
     */
    void interpolatePositionAngleOfBody(float progress);
}
