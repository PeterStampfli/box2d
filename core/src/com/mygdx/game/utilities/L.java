package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;

/**
 * Created by peter on 1/19/17.
 */

public class L {
    /**
     * debug message
     * @param message a text (String)
     */
    public static void og(String message){ Gdx.app.log("L.og",message);}

    /**
     * debug message
     * @param n an integer number(long, int or short)
     */
    public static void og(long n){ og(""+n);}
    public static void og(int n){ og(""+n);}

    /**
     * quick and dirty way for printing debug messages
     * @param f floating point number (double or float)
     */
    public static void og(double f){  og(""+f);}

    /**
     * quick and dirty way for printing debug messages
     * @param b boolean
     */
    public static void og(boolean b){  og(""+b);}


}
