package com.mygdx.game.Images;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by peter on 4/10/17.
 * static methods for creating shape2D polygon/polyline shapes from polypoint
 *
 * chain has its own creator using polypoint input
 */

public class Shape2DCreator {
    final private static float epsilon=0.01f;

    /**
     * create a polygon with the points of a Polypoint as vertices
     * @return
     */
    static public Polygon polygon(Polypoint polypoint){
        return new Polygon(polypoint.coordinates.toArray());
    }

    /**
     * create a polyline with the points of a Polypoint as vertices
     * @return
     */
    static public Polyline polyline(Polypoint polypoint){
        return new Polyline(polypoint.coordinates.toArray());
    }

    /**
     * make a polygon that is a line between two points with a given width/thickness
     * sharp cutoff at end points
     * returns null if the distance between the points is too short, catch this
     * @param thickness
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    static public Polygon line(float thickness, float x1, float y1, float x2, float y2){
        float halfWidth=0.5f*thickness;
        float length=(float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        if (length>epsilon) {
            float ex = (x2 - x1) / length * halfWidth;
            float ey = (y2 - y1) / length * halfWidth;
            float[] coordinates = {x1 + ey, y1 - ex, x2 + ey, y2 - ex, x2 - ey, y2 + ex, x1 - ey, y1 + ex};
            return new Polygon(coordinates);
        }
        return null;
    }

    /**
     * make a polygon that is a line between two points with a given width/thickness
     * sharp cutoff at end points
     * @param thickness
     * @param a
     * @param b
     */
    static public Polygon line( float thickness,Vector2 a, Vector2 b){
        return line(thickness,a.x,a.y,b.x,b.y);
    }

    /**
     * make lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * joins first and last point if it is a loop
     * @param thickness
     * @param isLoop
     * @param coordinates
     * @return
     */
    static public Shape2DCollection dotsAndLines(float thickness,boolean isLoop, float... coordinates){
        Shape2DCollection shapes=new Shape2DCollection();
        float radius=0.5f*thickness;
        int length=coordinates.length;
        for (int i=0;i<length-1;i+=2){
            shapes.addCircle(coordinates[i],coordinates[i+1],radius);
        }
        for (int i=0;i<length-3;i+=2){
            shapes.add(line(thickness,coordinates[i],coordinates[i+1],
                            coordinates[i+2],coordinates[i+3]));
        }
        if (isLoop){
            shapes.add(line(thickness,coordinates[0],coordinates[1],
                           coordinates[length-2],coordinates[length-1]));
        }
        return shapes;
    }

    /**
     * make lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * no loop
     * @param thickness
     * @param coordinates
     * @return
     */
    static public Shape2DCollection dotsAndLines(float thickness, float... coordinates){
        return dotsAndLines(thickness,false,coordinates);
    }

    /**
     * make lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     *
     * @param thickness
     * @param polypoint
     * @return
     */
    static public Shape2DCollection dotsAndLines(float thickness,Polypoint polypoint){
        return dotsAndLines(thickness,polypoint.isLoop,polypoint.coordinates.toArray());
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
    static public Shape2DCollection dotsAndLines(float thickness,Polygon polygon){
        return dotsAndLines(thickness,true,polygon.getTransformedVertices());
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
    static public Shape2DCollection dotsAndLines(float thickness,Polyline polyline){
        return dotsAndLines(thickness,false,polyline.getTransformedVertices());
    }

    /**
     * make lines of given thickness between points of a chain
     * line joints and ends are circles
     * ghosts are not included (use this to make a chain substantial
     * @param thickness
     * @param chain
     * @return
     */
    static public Shape2DCollection dotsAndLines(float thickness,Chain chain){
        return dotsAndLines(thickness,chain.isLoop,chain.coordinates);
    }

    /**
     * make lines of given thickness between  points of an edge
     * line joints and ends are circles
     * ghosts are not included (use this to make an edge substantial
     * @param thickness
     * @param edge
     * @param
     * @return
     */
    static public Shape2DCollection dotsAndLines(float thickness,Edge edge){
        return dotsAndLines(thickness,edge.aX,edge.aY,edge.bX,edge.bY);
    }


}
