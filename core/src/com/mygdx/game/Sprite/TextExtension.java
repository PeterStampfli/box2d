package com.mygdx.game.Sprite;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by peter on 4/22/17.
 */

public abstract class TextExtension implements SpriteDraw {
    public Pool<GlyphLayout> glyphLayoutPool;
    public GlyphLayout glyphLayout;
    public BitmapFont font;

    /**
     * to create we need glyphlayout pool and font
     * @param glyphLayoutPool
     * @param font
     */
    public TextExtension(Pool<GlyphLayout> glyphLayoutPool,BitmapFont font,ExtensibleSprite sprite){
        this.glyphLayoutPool=glyphLayoutPool;
        glyphLayout=glyphLayoutPool.obtain();
        this.font=font;
        applyTo(sprite);
    }

    /**
     * overwrite the draw method of the sprite
     * maybe other methods have to be overwritten too
     * to implement the decorator pattern do:
     * in declarations:
     * ...................
     * SpriteDraw previousDraw;
     * .................
     * in applyTo():
     * .......
     * previousDraw=sprite.spriteDraw;
     * sprite.setDraw(this);
     * .........
     * in draw(sprite, batch):
     * .......
     * previousDraw.draw(sprite,batch);
     * ......
     * @param sprite
     */
    public void applyTo(ExtensibleSprite sprite){
        sprite.setDraw(this);
    }

    /**
     * set the text, depends on actual kind of text, its layout (same as draw)
     * and dimensions of sprite
     * @param text
     */
    public abstract void setText(String text,ExtensibleSprite sprite);

    /**
     * reset all and free the glyphlayout (same for all text extensions)
     * GlyphLayout is poolable !!!
     */
    public void free(){
        glyphLayoutPool.free(glyphLayout);
    }


}
