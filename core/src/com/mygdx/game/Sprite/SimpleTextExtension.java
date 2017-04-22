package com.mygdx.game.Sprite;

/**
 * Created by peter on 4/22/17.
 */

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by peter on 4/20/17.
 * extend a sprite to draw a simple short centered text
 */

public class SimpleTextExtension extends TextExtension {

    /**
     * to create we need glyphlayout pool and font
     * @param glyphLayoutPool
     * @param font
     */
    public SimpleTextExtension(Pool<GlyphLayout> glyphLayoutPool, BitmapFont font,ExtensibleSprite sprite) {
        super(glyphLayoutPool, font, sprite);
    }

    /**
     * set the text of the sprite using its glyphLayout
     * @param text
     */
    @Override
    public void setText(String text,ExtensibleSprite sprite) {
        glyphLayout.setText(font, text);
    }

    /**
     * draw the sprite and then the text
     * @param sprite
     * @param batch
     */
    @Override
    public void draw(ExtensibleSprite sprite, Batch batch){
        sprite.superDraw(batch);
        font.draw(batch, glyphLayout, sprite.getWorldOriginX() - glyphLayout.width * 0.5f,
                sprite.getWorldOriginY() + glyphLayout.height * 0.5f);
    }
}