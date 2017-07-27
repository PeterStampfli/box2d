package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * A decorator for adding an effect to spriteTouchEnd
 */

public class SpriteTouchEndEffect implements SpriteTouchEnd {
    SpriteTouchEnd previousTouchEnd;
    SpriteAct effect;

    /**
     * Create a SpriteTouchEnd object with an effect and add it as a decoration to a sprite.
     *
     * @param sprite ExtensibleSprite, to which we attach the effect
     * @param effect SpriteEffect
     */
    public SpriteTouchEndEffect(ExtensibleSprite sprite, SpriteAct effect){
        previousTouchEnd=sprite.getTouchEnd();
        sprite.setTouchEnd(this);
        this.effect=effect;
    }

    /**
     * Do first the other touchEnd actions of the sprite and then make the effect.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     * @return true, if something changed
     */
    public boolean touchEnd(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position){
        boolean changed=previousTouchEnd.touchEnd(sprite,position);
        if (effect.act(sprite)) {
            changed = true;
        }
        return changed;
    }
}
