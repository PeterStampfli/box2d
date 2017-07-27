package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * A decorator for adding an effect to spriteTouchBegin
 */

public class SpriteTouchBeginEffect implements SpriteTouchBegin {
    SpriteTouchBegin previousTouchBegin;
    SpriteAct effect;

    /**
     * Create a SpriteTouchBegin object with an effect and add it as a decoration to a sprite.
     *
     * @param sprite ExtensibleSprite, to which we attach the effect
     * @param effect SpriteEffect
     */
    public SpriteTouchBeginEffect(ExtensibleSprite sprite, SpriteAct effect){
        previousTouchBegin=sprite.getTouchBegin();
        sprite.setTouchBegin(this);
        this.effect=effect;
    }

    /**
     * Do first the other touchBegin actions of the sprite and then make the effect.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     * @return true, if something changed
     */
    public boolean touchBegin(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position){
        boolean changed=previousTouchBegin.touchBegin(sprite,position);
        if (effect.act(sprite)) {
            changed = true;
        }
        return changed;
    }
}
