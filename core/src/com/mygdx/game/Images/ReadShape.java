package com.mygdx.game.Images;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.utilities.ReadData;

import java.nio.ByteBuffer;

/**
 * Created by peter on 8/15/17.
 */

public class ReadShape {


    /**
     * create a new circle object with data read from byteBuffer
     *
     * @param buffer
     * @return
     */
    static public Circle circle(ByteBuffer buffer){
        return new Circle(buffer.getFloat(),buffer.getFloat(),buffer.getFloat());
    }

    /**
     * create a new Rectangle object from data on a byteBuffer
     *
     * @param buffer
     * @return
     */
    static public Rectangle rectangle(ByteBuffer buffer){
        return new Rectangle(buffer.getFloat(),buffer.getFloat(),buffer.getFloat(),buffer.getFloat());
    }

    /**
     * create a new polygon object from data on a byteBuffer
     * first int length, then float[length] vertices
     *
     * @param buffer
     * @return
     */
    static public Polygon polygon(ByteBuffer buffer){
        return new Polygon(ReadData.getFloats(buffer));
    }

    /**
     * create a new polyline object from data on a byteBuffer
     * first int length, then float[length] vertices
     *
     * @param buffer
     * @return
     */
    static public Polyline polyline(ByteBuffer buffer){
        return new Polyline(ReadData.getFloats(buffer));
    }

    /**
     * create a new polyPoint object from data on byte buffer
     * first int length, then coordinates, then boolean isLoop
     * @param buffer
     * @return
     */
    static public Polypoint polypoint(ByteBuffer buffer){
        return new Polypoint(ReadData.getBoolean(buffer), ReadData.getFloats(buffer));
    }

    /**
     * Create an edge object from data on byteBuffer
     * first coordinates of a and b, then ghost a,then ghost b
     * @param buffer
     * @return
     */
    static public Edge edge(ByteBuffer buffer){
        Edge edge=new Edge(buffer.getFloat(),buffer.getFloat(),buffer.getFloat(),buffer.getFloat());
        if (ReadData.getBoolean(buffer)){
            edge.addGhostA(buffer.getFloat(),buffer.getFloat());
        }
        if (ReadData.getBoolean(buffer)){
            edge.addGhostB(buffer.getFloat(),buffer.getFloat());
        }
        return edge;
    }

    /**
     * Create an chain object from data on byteBuffer
     * first length of coordinates, then coordinates , then isLoop, then ghost a,then ghost b
     * @param buffer
     * @return
     */
    static public Chain chain(ByteBuffer buffer){
        Chain chain=new Chain(ReadData.getFloats(buffer));
        chain.setIsLoop(ReadData.getBoolean(buffer));
        if (ReadData.getBoolean(buffer)){
            chain.addGhostA(buffer.getFloat(),buffer.getFloat());
        }
        if (ReadData.getBoolean(buffer)){
            chain.addGhostB(buffer.getFloat(),buffer.getFloat());
        }
        return chain;
    }

    /**
     * create a shap2dCollection object from a byteBuffer
     * @param buffer
     * @return
     */
    static public Shape2DCollection shape2DCollection(ByteBuffer buffer){
        Shape2DCollection collection=new Shape2DCollection();
        int numberOfShapes=ReadData.getInt(buffer);
        for (int i=0;i<numberOfShapes;i++){
            collection.items.add(shape2D(buffer));
        }
        return collection;
    }

    /**
     * create and read a shape2D from buffer.
     * first type of shape, then shape data
     *
     * @param buffer
     * @return
     */
    static public Shape2D shape2D(ByteBuffer buffer){
        switch (Shape2DType.ofByte(ReadData.getByte(buffer))){
            case CIRCLE:
                return circle(buffer);
            case RECTANGLE:
                return rectangle(buffer);
            case  POLYGON:
                return polygon(buffer);
            case POLYPOINT:
                return polypoint(buffer);
            case POLYLINE:
                return polyline(buffer);
            case EDGE:
                return edge(buffer);
            case CHAIN:
                return chain(buffer);
            case COLLECTION:
                return shape2DCollection(buffer);
        }
        return null;
    }



}
