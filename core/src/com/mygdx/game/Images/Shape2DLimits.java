package com.mygdx.game.Images;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.FloatArray;

/**
 * Created by peter on 8/21/17.
 */

public class Shape2DLimits {

    /**
     * calculate maximum x-coordinate of an array of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float maxXCoordinates(float[] coordinates){
        float x=-1000000;
        for (int i=coordinates.length-2;i>=0;i-=2){
            x=Math.max(x,coordinates[i]);
        }
        return x;
    }

    /**
     * calculate maximum x-coordinate of a FloatArray of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float maxXCoordinates(FloatArray coordinates){
        float x=-1000000;
        for (int i=coordinates.size-2;i>=0;i-=2){
            x=Math.max(x,coordinates.get(i));
        }
        return x;
    }

    /**
     * calculate maximum x-coordinate of a circle
     *
     * @param circle
     */
    static public float maxXCircle(Circle circle) {
        return circle.x+circle.radius;
    }

    /**
     * calculate the maximum x-coordinate of a rectangle
     * @param rectangle
     * @return
     */
    static public float maxXRectangle(Rectangle rectangle){
        return rectangle.x+rectangle.width;
    }

    /**
     * calculate the maximum x-coordinate of a chain, without ghosts
     *
     * @param chain
     * @return
     */
    static public float maxXChain(Chain chain){
        return maxXCoordinates(chain.coordinates);
    }

    /**
     * calculate the maximum x-coordinate of an edge, without ghosts
     *
     * @param edge
     * @return
     */
    static public float maxXEdge(Edge edge){
        return Math.max(edge.aX,edge.bX);
    }

    /**
     * calculate the maximum x-coordinate of a polygon, local vertices
     *
     * @param polygon
     * @return
     */
    static public float maxXPolygon(Polygon polygon){
        return maxXCoordinates(polygon.getVertices());
    }

    /**
     * calculate the maximum x-coordinate of a polygon, local vertices
     *
     * @param polyline
     * @return
     */
    static public float maxXPolyline(Polyline polyline){
        return maxXCoordinates(polyline.getVertices());
    }

    /**
     * calculate the maximum x-coordinate of a polypoint, local vertices
     *
     * @param polypoint
     * @return
     */
    static public float maxXPolypoint(Polypoint polypoint){
        return maxXCoordinates(polypoint.coordinates);
    }

    /**
     * determine maximum x-coordinate for a shape2DCollection (including dots and lines)
     *
     * @param collection
     */
    static public float maxXCollection(Shape2DCollection collection){
        float x=-10000000;
        for (Shape2D subShape:collection.items){
            x=Math.max(x, maxXShape(subShape));
        }
        return x;
    }

