package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Shape2D;
import com.mygdx.game.utilities.Device;

/**
 * Getting extensible sprites from the pool and setting their basic actions. No extensions.
 * Set position and angle later.
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
     * Create the builder with a device that has glyphLayout and sprite pools.
     * Set minimal default actions.
     *
     * @param device Device, with pools.
     */
    public ExtensibleSpriteBuilder(Device device){
        this.device=device;
        setContains(SpriteActions.containsTransRotate);
        setDraw(SpriteActions.draw);
        setKeepVisible(SpriteActions.keepVisibleNull);
        setTouchBegin(SpriteActions.touchBeginNull);
        setTouchEnd(SpriteActions.touchEndNull);
        setTouchDrag(SpriteActions.touchDragNull);
        setScroll(SpriteActions.scrollNull);
    }

    /**
     * Set the SpriteContains strategy for new sprites.
     *
     * @param spriteContains SpriteContains object with a contains method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setContains(SpriteContains spriteContains){
        masterContains = spriteContains;
        return this;
    }

    /**
     * Set the SpriteDraw strategy for new sprites.
     *
     * @param spriteDraw SpriteDraw object with a draw method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setDraw(SpriteDraw spriteDraw){
        masterDraw =spriteDraw;
        return  this;
    }

    /**
     * Set the SpriteKeepVisible strategy for new sprites.
     *
     * @param spriteKeepVisible SpriteKeepVisible object with a keepVisible method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setKeepVisible(SpriteKeepVisible spriteKeepVisible){
        masterKeepVisible =spriteKeepVisible;
        return this;
    }

    /**
     * Set the SpriteTouchBegin strategy for new sprites.
     *
     * @param spriteTouchBegin SpriteTouchBegin object with a touchBegin method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchBegin(SpriteTouchBegin spriteTouchBegin){
        masterTouchBegin =spriteTouchBegin;
        return this;
    }

    /**
     * Set the SpriteTouchDrag strategy for new sprites.
     * @param spriteTouchDrag SpriteTouchDrag object with a touchDrag method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchDrag(SpriteTouchDrag spriteTouchDrag){
        masterTouchDrag =spriteTouchDrag;
        return this;
    }

    /**
     * Set the SpriteTouchEnd strategy for new sprites.
     *
     * @param spriteTouchEnd SpriteTouchEnd object with a touchEnd method.
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setTouchEnd(SpriteTouchEnd spriteTouchEnd){
        masterTouchEnd =spriteTouchEnd;
        return this;
    }

    /**
     * Set the SpriteScroll strategy for new sprites
     *
     * @param spriteScroll SpriteScroll, object with a scroll method
     * @return this, for chaining
     */
    public ExtensibleSpriteBuilder setScroll(SpriteScroll spriteScroll){
        masterScroll =spriteScroll;
        return this;
    }

    /**
     * Set sprite actions for a translating rotating sprite. Keeping it visible.
     *
     * @return this, for chaining.
     */
    public ExtensibleSpriteBuilder setTransRotate(){
        setContains(SpriteActions.containsTransRotate);
        setKeepVisible(SpriteActions.keepVisibleOrigin);
        setDraw(SpriteActions.draw);
        setTouchBegin(SpriteActions.touchBeginNull);
        setTouchDrag(SpriteActions.touchDragTransRotate);
        setTouchEnd(SpriteActions.touchEndNull);
        setScroll(SpriteActions.scrollNull);
        return this;
    }

    /**
     * Set sprite actions for an only translating sprite. Keeping it visible.
     *
     * @return this, for chaining.
     */
    public ExtensibleSpriteBuilder setTranslate(){
        setContains(SpriteActions.containsTranslate);
        setKeepVisible(SpriteActions.keepVisibleOrigin);
        setDraw(SpriteActions.draw);
        setTouchBegin(SpriteActions.touchBeginNull);
        setTouchDrag(SpriteActions.touchDragTranslate);
        setTouchEnd(SpriteActions.touchEndNull);
        setScroll(SpriteActions.scrollNull);
        return this;
    }

    /**
     * Set sprite actions for a sprite that does not move.
     *
     * @return this, for chaining.
     */
    public ExtensibleSpriteBuilder setNoMovement(){
        setContains(SpriteActions.containsTranslate);
        setKeepVisible(SpriteActions.keepVisibleNull);
        setDraw(SpriteActions.draw);
        setTouchBegin(SpriteActions.touchBeginNull);
        setTouchDrag(SpriteActions.touchDragNull);
        setTouchEnd(SpriteActions.touchEndNull);
        setScroll(SpriteActions.scrollNull);
        return this;
    }

    /**
     * For the builders only. Set the sprite data.
     *
     * @param sprite ExtensibleSprite, or subtype, to set up.
     * @param textureRegion TextureRegion, image
     * @param shape Shape2D, shape
     */
    public void setup(ExtensibleSprite sprite, TextureRegion textureRegion, Shape2D shape){
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
        sprite.setScroll(masterScroll);
    }

    /**
     * Obtain an extensible sprite from its pool, with given texture region and shape2d shape.
     *
     * @param textureRegion TextureRegion, image
     * @param shape Shape2D, shape
     * @return ExtensibleSprite, the sprite
     */
    public ExtensibleSprite build(TextureRegion textureRegion, Shape2D shape){
        ExtensibleSprite sprite=device.extensibleSpritePool.obtain();
        setup(sprite,textureRegion,shape);
        return sprite;
    }

    /**
     * Build a sprite with given texture region, but without shape.
     *
     * @param textureRegion TextureRegion, image
     * @return ExtensibleSprite, the sprite
     */
    public ExtensibleSprite build(TextureRegion textureRegion){
        return build(textureRegion,null);
    }
}
