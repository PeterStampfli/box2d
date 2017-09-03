package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/**
 * Simplifies the logging.
 */

public class L {

    // limit for number of data
    public static int MAX_NUMBERS=12;

    /**
     * Log a debug message.
     *
     * @param message String, any text
     */
    public static void og(String message){ Gdx.app.log("L.og",message);}

    /**
     * Log the value of an integer
     *
     * @param n an integer number(long, int or short)
     */
    public static void og(long n){ og(""+n);}
    public static void og(int n){ og(""+n);}

    /**
     * Log the value of a floating point number.
     *
     * @param f a floating point number (double or float)
     */
    public static void og(double f){  og(""+f);}

    /**
     * Log a boolean.
     *
     * @param b boolean
     */
    public static void og(boolean b){ og(""+b);}

    /**
     * Safe logging of an object. Even of a null object.
     *
     * @param object Object
     */
    public static void og(Object object){
        if (object==null){
            og("null");
        }
        else{
            og(object.toString());
        }
    }

    /**
     * generate size-dependent end string for array
     *
     * @param length int, length of array
     * @return String, for terminating the array log
     */
    private static String end(int length){
        if (length<=MAX_NUMBERS) {
            return "]";
        }
        return ", ...";
    }

    /**
     * logging a float[] with its data
     *
     * @param array float[] array
     */
    public static void og(float[] array){
        String message="[";
        int length=Math.min(array.length,MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=array[i]+", ";
        }
        if (length>=0){
            message+=array[length];
        }
        og(message+end(array.length));
    }

    /**
     * logging an int[] with its data
     *
     * @param array int[] array
     */
    public static void og(int[] array){
        String message="[";
        int length=Math.min(array.length,MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=array[i]+", ";
        }
        if (length>=0){
            message+=array[length];
        }
        og(message+end(array.length));
    }

    /**
     * logging a short[] with its data
     *
     * @param array short[] array
     */
    public static void og(short[] array){
        String message="[";
        int length=Math.min(array.length,MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=array[i]+", ";
        }
        if (length>=0){
            message+=array[length];
        }
        og(message+end(array.length));
    }

    /**
     * logging a byte[] with its data
     *
     * @param array byte[] array
     */
    public static void og(byte[] array){
        String message="[";
        int length=Math.min(array.length,MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=array[i]+", ";
        }
        if (length>=0){
            message+=array[length];
        }
        og(message+end(array.length));
    }

    /**
     * logging a FloatBuffer with its data
     *
     * @param buffer FloatBuffer
     */
    public static void og(FloatBuffer buffer){
        int position=buffer.position();
        og("FloatBuffer: position "+position+" remaining "+buffer.capacity());
        String message="[";
        int length=Math.min(buffer.capacity(),MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=buffer.get()+", ";
        }
        if (length>=0){
            message+=buffer.get();
        }
        buffer.position(position);
        og(message+end(buffer.capacity()));
    }

    /**
     * logging an IntBuffer with its data
     *
     * @param buffer IntBuffer
     */
    public static void og(IntBuffer buffer){
        int position=buffer.position();
        og("IntBuffer: position "+position+" remaining "+buffer.capacity());
        String message="[";
        int length=Math.min(buffer.capacity(),MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=buffer.get()+", ";
        }
        if (length>=0){
            message+=buffer.get();
        }
        buffer.position(position);
        og(message+end(buffer.capacity()));
    }

    /**
     * logging a ShortBuffer with its data
     *
     * @param buffer ShortBuffer
     */
    public static void og(ShortBuffer buffer){
        int position=buffer.position();
        og("FloatBuffer: position "+position+" remaining "+buffer.capacity());
        String message="[";
        int length=Math.min(buffer.capacity(),MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=buffer.get()+", ";
        }
        if (length>=0){
            message+=buffer.get();
        }
        buffer.position(position);
        og(message+end(buffer.capacity()));
    }

    /**
     * logging a ByteBuffer with its data
     *
     * @param buffer ByteBuffer
     */
    public static void og(ByteBuffer buffer){
        int position=buffer.position();
        og("FloatBuffer: position "+position+" remaining "+buffer.capacity());
        String message="[";
        int length=Math.min(buffer.capacity(),MAX_NUMBERS)-1;
        for (int i=0;i<length;i++){
            message+=buffer.get()+", ";
        }
        if (length>=0){
            message+=buffer.get();
        }
        buffer.position(position);
        og(message+end(buffer.capacity()));
    }
}
