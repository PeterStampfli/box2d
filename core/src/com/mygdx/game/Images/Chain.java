package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/12/17.
 * to go with box2d chainShape
 */

public class Chain implements Shape2D {
    public float[] coordinates;
    public float ghostAX,ghostAY,ghostBX,ghostBY;
    public boolean ghostAExists=false;
    public boolean ghostBExists=false;
    public boolean isLoop=false;

    /**
     * create chain with point coordinates
     * set ghosts or set isLoop separately
     * @param coordinates
     */
    public Chain(float... coordinates){
        set(coordinates);
    }

    /**
     * create chain with points from Polypoint, including isLoooüp
     * set ghosts separately
     * @param polypoint
     */
    public Chain(Polypoint polypoint){
        set(polypoint);
    }

    /**
     * set internal vertices of the chain
     * @param coordinates
     * @return
     */
    public Chain set(float... coordinates){
        this.coordinates=coordinates;
        return this;
    }

    /**
     * set internal vertices of the chain from polypoint
     * if polypoint is a loop delete ghosts and set is loop
     * @param polypoint
     * @return
     */
    public Chain set(Polypoint polypoint){
        this.coordinates=polypoint.coordinates.toArray();
        setIsLoop(polypoint.isLoop);
        return this;
    }

    /**
     * set that chain is a loop
     * if it is a loop delete ghosts
     * @return
     */
    public Chain setIsLoop(boolean isLoop){
        if (isLoop){
            deleteGhosts();
        }
        this.isLoop=isLoop;
        return this;
    }

    /**
     * null ghosts and loop for reuse
     */
    public Chain deleteGhosts(){
        ghostAExists=false;
        ghostBExists=false;
        return this;
    }

    /**
     * set ghost A position and that it exists,
     * setIsLoop =false
     * @param x
     * @param y
     * @return
     */
    public Chain addGhostA(float x, float y){
        ghostAExists=true;
        isLoop=false;
        ghostAX=x;
        ghostAY=y;
        return this;
    }

    /**
     * set ghost A position and that it exists and that this is not a loop
     * @param position
     * @return
     */
    public Chain addGhostA(Vector2 position){
        return addGhostA(position.x,position.y);
    }

    /**
     * set ghost a position and that it exists, set that it is not a loop
     * @param x
     * @param y
     * @return
     */
    public Chain addGhostB(float x, float y){
        ghostBExists=true;
        isLoop=false;
        ghostBX=x;
        ghostBY=y;
        return this;
    }

    /**
     * set ghost B position and that it exists
     * @param position
     * @return
     */
    public Chain addGhostB(Vector2 position){
        return addGhostB(position.x,position.y);
    }

    @Override
    public boolean contains(Vector2 point) {
        return false;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }
}
