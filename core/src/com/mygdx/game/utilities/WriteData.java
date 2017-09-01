package com.mygdx.game.utilities;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Appending basic data on files.
 */

public class WriteData {


    /**
     * Write pixmap as a png file on external storage. dispose pixmap.
     * @param pixmap Pixmap, dispose later
     * @param path String, file name and folder path
     */
    static public void writePixmap(Pixmap pixmap, String path){
        PixmapIO.writePNG(FileU.createExternalFileHandle(path),pixmap);
    }

    /**
     * Append a byte buffer content to a file
     * Rewinds the buffer before writing for safety
     *
     * @param fileHandle FileHandle
     * @param byteBuffer ByteBuffer
     */
    static public void appendBuffer(FileHandle fileHandle,ByteBuffer byteBuffer){
        byteBuffer.rewind();
        int nBytes=byteBuffer.capacity();
        byte[] bytes=new byte[nBytes];
        byteBuffer.get(bytes);
        fileHandle.writeBytes(bytes,true);
    }

    /**
     * append a float number to a file
     *
     * @param fileHandle FileHandle
     * @param f
     */
    static public void appendFloat(FileHandle fileHandle,float f){
        appendBuffer(fileHandle,ByteBuffer.allocate(4).putFloat(f));
    }

    /**
     * append an int number to a file
     *
     * @param fileHandle FileHandle
     * @param i
     */
    static public void appendInt(FileHandle fileHandle,int i){
        appendBuffer(fileHandle,ByteBuffer.allocate(4).putInt(i));
    }

    /**
     * append a short number to a file
     *
     * @param fileHandle FileHandle
     * @param s
     */
    static public void appendShort(FileHandle fileHandle,short s){
        appendBuffer(fileHandle,ByteBuffer.allocate(2).putShort(s));
    }

    /**
     * append a byte to a file
     *
     * @param fileHandle FileHandle
     * @param b
     */
    static public void appendByte(FileHandle fileHandle,byte b){
        appendBuffer(fileHandle,ByteBuffer.allocate(1).put(b));
    }

    /**
     * append a boolean as a byte to a file
     *
     * @param fileHandle FileHandle
     * @param b
     */
    static public void appendBoolean(FileHandle fileHandle,boolean b){
        appendBuffer(fileHandle,ByteBuffer.allocate(1).put((byte) (b?1:0)));
    }

    /**
     * append content of FloatArray on a file
     *
     * @param fileHandle FileHandle
     * @param array
     */
    static public void appendFloats(FileHandle fileHandle,FloatArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length);
        FloatBuffer buffer=byteBuffer.asFloatBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of IntArray to a file
     * write size separately (keep it simple and transparent)
     *
     * @param fileHandle FileHandle
     *  @param array
     */
    static public void appendInts(FileHandle fileHandle,IntArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length);
        IntBuffer buffer=byteBuffer.asIntBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of ShortArray to a file as bytes
     *
     * @param fileHandle FileHandle
     *  @param array ShortArray
     */
    static public void appendShorts(FileHandle fileHandle,ShortArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*length);
        ShortBuffer buffer=byteBuffer.asShortBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of ByteArray to a file as bytes
     *
     * @param fileHandle FileHandle
     * @param array ByteArray
     */
    static public void appendBytes(FileHandle fileHandle,ByteArray array){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(length);
        for (int i=0;i<length;i++) {
            byteBuffer.put(array.get(i));
        }
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of float[]  on a file
     *
     * @param fileHandle FileHandle
     * @param array float... or float[]
     */
    static public void appendFloats(FileHandle fileHandle,float... array){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*array.length);
        byteBuffer.asFloatBuffer().put(array);
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of int[] on a file
     *
     * @param fileHandle FileHandle
     * @param array int... or int[]
     */
    static public void appendInts(FileHandle fileHandle,int... array){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*array.length);
        byteBuffer.asIntBuffer().put(array);
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of short[] on a file
     *
     * @param fileHandle FileHandle
     * @param array short... or short[]
     */
    static public void appendShorts(FileHandle fileHandle,short... array){
        ByteBuffer byteBuffer=ByteBuffer.allocate(2*array.length);
        byteBuffer.asShortBuffer().put(array);
        appendBuffer(fileHandle,byteBuffer);
    }

    /**
     * append content of a byte[] to a file
     * @param fileHandle FileHandle
     * @param array byte... or byte[]
     */
    static public void appendBytes(FileHandle fileHandle,byte... array){
        fileHandle.writeBytes(array,true);
    }
}
