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
        float length=(float) Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1)+0.1f);
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

    /**
     * make lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     * choins first and last point if it is a loop
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
            shapes.addLine(thickness,coordinates[i],coordinates[i+1],
                            coordinates[i+2],coordinates[i+3]);
        }
        if (isLoop){
            shapes.addLine(thickness,coordinates[0],coordinates[1],
                           coordinates[length-2],coordinates[length-1]);
        }
        return shapes;
    }


    /**
     * make lines of given thickness between points as defined by coordinates
     * line joints and ends are circles
     *
     * @param thickness
     * @param polypoint
     * @param
     * @return
     */
   // static public Shape2DCollection dotsAndLines(float thickness,Polypoint polypoint){

   // }


    }
