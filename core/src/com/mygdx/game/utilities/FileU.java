package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.ByteArray;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ShortArray;
import com.mygdx.game.Images.Chain;
import com.mygdx.game.Images.Edge;
import com.mygdx.game.Images.Polypoint;
import com.mygdx.game.Images.Shape2DCollection;
import com.mygdx.game.Images.Shape2DType;

import java.nio.ByteBuffer;

/**
 * File access, reading and writing, conversion to and from bytes
 */

public class FileU {

    /**
     * create a file handle to an internal file in android assets. read only
     *
     * @param path
     * @return
     */
    static public FileHandle createInternalFileHandle(String path){
        return Gdx.files.internal(path);
    }

    /**
     * create a file handle to an local file.
     *
     * @param path
     * @return
     */
    static public FileHandle createLocalFileHandle(String path){
        return Gdx.files.local(path);
    }

    /**
     * create a file handle to an external file.
     *
     * @param path
     * @return
     */
    static public FileHandle createExternalFileHandle(String path){
        return Gdx.files.external(path);
    }

    /**
     * check if external storage exists
     *
     * @return
     */
    static public boolean isExternalStorageAvailable(){
        return Gdx.files.isExternalStorageAvailable();
    }

    /**
     * check if local storage exists
     *
     * @return
     */
    static public boolean isLocalStorageAvailable(){
        return Gdx.files.isLocalStorageAvailable();
    }

    /**
     * get the external storage path
     *
     * @return
     */
    static public String getExternalStoragePath(){
        return Gdx.files.getExternalStoragePath();
    }

    /**
     * get the local storage path
     *
     * @return
     */
    static public String getLocalStoragePath(){
        return Gdx.files.getLocalStoragePath();
    }

    //  writing and reading

    /**
     * Write pixmap as a png file on external storage. dispose pixmap.
     * @param pixmap will be disposed
     * @param path
     */
    static public void writePixmap(Pixmap pixmap, String path){
        PixmapIO.writePNG(createExternalFileHandle(path),pixmap);
        pixmap.dispose();
    }

    // instead of append=false use fileHandle.delete()
    // append pieces to a file
    // read file as a single bytebuffer, read pieces from bytebuffer

    /**
     * create ByteBuffer and read data from file given by filehandle
     * @param fileHandle
     * @return a new byteBuffer with the data, or null if file does not exist
     */
    static public ByteBuffer readByteBuffer(FileHandle fileHandle){
        if (fileHandle.exists()) {
            byte[] bytes = fileHandle.readBytes();
            return ByteBuffer.wrap(bytes);
        }
        else {
            return null;
        }
    }

    /**
     * Append a byte buffer content to a file
     * Rewinds the buffer
     *
     *  @param byteBuffer
     * @param fileHandle
     */
    static public void writeBuffer(ByteBuffer byteBuffer, FileHandle fileHandle){
        byteBuffer.rewind();
        int nBytes=byteBuffer.capacity();
        byte[] bytes=new byte[nBytes];
        byteBuffer.get(bytes);
        fileHandle.writeBytes(bytes,true);
    }

    /**
     * append a float number to a file
     * @param f
     * @param fileHandle
     */
    static public void writeFloat(float f, FileHandle fileHandle){
        writeBuffer(ByteBuffer.allocate(4).putFloat(f),fileHandle);
    }

    /**
     * append an int number to a file
     * @param i
     * @param fileHandle
     */
    static public void writeInt(int i, FileHandle fileHandle){
        writeBuffer(ByteBuffer.allocate(4).putInt(i),fileHandle);
    }

    /**
     * append a short number to a file
     * @param i
     * @param fileHandle
     */
    static public void writeShort(short i, FileHandle fileHandle){
        writeBuffer(ByteBuffer.allocate(2).putShort(i),fileHandle);
    }

    /**
     * append a byte to a file
     * @param i
     * @param fileHandle
     */
    static public void writeByte(byte i, FileHandle fileHandle){
        writeBuffer(ByteBuffer.allocate(1).put(i),fileHandle);
    }

    /**
     * append content of FloatArray (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeFloats(FloatArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeFloats(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of IntArray to a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeInts(IntArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeInts(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of ShortArray to a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeShorts(ShortArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeShorts(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of ByteArray to a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeBytes(ByteArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeBytes(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of float[] (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeFloats(float[] array, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*array.length);
        byteBuffer.asFloatBuffer().put(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of int[] (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeInts(int[] array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeInts(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of short[] (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeShorts(short[] array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeShorts(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append content of byte[] (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void writeBytes(byte[] array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.makeBytes(array);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for a circle to a file
     *
     * @param circle
     * @param fileHandle
     */
    static public void writeShape(Circle circle, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(circle);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for a rectangle to a file
     *
     * @param rectangle
     * @param fileHandle
     */
    static public void writeShape(Rectangle rectangle, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(rectangle);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for a polygon to a file
     *
     * @param polygon
     * @param fileHandle
     */
    static public void writeShape(Polygon polygon, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(polygon);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for a polyline to a file
     *
     * @param polyline
     * @param fileHandle
     */
    static public void writeShape(Polyline polyline, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(polyline);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for an edge to a file
     *
     * @param edge
     * @param fileHandle
     */
    static public void writeShape(Edge edge, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(edge);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append data for an chain to a file
     *
     * @param chain
     * @param fileHandle
     */
    static public void writeShape(Chain chain, FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferU.makeShape(chain);
        writeBuffer(byteBuffer,fileHandle);
    }

    /**
     * append shape2DCollection
     * number of shapes followed by the shapes: ShapeType and shape data
     *
     * @param collection
     * @param fileHandle
     */
    static public void writeShape(Shape2DCollection collection,FileHandle fileHandle){
        writeInt(collection.shapes2D.size,fileHandle);
        for (Shape2D shape:collection.shapes2D) {
            if (shape instanceof Polygon) {
                writeByte(Shape2DType.POLYGON.toByte(),fileHandle);
                writeShape((Polygon) shape,fileHandle);
            } else if (shape instanceof Circle) {
                writeByte(Shape2DType.CIRCLE.toByte(),fileHandle);
                writeShape((Circle) shape,fileHandle);
            } else if (shape instanceof Rectangle) {
                writeByte(Shape2DType.RECTANGLE.toByte(),fileHandle);
                writeShape((Rectangle) shape,fileHandle);
            } else if (shape instanceof Polypoint) {
                writeByte(Shape2DType.POLYPOINT.toByte(),fileHandle);
              //  writeShape((Polypoint) shape,fileHandle);
            } else if (shape instanceof Polyline) {
                writeByte(Shape2DType.POLYLINE.toByte(),fileHandle);
                writeShape((Polyline) shape,fileHandle);
            } else if (shape instanceof Edge) {
                writeByte(Shape2DType.EDGE.toByte(),fileHandle);
                writeShape((Edge) shape,fileHandle);
            } else if (shape instanceof Chain) {
                writeByte(Shape2DType.CHAIN.toByte(),fileHandle);
                writeShape((Chain) shape,fileHandle);
            } else if (shape instanceof Shape2DCollection) {                 // includes subclass DotsAndLines
                writeByte(Shape2DType.SHAPE2DCOLLECTION.toByte(),fileHandle);
                writeShape((Shape2DCollection) shape,fileHandle);
            } else {
                Gdx.app.log(" ******************** makeShape", "unknown shape " + shape.getClass());
            }
        }

    }
}
