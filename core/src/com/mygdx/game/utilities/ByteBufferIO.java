package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.nio.ByteBuffer;

/**
 * Created by peter on 3/10/17.
 * Reading and writing Bytebuffers on th filesystem.
 * Bytebuffer can contain any primitive datatype.
 */

public class ByteBufferIO {

    /**
     * write the bytebuffer on a file given by filehandle
     * mode append or replace
     * @param byteBuffer
     * @param fileHandle
     * @param append
     */
    static void write(ByteBuffer byteBuffer, FileHandle fileHandle,boolean append){
        int nBytes=byteBuffer.position()!=0?byteBuffer.position():byteBuffer.capacity();
        byte[] bytes=new byte[nBytes];
        byteBuffer.rewind();
        byteBuffer.get(bytes);
        fileHandle.writeBytes(bytes,append);
        Gdx.app.log("***",""+nBytes);
    }

    /**
     * write byteBuffer on file "filename" in local filesystem,
     * mode append or replace
     * @param byteBuffer
     * @param fileName
     * @param append
     */
    static void write(ByteBuffer byteBuffer,String fileName,boolean append){
        ByteBufferIO.write(byteBuffer,Gdx.files.local(fileName),append);
    }

    /**
     * create ByteBuffer and read data from file given by filehandle
     * @param fileHandle
     * @return a new byteBuffer with the data
     */
    static ByteBuffer read(FileHandle fileHandle){
        byte[] bytes=fileHandle.readBytes();
        return ByteBuffer.wrap(bytes);
    }

    /**
     * create ByteBuffer and read data from file with given name in local filesystem
     * @param fileName
     * @return a new byteBuffer with the data
     */
    static ByteBuffer read(String fileName){
        return ByteBufferIO.read(Gdx.files.local(fileName));
    }
}
