package com.mygdx.game.Images;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.utilities.WriteData;

/**
 * Write data of shapes and shape collections on a file
 */

public class WriteShape {

    /**
     * append data of a Circle object
     *
     * @param fileHandle FileHandle
     * @param circle Circle
     */
    static public void appendCircle(FileHandle fileHandle,Circle circle){
        WriteData.appendFloats(fileHandle,circle.x,circle.y,circle.radius);
    }

    /**
     * append data of a Rectangle object
     *
     * @param fileHandle FileHandle
     * @param rectangle Rectangle
     */
    static public void appendRectangle(FileHandle fileHandle,Rectangle rectangle){
        WriteData.appendFloats(fileHandle,rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    /**
     * append data of a polygon object
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param fileHandle FileHandle
     * @param polygon Polygon
     */
    static public void appendPolygon(FileHandle fileHandle,Polygon polygon){
        float[] vertices=polygon.getVertices();
        WriteData.appendInt(fileHandle,vertices.length);
        WriteData.appendFloats(fileHandle,vertices);
    }

    /**
     * append a polyline
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param polyline Polyline
     */
    static public void appendPolyline(FileHandle fileHandle,Polyline polyline){
        float[] vertices=polyline.getVertices();
        WriteData.appendInt(fileHandle,vertices.length);
        WriteData.appendFloats(fileHandle,vertices);
    }

    /**
     * append a polypoint:
     * first int number of coordinates, then float[] coordinates, then boolean isLop
     * @param fileHandle FileHandle
     * @param polypoint Polypoint
     */
    static public void appendPolypoint(FileHandle fileHandle, Polypoint polypoint){
        WriteData.appendInt(fileHandle,polypoint.coordinates.size);
        WriteData.appendFloats(fileHandle,polypoint.coordinates);
        WriteData.appendBoolean(fileHandle,polypoint.isLoop);
    }

    /**
     * appending a ghost depending if it exists
     *
     * @param fileHandle FileHandle
     * @param exists boolean, true if ghost exists
     * @param x float, x-coordinate of ghost
     * @param y float, y-coordinate
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
     * @param fileHandle FileHandle
     * @param edge Edge
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
     * @param fileHandle FileHandle
     * @param chain Chain
     */
    static public void  appendChain(FileHandle fileHandle,Chain chain){
        WriteData.appendInt(fileHandle,chain.coordinates.length);
        WriteData.appendFloats(fileHandle,chain.coordinates);
        WriteData.appendBoolean(fileHandle,chain.isLoop);
        appendGhost(fileHandle,chain.ghostAExists,chain.ghostAX,chain.ghostAY);
        appendGhost(fileHandle,chain.ghostBExists,chain.ghostBX,chain.ghostBY);
    }

    /**
     * append a shape2DCollection, first int number of sub-shapes, then the sub-shapes
     *
     * @param fileHandle FileHandle
     * @param collection Shape2DCollection
     */
    static public void appendShape2DCollection(FileHandle fileHandle,Shape2DCollection collection){
        WriteData.appendInt(fileHandle,collection.items.size);
        for (Shape2D subShape:collection.items){
            appendShape(fileHandle,subShape);
        }
    }

    /**
     * append a byte for the shape type to a file
     *
     * @param fileHandle FileHandle
     * @param type Shape2DType, will be transformed to byte and appended
     */
    static private void appendType(FileHandle fileHandle, Shape2DType type){
        WriteData.appendByte(fileHandle,type.toByte());
    }

    /**
     * append any Shape2D shape, preceded by its type, to a file, including collections.
     *
     * @param fileHandle FileHandle
     * @param shape Shape2D to append
     */
    static public void appendShape(FileHandle fileHandle,Shape2D shape){
        if (shape instanceof Polygon){
            appendType(fileHandle,Shape2DType.POLYGON);
            appendPolygon(fileHandle,(Polygon)shape);
        }
        else if (shape instanceof Circle){
            appendType(fileHandle,Shape2DType.CIRCLE);
            appendCircle(fileHandle,(Circle) shape);
        }
        else if (shape instanceof Rectangle){
            appendType(fileHandle,Shape2DType.RECTANGLE);
            appendRectangle(fileHandle,(Rectangle) shape);
        }
        else if (shape instanceof Polypoint){
            appendType(fileHandle,Shape2DType.POLYPOINT);
            appendPolypoint(fileHandle,(Polypoint) shape);
        }
        else if (shape instanceof Polyline){
            appendType(fileHandle,Shape2DType.POLYLINE);
            appendPolyline(fileHandle,(Polyline) shape);
        }
        else if (shape instanceof Edge){
            appendType(fileHandle,Shape2DType.EDGE);
            appendEdge(fileHandle,(Edge) shape);
        }
        else if (shape instanceof Chain){
            appendType(fileHandle,Shape2DType.CHAIN);
            appendChain(fileHandle,(Chain) shape);
        }
        else if (shape instanceof Shape2DCollection){                 // includes subclass DotsAndLines
            appendType(fileHandle,Shape2DType.COLLECTION);
            appendShape2DCollection(fileHandle,(Shape2DCollection) shape);
        }
        else {
            throw new RuntimeException("unknown shape "+shape.getClass());
        }
    }

}
