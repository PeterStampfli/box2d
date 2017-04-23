package com.mygdx.game.Images;

import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/10/17.
 * graphics shape2D that parallels the box2d edgeShape
 */

public class Edge implements Shape2D {

    public float ghostAX,ghostAY,aX,aY,bX,bY,ghostBX,ghostBY;
    public boolean ghostAExists=false;
    public boolean ghostBExists=false;

    /**
     * edge without data to create for reuse
     */
    public Edge(){}

    /**
     * create an edge with both ghost vertices
     * @param ghostAX
     * @param ghostAY
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     * @param ghostBX
     * @param ghostBY
     */
    public Edge(float ghostAX,float ghostAY,float aX,float aY,
                float bX, float bY,float ghostBX,float ghostBY){
        addGhostA(ghostAX,ghostAY);
        set(aX,aY,bX,bY);
        addGhostB(ghostBX,ghostBY);
    }

    /**
     * create an edge without ghost vertices
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     */
    public Edge(float aX,float aY,float bX, float bY){
        deleteGhosts();
        set(aX,aY,bX,bY);
    }

    /**
     * create an edge with both ghost vertices
     * @param ghostA
     * @param a
     * @param b
     * @param ghostB
     */
    public Edge(Vector2 ghostA,Vector2 a,Vector2 b,Vector2 ghostB){
        this(ghostA.x,ghostA.y,a.x,a.y,b.x,b.y,ghostB.x,ghostB.y);
    }

    /**
     * create an edge without ghost vertices
     * @param a
     * @param b
     */
    public Edge(Vector2 a,Vector2 b){
        this(a.x,a.y,b.x,b.y);
    }

    /**
     * null ghosts for reuse
     */
    public Edge deleteGhosts(){
        ghostAExists=false;
        ghostBExists=false;
        return this;
    }

    /**
     * set ghost A position and that it exists
     * @param x
     * @param y
     * @return
     */
    public Edge addGhostA(float x, float y){
        ghostAExists=true;
        ghostAX=x;
        ghostAY=y;
        return this;
    }

    /**
     * set ghost A position and that it exists
     * @param position
     * @return
     */
    public Edge addGhostA(Vector2 position){
        return addGhostA(position.x,position.y);
    }

    /**
     * set ghost b position and that it exists
     * @param x
     * @param y
     * @return
     */
    public Edge addGhostB(float x, float y){
        ghostBExists=true;
        ghostBX=x;
        ghostBY=y;
        return this;
    }

    /**
     * set ghost B position and that it exists
     * @param position
     * @return
     */
    public Edge addGhostB(Vector2 position){
        return addGhostB(position.x,position.y);
    }

    /**
     * set the edge positions
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public Edge set(float x1,float y1,float x2,float y2){
        aX=x1;
        aY=y1;
        bX=x2;
        bY=y2;
        return this;
    }

    /**
     * set the edge positions
     * @param a
     * @param b
     * @return
     */
    public Edge set(Vector2 a,Vector2 b){
        return set(a.x,a.y,b.x,b.y);
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
