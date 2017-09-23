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

    /**
     * calculate the correct modulus for negative numbers too. Not the remainder.
     *
     * @param a int
     * @param b int
     * @return int, a mod b
     */
    public static int mod(int a,int b){
        return a=(a>=0)?a%b:a%b+b;
    }

}
