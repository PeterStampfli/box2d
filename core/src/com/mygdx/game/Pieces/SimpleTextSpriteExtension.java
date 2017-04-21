package com.mygdx.game.Pieces;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

/**
 * Created by peter on 4/20/17.
 * extend a sprite to draw a simple short centered text
 */

public class SimpleTextSpriteExtension implements com.mygdx.game.Sprite.SpriteDraw {
    public GlyphLayout glyphLayout = new GlyphLayout();
    public BitmapFont font;
    public com.mygdx.game.Sprite.ExtensibleSprite sprite;

    /**
     * create a simple text extension with masterFont
     * @param font
     */
    public SimpleTextSpriteExtension(BitmapFont font){
        this.font=font;
    }

    /**
     * set the text of the sprite using its glyphLayout
     *
     * @param text
     */
    public void setText(String text) {
        glyphLayout.setText(font, text);
    }

    /**
     * draw the sprite and then the text
     * @param sprite
     * @param batch
     */
    @Override
    public void draw(com.mygdx.game.Sprite.ExtensibleSprite sprite, Batch batch){
        sprite.superDraw(batch);
        font.draw(batch, glyphLayout, sprite.getWorldOriginX() - glyphLayout.width * 0.5f,
                sprite.getWorldOriginY() + glyphLayout.height * 0.5f);
    }
}

