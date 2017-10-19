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
     * create an empty borderShape.
     */
    public Chain() {
    }

    /**
     * Create a borderShape with float coordinate pairs of the points.
     * Set ghosts or set isLoop separately.
     *
     * @param coordinates float ... or float[] of (x,y) coordinate pairs
     */
    public Chain(float... coordinates) {
        set(coordinates);
    }

    /**
     * Create a borderShape with points from a Polypoint object, including isLoop.
     * Set ghosts separately.
     *
     * @param polypoint Polypoint object that defines the borderShape, except ghosts.
     */
    public Chain(Polypoint polypoint) {
        set(polypoint);
    }

    /**
     * Set internal vertices of the borderShape with float coordinate pairs of the points.
     *
     * @param coordinates float ... or float[] of (x,y) coordinate pairs
     * @return this, for chaining
     */
    public Chain set(float... coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    /**
     * set internal vertices of the borderShape from borderPoints
     * If the borderPoints object is a loop then delete ghosts and set isLoop=true.
     *
     * @param polypoint Polypoint, defines the borderShape
     * @return this, for chaining.
     */
    public Chain set(Polypoint polypoint) {
        set(polypoint.coordinates.toArray());
        setIsLoop(polypoint.isLoop);
        deleteGhosts();
        return this;
    }

    /**
     * Set that borderShape is a loop and delete ghosts.
     *
     * @return this, for chaining
     */
    public Chain setIsLoop() {
        return setIsLoop(true);
    }

    /**
     * Set that borderShape is a loop or not. If it is a loop then delete ghosts.
     *
     * @param isLoop boolean, true if borderShape is a loop
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
     * set ghost A position and that it exists and that this borderShape is not a loop
     *
     * @param position Vector2D, position of the ghost
     * @return this, for chaining
     */
    public Chain addGhostA(Vector2 position) {
        return addGhostA(position.x, position.y);
    }

    /**
     * set ghost a position and that it exists and that this borderShape is not a loop
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
