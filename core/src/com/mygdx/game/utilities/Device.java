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
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Images.Shape2DRenderer;

/**
 * Created by peter on 11/26/16.
 *
 * Standard resources: disposer and assetManager. Renderers, framebuffer and default font on demand
 */

public class Device implements Disposable{

    public Disposer disposer;
    public SpriteBatch spriteBatch;
    public BitmapFont bitmapFont;
    public Shape2DRenderer shape2DRenderer;
    public OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    public AssetManager assetManager;
    public BasicAssets basicAssets;

    /**
     * Always create an assetManager and basic assets
     */
    public Device(){
        disposer=new Disposer("Device");
        assetManager=new AssetManager();
        disposer.add(assetManager,"assetManager");
        basicAssets=new BasicAssets(this);
    }

    /**
     * set disposer logging on or off (default is off)
     * @param logging
     */
    public void setLogging(boolean logging){
        disposer.setLogging(logging);
    }

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
     * default bitmap font for debugging
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
