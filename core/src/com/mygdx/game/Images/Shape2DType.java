package com.mygdx.game.Images;

/**
 * the different shape types: for reading/writing as byte
 */

public enum Shape2DType {
    COLLECTION(0),
    CIRCLE(1),
    RECTANGLE(2),
    POLYGON(3),
    POLYLINE(4),
    POLYPOINT(5),
    EDGE(6),
    CHAIN(7);

    private int value;

    Shape2DType(int value){
        this.value=value;
    }

    /**
     * get the byte value that represents the type
     *
     * @return byte, number of the type
     */
    public byte toByte() {
        return (byte) value;
    }

    /**
     * from a byte value obtain the type
     *
     * @param key byte, number for type
     * @return Shape2DType, resulting from the number
     */
    public static Shape2DType ofByte(byte key){
        for (Shape2DType type:Shape2DType.values()){
            if (type.toByte()==key){
                return type;
            }
        }
        throw new RuntimeException("No type matches byte value"+key);
    }
}