    /**
     *  determine maximum of x-coordinate for a shape2D
     *
     * @param shape
     * @return
     */
    static public float maxXShape(Shape2D shape){

        if (shape==null){}
        else if (shape instanceof Polygon){
            return maxXPolygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            return maxXCircle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            return maxXRectangle((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            return maxXPolypoint((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            return maxXPolyline((Polyline) shape);
        }
        else if (shape instanceof Edge){
            return maxXEdge((Edge) shape);
        }
        else if (shape instanceof Chain){
            return maxXChain((Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            return maxXCollection((Shape2DCollection) shape);
        }
        else {
            Gdx.app.log(" ******************** maxX","unknown shape "+shape.getClass());
        }
        return 0;
    }


    /**
     * calculate maximum y-coordinate of an array of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float maxYCoordinates(float[] coordinates){
        float x=-1000000;
        for (int i=coordinates.length-1;i>=0;i-=2){
            x=Math.max(x,coordinates[i]);
        }
        return x;
    }

    /**
     * calculate maximum y-coordinate of a FloatArray of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float maxYCoordinates(FloatArray coordinates){
        float x=-1000000;
        for (int i=coordinates.size-1;i>=0;i-=2){
            x=Math.max(x,coordinates.get(i));
        }
        return x;
    }

    /**
     * calculate maximum y-coordinate of a circle
     *
     * @param circle
     */
    static public float maxYCircle(Circle circle) {
        return circle.y+circle.radius;
    }

    /**
     * calculate the maximum y-coordinate of a rectangle
     * @param rectangle
     * @return
     */
    static public float maxYRectangle(Rectangle rectangle){
        return rectangle.y+rectangle.height;
    }

    /**
     * calculate the maximum y-coordinate of a chain, without ghosts
     *
     * @param chain
     * @return
     */
    static public float maxYChain(Chain chain){
        return maxYCoordinates(chain.coordinates);
    }

    /**
     * calculate the maximum y-coordinate of an edge, without ghosts
     *
     * @param edge
     * @return
     */
    static public float maxYEdge(Edge edge){
        return Math.max(edge.aY,edge.bY);
    }

    /**
     * calculate the maximum y-coordinate of a polygon, local vertices
     *
     * @param polygon
     * @return
     */
    static public float maxYPolygon(Polygon polygon){
        return maxYCoordinates(polygon.getVertices());
    }

    /**
     * calculate the maximum y-coordinate of a polygon, local vertices
     *
     * @param polyline
     * @return
     */
    static public float maxYPolyline(Polyline polyline){
        return maxYCoordinates(polyline.getVertices());
    }

    /**
     * calculate the maximum y-coordinate of a polypoint, local vertices
     *
     * @param polypoint
     * @return
     */
    static public float maxYPolypoint(Polypoint polypoint){
        return maxYCoordinates(polypoint.coordinates);
    }

    /**
     * determine maximum y-coordinate for a shape2DCollection (including dots and lines)
     *
     * @param collection
     */
    static public float maxYCollection(Shape2DCollection collection){
        float x=-10000000;
        for (Shape2D subShape:collection.items){
            x=Math.max(x, maxYShape(subShape));
        }
        return x;
    }

    /**
     *  determine maximum of y-coordinate for a shape2D
     *
     * @param shape
     * @return
     */
    static public float maxYShape(Shape2D shape){

        if (shape==null){}
        else if (shape instanceof Polygon){
            return maxYPolygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            return maxYCircle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            return maxYRectangle((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            return maxYPolypoint((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            return maxYPolyline((Polyline) shape);
        }
        else if (shape instanceof Edge){
            return maxYEdge((Edge) shape);
        }
        else if (shape instanceof Chain){
            return maxYChain((Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            return maxYCollection((Shape2DCollection) shape);
        }
        else {
            Gdx.app.log(" ******************** maxY","unknown shape "+shape.getClass());
        }
        return 0;
    }


    /**
     * calculate minimum x-coordinate of an array of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float minXCoordinates(float[] coordinates){
        float x=1000000;
        for (int i=coordinates.length-2;i>=0;i-=2){
            x=Math.min(x,coordinates[i]);
        }
        return x;
    }

    /**
     * calculate minimum x-coordinate of a FloatArray of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float minXCoordinates(FloatArray coordinates){
        float x=1000000;
        for (int i=coordinates.size-2;i>=0;i-=2){
            x=Math.min(x,coordinates.get(i));
        }
        return x;
    }

    /**
     * calculate minimum x-coordinate of a circle
     *
     * @param circle
     */
    static public float minXCircle(Circle circle) {
        return circle.x-circle.radius;
    }

    /**
     * calculate the minimum x-coordinate of a rectangle
     * @param rectangle
     * @return
     */
    static public float minXRectangle(Rectangle rectangle){
        return rectangle.x-rectangle.width;
    }

    /**
     * calculate the minimum x-coordinate of a chain, without ghosts
     *
     * @param chain
     * @return
     */
    static public float minXChain(Chain chain){
        return minXCoordinates(chain.coordinates);
    }

    /**
     * calculate the minimum x-coordinate of an edge, without ghosts
     *
     * @param edge
     * @return
     */
    static public float minXEdge(Edge edge){
        return Math.min(edge.aX,edge.bX);
    }

    /**
     * calculate the minimum x-coordinate of a polygon, local vertices
     *
     * @param polygon
     * @return
     */
    static public float minXPolygon(Polygon polygon){
        return minXCoordinates(polygon.getVertices());
    }

    /**
     * calculate the minimum x-coordinate of a polygon, local vertices
     *
     * @param polyline
     * @return
     */
    static public float minXPolyline(Polyline polyline){
        return minXCoordinates(polyline.getVertices());
    }

    /**
     * calculate the minimum x-coordinate of a polypoint, local vertices
     *
     * @param polypoint
     * @return
     */
    static public float minXPolypoint(Polypoint polypoint){
        return minXCoordinates(polypoint.coordinates);
    }

    /**
     * determine minimum x-coordinate for a shape2DCollection (including dots and lines)
     *
     * @param collection
     */
    static public float minXCollection(Shape2DCollection collection){
        float x=10000000;
        for (Shape2D subShape:collection.items){
            x=Math.min(x, minXShape(subShape));
        }
        return x;
    }

    /**
     *  determine minimum of x-coordinate for a shape2D
     *
     * @param shape
     * @return
     */
    static public float minXShape(Shape2D shape){

        if (shape==null){}
        else if (shape instanceof Polygon){
            return minXPolygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            return minXCircle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            return minXRectangle((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            return minXPolypoint((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            return minXPolyline((Polyline) shape);
        }
        else if (shape instanceof Edge){
            return minXEdge((Edge) shape);
        }
        else if (shape instanceof Chain){
            return minXChain((Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            return minXCollection((Shape2DCollection) shape);
        }
        else {
            Gdx.app.log(" ******************** minX","unknown shape "+shape.getClass());
        }
        return 0;
    }


    /**
     * calculate minimum y-coordinate of an array of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float minYCoordinates(float[] coordinates){
        float x=1000000;
        for (int i=coordinates.length-1;i>=0;i-=2){
            x=Math.min(x,coordinates[i]);
        }
        return x;
    }

    /**
     * calculate minimum y-coordinate of a FloatArray of coordinates
     *
     * @param coordinates
     * @return
     */
    static public float minYCoordinates(FloatArray coordinates){
        float x=1000000;
        for (int i=coordinates.size-1;i>=0;i-=2){
            x=Math.min(x,coordinates.get(i));
        }
        return x;
    }

    /**
     * calculate minimum y-coordinate of a circle
     *
     * @param circle
     */
    static public float minYCircle(Circle circle) {
        return circle.y-circle.radius;
    }

    /**
     * calculate the minimum y-coordinate of a rectangle
     * @param rectangle
     * @return
     */
    static public float minYRectangle(Rectangle rectangle){
        return rectangle.y-rectangle.height;
    }

    /**
     * calculate the minimum y-coordinate of a chain, without ghosts
     *
     * @param chain
     * @return
     */
    static public float minYChain(Chain chain){
        return minYCoordinates(chain.coordinates);
    }

    /**
     * calculate the minimum y-coordinate of an edge, without ghosts
     *
     * @param edge
     * @return
     */
    static public float minYEdge(Edge edge){
        return Math.min(edge.aY,edge.bY);
    }

    /**
     * calculate the minimum y-coordinate of a polygon, local vertices
     *
     * @param polygon
     * @return
     */
    static public float minYPolygon(Polygon polygon){
        return minYCoordinates(polygon.getVertices());
    }

    /**
     * calculate the minimum y-coordinate of a polygon, local vertices
     *
     * @param polyline
     * @return
     */
    static public float minYPolyline(Polyline polyline){
        return minYCoordinates(polyline.getVertices());
    }

    /**
     * calculate the minimum y-coordinate of a polypoint, local vertices
     *
     * @param polypoint
     * @return
     */
    static public float minYPolypoint(Polypoint polypoint){
        return minYCoordinates(polypoint.coordinates);
    }

    /**
     * determine minimum y-coordinate for a shape2DCollection (including dots and lines)
     *
     * @param collection
     */
    static public float minYCollection(Shape2DCollection collection){
        float x=10000000;
        for (Shape2D subShape:collection.items){
            x=Math.min(x, minYShape(subShape));
        }
        return x;
    }

    /**
     *  determine minimum of y-coordinate for a shape2D
     *
     * @param shape
     * @return
     */
    static public float minYShape(Shape2D shape){

        if (shape==null){}
        else if (shape instanceof Polygon){
            return minYPolygon((Polygon)shape);
        }
        else if (shape instanceof Circle){
            return minYCircle((Circle) shape);
        }
        else if (shape instanceof Rectangle){
            return minYRectangle((Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            return minYPolypoint((Polypoint)shape);
        }
        else if (shape instanceof Polyline){
            return minYPolyline((Polyline) shape);
        }
        else if (shape instanceof Edge){
            return minYEdge((Edge) shape);
        }
        else if (shape instanceof Chain){
            return minYChain((Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            return minYCollection((Shape2DCollection) shape);
        }
        else {
            Gdx.app.log(" ******************** minY","unknown shape "+shape.getClass());
        }
        return 0;
    }


}