package com.mygdx.game.utilities;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Edge;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Created by peter on 8/10/17.
 */

public class ByteBufferU {

    /**
     * advance the position of the buffer by given amount
     * @param buffer
     * @param i
     */
    static public void advance(ByteBuffer buffer,int i){
        buffer.position(buffer.position()+i);
    }

    /**
     * write float numbers on a byteBuffer, advancing the position
     * sometimes we know the length by default
     *
     * @param buffer
     * @param floats
     */
    static public void putFloats(ByteBuffer buffer, float... floats){
        buffer.asFloatBuffer().put(floats);
        advance(buffer,4*floats.length);
    }

    /**
     * write numbers of a floatArray on a byteBuffer, advancing the position
     * sometimes we know the length by default
     *
     * @param byteBuffer
     * @param array
     */
    static public void putFloats(ByteBuffer byteBuffer, FloatArray array){
        int length=array.size;
        FloatBuffer buffer=byteBuffer.asFloatBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));                  // does not change position of the byteBuffer
        }
        advance(byteBuffer,4*length);
    }

    /**
     * put a boolean as a byte
     *
     * @param buffer
     * @param boo
     */
    static public void putBoolean(ByteBuffer buffer, boolean boo){
        buffer.put(MathU.toByte(boo));
    }

    /**
     * make a byte buffer that contains size and content of a FloatArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeFloats(FloatArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length+4);
        byteBuffer.putInt(length);
        putFloats(byteBuffer,array);
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of an IntArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeInts(IntArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length+4);
        byteBuffer.putInt(length);
        IntBuffer buffer=byteBuffer.asIntBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));            // does not change position of the byteBuffer
        }
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a shortArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeShorts(ShortArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*length+4);
        byteBuffer.putInt(length);
        ShortBuffer buffer=byteBuffer.asShortBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));            // does not change position of the byteBuffer
        }
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a ByteArray
     *
     * @param array
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeBytes(ByteArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(length+4);
        byteBuffer.putInt(length);
        for (int i=0;i<length;i++) {
            byteBuffer.put(array.get(i));
        }
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains only content of a float[]
     *
     * @param floats
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeFloatsOnly(float... floats){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*floats.length);
        byteBuffer.asFloatBuffer().put(floats);
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a float[]
     *
     * @param floats
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeFloats(float... floats){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*floats.length+4);
        byteBuffer.putInt(floats.length);
        byteBuffer.asFloatBuffer().put(floats);
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a int[]
     *
     * @param ints
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeInts(int... ints){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*ints.length+4);
        byteBuffer.putInt(ints.length);
        byteBuffer.asIntBuffer().put(ints);    // does not change position of the byteBuffer
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a short[]
     *
     * @param shorts
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeShorts(short... shorts){
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*shorts.length+4);
        byteBuffer.putInt(shorts.length);
        byteBuffer.asShortBuffer().put(shorts);    // does not change position of the byteBuffer
        return byteBuffer;
    }

    /**
     * make a byte buffer that contains size and content of a byte[]
     *
     * @param bytes
     * @return a byteBuffer with data, write (append) it on a file
     */
    static public ByteBuffer makeBytes(byte... bytes){
        ByteBuffer byteBuffer=ByteBuffer.allocate(bytes.length+4);
        byteBuffer.putInt(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.rewind();
        return byteBuffer;
    }

    /**
     * make a byteBuffer with the data of a Circle object,append on a file
     *
     * @param circle
     * @return
     */
    static public ByteBuffer makeShape(Circle circle){
        return makeFloatsOnly(circle.x,circle.y,circle.radius);
    }

    /**
     * make a byteBuffer with the data of a Rectangle object,append on a file
     *
     * @param rectangle
     * @return
     */
    static public ByteBuffer makeShape(Rectangle rectangle){
        return makeFloatsOnly(rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    /**
     * make a byteBuffer with data of a polygon object
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param polygon
     * @return
     */
    static public ByteBuffer makeShape(Polygon polygon){
        float[] vertices=polygon.getVertices();
        return makeFloats(vertices);
    }

    /**
     * make a byteBuffer with data of a polyline object
     * first integer length of vertices array, then float[] vertices array
     * length=2*number of vertices
     *
     * @param polyline
     * @return
     */
    static public ByteBuffer makeShape(Polyline polyline){
        float[] vertices=polyline.getVertices();
        return makeFloats(vertices);
    }

    /**
     * writing a ghost depending if it exists
     *
     * @param buffer
     * @param exists
     * @param x
     * @param y
     */
    static private void putGhost(ByteBuffer buffer,boolean exists,float x,float y){
        putBoolean(buffer,exists);
        if (exists){
            buffer.putFloat(x);
            buffer.putFloat(y);
        }
    }

    /**
     * make a byteBuffer that defines an edge object.
     * First coordinates of points a and b
     * following byte is 1 if ghostA exists followed by ghostA coordinates, else byte is 0
     * following byte is 1 if ghostB exists followed by ghostB coordinates, else byte is 0
     *
     * @param edge
     * @return
     */
    static public ByteBuffer makeShape(Edge edge){
        int length=18;                           // two bytes plus 4 floats
        if (edge.ghostAExists){
            length+=8;
        }
        if (edge.ghostBExists){
            length+=8;
        }
        ByteBuffer buffer=ByteBuffer.allocate(length);
        putFloats(buffer,edge.aX,edge.aY,edge.bX,edge.bY);
        putGhost(buffer,edge.ghostAExists,edge.ghostAX,edge.ghostAY);
        putGhost(buffer,edge.ghostBExists,edge.ghostBX,edge.ghostBY);
        buffer.rewind();
        return buffer;
    }

    /**
     * make a byteBuffer that defines a chain object.
     * First length of coordinates float[], then coordinates of points
     * following byte for is loop
     * following byte is 1 if ghostA exists followed by ghostA coordinates, else byte is 0
     * following byte is 1 if ghostB exists followed by ghostB coordinates, else byte is 0
     *
     * @param chain
     * @return
     */
    static public ByteBuffer makeShape(Chain chain) {
        int length = 1 + 4 + 4 * chain.coordinates.length;
        if (chain.ghostAExists) {
            length += 8;
        }
        if (chain.ghostBExists) {
            length += 8;
        }
        ByteBuffer buffer = ByteBuffer.allocate(length);
        buffer.putInt(chain.coordinates.length);
        putFloats(buffer, chain.coordinates);
        putBoolean(buffer, chain.isLoop);
        putGhost(buffer, chain.ghostAExists, chain.ghostAX, chain.ghostAY);
        putGhost(buffer, chain.ghostBExists, chain.ghostBX, chain.ghostBY);
        buffer.rewind();
        return buffer;
    }

    public static boolean readBoolean(ByteBuffer buffer){
        return MathU.toBoolean(buffer.get());
    }

    /**
     * read incrementally another floatArray from a ByteBuffer
     *
     * @param array
     * @param buffer
     */
    static public void readFloats(FloatArray array, ByteBuffer buffer){
        int length=buffer.getInt();
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getFloat());                      // position of float buffer advances
        }
    }

    /**
     * read incrementally into existing intArray from a ByteBuffer
     *
     * @param array
     * @param buffer
     */
    static public void readInts(IntArray array, ByteBuffer buffer){
        int length=buffer.getInt();
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getInt());
        }
    }

    /**
     * read incrementally into existing shortArray from a ByteBuffer
     *
     * @param array
     * @param buffer
     */
    static public void readShorts(ShortArray array, ByteBuffer buffer){
        int length=buffer.getInt();
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.getShort());
        }
    }

    /**
     * read incrementally into existing byteArray from a ByteBuffer
     *
     * @param array
     * @param buffer
     */
    static public void readBytes(ByteArray array, ByteBuffer buffer){
        int length=buffer.getInt();
        array.clear();
        array.ensureCapacity(length);
        for (int i = 0; i < length; i++) {
            array.add(buffer.get());
        }
    }

    /**
     * read incrementally existing float[] from a ByteBuffer
     *
     * @param floats
     * @param buffer
     */
    static public void readFloats(float[] floats, ByteBuffer buffer){
        buffer.asFloatBuffer().get(floats);
        ByteBufferU.advance(buffer,4*floats.length);
    }

    /**
     * read an integer length from the bytebuffer, create and read a float[] of this length
     * return the float[]
     *
     * @param buffer
     * @return
     */
    static public float[] getFloats(ByteBuffer buffer){
        int length=buffer.getInt();
        float[] fs=new float[length];
        ByteBufferU.readFloats(fs,buffer);
        return fs;
    }

    /**
     * read incrementally existing int[] from a ByteBuffer
     *
     * @param ints
     * @param buffer
     */
    static public void readInts(int[] ints, ByteBuffer buffer){
        buffer.asIntBuffer().get(ints);
        ByteBufferU.advance(buffer,4*ints.length);
    }

    /**
     * read incrementally existing short[] from a ByteBuffer
     *
     * @param shorts
     * @param buffer
     */
    static public void readShorts(short[] shorts, ByteBuffer buffer){
        buffer.asShortBuffer().get(shorts);
        ByteBufferU.advance(buffer,2*shorts.length);
    }

    // for bytes use byteBuffer.get(bytes)

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
        return new Polygon(getFloats(buffer));
    }

    /**
     * create a new polyline object from data on a byteBuffer
     * first int length, then float[length] vertices
     *
     * @param buffer
     * @return
     */
    static public Polyline polyline(ByteBuffer buffer){
        return new Polyline(getFloats(buffer));
    }

    /**
     * Create an edge object from data on bytebuffer
     * first coordinates of a and b, then ghost a,then ghost b
     * @param buffer
     * @return
     */
    static public Edge edge(ByteBuffer buffer){
        Edge edge=new Edge(buffer.getFloat(),buffer.getFloat(),buffer.getFloat(),buffer.getFloat());
        if (readBoolean(buffer)){
            edge.addGhostA(buffer.getFloat(),buffer.getFloat());
        }
        if (readBoolean(buffer)){
            edge.addGhostB(buffer.getFloat(),buffer.getFloat());
        }
        return edge;
    }

    /**
     * create a chain object from data on byte buffer
     * @param buffer
     * @return
     */
    static public Chain chain(ByteBuffer buffer){
        Chain chain=new Chain(getFloats(buffer));
        if (readBoolean(buffer)){
            chain.addGhostA(buffer.getFloat(),buffer.getFloat());
        }
        if (readBoolean(buffer)){
            chain.addGhostB(buffer.getFloat(),buffer.getFloat());
        }
        return chain;
    }

    /**
     * polYpoint: number of coordinates, coordinates data, float maxdletaangle,boolean isLoop
     */
}
