package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 4/22/17.
 */

public abstract class TextExtension extends SpriteDrawDecorator {
    public Pool<GlyphLayout> glyphLayoutPool;
    public GlyphLayout glyphLayout;
    public BitmapFont font;

    /**
     * Create the extension with a glyphLayout pool and font. Attach to a sprite.
     * Set it as the sprites textExtension.
     *
     * @param device Device with its glyphLayoutPool
     * @param font BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */
    public TextExtension(Device device, BitmapFont font, ExtensibleSprite sprite){
        super(sprite);
        sprite.textExtension=this;
        this.glyphLayoutPool=device.glyphLayoutPool;
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
