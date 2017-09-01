package com.mygdx.game.Images;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.utilities.MathU;

/**
 * Create a collection of points connected by lines. Make points on circle arcs.
 */

public class Polypoint extends Shape2DAdapter {

    public FloatArray coordinates;
    public float maxDeltaAngle = 0.1f;
    public boolean isLoop = false;

    /**
     * default creator, for polypoint with empty coordinates
     */
    public Polypoint(){
        coordinates = new FloatArray();
    }

    /**
     * creator for known coordinates and if it is a loop
     *  @param isLoop boolean, true if polypoint should be loop
     * @param coordinates float... or float[] with coordinate pairs
     */
    public Polypoint(boolean isLoop, float... coordinates){
        this.coordinates=new FloatArray(coordinates);
        this.isLoop=isLoop;
    }

    /**
     * Clear for reuse. Deletes the points (their coordinates).
     *
     * @return this, for chaining
     */
    public Polypoint clear() {
        coordinates.clear();
        return this;
    }

    /**
     * Set if it is a loop. Implies that the last point should have a line to the first point.
     *
     * @param isLoop boolean, true if this is a loop
     * @return this, for chaining
     */
    public Polypoint setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
        return this;
    }

    /**
     * Set that it is a loop. Implies that the last point should have a line to the first point.
     *
     * @return this, for chaining
     */
    public Polypoint setIsLoop() {
        this.isLoop = true;
        return this;
    }

    /**
     * Get a shape2D polygon based on the vertices of a polypoint object.
     *
     * @return Polygon, with the same points. An independent copy.
     */
    public Polygon getPolygon() {
        return new Polygon(coordinates.toArray());
    }

    /**
     * Add points given by coordinate pairs (x,y)
     *
     * @param newCoordinates float... or float[], containing pairs of (x,y) coordinates
     */
    public void add(float... newCoordinates) {
        int newCoordinatesLength = newCoordinates.length;
        int length = coordinates.size;
        for (int i = 0; i < newCoordinatesLength; i += 2) {
            if ((length == 0) || (Math.abs(newCoordinates[i] - coordinates.get(length - 2)) > MathU.epsilon)
                    || (Math.abs(newCoordinates[i + 1] - coordinates.get(length - 1)) > MathU.epsilon)) {
                coordinates.add(newCoordinates[i]);
                coordinates.add(newCoordinates[i + 1]);
                length += 2;
            }
        }
    }

    /**
     * add points (and corresponding line segments)
     *
     * @param points Vector2... or Vector2[] of points
     */
    public void add(Vector2... points) {
        for (Vector2 point : points) {
            add(point.x, point.y);
        }
    }

    /**
     * Make points on a circle arc around a given center with a given radius.
     * Goes from angle alpha to beta, clockwise or anticlockwise.
     *
     * @param centerX          float, x-coordinate of the center
     * @param centerY          float, y-coordinate of the center
     * @param radius           float, radius of the circle
     * @param alpha            float, start angle
     * @param beta             float, end angle
     * @param counterclockwise boolean, true for going counterclockwise
     */
    public void basicArc(float centerX, float centerY, float radius,
                         float alpha, float beta, boolean counterclockwise) {
        if (counterclockwise) {
            if (beta < alpha) {
                beta += MathUtils.PI2;
            }
        } else {
            if (beta > alpha) {
                beta -= MathUtils.PI2;
            }
        }
        int nSegments = MathUtils.ceil(Math.abs(beta - alpha) / maxDeltaAngle);
        float deltaAngle = (beta - alpha) / nSegments;
        float angle = alpha;
        for (int i = 0; i <= nSegments; i++) {
            add(centerX + radius * MathUtils.cos(angle), centerY + radius * MathUtils.sin(angle));
            angle += deltaAngle;
        }
    }


    /**
     * Make points on a circle arc around a given center with a given radius.
     * Goes from angle alpha to beta, clockwise or anticlockwise.
     *
     * @param center           Vector2, the center
     * @param radius           float, radius of the circle
     * @param alpha            float, start angle
     * @param beta             float, end angle
     * @param counterClockwise boolean, true for going counterclockwise
     */
    public void basicArc(Vector2 center, float radius,
                         float alpha, float beta, boolean counterClockwise) {
        basicArc(center.x, center.y, radius, alpha, beta, counterClockwise);
    }

    /**
     * Make points on an arc going from point a to point b.
     * The center of the arc is on the line between point a and point someCenter.
     * Point someCenter may be the center of this arc or of another arc that joins this arc.
     *
     * @param aX               float, x-coordinate of point a
     * @param aY               float, y-coordinate of point a
     * @param bX               float, x-coordinate of point b
     * @param bY               float, y-coordinate of point b
     * @param someCenterX      float, x-coordinate of someCenter
     * @param someCenterY      float, x-coordinate of someCenter
     * @param counterclockwise boolean, true for going counterclockwise
     */
    public void addArcABSomeCenter(float aX, float aY, float bX, float bY,
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
        basicArc(centerX, centerY, Math.abs(radius), alpha, beta, counterclockwise);
    }

    /**
     * Make points on an arc going from point a to point b.
     * The center of the arc is on the line between point a and point someCenter.
     * Point someCenter may be the center of this arc or of another arc that joins this arc.
     *
     * @param a                Vector2, point a
     * @param b                Vector2, point b
     * @param someCenter       Vector2, center point of some circle
     * @param counterclockwise boolean, true for going counterclockwise
     */
    public void addArcABSomeCenter(Vector2 a, Vector2 b, Vector2 someCenter, boolean counterclockwise) {
        addArcABSomeCenter(a.x, a.y, b.x, b.y, someCenter.x, someCenter.y, counterclockwise);
    }

    /**
     * Make points on an arc from point a to point b.
     * The tangentPoint defines a tangent to the arc going out from point a.
     * Use for joining a straight line to the arc.
     *
     * @param aX               float, x-coordinate of point a
     * @param aY               float, y-coordinate of point a
     * @param bX               float, x-coordinate of point b
     * @param bY               float, y-coordinate of point b
     * @param tangentPointX    float, x-coordinate of tangentPoint
     * @param tangentPointY    float, y-coordinate of tangentPoint
     * @param counterclockwise boolean, true for going counterclockwise
     */
    public void addArcABTangent(float aX, float aY, float bX, float bY,
                                float tangentPointX, float tangentPointY, boolean counterclockwise) {
        float someCenterX = aX + (tangentPointY - aY);
        float someCenterY = aY - (tangentPointX - aX);
        addArcABSomeCenter(aX, aY, bX, bY, someCenterX, someCenterY, counterclockwise);
    }

    /**
     * Make points on an arc from point a to point b.
     * The tangentPoint defines a tangent to the arc going out from point a.
     * Use for joining a straight line to the arc.
     *
     * @param a                Vector2, point a
     * @param b                Vector2, point b
     * @param tangentPoint     Vector2, tangent point
     * @param counterclockwise boolean, true for going counterclockwise
     */
    public void addArcABTangent(Vector2 a, Vector2 b, Vector2 tangentPoint, boolean counterclockwise) {
        addArcABTangent(a.x, a.y, b.x, b.y, tangentPoint.x, tangentPoint.y, counterclockwise);
    }

    /**
     * Make points on an arc going from point a to point b to point c.
     *
     * @param aX float, x-coordinate of point a
     * @param aY float, y-coordinate of point a
     * @param bX float, x-coordinate of point b
     * @param bY float, y-coordinate of point b
     * @param cX float, x-coordinate of point c
     * @param cY float, y-coordinate of point c
     */
    public void addArcABC(float aX, float aY, float bX, float bY, float cX, float cY) {
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
        basicArc(centerX, centerY, radius, alpha, gamma, counterClockwise);
    }

    /**
     * Make points on an arc going from point a to point b to point c.
     *
     * @param a Vector2, point a
     * @param b Vector2, point b
     * @param c Vector2, point c
     */
    public void addArcABC(Vector2 a, Vector2 b, Vector2 c) {
        addArcABC(a.x, a.y, b.x, b.y, c.x, c.y);
    }

}
