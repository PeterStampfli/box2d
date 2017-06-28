package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Simple static routines with mnemonic simple meaningful interfaces for obscure libgdx calls.
 */

public class Basic {

    private static int numberOfRenderCalls;
    private static float timeOfLastFrame = 10;
    public static final float epsilon=0.1f;


    /**
     * Switch continuous rendering on or off. (Input events always trigger rendering!)
     *
     * @param on boolean, use true for continuous rendering
     */
    public static void setContinuousRendering(boolean on) {
        Gdx.graphics.setContinuousRendering(on);
    }

    /**
     * Demand a call of renderFrame. Not needed if input event already triggers rendering.
     */
    public static void requestRendering() {
        Gdx.graphics.requestRendering();
    }

    /**
     * reset number of renderFrame calls
     */
    public static void resetNumberOfRenderCalls() {
        numberOfRenderCalls = 0;
    }

    /**
     * Log the number of renderFrame calls to check the frequency of discontinuous rendering.
     */
    public static void logNumberOfRenderCalls() {
        numberOfRenderCalls++;
        L.og("renderFrame calls: " + numberOfRenderCalls);
    }

    /**
     * Clear the screen with a solid color
     *
     * @param r 0...1, red component
     * @param g 0...1, green component
     * @param b 0...1, blue component
     */
    public static void clearBackground(float r, float g, float b) {
        Gdx.gl.glClearColor(r, g, b, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /**
     * Clear the screen with a solid color.
     *
     * @param color a libGdx Color, the alpha component is ignored
     */
    public static void clearBackground(Color color) {
        clearBackground(color.r, color.g, color.b);
    }

    /**
     * set texture to linear interpolation
     *
     * @param texture
     */
    public static void linearInterpolation(Texture texture) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    /**
     * Fill the screen with a background image, adjusted to fill.
     * Clear background and Call batch.begin() before and set tint.
     *
     * @param img
     */
    public static void clearBackground(SpriteBatch batch, Viewport viewport, TextureRegion img){
        float scale=Math.max(viewport.getWorldWidth()/img.getRegionWidth(),
                            viewport.getWorldHeight()/img.getRegionHeight());
        batch.draw(img,0,0,scale*img.getRegionWidth(),scale*img.getRegionHeight());
    }

    /**
     * Create a texture region from a pixmap and dispose the pixmap
     *
     * @param pixmap will be disposed
     * @return
     */
    public static TextureRegion textureRegionFromPixmap(Pixmap pixmap){
        TextureRegion result=new TextureRegion(new Texture(pixmap));
        pixmap.dispose();
        return result;
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
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
    }

    /**
     * Get the absolute time in seconds
     *
     * @return float, time in seconds
     */
    public static float getTime() {
        return MathUtils.nanoToSec * TimeUtils.nanoTime();
    }

    /**
     * Get time passed since an earlier moment.
     *
     * @param time of earlier moment in seconds
     * @return float, time passed in seconds
     */
    public static float getTimeSince(float time) {
        return MathUtils.nanoToSec * TimeUtils.nanoTime() - time;
    }

    /**
     * Get the smoothed time intervall between render calls
     *
     * @return float, time interval in seconds
     */
    public static float getAverageDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    /**
     * Get true measured time intervall between current render call and previous render call.
     *
     * @return float, time interval in seconds
     */
    public static float getTrueDeltaTime() {
        return Gdx.graphics.getRawDeltaTime();
    }

    /**
     * Check if a time passed is smaller than a given value in seconds. Adds up the time between
     * render calls. If the total is larger than a limit, then returns true and resets the accumulated time to zero.
     * Use for debugging and for reducing the frame drawing rate.
     *
     * @param timeLimit in seconds
     * @return boolean, false if accumulated time is smaller than the time limit.
     */
    public static boolean timeSinceLastFrameIsLargerThan(float timeLimit) {
        timeOfLastFrame += getTrueDeltaTime();
        if (timeOfLastFrame >= timeLimit) {
            timeOfLastFrame = 0;
            return true;
        }
        return false;
    }

    /**
     * Get a float[] array from an Array of Vector2 objects.
     *
     * @param vectors
     * @return float[], with the vectors as float x,y pairs.
     */
    static public float[] toFloats(Array<Vector2> vectors) {
        float[] result = new float[2 * vectors.size];
        int i = 0;
        for (Vector2 vector : vectors) {
            result[i++] = vector.x;
            result[i++] = vector.y;
        }
        return result;
    }

    /**
     * Create a float array that is a scaled copy of another float array.
     *
     * @param vertices
     * @param scale
     * @return float[], a scaled copy
     */
    static public float[] scaled(float[] vertices, float scale) {
        int length = vertices.length;
        float[] scaledVertices = new float[length];
        for (int i = 0; i < length; i++) {
            scaledVertices[i] = scale * vertices[i];
        }
        return scaledVertices;
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
        PixmapIO.writePNG(Basic.createExternalFileHandle(path),pixmap);
        pixmap.dispose();
    }
}
