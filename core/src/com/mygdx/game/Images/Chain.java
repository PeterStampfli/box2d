package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Created by peter on 4/5/17.
 * joining straight lines with circles (dots) to make a chain
 * related to box2D ChainShape
 */

public class Chain implements Shape2D {

    public FloatArray coordinates;
    public float thickness=0;
    private float epsilon=0.1f;                   // on the basis of a pixel scale, 1=pixelsize
    public float maxDeltaAngle=0.1f;

    /**
     * chain of thickness zero, as a pure chainShape for box2D ??
     */
    public Chain(){
        coordinates=new FloatArray();
    }

    /**
     * chain of finite thickness for graphics
     * @param thickness
     */
    public Chain(float thickness){
        coordinates=new FloatArray();
        this.thickness=thickness;
    }

    // default Shape2D methods ???
    @Override
    public boolean contains(Vector2 point) {
        return false;
    }

    @Override
    public boolean contains(float x, float y) {
        return false;
    }

    /**
     * add a point with coordinates (x,y)
     * if it is same as last point it will not be added
     * (important for joining arcs)
     * @param x
     * @param y
     */
    public void add(float x,float y){
        int length=coordinates.size;
        if ((length==0)||(Math.abs(x-coordinates.get(length-2))>epsilon)
                ||(Math.abs(y-coordinates.get(length-1))>epsilon)){
            coordinates.addAll(x,y);
        }
    }

    /**
     * add a point
     * if it is same as last point it will not be added
     * (important for joining arcs)
     * @param point
     */
    public void add(Vector2 point){
        add(point.x,point.y);
    }

    /**
     * add points (and corresponding line segments)
     * @param coordinates
     * @return
     */
    public void add(float... coordinates){
        int length=coordinates.length;
        for (int i=0;i<length;i+=2){
            add(coordinates[i],coordinates[i+1]);
        }
    }

    /**
     * add points (and corresponding line segments)
     * @param points
     */
    public void add(Vector2... points){
        for (Vector2 point:points){
            add(point);
        }
    }

    /**
     * create points on a circle around given center with given radius
     * from angle alpha to beta
     * going clockwise or anticlockwise on the circle
     * @param centerX
     * @param centerY
     * @param radius
     * @param alpha
     * @param beta
     * @param counterClockwise
     */
    public void addBasicArc(float centerX,float centerY, float radius,
                            float alpha, float beta, boolean counterClockwise) {
        if (counterClockwise) {
            if (beta < alpha) {
                beta += MathUtils.PI2;
            }
        } else {
            if (beta > alpha) {
                beta -= MathUtils.PI2;
            }
        }
        int nSegments=MathUtils.ceil(Math.abs(beta-alpha)/maxDeltaAngle);
        Gdx.app.log("nseg",""+nSegments);
        float deltaAngle=(beta-alpha)/nSegments;
        float angle=alpha;
        for (int i=0;i<=nSegments;i++){
            add(centerX+radius*MathUtils.cos(angle),centerY+radius*MathUtils.sin(angle));
            angle+=deltaAngle;
        }
    }


    /**
     * create points on a circle arc around given center with given radius
     * from angle alpha to beta
     * going clockwise or anticlockwise on the circle
     * @param center
     * @param radius
     * @param alpha
     * @param beta
     * @param counterClockwise
     */
    public void addBasicArc(Vector2 center, float radius,
                            float alpha, float beta, boolean counterClockwise) {
        addBasicArc(center.x,center.y,radius,alpha,beta,counterClockwise);
    }

    /**
     * create points on an arc going from a to b
     * the center of the arc is on the line between a and someCenter
     * someCenter may be the center of the arc or of another arc that joins tangentially this arc
     * THIS should be used to join two circles
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     * @param someCenterX
     * @param someCenterY
     * @param counterclockwise
     */
    public void addArcABSomeCenter(float aX,float aY,float bX,float bY,
                                   float someCenterX,float someCenterY,boolean counterclockwise){
        // unit vector pointing from a to someCenter
        float unitAToCenterX=someCenterX-aX;
        float unitAToCenterY=someCenterY-aY;
        float normFactor=1f/Vector2.len(unitAToCenterX,unitAToCenterY);
        unitAToCenterX*=normFactor;
        unitAToCenterY*=normFactor;
        float aBX=bX-aX;
        float aBY=bY-aY;
        // radius = 0.5*distance between a and b/ cosine of angle between AB and AToSomeCenter
        // with sign
        float radius=0.5f*(aBX*aBX+aBY*aBY)/(aBX*unitAToCenterX+aBY*unitAToCenterY);
        float centerX=aX+unitAToCenterX*radius;
        float centerY=aY+unitAToCenterY*radius;
        float alpha=MathUtils.atan2(aY-centerY,aX-centerX);
        float beta=MathUtils.atan2(bY-centerY,bX-centerX);
        addBasicArc(centerX,centerY,Math.abs(radius),alpha,beta,counterclockwise);
    }

    /**
     * create points on an arc going from a to b
     * the center of the arc is on the line between a and someCenter
     * someCenter may be the center of the arc or of another arc that joins tangentially this arc
     * THIS should be used to join two circles
     * @param a
     * @param b
     * @param someCenter
     * @param counterclockwise
     */
    public void addArcABSomeCenter(Vector2 a,Vector2 b,Vector2 someCenter,boolean counterclockwise) {
        addArcABSomeCenter(a.x,a.y,b.x,b.y,someCenter.x,someCenter.y,counterclockwise);
    }
}
