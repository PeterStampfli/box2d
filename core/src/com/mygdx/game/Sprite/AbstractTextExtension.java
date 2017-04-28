package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utilities.L;

/**
 * Created by peter on 4/22/17.
 */

public abstract class AbstractTextExtension extends SpriteDrawDecorator {
    public Pool<GlyphLayout> glyphLayoutPool;
    public GlyphLayout glyphLayout;
    public BitmapFont font;


    public static class Builder{
        public AbstractTextExtension build(Pool<GlyphLayout> glyphLayoutPool, BitmapFont font, ExtensibleSprite sprite){
            L.og("tada");
            return null;
        }

    }

    /**
     * Create the extension with a glyphLayout pool and font. Attach to a sprite.
     *
     * @param glyphLayoutPool GlyphLayoutPool
     * @param font BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */
    public AbstractTextExtension(Pool<GlyphLayout> glyphLayoutPool, BitmapFont font, ExtensibleSprite sprite){
        super(sprite);
        this.glyphLayoutPool=glyphLayoutPool;
        glyphLayout=glyphLayoutPool.obtain();
        this.font=font;
    }

    /**
     * Set the text, depends on actual kind of text, its layout and the dimensions of sprite.
     *
     * @param text String, the text to show.
     * @param sprite ExtensibleSprite, the text layout depends on the dimensions of the sprite.
     */
    public abstract void setText(String text,ExtensibleSprite sprite);

    /**
     * Frees the glyphLayout (same for all text extensions).
     * GlyphLayout is Poolable and has its reset method!!!
     * Gets called on freeing the sprite.
     */
    public void free(){
        glyphLayoutPool.free(glyphLayout);
    }
}
