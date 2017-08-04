package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by peter on 8/4/17.
 */

public class TextureU {

    /**
     * set texture to linear interpolation
     *
     * @param texture
     */
    public static void linearInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * set texture to  nearest neighbor "interpolation"
     *
     * @param texture
     */
    public static void nearestInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    /**
     * Set the texture of a TextureRegion to linear interpolation.
     *
     * @param textureRegion
     */
    public static void linearInterpolation(TextureRegion textureRegion) {
        linearInterpolation(textureRegion.getTexture());
    }

    /**
     * Set the texture of a TextureRegion to nearest neighbor "interpolation".
     *
     * @param textureRegion
     */
    public static void nearestInterpolation(TextureRegion textureRegion) {
        nearestInterpolation(textureRegion.getTexture());
    }


    /**
     * Create a texture region from a pixmap and dispose the pixmap. Make linear interpolation.
     *
     * @param pixmap will be disposed
     * @return
     */
    public static TextureRegion textureRegionFromPixmap(Pixmap pixmap){
        TextureRegion result=new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        linearInterpolation(result);
        return result;
    }

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

    /**
     * Write pixmap as a png file on external storage. dispose pixmap.
     * @param path
     * @param pixmap will be disposed
     */
    static public void writePNG(String path, Pixmap pixmap){
        PixmapIO.writePNG(createExternalFileHandle(path),pixmap);
        pixmap.dispose();
    }
}
