package com.mygdx.game.Images;

import com.badlogic.gdx.math.Vector2;

/**
 * A shape2D class that goes together with the box2d shape chainShape.
 */

public class Chain extends Shape2DAdapter {
    public float[] coordinates;
    public float ghostAX, ghostAY, ghostBX, ghostBY;
    public boolean ghostAExists = false;
    public boolean ghostBExists = false;
    public boolean isLoop = false;

    /**
     * create an empty chain.
     */
    public Chain() {
    }

    /**
     * Create a chain with float coordinate pairs of the points.
     * Set ghosts or set isLoop separately.
     *
     * @param coordinates float ... or float[] of (x,y) coordinate pairs
     */
    public Chain(float... coordinates) {
        set(coordinates);
    }

    /**
     * Create a chain with points from a Polypoint object, including isLoop.
     * Set ghosts separately.
     *
     * @param polypoint Polypoint object that defines the chain, except ghosts.
     */
    public Chain(Polypoint polypoint) {
        set(polypoint);
    }

    /**
     * Set internal vertices of the chain with float coordinate pairs of the points.
     *
     * @param coordinates float ... or float[] of (x,y) coordinate pairs
     * @return this, for chaining
     */
    public Chain set(float... coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * set internal vertices of the chain from polypoint
     * If the polypoint object is a loop then delete ghosts and set isLoop=true.
     *
     * @param polypoint
     * @return this, for chaining.
     */
    public Chain set(Polypoint polypoint) {
        this.coordinates = polypoint.coordinates.toArray();
        setIsLoop(polypoint.isLoop);
        return this;
    }

    /**
     * Set that chain is a loop or not. If it is a loop then delete ghosts.
     *
     * @return this, for chaining
     */
    public Chain setIsLoop(boolean isLoop) {
        if (isLoop) {
            deleteGhosts();
        }
        this.isLoop = isLoop;
        return this;
    }

    /**
     * Delete the ghost vertices for reuse.
     *
     * @return this, for chaining
     */
    public Chain deleteGhosts() {
        ghostAExists = false;
        ghostBExists = false;
        return this;
    }

    /**
     * Set position of ghost A and that it exists, sets isLoop =false.
     *
     * @param x float, x-coordinate of ghost
     * @param y float, y-coordinate of ghost
     * @return this, for chaining
     */
    public Chain addGhostA(float x, float y) {
        ghostAExists = true;
        isLoop = false;
        ghostAX = x;
        ghostAY = y;
        return this;
    }

    /**
     * set ghost A position and that it exists and that this is not a loop
     *
     * @param position Vector2D, position of the ghost
     * @return this, for chaining
     */
    public Chain addGhostA(Vector2 position) {
        return addGhostA(position.x, position.y);
    }

    /**
     * set ghost a position and that it exists, set thatthis is not a loop
     *
     * @param x float, x-coordinate of ghost
     * @param y float, y-coordinate of ghost
     * @return this, for chaining
     */
    public Chain addGhostB(float x, float y) {
        ghostBExists = true;
        isLoop = false;
        ghostBX = x;
        ghostBY = y;
        return this;
    }

    /**
     * set ghost B position and that it exists
     *
     * @param position Vector2D, position of the ghost
     * @return this, for chaining
     */
    public Chain addGhostB(Vector2 position) {
        return addGhostB(position.x, position.y);
    }
}
