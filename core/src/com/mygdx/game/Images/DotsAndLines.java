package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

/**
 * Create Shape2D shapes
 */

public class DotsAndLines {

    /**
     * Create a shape2D polygon. Vertices given by an Array<Vector2>.
     *
     * @param vertices Array<Vector2> of vertices
     * @return Polygon
     */
    static public Polygon polygon(Array<Vector2> vertices) {
        return new Polygon(Basic.toFloats(vertices));
    }

    /**
     * Create a shape2D polygon based on the vertices of a polypoint object.
     *
     * @param polypoint Polypoint object with the vertices for the polygon.
     * @return Polygon
     */
    static public Polygon polygon(Polypoint polypoint) {
        return new Polygon(polypoint.coordinates.toArray());
    }

    /**
     * Create a circle shape.
     *
     * @param position Vector2, center of the circle
     * @return Circle
     */
    static public Circle circle(Vector2 position, float radius) {
        return new Circle(position.x, position.y, radius);
    }

    /**
     * Create a rectangular polygon that makes a line between two points with a given thickness with
     * sharp cutoff at end points. Returns null if endpoints are too close to each other.
     *
     * @param thickness float, thickness of the line
     * @param x1        float, x-coordinate of first point
     * @param y1        float, y-coordinate of first point
     * @param x2        float, x-coordinate of second point
     * @param y2        float, y-coordinate of second point
     * @return Polygon
     */
    static public Polygon line(float thickness, float x1, float y1, float x2, float y2) {
        float epsilon = 0.01f;
        float halfWidth = 0.5f * thickness;
        float length = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        if (length > epsilon) {
            float ex = (x2 - x1) / length * halfWidth;
            float ey = (y2 - y1) / length * halfWidth;
            float[] coordinates = {x1 + ey, y1 - ex, x2 + ey, y2 - ex, x2 - ey, y2 + ex, x1 - ey, y1 + ex};
            return new Polygon(coordinates);
        }
        return null;
    }

    /**
     * Create a rectangular polygon that makes a line between two points with a given thickness with
     * sharp cutoff at end points. Returns null if endpoints are too close to each other.
     *
     * @param thickness float, thickness of the line
     * @param a         Vector2, first endpoint
     * @param b         Vector2, second endpoint
     * @return Polygon
     */
    static public Polygon line(float thickness, Vector2 a, Vector2 b) {
        return line(thickness, a.x, a.y, b.x, b.y);
    }

    /**
     * Make lines of given thickness between points. Join first and last point if it should be a loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness   float, thickness of the line and diameter of discs
     * @param isLoop      boolean, true if we want a loop, joining first and last point
     * @param coordinates float... or float[] pairs of coordinates of points
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, boolean isLoop, float... coordinates) {
        Shape2DCollection dotsAndLines=new Shape2DCollection();
        float radius = 0.5f * thickness;
        int length = coordinates.length;
        for (int i = 0; i < length - 1; i += 2) {
            dotsAndLines.add(new Circle(coordinates[i], coordinates[i + 1], radius));
        }
        for (int i = 0; i < length - 3; i += 2) {
            dotsAndLines.add(line(thickness, coordinates[i], coordinates[i + 1],
                                                  coordinates[i + 2], coordinates[i + 3]));
        }
        if (isLoop) {
            dotsAndLines.add(line(thickness, coordinates[0], coordinates[1],
                                  coordinates[length - 2], coordinates[length - 1]));
        }
        return dotsAndLines;
    }

    /**
     * Make lines of given thickness between points. No loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness   float, thickness of the line and diameter of discs
     * @param coordinates float... or float[] pairs of coordinates of points
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, float... coordinates) {
        return dotsAndLines(thickness, false, coordinates);
    }

    /**
     * Make lines of given thickness between points defined by a Polypoint object.
     * Join first and last point if the Polypoint is a loop.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness float, thickness of the line and diameter of discs
     * @param polypoint Polypoint object that defines the points and if it is a loop
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, Polypoint polypoint) {
        return dotsAndLines(thickness, polypoint.isLoop, polypoint.coordinates.toArray());
    }

    /**
     * Make lines of given thickness between between world points of a polygon.
     * They may be rotated, translated and scaled.
     * Join first and last point because it is a Polygon.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness float, thickness of the line and diameter of discs
     * @param polygon   Polygon
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, Polygon polygon) {
        return dotsAndLines(thickness, true, polygon.getTransformedVertices());
    }

    /**
     * Make lines of given thickness between between world points of a Polyline.
     * They may be rotated, translated and scaled.
     * First and last point are not joined, because it is a Polyline.
     * Make discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness float, thickness of the line and diameter of discs
     * @param polyline  Polyline
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, Polyline polyline) {
        return dotsAndLines(thickness, false, polyline.getTransformedVertices());
    }

    /**
     * Make lines of given thickness between the points of a Chain.
     * Ignores ghosts. Joins first and last point if Chain is a loop.
     * Draw discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness float, thickness of the line and diameter of discs
     * @param chain     Chain
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection addDotsAndLines(float thickness, Chain chain) {
        return dotsAndLines(thickness, chain.isLoop, chain.coordinates);
    }

    /**
     * Make lines of given thickness between the points of an Edge.
     * Ignores ghosts.
     * Draw discs of same diameter at the points to make line joints and ends.
     *
     * @param thickness float, thickness of the line and diameter of discs
     * @param edge      Edge
     * @return Shape2DCollection with the lines and discs
     */
    static public Shape2DCollection dotsAndLines(float thickness, Edge edge) {
        return dotsAndLines(thickness, false, edge.aX, edge.aY, edge.bX, edge.bY);
    }
}
