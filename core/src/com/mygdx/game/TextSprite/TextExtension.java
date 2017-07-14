package com.mygdx.game.TextSprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.game.Sprite.ExtensibleSprite;
import com.mygdx.game.Sprite.SpriteDraw;

/**
 * Created by peter on 4/22/17.
 */

public abstract class TextExtension implements SpriteDraw {
    public Pool<GlyphLayout> glyphLayoutPool;
    public GlyphLayout glyphLayout;
    public BitmapFont font;
    ExtensibleSprite sprite;
    SpriteDraw basicSpriteDraw;

    /**
     * Create the extension with a glyphLayout pool and font. Attach to a sprite.
     * Set it as the sprites textExtension.
     *  @param font BitmapFont
     * @param sprite ExtensibleSprite, the text will be attached to this sprite
     */
    public TextExtension(BitmapFont font, ExtensibleSprite sprite){
        basicSpriteDraw=sprite.spriteDraw;
        this.sprite=sprite;
        sprite.setDraw(this);
        sprite.textExtension=this;
        glyphLayoutPool=sprite.device.glyphLayoutPool;
        glyphLayout=glyphLayoutPool.obtain();
        this.font=font;
    }

    /**
     * draw the basic sprite without the button extension addons
     */
    public void doBasicSpriteDraw(){
        basicSpriteDraw.draw(sprite);
    }


    /**
     * Set the text, depends on actual kind of text, its layout and the dimensions of sprite.
     *  @param text String, the text to show.
     *
     */
    public abstract void setText(String text);

    /**
     * Frees the glyphLayout. GlyphLayout has its reset method that deletes the text.
     */
    public void free(){
        glyphLayoutPool.free(glyphLayout);
    }
}
