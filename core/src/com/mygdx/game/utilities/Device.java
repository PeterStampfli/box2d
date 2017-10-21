package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
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
import com.badlogic.gdx.math.EarClippingTriangulator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Buttons.ButtonBuilder;
import com.mygdx.game.Images.Shape2DRenderer;
import com.mygdx.game.Pieces.TouchMover;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.ExtensibleSpriteBuilder;

/**
 * Created by peter on 11/26/16.
 * <p>
 * Create and collect standard resources: disposer and assetManager. Viewports, renderers, framebuffer and default masterFont on demand.
 * Collect objects that need to be resized.
 */

public class Device implements Disposable,Resizable {

    public final Pool<ExtensibleSprite> extensibleSpritePool;
    public final Pool<GlyphLayout> glyphLayoutPool;
    public Disposer disposer;
    public SpriteBatch spriteBatch;
    public BitmapFont bitmapFont;
    public Shape2DRenderer shape2DRenderer;
    public EarClippingTriangulator triangulator;
    public Accelerometer accelerometer;
    public AssetManager assetManager;
    public BasicAssets basicAssets;
    public Camera camera;
    public TouchReader touchReader;
    public TouchMover touchMover;
    public ExtensibleSpriteBuilder extensibleSpriteBuilder;
    public ButtonBuilder buttonBuilder;
    public Array<Resizable> resizables = new Array<Resizable>();
    public Array<Viewport> viewports = new Array<Viewport>();
    public boolean soundIsOn=true;
    private Vector3 vector3=new Vector3();

    /**
     * Create with an assetManager, spriteBatch, pools and basic assets.
     */
    public Device() {
        disposer = new Disposer("Device");
        spriteBatch = new SpriteBatch();
        disposer.add(spriteBatch, "device spriteBatch");
        assetManager = new AssetManager();
        disposer.add(assetManager, "assetManager");
        basicAssets = new BasicAssets(this);
        touchReader = new TouchReader(this);
        addResizable(touchReader);
        touchMover=new TouchMover(this);
        extensibleSpriteBuilder=new ExtensibleSpriteBuilder(this);
        buttonBuilder=new ButtonBuilder(extensibleSpriteBuilder);
        extensibleSpritePool = Pools.get(ExtensibleSprite.class);
        glyphLayoutPool = Pools.get(GlyphLayout.class);
    }

    /**
     * Set logging of the disposer on.
     *
     * @return  this, for chaining
     */
    public Device logging() {
        disposer.logging();
        return this;
    }

    /**
     * Set the camera for un-projecting (TouchReader). Call in show() method of screens. Or in render-update if using more than one camera/viewport.
     *
     * @param camera Camera
     */
    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    /**
     * Set the camera for un-projecting (TouchReader), using the camera of a viewport. Call in show() method of screens.
     *
     * @param viewport Viewport, with camera for unprojecting
     */
    public void setCamera(Viewport viewport) {
        this.camera = viewport.getCamera();
    }

    /**
     * Un-project a screen position as defined by the current camera.
     * Screen coordinates are inverted. The upper left corner is at (0,0).
     * Coordinates are in pixels.
     * Un-projected coordinates are upright. For an extendViewport the lower left corner is at (0,0)
     * and the upper right corner is at its (worldWidth,worldHeight)
     *
     * @param position Vector2
     * @return Vector2, the unprojected position
     */
    public Vector2 unproject(Vector2 position){
        vector3.set(position.x, position.y, 0f);
        camera.unproject(vector3);
        return position.set(vector3.x, vector3.y);
    }

    // keep track of everything to resize, and do resize on demand

    /**
     * Add a resizable to the list of resizables.
     *
     * @param resizable Resizable object
     */
    public void addResizable(Resizable resizable) {
        resizables.add(resizable);
    }

    /**
     * Add a viewport to the list of viewports to update.
     *
     * @param viewport ViewPort
     */
    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
    }

    /**
     * First update viewports and then Resize the resizables.
     * (the resizables might depend on viewports)
     * Call this in the main resize method.
     *
     * @param width int, width of screen
     * @param height int, height of screen
     */
    public void resize(int width, int height) {
        for (Viewport viewport : viewports) {
            viewport.update(width, height);
        }
        for (Resizable resizable : resizables) {
            resizable.resize(width, height);
        }
    }

    // create viewports with their cameras

    /**
     * Create an extended viewport
     * with a supplied OrthographicCamera (followCamera ...). Sets the camera to center of minimum world region.
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
     * create an extended viewport with a new orthographic camera. Sets the camera to center of minimum world region.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @return the ExtendViewport
     */
    public Viewport createExtendViewport(float minWidth, float minHeight) {
        return createExtendViewport(minWidth, minHeight, new OrthographicCamera());
    }

    /**
     * Create a fit viewport. Sets the camera to center of minimum world region.
     * with a supplied OrthographicCamera (followCamera ...).
     * Add to the list of viewports for resizing.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @param camera OrthographicCamera, for the viewport
     * @return the FitViewport
     */
    public Viewport createFitViewport(float minWidth, float minHeight, OrthographicCamera camera) {
        camera.setToOrtho(false, minWidth, minHeight);
        Viewport viewport = new FitViewport(minWidth, minHeight, camera);
        addViewport(viewport);
        return viewport;
    }

    /**
     * Create a fit viewport with its own orthographic camera. Sets the camera to center of minimum world region.
     *
     * @param minWidth  float, minimum width of the camera viewport in virtual pixels.
     * @param minHeight float, minimum height of the camera viewport in virtual pixels.
     * @return the FitViewport
     */
    public Viewport createFitViewport(float minWidth, float minHeight) {
        return createFitViewport(minWidth, minHeight, new OrthographicCamera());
    }

    /**
     * Create a default bitmap masterFont for debugging. Add to the disposer.
     *
     * @return the bitmapFont
     */
    public BitmapFont createDefaultBitmapFont() {
        if (bitmapFont == null) {
            bitmapFont = new BitmapFont();
            disposer.add(bitmapFont, "device defaultBitmapFont");
        }
        return bitmapFont;
    }

    /**
     * Create an extended shapeRenderer for debugging shape2D's. Add to disposer.
     *
     * @return the shape2DRenderer
     */
    public Shape2DRenderer createShape2DRenderer() {
        if (shape2DRenderer == null) {
            shape2DRenderer = new Shape2DRenderer();
            disposer.add(shape2DRenderer, "device shapeRenderer");
        }
        return shape2DRenderer;
    }

    /**
     * create a new earclipping triangulator if it does not yet exist
     *
     * @return the triangulator
     */
    public EarClippingTriangulator createTriangulator(){
        if (triangulator==null){
            triangulator=new EarClippingTriangulator();
        }
        return triangulator;
    }

    /**
     * create a new accelerometer if it does not exist. add to list of resizables
     *
     * @return accelerometer
     */
    public Accelerometer createAccelerometer(){
        if (accelerometer==null){
            accelerometer=new Accelerometer();
            addResizable(accelerometer);
        }
        return accelerometer;
    }

    /**
     * Create a renderer for a tiled map. Uses the device.spriteBatch and the camera of a given viewport.
     *
     * @param viewport Viewport, for rendering
     * @param tiledMap TiledMap, the map
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
     * @return FrameBuffer
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
     * @return ParticleEffect
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
     * Calls finalization and garbage collection.
     */
    @Override
    public void dispose() {
        disposer.dispose();
    }
}