package com.mygdx.game.Sprite;

/**
 * An empty decorator for SpriteDraw objects. Typically draws first the sprite as before
 * with "previousDraw.draw(sprite,batch,camera);" and then draws something extra.
 */

abstract public class SpriteDrawDecorator implements SpriteDraw {
    SpriteDraw previousDraw;

    /**
     * Apply the decorator pattern to the draw method of a sprite.
     *
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */
    public SpriteDrawDecorator(ExtensibleSprite sprite){
        previousDraw=sprite.spriteDraw;
        sprite.setDraw(this);
    }
}
