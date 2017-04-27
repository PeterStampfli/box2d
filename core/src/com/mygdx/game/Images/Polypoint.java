package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Create a collection of points. Make points on circle arcs.
 */

public class Polypoint extends Shape2DAdapter {

    public FloatArray coordinates = new FloatArray();
    public float maxDeltaAngle = 0.1f;
    public boolean isLoop = false;
    private float epsilon = 0.1f;                   // on the basis of a pixel scale, 1=pixelsize

    /**
     * clear coordinates for reuse
     *
     * @return this
     */
    public Polypoint clear() {
        coordinates.clear();
        return this;
    }

    /**
     * set if it is meant to be a loop
     *
     * @param isLoop
     * @return this
     */
    public Polypoint setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }

    /**
     * set that it is meant to be a loop
     *
     * @return
     */
    public Polypoint setIsLoop() {
        return setIsLoop(true);
    }

    /**
     * add a point with coordinates (x,y)
     * if it is same as last point it will not be added
     * (important for joining arcs)
     *
     * @param x
     * @param y
     * @return this
     */
    public Polypoint add(float x, float y) {
        int length = coordinates.size;
        if ((length == 0) || (Math.abs(x - coordinates.get(length - 2)) > epsilon)
                || (Math.abs(y - coordinates.get(length - 1)) > epsilon)) {
            coordinates.addAll(x, y);
        }
        return this;
    }

    /**
     * add a point
     * if it is same as last point it will not be added
     * (important for joining arcs)
     *
     * @param point
     * @return this
     */
    public Polypoint add(Vector2 point) {
        return add(point.x, point.y);
    }

    /**
     * add points (and corresponding line segments)
     *
     * @param coordinates
     * @return this
     */
    public Polypoint add(float... coordinates) {
        int length = coordinates.length;
        for (int i = 0; i < length; i += 2) {
            add(coordinates[i], coordinates[i + 1]);
        }
        return this;
    }

    /**
     * add points (and corresponding line segments)
     *
     * @param points
     * @return this
     */
    public Polypoint add(Vector2... points) {
        for (Vector2 point : points) {
            add(point);
        }
        return this;
    }

    /**
     * create points on a circle around given center with given radius
     * from angle alpha to beta
     * going clockwise or anticlockwise on the circle
     *
     * @param centerX
     * @param centerY
     * @param radius
     * @param alpha
     * @param beta
     * @param counterClockwise
     * @return this
     */
    public Polypoint addBasicArc(float centerX, float centerY, float radius,
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
        int nSegments = MathUtils.ceil(Math.abs(beta - alpha) / maxDeltaAngle);
        Gdx.app.log("nseg", "" + nSegments);
        float deltaAngle = (beta - alpha) / nSegments;
        float angle = alpha;
        for (int i = 0; i <= nSegments; i++) {
            add(centerX + radius * MathUtils.cos(angle), centerY + radius * MathUtils.sin(angle));
            angle += deltaAngle;
        }
        return this;
    }


    /**
     * create points on a circle arc around given center with given radius
     * from angle alpha to beta
     * going clockwise or anticlockwise on the circle
     *
     * @param center
     * @param radius
     * @param alpha
     * @param beta
     * @param counterClockwise
     * @return this
     */
    public Polypoint addBasicArc(Vector2 center, float radius,
                                 float alpha, float beta, boolean counterClockwise) {
        return addBasicArc(center.x, center.y, radius, alpha, beta, counterClockwise);
    }

    /**
     * create points on an arc going from a to b
     * the center of the arc is on the line between a and someCenter
     * someCenter may be the center of the arc or of another arc that joins tangentially this arc
     * THIS should be used to join two circles
     *
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     * @param someCenterX
     * @param someCenterY
     * @param counterclockwise
     * @return this
     */
    public Polypoint addArcABSomeCenter(float aX, float aY, float bX, float bY,
                                        float someCenterX, float someCenterY, boolean counterclockwise) {
        // unit vector pointing from a to someCenter
        float unitAToCenterX = someCenterX - aX;
        float unitAToCenterY = someCenterY - aY;
        float normFactor = 1f / Vector2.len(unitAToCenterX, unitAToCenterY);
        unitAToCenterX *= normFactor;
        unitAToCenterY *= normFactor;
        float aBX = bX - aX;
        float aBY = bY - aY;
        // radius = 0.5*distance between a and b/ cosine of angle between AB and AToSomeCenter
        // with sign
        float radius = 0.5f * (aBX * aBX + aBY * aBY) / (aBX * unitAToCenterX + aBY * unitAToCenterY);
        float centerX = aX + unitAToCenterX * radius;
        float centerY = aY + unitAToCenterY * radius;
        float alpha = MathUtils.atan2(aY - centerY, aX - centerX);
        float beta = MathUtils.atan2(bY - centerY, bX - centerX);
        addBasicArc(centerX, centerY, Math.abs(radius), alpha, beta, counterclockwise);
        return this;
    }

    /**
     * create points on an arc going from a to b
     * the center of the arc is on the line between a and someCenter
     * someCenter may be the center of the arc or of another arc that joins tangentially this arc
     * THIS should be used to join two circles
     *
     * @param a
     * @param b
     * @param someCenter
     * @param counterclockwise
     * @return this
     */
    public Polypoint addArcABSomeCenter(Vector2 a, Vector2 b, Vector2 someCenter, boolean counterclockwise) {
        return addArcABSomeCenter(a.x, a.y, b.x, b.y, someCenter.x, someCenter.y, counterclockwise);
    }

    /**
     * creat points on an arc from a to b
     * the tangentPoint defines a tangent going from point a
     * use only for joining a straight line
     *
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     * @param tangentPointX
     * @param tangentPointY
     * @param counterclockwise
     * @return this
     */
    public Polypoint addArcABTangent(float aX, float aY, float bX, float bY,
                                     float tangentPointX, float tangentPointY, boolean counterclockwise) {
        float someCenterX = aX + (tangentPointY - aY);
        float someCenterY = aY - (tangentPointX - aX);
        addArcABSomeCenter(aX, aY, bX, bY, someCenterX, someCenterY, counterclockwise);
        return this;
    }

    /**
     * creat points on an arc from a to b
     * the tangentPoint defines a tangent going from point a
     * use only for joining a straight line
     *
     * @param a
     * @param b
     * @param tangentPoint
     * @param counterclockwise
     * @return this
     */
    public Polypoint addArcABTangent(Vector2 a, Vector2 b, Vector2 tangentPoint, boolean counterclockwise) {
        return addArcABTangent(a.x, a.y, b.x, b.y, tangentPoint.x, tangentPoint.y, counterclockwise);
    }

    /**
     * add an arc going from point a to b to c
     *
     * @param aX
     * @param aY
     * @param bX
     * @param bY
     * @param cX
     * @param cY
     * @return this
     */
    public Polypoint addArcABC(float aX, float aY, float bX, float bY, float cX, float cY) {
        float a2mb2 = (aX - bX) * (aX + bX) + (aY - bY) * (aY + bY);
        float a2mc2 = (aX - cX) * (aX + cX) + (aY - cY) * (aY + cY);
        float den = 2 * ((bY - aY) * (cX - aX) - (cY - aY) * (bX - aX));
        float centerX = ((cY - aY) * a2mb2 - (bY - aY) * a2mc2) / den;
        float centerY = -((cX - aX) * a2mb2 - (bX - aX) * a2mc2) / den;
        float alpha = MathUtils.atan2(aY - centerY, aX - centerX);
        float beta = MathUtils.atan2(bY - centerY, bX - centerX);
        float gamma = MathUtils.atan2(cY - centerY, cX - centerX);
        float radius = Vector2.dst(aX, aY, centerX, centerY);
        boolean counterClockwise = ((alpha < beta) && (beta < gamma)) || ((beta < gamma) && (gamma < alpha)) || ((gamma < alpha) && (alpha < beta));
        addBasicArc(centerX, centerY, radius, alpha, gamma, counterClockwise);
        return this;
    }

    /**
     * add an arc going from point a to b to c
     *
     * @param a
     * @param b
     * @param c
     * @return this
     */
    public Polypoint addArcABC(Vector2 a, Vector2 b, Vector2 c) {
        return addArcABC(a.x, a.y, b.x, b.y, c.x, c.y);
    }

}
