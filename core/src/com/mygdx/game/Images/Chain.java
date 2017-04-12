package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/12/17.
 * to go with box2d chainShape
 */

public class Chain implements Shape2D {
    float[] coordinates;
    float ghostAX,ghostAY,ghostBX,ghostBY;
    boolean ghostAExists=false;
    boolean ghostBExists=false;
    boolean isLoop=false;

    /**
     * create chain with point coordinates
     * st ghosts or isLoop separately
     * @param coordinates
     */
    public Chain(float... coordinates){
        this.coordinates=coordinates;
    }

    /**
     * create chain with point coordinates
     * st ghosts or isLoop separately
     * @param polypoint
     */
    public Chain(Polypoint polypoint){
        this.coordinates=polypoint.coordinates.toArray();
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
     * set internal vertices of the chain
     * @param polypoint
     * @return
     */
    public Chain set(Polypoint polypoint){
        this.coordinates=polypoint.coordinates.toArray();
        return this;
    }

    /**
     * null ghosts and loop for reuse
     */
    public Chain deleteGhosts(){
        ghostAExists=false;
        ghostBExists=false;
        isLoop=false;
        return this;
    }

    /**
     * set ghost A position and that it exists,
     * isLoop =false
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
     * set ghost A position and that it exists
     * @param position
     * @return
     */
    public Chain addGhostA(Vector2 position){
        return addGhostA(position.x,position.y);
    }

    /**
     * set ghost a position and that it exists
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

    /**
     * set that chain is a loop without ghosts
     * @return
     */
    public Chain isLoop(){
        deleteGhosts();
        isLoop=true;
        return this;
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
