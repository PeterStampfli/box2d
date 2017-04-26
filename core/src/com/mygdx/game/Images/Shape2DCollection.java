package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.utilities.Basic;

/**
 * Collection of Shape2D shapes. Make Circle and rectangles to draw connected points.
 */

public class Shape2DCollection extends Shape2DAdapter {
    final private static float epsilon = 0.01f;
    public Array<Shape2D> shapes2D = new Array<Shape2D>();

    /**
     * check if one of the shapes contains a given point
     *
     * @param x float, x-coordinate of the point
     * @param y float, y-coordinate of the point
     * @return boolean, true if a shape contains the point
     */
    @Override
    public boolean contains(float x, float y) {
        for (Shape2D shape2D : shapes2D) {
            if (shape2D.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Add one or several shapes to the collection. Does nothing if a shape is null.
     *
     * @param shapes2D Shape2D... or Shape2D[], the shapes to add.
     * @return this, for chaining
     */
    public Shape2DCollection add(Shape2D... shapes2D) {
        for (Shape2D shape2D : shapes2D) {
            if (shape2D != null) {
                this.shapes2D.add(shape2D);
            }
        }
        return this;
    }

    /**
     * Add a shape2D polygon to the collection.
     * Vertices are given by a float[] array.
     *
     * @param verticesXY float[] of (x,y) coordinates of vertices
     * @return this, for chaining
     */
    public Shape2DCollection addPolygon(float... verticesXY) {
        add(new Polygon(verticesXY));
        return this;
    }

    /**
     * Add a shape2D polygon to the collection. Vertices given by an Array<Vector2>.
     *
     * @param vertices Array<Vector2> of vertices
     * @return this, for chaining
     */
    public Shape2DCollection addPolygon(Array<Vector2> vertices) {
        addPolygon(Basic.toFloats(vertices));
        return this;
    }

    /**
     * Add a shape2D polygon to the collection based on the vertices of a polypoint object.
     *
     * @param polypoint Polypoint object with the vertices for the polygon.
     * @return this, for chaining
     */
    public Shape2DCollection addPolygon(Polypoint polypoint) {
        addPolygon(polypoint.coordinates.toArray());
        return this;
    }

    /**
     * Add a circle shape to the collection.
     *
     * @param x      float, x-coordinate of the center
     * @param y      float, y-coordinate of the center
     * @param radius float, radius of the circle
     * @return this, for chaining
     */
    public Shape2DCollection addCircle(float x, float y, float radius) {
        add(new Circle(x, y, radius));
        return this;
    }

    /**
     * Add a circle shape to the collection.
     *
     * @param position Vector2, center of the circle
     * @return this, for chaining
     */
    public Shape2DCollection addCircle(Vector2 position, float radius) {
        add(new Circle(position.x, position.y, radius));
        return this;
    }

    /**
     * Add a rectangle to the collection. (x,y) is the lower left corner.
     *
     * @param x      float, left edge of the rectangle
     * @param y      float, lower edge of the rectangle
     * @param width  float, width of the rectangle
     * @param height float, height of the rectangle
     * @return this, for chaining
     */
    public Shape2DCollection addRectangle(float x, float y, float width, float height) {
        add(new Rectangle(x, y, width, height));
        return this;
    }

    /**
     * Add a rectangular polygon that makes a line between two points with a given thickness with
     * sharp cutoff at end points.
     *
     * @param thickness float, thickness of the line
     * @param x1 float, x-coordinate of first point
     * @param y1 float, y-coordinate of first point
     * @param x2 float, x-coordinate of second point
     * @param y2 float, y-coordinate of second point
     */
    public Shape2DCollection addLine(float thickness, float x1, float y1, float x2, float y2) {
        float halfWidth = 0.5f * thickness;
        float length = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        if (length > epsilon) {
            float ex = (x2 - x1) / length * halfWidth;
            float ey = (y2 - y1) / length * halfWidth;
            float[] coordinates = {x1 + ey, y1 - ex, x2 + ey, y2 - ex, x2 - ey, y2 + ex, x1 - ey, y1 + ex};
            addPolygon(coordinates);
        }
        return this;
    }

    /**
     * Add a rectangular polygon that makes a line between two points with a given thickness with
     * sharp cutoff at end points.
     *
     * @param thickness float, thickness of the line
     * @param a Vector2, first endpoint
     * @param b Vector2, second endpoint
     */
    public Shape2DCollection addLine(float thickness, Vector2 a, Vector2 b) {
        return addLine(thickness, a.x, a.y, b.x, b.y);
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * joins first and last point if it is a loop
     *
     * @param thickness
     * @param isLoop
     * @param coordinates
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, boolean isLoop, float... coordinates) {
        float radius = 0.5f * thickness;
        int length = coordinates.length;
        for (int i = 0; i < length - 1; i += 2) {
            addCircle(coordinates[i], coordinates[i + 1], radius);
        }
        for (int i = 0; i < length - 3; i += 2) {
            addLine(thickness, coordinates[i], coordinates[i + 1],
                    coordinates[i + 2], coordinates[i + 3]);
        }
        if (isLoop) {
            addLine(thickness, coordinates[0], coordinates[1],
                    coordinates[length - 2], coordinates[length - 1]);
        }
        return this;
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * no loop
     *
     * @param thickness
     * @param coordinates
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, float... coordinates) {
        addDotsAndLines(thickness, false, coordinates);
        return this;
    }

    /**
     * add lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * loop is closed if polypoint isLoop=true
     *
     * @param thickness
     * @param polypoint
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, Polypoint polypoint) {
        addDotsAndLines(thickness, polypoint.isLoop, polypoint.coordinates.toArray());
        return this;
    }

    /**
     * make lines of given thickness between world points of a polygon
     * may be rotated translated and scaled (around origin)
     * line joints and ends are circles
     * endpoints are joined as for any real polygon
     *
     * @param thickness
     * @param polygon
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, Polygon polygon) {
        addDotsAndLines(thickness, true, polygon.getTransformedVertices());
        return this;
    }

    /**
     * make lines of given thickness between world points of a polyline
     * may be rotated translated and scaled (around origin)
     * line joints and ends are circles
     * endpoints are not joined
     *
     * @param thickness
     * @param polyline
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, Polyline polyline) {
        addDotsAndLines(thickness, false, polyline.getTransformedVertices());
        return this;
    }

    /**
     * make lines of given thickness between points of a chain
     * line joints and ends are circles
     * ghosts are not included (use this to make a chain substantial
     *
     * @param thickness
     * @param chain
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, Chain chain) {
        addDotsAndLines(thickness, chain.isLoop, chain.coordinates);
        return this;
    }

    /**
     * make lines of given thickness between  points of an edge
     * line joints and ends are circles
     * ghosts are not included (use this to make an edge substantial
     *
     * @param thickness
     * @param edge
     * @param
     * @return
     */
    public Shape2DCollection addDotsAndLines(float thickness, Edge edge) {
        addDotsAndLines(thickness, false, edge.aX, edge.aY, edge.bX, edge.bY);
        return this;
    }


}
