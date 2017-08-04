package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.IntArray;

import java.nio.ByteBuffer;
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

    /**
     * Write pixmap as a png file on external storage. dispose pixmap.
     * @param path
     * @param pixmap will be disposed
     */
    static public void writePNG(String path, Pixmap pixmap){
        PixmapIO.writePNG(createExternalFileHandle(path),pixmap);
        pixmap.dispose();
    }

    /**
     * write IntArray on a file as bytes
     *
     * @param array
     * @param fileHandle
     * @param append
     */
    static public void writeIntArray(IntArray array, FileHandle fileHandle, boolean append){
        int length=array.size;
        ByteBuffer byteBuffer=ByteBuffer.allocate(4*length);
        IntBuffer buffer=byteBuffer.asIntBuffer();
        for (int i=0;i<length;i++) {
            buffer.put(array.get(i));
        }
        ByteBufferIO.write(byteBuffer,fileHandle,append);
    }

    /**
     * create Int(eger)Buffer from reading data from file given by fileHandle
     * @param fileHandle
     * @return a new buffer with data
     */
    static public IntBuffer readIntBuffer(FileHandle fileHandle){
        ByteBuffer byteBuffer=ByteBufferIO.read(fileHandle);
        return byteBuffer.asIntBuffer();
    }

    /**
     * read file of bytes, convert to integers and put in IntArray
     *
     * @param array   will be changed to data of file defined by fileHndle, if exists
     * @param fileHandle
     */
    static public void readIntArray(IntArray array,FileHandle fileHandle){
        if (fileHandle.exists()) {
            IntBuffer buffer = readIntBuffer(fileHandle);
            buffer.rewind();
            int length = buffer.limit();
            array.clear();
            for (int i = 0; i < length; i++) {
                array.add(buffer.get());
            }
        }
    }
}
