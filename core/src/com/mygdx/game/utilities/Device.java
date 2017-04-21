package com.mygdx.game.utilities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
import com.mygdx.game.Pieces.SimpleTextSpriteExtension;
import com.mygdx.game.Sprite.ExtensibleSprite;

/**
 * Created by peter on 11/26/16.
 *
 * Standard resources: disposer and assetManager. Viewports,Renderers, framebuffer and default masterFont on demand
 * collecting objects that need to be resized
 */

public class Device implements Disposable{

    public Disposer disposer;
    public SpriteBatch spriteBatch;
    public BitmapFont bitmapFont;
    public Shape2DRenderer shape2DRenderer;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public AssetManager assetManager;
    public BasicAssets basicAssets;
    public TouchReader touchReader;
    public final Pool<ExtensibleSprite> extensibleSpritePool;
    public final Pool<SimpleTextSpriteExtension> simpleTextSpriteExtensionPool;

    public Array<Resizable> resizables=new Array<Resizable>();
    public Array<Viewport> viewports=new Array<Viewport>();

    /**
     * Always create an assetManager and basic assets
     */
    public Device(){
        disposer=new Disposer("Device");
        assetManager=new AssetManager();
        disposer.add(assetManager,"assetManager");
        basicAssets=new BasicAssets(this);
        touchReader=new TouchReader();
        addResizable(touchReader);
        extensibleSpritePool= Pools.get(ExtensibleSprite.class);
        simpleTextSpriteExtensionPool=Pools.get(SimpleTextSpriteExtension.class);
    }

    /**
     * set disposer logging on or off (default is off)
     * @param logging
     */
    public Device setLogging(boolean logging){
        disposer.setLogging(logging);
        return  this;
    }

    // keep track of everything to resize, and do resize on demand

    /**
     * add a resizable to the list of resizables
     * @param resizable
     */
    public void addResizable(Resizable resizable){
        resizables.add(resizable);
    }

    /**
     * add a viewport to the list of viewports to update
     * @param viewport
     */
    public void addViewport(Viewport viewport){
        viewports.add(viewport);
    }

    /**
     * resize the resizables and update viewports
     * call in resize
     * @param width
     * @param height
     */
    public void resize(int width,int height){
        for (Resizable resizable:resizables){
            resizable.resize(width, height);
        }
        for (Viewport viewport:viewports){
            viewport.update(width, height);
        }
    }

    // create viewports with their cameras

    /**
     * create an extended viewport, centered masterTextureRegion
     * with a supplied OrthographicCamera (followCamera ...)
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @param camera
     * @return
     */
    public Viewport createExtendViewport(float minWidth, float minHeight,OrthographicCamera camera){
        camera.setToOrtho(false,minWidth,minHeight);
        Viewport viewport=new ExtendViewport(minWidth,minHeight,camera);
        addViewport(viewport);
        return viewport;
    }

    /**
     * create an extended viewport with its own orthographic camera, centered masterTextureRegion
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @return
     */
    public Viewport createExtendViewport(float minWidth, float minHeight){
        return createExtendViewport(minWidth, minHeight, new OrthographicCamera());
    }

    /**
     *  create an fit viewport with a supplied camera, centered masterTextureRegion
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @param camera
     * @return
     */
    public Viewport createFitViewport(float minWidth, float minHeight, OrthographicCamera camera){
        camera.setToOrtho(false,minWidth,minHeight);
        Viewport viewport=new FitViewport(minWidth,minHeight,camera);
        addViewport(viewport);
        return viewport;
    }

    /**
     *  create an fit viewport with its own orthographic camera, centered masterTextureRegion
     *  resize: 		viewport.update(width, height);
     * @param minWidth
     * @param minHeight
     * @return
     */
    public Viewport createFitViewport(float minWidth, float minHeight){
        return createFitViewport(minWidth, minHeight,new OrthographicCamera());
    }
    // create renderer and related things

    /**
     * make spritebatch and add to disposer
     * @return Device, for chaining
     */
    public Device createSpriteBatch(){
        if (spriteBatch==null) {
            spriteBatch=new SpriteBatch();
            disposer.add(spriteBatch,"spriteBatch");
        }
        return  this;
    }

    /**
     * default bitmap masterFont for debugging
     * @return
     */
    public Device createDefaultBitmapFont(){
        if (bitmapFont==null) {
            bitmapFont = new BitmapFont();
            disposer.add(bitmapFont,"defaultBitmapFont");
        }
        return  this;
    }

    /**
     * shaperenderer for debugging
     * @return
     */
    public Device createShape2DRenderer(){
        if (shape2DRenderer==null) {
            shape2DRenderer=new Shape2DRenderer();
            disposer.add(shape2DRenderer,"shapeRenderer");
        }
        return  this;
    }

    /**
     * renderer for tiled maps
     * @param viewport
     * @param tiledMap
     */
    public void createOrthogonalTiledMapRenderer(Viewport viewport, TiledMap tiledMap){
        if (orthogonalTiledMapRenderer==null) {
            orthogonalTiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap, spriteBatch);
            viewport.apply(true);
            orthogonalTiledMapRenderer.setView((OrthographicCamera)viewport.getCamera());
            disposer.add(orthogonalTiledMapRenderer,"tileMapRenderer");
        }
    }

    /**
     * creates and returns a framebuffer with the same size as the screen
     * @param format
     * @return
     */
    public FrameBuffer createFrameBuffer(Pixmap.Format format){
        FrameBuffer frameBuffer= new FrameBuffer(format, Gdx.graphics.getWidth(),Gdx.graphics.getHeight(),false);
        disposer.add(frameBuffer,"frameBuffer");
        return  frameBuffer;
    }

    /**
     * framebuffer with default rgb format without transparency
     * @return
     */
    public FrameBuffer createFrameBuffer(){
        return createFrameBuffer(Pixmap.Format.RGB888);
    }

    /**
     * do not forget to call the disposer
     */
    @Override
    public void dispose(){
        disposer.dispose();
    }
}
