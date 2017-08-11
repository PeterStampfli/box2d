package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.IntArray;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

    // instead of append=false use fileHandle.delete()

    /**
     * Write pixmap as a png file on external storage. dispose pixmap.
     * @param path
     * @param pixmap will be disposed
     */
    static public void write(String path, Pixmap pixmap){
        PixmapIO.writePNG(createExternalFileHandle(path),pixmap);
        pixmap.dispose();
    }

    /**
     * Append a byte buffer content to a file
     * Rewinds the buffer
     *
     *  @param byteBuffer
     * @param fileHandle
     */
    static public void write(ByteBuffer byteBuffer, FileHandle fileHandle){
        int nBytes=byteBuffer.capacity();
        byte[] bytes=new byte[nBytes];
        byteBuffer.rewind();
        byteBuffer.get(bytes);
        fileHandle.writeBytes(bytes,true);
    }

    /**
     * append a float number to a file
     * @param f
     * @param fileHandle
     */
    static public void write(float f,FileHandle fileHandle){
        write(ByteBuffer.allocate(4).putFloat(f),fileHandle);
    }

    /**
     * append an int number to a file
     * @param i
     * @param fileHandle
     */
    static public void write(int i,FileHandle fileHandle){
        write(ByteBuffer.allocate(4).putInt(i),fileHandle);
    }

    /**
     * append content of IntArray to a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void write(IntArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.make(array);
        write(byteBuffer,fileHandle);
    }

    /**
     * append content of FloatArray (size, followed by data) on a file as bytes
     *  @param array
     * @param fileHandle
     */
    static public void write(FloatArray array, FileHandle fileHandle){
        ByteBuffer byteBuffer= ByteBufferU.make(array);
        write(byteBuffer,fileHandle);
    }


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
     * create Int(eger)Buffer from reading data from file given by fileHandle
     * @param fileHandle
     * @return a new buffer with data
     */
    static public IntBuffer readIntBuffer(FileHandle fileHandle){
        ByteBuffer byteBuffer=readByteBuffer(fileHandle);
        return byteBuffer.asIntBuffer();
    }

    /**
     * create FloatBuffer from reading data from file given by fileHandle
     * @param fileHandle
     * @return a new buffer with data
     */
    static public FloatBuffer readFloatBuffer(FileHandle fileHandle){
        ByteBuffer byteBuffer=readByteBuffer(fileHandle);
        return byteBuffer.asFloatBuffer();
    }

    /**
     * read file of bytes, convert to integers and put in IntArray
     *
     * @param array   will be changed to data of file defined by fileHndle, if exists
     * @param fileHandle
     */
    static public void read(IntArray array, FileHandle fileHandle){
        if (fileHandle.exists()) {
            IntBuffer buffer = readIntBuffer(fileHandle);
            ByteBufferU.readByteBuffer(array,buffer);
        }
    }

    /**
     * read file of bytes, convert to floats (size, followed by data) and put in FloatArray
     *
     * @param array   will be changed to data of file defined by fileHandle, if exists
     * @param fileHandle
     */
    static public void read(FloatArray array, FileHandle fileHandle){
        if (fileHandle.exists()) {
            FloatBuffer buffer = readFloatBuffer(fileHandle);
            ByteBufferU.readByteBuffer(array,buffer);
        }
    }
}
