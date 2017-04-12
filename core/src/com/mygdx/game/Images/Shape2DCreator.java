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
     * @param thickness
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    static public Polygon line(float thickness, float x1, float y1, float x2, float y2){
        float halfWidth=0.5f*thickness;
        float length=(float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
        float ex=(x2-x1)/length*halfWidth;
        float ey=(y2-y1)/length*halfWidth;
        float[] coordinates={x1+ey,y1-ex,x2+ey,y2-ex,x2-ey,y2+ex,x1-ey,y1+ex};
        return new Polygon(coordinates);
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
}
