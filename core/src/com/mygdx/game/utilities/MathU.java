package com.mygdx.game.utilities;

/**
 * Created by peter on 8/4/17.
 */

public class MathU {
    public static final float epsilon=0.1f;
    public static final float rt3=(float)Math.sqrt(3);

    /**
     * get a byte from a boolean, 0==false and 1==true
     *
     * @param boo
     * @return
     */
    public static byte toByte(boolean boo){
        return (byte) (boo?1:0);
    }

    /**
     * get a boolean from a byte
     *
     * @param b
     * @return
     */
    public static boolean toBoolean(byte b){
        return (b!=0);
    }
}
