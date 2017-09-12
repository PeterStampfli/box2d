package com.mygdx.game.utilities;

/**
 * special math
 */

public class MathU {
    public static final float epsilon=0.1f;
    public static final float rt3=(float)Math.sqrt(3);


    /**
     * test if a lessEqual b lessEqual c
     *
     * @param a float
     * @param b float
     * @param c float
     * @return boolean
     */
    public static boolean isOrdered(float a,float b,float c){
        return (a<=b)&&(b<=c);
    }

}
