package com.mygdx.game.utilities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;

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

}
