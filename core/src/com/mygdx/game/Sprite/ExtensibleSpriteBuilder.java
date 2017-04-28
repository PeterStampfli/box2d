package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 4/21/17.
 */

public class ExtensibleSpriteBuilder {
    public Pool<ExtensibleSprite> extensibleSpritePool;
    public Pool<GlyphLayout> glyphLayoutPool;
    public BitmapFont font;
    public TextureRegion masterTextureRegion;
    public Shape2D masterShape;
    public NullTextExtension TextExtension;
    public SpriteContains masterContains;
    public SpriteDraw masterDraw;
    public SpriteKeepVisible masterKeepVisible;
    public SpriteTouchBegin masterTouchBegin;
    public SpriteTouchDrag masterTouchDrag;
    public SpriteTouchEnd masterTouchEnd;
    public SpriteScroll masterScroll;

    /**
     * to create we need to know the pools (device)
     * font is independent
     * set defaults for the actions...
     * basic drawing only, else nothing
     * @param device
     * @param font
     */
    public ExtensibleSpriteBuilder(Device device,BitmapFont font){
        this.extensibleSpritePool=device.extensibleSpritePool;
        this.glyphLayoutPool=device.glyphLayoutPool;
        this.font=font;
        setTextExtension(null);
        setContains(SpriteActions.shapeContains);
        setDraw(SpriteActions.simpleDraw);
        setKeepVisible(SpriteActions.nullKeepVisible);
        setTouchBegin(SpriteActions.nullTouchBegin);
        setTouchEnd(SpriteActions.nullTouchEnd);
        setTouchDrag(SpriteActions.nullTouchDrag);
        setScroll(SpriteActions.nullScroll);
    }

    /**
     * set the texture region default
     * @param textureRegion
     * @return
     */
    public ExtensibleSpriteBuilder setTextureRegion(TextureRegion textureRegion){
        masterTextureRegion =textureRegion;
        return this;
    }

    /**
     * set the default shape
     * @param shape
     * @return
     */
    public ExtensibleSpriteBuilder setShape(Shape2D shape){
        masterShape=shape;
        return this;
    }

    /**
     * set the master type for text extension
     * @param textExtension
     * @return
     */
    public ExtensibleSpriteBuilder setTextExtension(NullTextExtension textExtension){
        masterTextExtension=textExtension;
        return this;
    }

    /**
     * set the master method for contains the point
     * @param spriteContains
     * @return
     */
    public ExtensibleSpriteBuilder setContains(SpriteContains spriteContains){
        masterContains = spriteContains;
        return this;
    }

    /**
     * set the master method for sprite draw
     * @param spriteDraw
     * @return
     */
    public ExtensibleSpriteBuilder setDraw(SpriteDraw spriteDraw){
        masterDraw =spriteDraw;
        return  this;
    }

    /**
     * set master method for keep visible
     * @param spriteKeepVisible
     * @return
     */
    public ExtensibleSpriteBuilder setKeepVisible(SpriteKeepVisible spriteKeepVisible){
        masterKeepVisible =spriteKeepVisible;
        return this;
    }

    /**
     * select the draw for touch begin
     * @param spriteTouchBegin
     * @return
     */
    public ExtensibleSpriteBuilder setTouchBegin(SpriteTouchBegin spriteTouchBegin){
        masterTouchBegin =spriteTouchBegin;
        return this;
    }

    /**
     * set the draw for touch drag
     * @param spriteTouchDrag
     * @return
     */
    public ExtensibleSpriteBuilder setTouchDrag(SpriteTouchDrag spriteTouchDrag){
        masterTouchDrag =spriteTouchDrag;
        return this;
    }

    /**
     * set the master for touch begin
     * @param spriteTouchEnd
     * @return
     */
    public ExtensibleSpriteBuilder setTouchEnd(SpriteTouchEnd spriteTouchEnd){
        masterTouchEnd =spriteTouchEnd;
        return this;
    }

    /**
     * set the master for scroll
     * @param spriteScroll
     * @return
     */
    public ExtensibleSpriteBuilder setScroll(SpriteScroll spriteScroll){
        masterScroll =spriteScroll;
        return this;
    }

    /**
     * obtain an extensible sprite from its pool, with given texture region and shape2d masterShape
     * use master methods
     * @param textureRegion
     * @param shape
     * @return
     */
    public ExtensibleSprite build(TextureRegion textureRegion, Shape2D shape){
        ExtensibleSprite sprite=extensibleSpritePool.obtain();
        sprite.setRegion(textureRegion);
        sprite.setColor(Color.WHITE);
        sprite.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        sprite.setOrigin(textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2);
        sprite.shape=shape;
        sprite.extensibleSpritePool=extensibleSpritePool;
        sprite.setContains(masterContains);
        sprite.setKeepVisible(masterKeepVisible);
        sprite.setDraw(masterDraw);
        sprite.setTouchBegin(masterTouchBegin);
        sprite.setTouchDrag(masterTouchDrag);
        sprite.setTouchEnd(masterTouchEnd);
        sprite.textExtension=masterTextExtension;
        return sprite;
    }


    /**
     * sprite with given texture regio, but no masterShape
     * @param textureRegion
     * @return
     */
    public ExtensibleSprite build(TextureRegion textureRegion){
        return build(textureRegion,null);
    }

    /**
     * sprite with set textur region masterTextureRegion and set masterShape
     * @return
     */
    public ExtensibleSprite build(){
        return build(masterTextureRegion, masterShape);
    }
}
