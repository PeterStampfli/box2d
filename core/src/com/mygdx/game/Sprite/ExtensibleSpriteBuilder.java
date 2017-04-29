package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.utilities.Device;

/**
 * Getting extensible sprites from the pool and setting their basic actions. No extensions.
 */

public class ExtensibleSpriteBuilder {
    public Device device;
    public SpriteContains masterContains;
    public SpriteDraw masterDraw;
    public SpriteKeepVisible masterKeepVisible;
    public SpriteTouchBegin masterTouchBegin;
    public SpriteTouchDrag masterTouchDrag;
    public SpriteTouchEnd masterTouchEnd;
    public SpriteScroll masterScroll;

    /**
     * Create with a device that has glyphlayout and sprite pools.
     * Set minimal actions.
     *
     * @param device Device, with pools.
     */
    public ExtensibleSpriteBuilder(Device device){
        this.device=device;
        setContains(SpriteActions.shapeContains);
        setDraw(SpriteActions.simpleDraw);
        setKeepVisible(SpriteActions.nullKeepVisible);
        setTouchBegin(SpriteActions.nullTouchBegin);
        setTouchEnd(SpriteActions.nullTouchEnd);
        setTouchDrag(SpriteActions.nullTouchDrag);
        setScroll(SpriteActions.nullScroll);
    }

    /**
     * Set the SpriteContains object for new sprites.
     *
     * @param spriteContains SpriteContains, object with contains method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setContains(SpriteContains spriteContains){
        masterContains = spriteContains;
        return this;
    }

    /**
     * set the master method for sprite draw
     * @param spriteDraw
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setDraw(SpriteDraw spriteDraw){
        masterDraw =spriteDraw;
        return  this;
    }

    /**
     * set master method for keep visible
     * @param spriteKeepVisible
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setKeepVisible(SpriteKeepVisible spriteKeepVisible){
        masterKeepVisible =spriteKeepVisible;
        return this;
    }

    /**
     * select the draw for touch begin
     * @param spriteTouchBegin
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchBegin(SpriteTouchBegin spriteTouchBegin){
        masterTouchBegin =spriteTouchBegin;
        return this;
    }

    /**
     * set the draw for touch drag
     * @param spriteTouchDrag
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchDrag(SpriteTouchDrag spriteTouchDrag){
        masterTouchDrag =spriteTouchDrag;
        return this;
    }

    /**
     * set the master for touch begin
     * @param spriteTouchEnd
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchEnd(SpriteTouchEnd spriteTouchEnd){
        masterTouchEnd =spriteTouchEnd;
        return this;
    }

    /**
     * set the master for scroll
     * @param spriteScroll
     * @return this, for chaining
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
        ExtensibleSprite sprite=device.extensibleSpritePool.obtain();
        sprite.setRegion(textureRegion);
        sprite.setColor(Color.WHITE);
        sprite.setSize(textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        sprite.setOrigin(textureRegion.getRegionWidth() / 2, textureRegion.getRegionHeight() / 2);
        sprite.shape=shape;
        sprite.device=device;
        sprite.setContains(masterContains);
        sprite.setKeepVisible(masterKeepVisible);
        sprite.setDraw(masterDraw);
        sprite.setTouchBegin(masterTouchBegin);
        sprite.setTouchDrag(masterTouchDrag);
        sprite.setTouchEnd(masterTouchEnd);
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
}
