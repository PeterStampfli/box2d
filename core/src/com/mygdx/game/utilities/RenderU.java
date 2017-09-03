package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * rendering  utilities
 */

public class RenderU {
    private static int numberOfRenderCalls;

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
     * set the projection (matrix) of a batch based on a viewport
     *
     * @param batch Batch, SpriteBatch,, set its projection matrix
     * @param viewport Viewport, now in use, determines the projection matrix
     */
    public static void setProjection(Batch batch, Viewport viewport){
        batch.setProjectionMatrix(viewport.getCamera().combined);
    }

    /**
     * Set the camera position to the center of its visible world dimensions.
     * Makes that the camera sees the point (0,0) at its lower left corner.
     *
     * @param camera Camera
     */
    public static void center(Camera camera){
        camera.position.set(0.5f*camera.viewportWidth,0.5f*camera.viewportHeight,0);
    }

    /**
     * Set the viewport camera to the center of the viewports visible world dimensions. For extendViewport
     * the left bottom corner of the screen will have coordinates (0,0)
     *
     * @param viewport Viewport, set its camera
     */
    public static void center(Viewport viewport){
        center(viewport.getCamera());
    }

    /**
     * Fill the screen with a background image, adjusted to fill.
     * Clear background and Call batch.begin() before and set tint.
     *
     * @param batch SpriteBatch, for drawing
     * @param viewport Viewport, in use now
     * @param img TextureRegion, with the image for filling the screen
     */
    public static void fillScreen(SpriteBatch batch, Viewport viewport, TextureRegion img){
        float scale=Math.max(viewport.getWorldWidth()/img.getRegionWidth(),
                            viewport.getWorldHeight()/img.getRegionHeight());
        batch.draw(img,0,0,scale*img.getRegionWidth(),scale*img.getRegionHeight());
    }
}
