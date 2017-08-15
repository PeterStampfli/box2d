package com.mygdx.game.utilities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Polypoint;

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

    /**
     * append a polypoint:
     * first int number of coordinates, then float[] coordinates, then boolean isLop
     * @param fileHandle
     * @param polypoint
     */
    static public void appendPolypoint(FileHandle fileHandle, Polypoint polypoint){
        WriteData.appendInt(fileHandle,polypoint.coordinates.size);
        WriteData.appendFloats(fileHandle,polypoint.coordinates);
        WriteData.appendBoolean(fileHandle,polypoint.isLoop);
    }

    /**
     * appending a ghost depending if it exists
     *
     * @param fileHandle
     * @param exists
     * @param x
     * @param y
     */
    static private void appendGhost(FileHandle fileHandle, boolean exists, float x, float y){
        WriteData.appendBoolean(fileHandle,exists);
        if (exists){
            WriteData.appendFloats(fileHandle,x,y);
        }
    }

    /**
     * append an edge object.
     * First coordinates of points a and b
     * following byte is 1 if ghostA exists followed by ghostA coordinates, else byte is 0
     * following byte is 1 if ghostB exists followed by ghostB coordinates, else byte is 0
     *
     * @param fileHandle
     * @param edge
     * @return
     */
    static public void  appendEdge(FileHandle fileHandle,Edge edge){
        WriteData.appendFloats(fileHandle,edge.aX,edge.aY,edge.bX,edge.bY);
        appendGhost(fileHandle,edge.ghostAExists,edge.ghostAX,edge.ghostAY);
        appendGhost(fileHandle,edge.ghostBExists,edge.ghostBX,edge.ghostBY);
    }

    /**
     * append a chain object.
     * First int length and coordinates
     * then boolean isLoop
     * following byte is 1 if ghostA exists followed by ghostA coordinates, else byte is 0
     * following byte is 1 if ghostB exists followed by ghostB coordinates, else byte is 0
     *
     * @param fileHandle
     * @param chain
     * @return
     */
    static public void  appendChain(FileHandle fileHandle,Chain chain){
        WriteData.appendInt(fileHandle,chain.coordinates.length);
        WriteData.appendFloats(fileHandle,chain.coordinates);
        WriteData.appendBoolean(fileHandle,chain.isLoop);
        appendGhost(fileHandle,chain.ghostAExists,chain.ghostAX,chain.ghostAY);
        appendGhost(fileHandle,chain.ghostBExists,chain.ghostBX,chain.ghostBY);
    }

}
