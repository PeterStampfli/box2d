package com.mygdx.game.utilities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by peter on 8/15/17.
 */

public class WriteShape {

    /**
     * append data of a Circle object
     *
     * @param fileHandle
     * @param circle
     * @return
     */
    static public void appendCircle(FileHandle fileHandle,Circle circle){
        WriteData.appendFloats(fileHandle,circle.x,circle.y,circle.radius);
    }

    /**
     * append data of a Rectangle object
     *
     * @param fileHandle
     * @param rectangle
     * @return
     */
    static public void appendRectangle(FileHandle fileHandle,Rectangle rectangle){
        WriteData.appendFloats(fileHandle,rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    /**
     * append data of a polygon object
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param fileHandle
     * @param polygon
     */
    static public void appendPolygon(FileHandle fileHandle,Polygon polygon){
        float[] vertices=polygon.getVertices();
        WriteData.appendInt(fileHandle,vertices.length);
        WriteData.appendFloats(fileHandle,vertices);
    }

    /**
     * make a byteBuffer with data of a polyline object
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param polyline
     * @return
     */
    static public void appendPolyline(FileHandle fileHandle,Polyline polyline){
        float[] vertices=polyline.getVertices();
        WriteData.appendInt(fileHandle,vertices.length);
        WriteData.appendFloats(fileHandle,vertices);
    }


}
