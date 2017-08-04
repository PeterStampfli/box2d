package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.FloatArray;
import com.mygdx.game.utilities.MathU;

/**
 * Shape2DCollection that joins points with lines and discs (collected Shape2D polygons and discs).
 * Takes the points as they are at the moment. Input can be polypoint, polyline, polygon and pairs of coordinates.
 * Do smoothing afterwards.
 */

public class DotsAndLines extends Shape2DCollection {
    float width = 10;

    /**
     * Create a rectangular polygon that makes a line between two points with a given width with
     * sharp cutoff at end points. Returns null if endpoints are too close to each other.
     *
     * @param width float, width of the line
     * @param x1    float, x-coordinate of first point
     * @param y1    float, y-coordinate of first point
     * @param x2    float, x-coordinate of second point
     * @param y2    float, y-coordinate of second point
     * @return Polygon
     */
    static public Polygon line(float width, float x1, float y1, float x2, float y2) {
        float halfWidth = 0.5f * width;
        float length = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        if (length > MathU.epsilon) {
            float ex = (x2 - x1) / length * halfWidth;
            float ey = (y2 - y1) / length * halfWidth;
            float[] coordinates = {x1 - ey, y1 + ex, x1 + ey, y1 - ex, x2 + ey, y2 - ex, x2 - ey, y2 + ex};
            return new Polygon(coordinates);
        }
        return null;
    }

    /**
     * Create a rectangular polygon that makes a line between two points with a given width with
     * sharp cutoff at end points. Returns null if endpoints are too close to each other.
     *
     * @param width float, width of the line
     * @param a     Vector2, first endpoint
     * @param b     Vector2, second endpoint
     * @return Polygon
     */
    static public Polygon line(float width, Vector2 a, Vector2 b) {
        return line(width, a.x, a.y, b.x, b.y);
    }

    /**
     * Make lines of given width between points. Join first and last point if it should be a loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param isLoop      boolean, true if we want a loop, joining first and last point
     * @param coordinates float... or float[] pairs of coordinates of points
     */
    public DotsAndLines add(boolean isLoop, float... coordinates) {
        float radius = 0.5f * width;
        int length = coordinates.length;
        for (int i = 0; i < length - 1; i += 2) {
            super.add(new Circle(coordinates[i], coordinates[i + 1], radius));
        }
        for (int i = 0; i < length - 3; i += 2) {
            super.add(line(width, coordinates[i], coordinates[i + 1],
                    coordinates[i + 2], coordinates[i + 3]));
        }
        if (isLoop) {
            super.add(line(width, coordinates[0], coordinates[1],
                    coordinates[length - 2], coordinates[length - 1]));
        }
        return this;
    }

    /**
     * Make lines of given width between points. No loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param coordinates float... or float[] pairs of coordinates of points
     */
    public DotsAndLines add(float... coordinates) {
        add(false, coordinates);
        return this;
    }

    /**
     * Make lines of given width between points defined by a Polypoint object.
     * Join first and last point if the Polypoint is a loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param polypoint Polypoint object that defines the points and if it is a loop
     * @return Shape2DCollection with the lines and discs
     */
    public DotsAndLines add(Polypoint polypoint) {
        FloatArray coordinates = polypoint.coordinates;
        float radius = 0.5f * width;
        int length = coordinates.size;
        for (int i = 0; i < length - 1; i += 2) {
            super.add(new Circle(coordinates.get(i), coordinates.get(i + 1), radius));
        }
        for (int i = 0; i < length - 3; i += 2) {
            super.add(line(width, coordinates.get(i), coordinates.get(i + 1),
                    coordinates.get(i + 2), coordinates.get(i + 3)));
        }
        if (polypoint.isLoop) {
            super.add(line(width, coordinates.get(0), coordinates.get(1),
                    coordinates.get(length - 2), coordinates.get(length - 1)));
        }
        return this;
    }

    /**
     * Make lines of given width between between world points of a polygon.
     * They may be rotated, translated and scaled.
     * Join first and last point because it is a Polygon.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param polygon Polygon
     */
    public DotsAndLines add(Polygon polygon) {
        add(true, polygon.getTransformedVertices());
        return this;
    }

    /**
     * Make lines of given width between between world points of a Polyline.
     * They may be rotated, translated and scaled.
     * First and last point are not joined, because it is a Polyline.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param polyline Polyline
     */
    public DotsAndLines add(Polyline polyline) {
        add(false, polyline.getTransformedVertices());
        return this;
    }

    /**
     * Make lines of given width between the points of a Chain.
     * Ignores ghosts. Joins first and last point if Chain is a loop.
     * Draw discs of same diameter at the points to make line joints and ends.
     *
     * @param chain Chain
     */
    public DotsAndLines add(Chain chain) {
        add(chain.isLoop, chain.coordinates);
        return this;
    }

    // static methods for use in other places ??

    /**
     * Make lines of given width between the points of an Edge.
     * Ignores ghosts.
     * Draw discs of same diameter at the points to make line joints and ends.
     *
     * @param edge Edge
     */
    public DotsAndLines add(Edge edge) {
        add(false, edge.aX, edge.aY, edge.bX, edge.bY);
        return this;
    }

    /** add elements of a dotsAndLines object
     *
     * @param dotsAndLines
     * @return
     */
    public DotsAndLines add(DotsAndLines dotsAndLines){
        super.add(dotsAndLines.shapes2D);
        return this;
    }

    /**
     * Add shape2D shapes as dotsAndLines.
     * Includes collections, polyPoint, polyLine, polygon,chain and edge.
     *
     * @param shape Shape2D to add transformed
     */
    public void add(Shape2D shape){
        if (shape instanceof Polygon){
            add((Polygon)shape);
        }
        else if (shape instanceof Polypoint){
            add((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            add((Polyline) shape);
        }
        else if (shape instanceof Edge){
            add((Edge) shape);
        }
        else if (shape instanceof Chain){
            add((Chain) shape);
        }
        else if (shape instanceof DotsAndLines){
            add((DotsAndLines) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            Shape2DCollection shapes=(Shape2DCollection) shape;
            for (Shape2D subShape:shapes.shapes2D){
                add(subShape);
            }
        }
    }

    /**
     * Set the width of lines and diameter of dots.
     *
     * @param width
     * @return
     */
    public DotsAndLines setLineWidth(float width) {
        this.width = width;
        return this;
    }
}
