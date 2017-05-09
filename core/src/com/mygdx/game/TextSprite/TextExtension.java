package com.mygdx.game.TextSprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteDraw;
import com.mygdx.game.utilities.Device;

/**
 * Created by peter on 4/22/17.
 */

public abstract class TextExtension implements SpriteDraw {
    public Pool<GlyphLayout> glyphLayoutPool;
    public GlyphLayout glyphLayout;
    public BitmapFont font;
    SpriteDraw previousDraw;

    /**
     * Create the extension with a glyphLayout pool and font. Attach to a sprite.
     * Set it as the sprites textExtension.
     *
     * @param device Device with its glyphLayoutPool
     * @param font BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */
    public TextExtension(Device device, BitmapFont font, ExtensibleSprite sprite){
        previousDraw=sprite.spriteDraw;
        sprite.setDraw(this);
        sprite.textExtension=this;
        glyphLayoutPool=device.glyphLayoutPool;
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
     * Frees the glyphLayout. GlyphLayout has its reset method that deletes the text.
     */
    public void free(){
        glyphLayoutPool.free(glyphLayout);
    }
}
