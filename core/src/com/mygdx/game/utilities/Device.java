package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 11/26/16.
 * <p>
 * Create and collect standard resources: disposer and assetManager. Viewports, Renderers, framebuffer and default masterFont on demand.
 * Collect objects that need to be resized.
 */

public class Device implements Disposable {

    public final Pool<ExtensibleSprite> extensibleSpritePool;
    public final Pool<GlyphLayout> glyphLayoutPool;
    public Disposer disposer;
    public SpriteBatch spriteBatch;
    public BitmapFont bitmapFont;
    public Shape2DRenderer shape2DRenderer;
    public AssetManager assetManager;
    public BasicAssets basicAssets;
    public TouchReader touchReader;
    public Array<Resizable> resizables = new Array<Resizable>();
    public Array<Viewport> viewports = new Array<Viewport>();
    public boolean soundIsOn=true;

    /**
     * Create an assetManager, pools and basic assets.
     */
    public Device() {
        disposer = new Disposer("Device");
        assetManager = new AssetManager();
        disposer.add(assetManager, "assetManager");
        basicAssets = new BasicAssets(this);
        touchReader = new TouchReader();
        addResizable(touchReader);
        extensibleSpritePool = Pools.get(ExtensibleSprite.class);
        glyphLayoutPool = Pools.get(GlyphLayout.class);
    }

    /**
     * Set logging of the disposer on or off (default is off).
     *
     * @param logging boolean, true to switch logging on
     */
    public Device setLogging(boolean logging) {
        disposer.setLogging(logging);
        return this;
    }

    // keep track of everything to resize, and do resize on demand

    /**
     * Add a resizable to the list of resizables.
     *
     * @param resizable
     */
    public void addResizable(Resizable resizable) {
        resizables.add(resizable);
    }

    /**
     * Add a viewport to the list of viewports to update.
     *
     * @param viewport
     */
    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
    }

    /**
     * Resize the resizables and update viewports
     * Call this in the main resize method.
     *
     * @param width
     * @param height
     */
    public void resize(int width, int height) {
        for (Resizable resizable : resizables) {
            resizable.resize(width, height);
        }
        for (Viewport viewport : viewports) {
            viewport.update(width, height);
        }
    }

    // create viewports with their cameras

    /**
     * Create an extended viewport, centered masterTextureRegion
     * with a supplied OrthographicCamera (followCamera ...).
     * Add to the list of viewports for resizing.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @param camera    OrthographicCamera
     * @return the ExtendViewport
     */
    public Viewport createExtendViewport(float minWidth, float minHeight, OrthographicCamera camera) {
        camera.setToOrtho(false, minWidth, minHeight);
        Viewport viewport = new ExtendViewport(minWidth, minHeight, camera);
        addViewport(viewport);
        return viewport;
    }

    /**
     * create an extended viewport and an orthographic camera, centered masterTextureRegion.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @return the ExtendViewport
     */
    public Viewport createExtendViewport(float minWidth, float minHeight) {
        return createExtendViewport(minWidth, minHeight, new OrthographicCamera());
    }

    /**
     * Create an fit viewport, centered masterTextureRegion
     * with a supplied OrthographicCamera (followCamera ...).
     * Add to the list of viewports for resizing.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @param camera
     * @return the FitViewport
     */
    public Viewport createFitViewport(float minWidth, float minHeight, OrthographicCamera camera) {
        camera.setToOrtho(false, minWidth, minHeight);
        Viewport viewport = new FitViewport(minWidth, minHeight, camera);
        addViewport(viewport);
        return viewport;
    }

    /**
     * Create a fit viewport with its own orthographic camera, centered masterTextureRegion
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @return the FitViewport
     */
    public Viewport createFitViewport(float minWidth, float minHeight) {
        return createFitViewport(minWidth, minHeight, new OrthographicCamera());
    }

    // create renderer and related things

    /**
     * Create a spritebatch and add it to the disposer.
     *
     * @return Device, for chaining
     */
    public Device createSpriteBatch() {
        if (spriteBatch == null) {
            spriteBatch = new SpriteBatch();
            disposer.add(spriteBatch, "device spriteBatch");
        }
        return this;
    }

    /**
     * Create a default bitmap masterFont for debugging. Add to the disposer.
     *
     * @return Device, for chaining
     */
    public Device createDefaultBitmapFont() {
        if (bitmapFont == null) {
            bitmapFont = new BitmapFont();
            disposer.add(bitmapFont, "device defaultBitmapFont");
        }
        return this;
    }

    /**
     * Create a shaperenderer for debugging. Add to disposer.
     *
     * @return Device, for chaining
     */
    public Device createShape2DRenderer() {
        if (shape2DRenderer == null) {
            shape2DRenderer = new Shape2DRenderer();
            disposer.add(shape2DRenderer, "device shapeRenderer");
        }
        return this;
    }

    /**
     * Create a renderer for a tiled map. Uses the device.spriteBatch and the camera of a given viewport.
     *
     * @param viewport
     * @param tiledMap
     * @return the OrthogonalTiledMapRenderer for given viewport and map
     */
    public OrthogonalTiledMapRenderer createOrthogonalTiledMapRenderer(Viewport viewport, TiledMap tiledMap) {
        OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
        orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
        viewport.apply(true);
        orthogonalTiledMapRenderer.setView((OrthographicCamera) viewport.getCamera());
        disposer.add(orthogonalTiledMapRenderer, "device tileMapRenderer");
        return orthogonalTiledMapRenderer;
    }

    /**
     * Creates a framebuffer with the same size as the screen.
     *
     * @param format Pixmap.Format for the FrameBuffer
     * @return the framebuffer
     */
    public FrameBuffer createFrameBuffer(Pixmap.Format format) {
        FrameBuffer frameBuffer = new FrameBuffer(format, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        disposer.add(frameBuffer, "frameBuffer");
        return frameBuffer;
    }

    /**
     * Create a framebuffer with 8 bit rgb format and no transparency.
     *
     * @return
     */
    public FrameBuffer createFrameBuffer() {
        return createFrameBuffer(Pixmap.Format.RGB888);
    }

    /**
     * Create an animation using an AtlasRegionArray.
     *
     * @param name String, name of the AtlasRegionArray
     * @param frameDuration float, duration of a frame in seconds
     * @param playMode Animation.PlayMode
     * @return Animation
     */
    public Animation createAnimation(String name, float frameDuration, Animation.PlayMode playMode) {
        return new Animation(frameDuration, basicAssets.getAtlasRegionArray(name), playMode);
    }

    /**
     * Create a particle effect using images in an atlas.
     *
     * @param effectName String, name of the effect file
     * @param atlasName  String, name of atlas with the images
     * @return
     */
    public ParticleEffect createParticleEffect(String effectName, String atlasName) {
        ParticleEffect particleEffect = new ParticleEffect();
        particleEffect.load(Gdx.files.internal(effectName + ".p"), basicAssets.getAtlas(atlasName));
        return particleEffect;
    }
    /**
     *
     * Switch sounds on or off.
     *
     * @param on, boolean, true to switch sound on
     */
    public void setSoundIsOn(boolean on){
        soundIsOn=on;
    }

    /**
     * play a sound if sound is on.
     *
     * @param name String, name of the sound without extension
     */
    public void playSound(String name){
        if (soundIsOn){
            basicAssets.getSound(name).play();
        }
    }




    /**
     * Calls the dispose method of the disposer. Call this in the final dispose.
     */
    @Override
    public void dispose() {
        disposer.dispose();
    }
}