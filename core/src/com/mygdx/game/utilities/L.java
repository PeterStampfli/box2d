package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;

/**
 * Simplifies the logging.
 */

public class L {

    // limit for number of data
    public static final int MAX_NUMBERS=10;

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
     * @param object
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
     * logging a float array with its data
     *
     * @param array
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
        if (array.length<=MAX_NUMBERS) {
            message += "]";
        }
        else {
            message+=", ...";
        }
        og(message);
    }

    /**
     * logging an int array with its data
     *
     * @param array
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
        if (array.length<=MAX_NUMBERS) {
            message += "]";
        }
        else {
            message+=", ...";
        }
        og(message);
    }
}
