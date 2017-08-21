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
    static public float maxXShape2DCollection(Shape2DCollection collection){
        float x=-10000000;
        for (Shape2D subShape:collection.items){
            x=Math.max(x,maxXShape2D(subShape));
        }
        return x;
    }

    /**
     *  determine maximum of x-coordinate for a shape2D
     *
     * @param shape
     * @return
     */
    static public float maxXShape2D(Shape2D shape){

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
            return maxXShape2DCollection((Shape2DCollection) shape);
        }
        else {
            Gdx.app.log(" ******************** maxX","unknown shape "+shape.getClass());
        }
        return 0;
    }


}