package com.mygdx.game.Sprite;

import com.badlogic.gdx.math.Vector2;

/**
 * A decorator for adding an effect to spriteTouchDrag
 */

public class SpriteTouchDragEffect implements SpriteTouchDrag {
    SpriteTouchDrag  previousTouchDrag;
    SpriteAct effect;

    /**
     * Create a SpriteTouchDrag object with an effect and add it as a decoration to a sprite.
     *
     * @param sprite ExtensibleSprite, to which we attach the effect
     * @param effect SpriteEffect
     */
    public SpriteTouchDragEffect(ExtensibleSprite sprite, SpriteAct effect){
        previousTouchDrag=sprite.getTouchDrag();
        sprite.setTouchDrag(this);
        this.effect=effect;
    }

    /**
     * Do first the other touchDrag actions of the sprite and then make the effect.
     *
     * @param sprite   ExtensibleSprite
     * @param position Vector2, position of touch
     * @return true, if something changed
     */
    public boolean touchDrag(com.mygdx.game.Sprite.ExtensibleSprite sprite, Vector2 position,Vector2 deltaPosition){
        boolean changed=previousTouchDrag.touchDrag(sprite,position,deltaPosition);
        if (effect.act(sprite)) {
            changed = true;
        }
        return changed;
    }
}
