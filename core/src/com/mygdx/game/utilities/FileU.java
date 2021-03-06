package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;

/**
 * File access, reading and writing, conversion to and from bytes
 * For desktop the local storage is android_assets
 * subdirectory: "garbage/garbage.png"
 */

public class FileU {

    /**
     * create a file handle to an internal file in android assets. read only
     *
     * @param path String, file name with extension, or relative directory path and file name
     * @return FileHandle
     */
    static public FileHandle createInternalFileHandle(String path){
        return Gdx.files.internal(path);
    }

    /**
     * create a file handle to an local file.
     *
     * @param path String, file name with extension, or relative directory path and file name
     * @return FileHandle
     */
    static public FileHandle createLocalFileHandle(String path){
        return Gdx.files.local(path);
    }

    /**
     * create a file handle to an external file.
     *
     * @param path String, file name with extension, or relative directory path and file name
     * @return FileHandle
     */
    static public FileHandle createExternalFileHandle(String path){
        return Gdx.files.external(path);
    }

    /**
     * check if external storage exists
     *
     * @return boolean, true if there is external storage
     */
    static public boolean isExternalStorageAvailable(){
        return Gdx.files.isExternalStorageAvailable();
    }

    /**
     * check if local storage exists
     *
     * @return boolean, true if there is local storage
     */
    static public boolean isLocalStorageAvailable(){
        return Gdx.files.isLocalStorageAvailable();
    }

    /**
     * get the external storage path
     *
     * @return String, the external storage path
     */
    static public String getExternalStoragePath(){
        return Gdx.files.getExternalStoragePath();
    }

    /**
     * get the local storage path
     *
     * @return String, the local storage path
     */
    static public String getLocalStoragePath(){
        return Gdx.files.getLocalStoragePath();
    }

    /**
     * create a pixmap from an image file (jpeg or png) in internal storage
     *
     * @param path FileHandle, for file name and folder
     * @return Pixmap, with the image
     */
    static public Pixmap createPixmap(String path){
        return new Pixmap(createInternalFileHandle(path));
    }

    /**
     * write a pixmap as png file to internal storage
     *
     * @param path FileHandle, for file name and folder
     * @param pixmap Pixmap to save as png.
     */
    static public void writeLocal(String path,Pixmap pixmap){
        PixmapIO.writePNG(createLocalFileHandle(path),pixmap);
    }
}
