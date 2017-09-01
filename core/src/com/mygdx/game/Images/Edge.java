package com.mygdx.game.Images;

import com.badlogic.gdx.math.Vector2;

/**
 * A shape2D class that goes together with the box2d shape edgeShape.
 */

public class Edge extends Shape2DAdapter {
    public float aX, aY, bX, bY;
    public float ghostAX, ghostAY, ghostBX, ghostBY;
    public boolean ghostAExists = false;
    public boolean ghostBExists = false;

    /**
     * edge without data to create for reuse
     */
    public Edge() {
    }

    /**
     * create an edge without ghost vertices
     *
     * @param aX float, x-coordinate of vertex a
     * @param aY float, y-coordinate of vertex a
     * @param bX float, x-coordinate of vertex b
     * @param bY float, y-coordinate of vertex b
     */
    public Edge(float aX, float aY, float bX, float bY) {
        set(aX, aY, bX, bY);
    }

    /**
     * create an edge without ghost vertices
     *
     * @param a Vector2, position of vertex a
     * @param b Vector2, position of vertex a
     */
    public Edge(Vector2 a, Vector2 b) {
        set(a, b);
    }

    /**
     * Delete the ghost vertices for reuse.
     *
     * @return this, for chaining
     */
    public Edge deleteGhosts() {
        ghostAExists = false;
        ghostBExists = false;
        return this;
    }

    /**
     * Set position of ghost A and that it exists.
     *
     * @param x float, x-coordinate of ghost A
     * @param y float, y-coordinate of ghost A
     * @return this, for chaining
     */
    public Edge addGhostA(float x, float y) {
        ghostAExists = true;
        ghostAX = x;
        ghostAY = y;
        return this;
    }

    /**
     * set ghost A position and that it exists
     *
     * @param position Vector2, position of ghost A
     * @return this, for chaining
     */
    public Edge addGhostA(Vector2 position) {
        return addGhostA(position.x, position.y);
    }

    /**
     * set ghost B position and that it exists.
     *
     * @param x float, x-coordinate of ghost B
     * @param y float, y-coordinate of ghost B
     * @return this, for chaining
     */
    public Edge addGhostB(float x, float y) {
        ghostBExists = true;
        ghostBX = x;
        ghostBY = y;
        return this;
    }

    /**
     * set ghost B position and that it exists
     *
     * @param position Vector2, position of ghost B
     * @return this, for chaining
     */
    public Edge addGhostB(Vector2 position) {
        return addGhostB(position.x, position.y);
    }

    /**
     * Set the positions of the vertices.
     *
     * @param aX float, x-coordinate of vertex a
     * @param aY float, y-coordinate of vertex a
     * @param bX float, x-coordinate of vertex b
     * @param bY float, y-coordinate of vertex b
     * @return this, for chaining
     */
    public Edge set(float aX, float aY, float bX, float bY) {
        this.aX = aX;
        this.aY = aY;
        this.bX = bX;
        this.bY = bY;
        return this;
    }

    /**
     * Set the positions of the vertices.
     *
     * @param a Vector2, position of point a
     * @param b Vector2, position of point b
     * @return this, for chaining
     */
    public Edge set(Vector2 a, Vector2 b) {
        return set(a.x, a.y, b.x, b.y);
    }

}
